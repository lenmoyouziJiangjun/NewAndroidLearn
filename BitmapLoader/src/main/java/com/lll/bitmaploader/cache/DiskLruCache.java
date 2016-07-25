package com.lll.bitmaploader.cache;

import android.content.SharedPreferences;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.security.Key;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Version 1.0
 * Created by lll on 16/7/7.
 * Description
 * copyright generalray4239@gmail.com
 */
public final class DiskLruCache implements Closeable {
    static final String JOURNAL_FILE = "imgCache";
    static final String JOURNAL_FILE_TMP = "imgCache.tmp";
    static final String MAGIC = "lll.io.DiskLruCache";
    static final String VERSION_1 = "1";
    static final long ANY_SEQUENCE_NUMBER = -1;
    /*文件状态*/
    private static final String CLEAN = "CLEAN";
    private static final String DIRTY = "DIRTY";
    private static final String REMOVE = "REMOVE";
    private static final String READ = "READ";

    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final int IO_BUFFER_SIZE = 8 * 1024;

    private final File directory;
    private final File journalFile;
    private final File journalFileTmp;
    private final int appVersion;
    private final long maxSize;
    private final int valueCount;
    private long size = 0;
    private Writer journalWriter;

    /**
     * 定义缓存集合
     */
    private final LinkedHashMap<String, Entry> lruEntries = new LinkedHashMap<>(0, 0.75f, true);

    private int redundantOpCount;

    /**
     * This cache uses a single background thread to evict entries.
     */
    private final ExecutorService executorService = new ThreadPoolExecutor(0, 1, 60l, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

    /**
     * 情况缓存的异步逻辑
     */
    private final Callable<Void> cleanupCallable = new Callable<Void>() {
        @Override
        public Void call() throws Exception {
            synchronized (DiskLruCache.this) {
                if (journalWriter == null) {
                    return null;
                }
                trimToSize();
                if (journalRebuildRequired()) {
                    rebuildJournal();
                    redundantOpCount = 0;
                }
            }
            return null;
        }
    };

    /**
     * To differentiate between old and current snapshots, each entry is given
     * a sequence number each time an edit is committed. A snapshot is stale if
     * its sequence number is not equal to its entry's sequence number.
     */
    private long nextSequenceNumber = 0;


    /**
     * @param directory  缓存目录
     * @param appVersion
     * @param valueCount
     * @param maxSize    缓存文件夹最大大小
     */
    private DiskLruCache(File directory, int appVersion, int valueCount, long maxSize) {
        this.directory = directory;
        this.appVersion = appVersion;
        this.journalFile = new File(directory, JOURNAL_FILE);
        this.journalFileTmp = new File(directory, JOURNAL_FILE_TMP);
        this.valueCount = valueCount;
        this.maxSize = maxSize;
    }

    /**
     * Opens the cache in {@code directory}, creating a cache if none exists
     * there.
     *
     * @param directory
     * @param appVersion
     * @param valueCount
     * @param maxSize
     * @return
     * @throws Exception
     */
    public static DiskLruCache open(File directory, int appVersion, int valueCount, long maxSize) throws Exception {
        if (maxSize <= 0 || valueCount <= 0) {
            throw new IllegalArgumentException("maxSize and valueCount mast >0");
        }
        DiskLruCache cache = new DiskLruCache(directory, appVersion, valueCount, maxSize);
        if (cache.journalFile.exists()) {//文件存在
            try {
                cache.readJournal();
                cache.processJournal();
                cache.journalWriter = new BufferedWriter(new FileWriter(cache.journalFile, true),
                        IO_BUFFER_SIZE);
                return cache;
            } catch (IOException journalIsCorrupt) {
//                System.logW("DiskLruCache " + directory + " is corrupt: "
//                        + journalIsCorrupt.getMessage() + ", removing");
                cache.delete();
            }
        }
        // create a new empty cache
        directory.mkdirs();
        cache = new DiskLruCache(directory, appVersion, valueCount, maxSize);
        cache.rebuildJournal();
        return cache;
    }

    /**
     * 数组copy
     * 细节在哪儿呢?1、对参数的合法性判断
     * 2、调用系统的newInstance,和copy提高了效率
     *
     * @param original
     * @param start
     * @param end
     * @param <T>
     * @return
     */
    private static <T> T[] copyOfRange(T[] original, int start, int end) {
        final int originalLength = original.length;
        if (start > end) {//参数不合法,跑出异常给外部处理
            throw new IllegalArgumentException();
        }
        if (start < 0 || start > originalLength) {
            throw new ArrayIndexOutOfBoundsException();
        }
        final int resultLength = end - start;
        final int copyLength = Math.max(resultLength, originalLength - start);
        /*创建数组实例*/
        final T[] result = (T[]) Array
                .newInstance(original.getClass().getComponentType(), resultLength);
        /*copy*/
        System.arraycopy(original, start, result, 0, copyLength);
        return result;
    }


    /**
     * Returns the remainder of 'reader' as a string, closing it when done.
     */
    public static String readFully(Reader reader) throws IOException {
        try {
            StringWriter writer = new StringWriter();
            char[] buffer = new char[1024];
            int count;
            //框架内部的异常跑出去给外部处理
            while ((count = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, count);
            }
            return writer.toString();
        } finally {
            reader.close();
        }
    }

    /**
     * Returns the ASCII characters up to but not including the next "\r\n", or
     * "\n".
     *
     * @param in
     * @return
     */
    public static String readAsciiLine(InputStream in) throws IOException {
        StringBuilder result = new StringBuilder(80);
        while (true) {
            int c = in.read();
            if (c == -1) {//没有读取到数据
                throw new EOFException();
            } else if (c == '\n') {//一行读完
                break;
            }
            result.append((char) c);
        }
        int length = result.length();
        if (length > 0 && result.charAt(length - 1) == '\r') {//末尾是空格
            result.setLength(length - 1);
        }
        return result.toString();
    }


    /**
     * 快速复制
     *
     * @param closeable
     * @throws Exception
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * 删除文件,如果文件是文件夹,迭代删除文件夹里面的内容
     *
     * @param dir
     * @throws IOException
     */
    public static void deleteContents(File dir) throws IOException {
        File[] files = dir.listFiles();
        if (files == null) {
            throw new IllegalArgumentException();
        }
        for (File file : files) {
            if (file.isDirectory()) {//文件夹,迭代
                deleteContents(file);
            }
            if (!file.delete()) {//文件删除失败
                throw new IOException("failed to delete file: " + file);
            }
        }
    }

    /**
     * 读取日志文件
     *
     * @throws IOException
     */
    private void readJournal() throws IOException {
        //将文件流包装成一个缓冲字节流
        InputStream in = new BufferedInputStream(new FileInputStream(journalFile));
        try {
            String magic = readAsciiLine(in);
            String version = readAsciiLine(in);
            String appVersionString = readAsciiLine(in);
            String valueCountString = readAsciiLine(in);
            String blank = readAsciiLine(in);
            if (!MAGIC.equals(magic)
                    || !VERSION_1.equals(version)
                    || !Integer.toString(appVersion).equals(appVersionString)
                    || !Integer.toString(valueCount).equals(valueCountString)
                    || !"".equals(blank)) {
                throw new IOException("unexpected journal header: ["
                        + magic + ", " + version + ", " + valueCountString + ", " + blank + "]");
            }
            while (true) {
                try {
                    readJournalLine(readAsciiLine(in));
                } catch (EOFException endOfJournal) {
                    break;
                }
            }
        } finally {
            closeQuietly(in);
        }
    }

    /**
     * 分行读取日志文件
     *
     * @param line
     * @throws IOException
     */
    private void readJournalLine(String line) throws IOException {
        String[] parts = line.split(" ");
        if (parts.length < 2) {
            throw new IOException("unexpected journal line: " + line);
        }
        String key = parts[1];
        //文件是删除
        if (parts[0].equals(REMOVE) && parts.length == 2) {
            lruEntries.remove(key);
            return;
        }
        Entry entry = lruEntries.get(key);
        if (entry == null) {
            entry = new Entry(key);
            lruEntries.put(key, entry);
        }
        if (parts[0].equals(CLEAN) && parts.length == 2 + valueCount) {
            entry.readable = true;
            entry.currentEditor = null;
            entry.setLengths(copyOfRange(parts, 2, parts.length));
        } else if (parts[0].equals(DIRTY) && parts.length == 2) {
            entry.currentEditor = new Editor(entry);
        } else if (parts[0].equals(READ) && parts.length == 2) {
            // this work was already done by calling lruEntries.get()
        } else {
            throw new IOException("unexpected journal line: " + line);
        }
    }

    /**
     * Computes the initial size and collects garbage as a part of opening the
     * cache. Dirty entries are assumed to be inconsistent and will be deleted.
     *
     * @throws IOException
     */
    private void processJournal() throws IOException {
        deleteIfExists(journalFileTmp);//删除临时日志文件
        for (Iterator<Entry> i = lruEntries.values().iterator(); i.hasNext(); ) {
            Entry entry = i.next();
            if (entry.currentEditor == null) {
                for (int t = 0; t < valueCount; t++) {
                    size += entry.lengths[t];
                }
            } else {
                entry.currentEditor = null;
                for (int t = 0; t < valueCount; t++) {
                    deleteIfExists(entry.getCleanFile(t));
                    deleteIfExists(entry.getDirtyFile(t));
                }
                i.remove();
            }
        }
    }

    /**
     * 创建日志文件
     *
     * @throws IOException
     */
    private synchronized void rebuildJournal() throws IOException {
        if (journalWriter != null) {
            journalWriter.close();
        }
        Writer writer = new BufferedWriter(new FileWriter(journalFileTmp), IO_BUFFER_SIZE);
        writer.write(MAGIC);
        writer.write("\n");
        writer.write(VERSION_1);
        writer.write("\n");
        writer.write(Integer.toString(appVersion));
        writer.write("\n");
        writer.write(Integer.toString(valueCount));
        writer.write("\n");
        writer.write("\n");

        for (Entry entry : lruEntries.values()) {
            if (entry.currentEditor != null) {
                writer.write(DIRTY + ' ' + entry.key + '\n');
            } else {
                writer.write(CLEAN + ' ' + entry.key + entry.getLengths() + "\n");
            }
        }
        writer.close();
        journalFileTmp.renameTo(journalFile);
        journalWriter = new BufferedWriter(new FileWriter(journalFile, true), IO_BUFFER_SIZE);
    }

    /**
     * @param file
     * @throws IOException
     */
    private static void deleteIfExists(File file) throws IOException {
        if (file.exists() && !file.delete()) {
            throw new IOException();
        }
    }

    /**
     * Returns a snapshot(快照) of the entry named {@code key}, or null if it doesn't
     * exist is not currently readable. If a value is returned, it is moved to
     * the head of the LRU queue.
     *
     * @param key
     * @return
     * @throws IOException
     */
    public synchronized Snapshot get(String key) throws IOException {
        checkNotClosed();
        validateKey(key);
        Entry entry = lruEntries.get(key);
        if (entry == null) {
            return null;
        }
        if (!entry.readable) {
            return null;
        }
        InputStream[] ins = new InputStream[valueCount];
        try {
            for (int i = 0; i < valueCount; i++) {
                ins[i] = new FileInputStream(entry.getCleanFile(i));
            }
        } catch (FileNotFoundException e) {
            return null;
        }

        redundantOpCount++;
        journalWriter.append(READ + ' ' + key + '\n');
        if (journalRebuildRequired()) {
            executorService.submit(cleanupCallable);
        }
        return new Snapshot(key, entry.sequenceNumber, ins);
    }

    public Editor editor(String key) throws IOException {
        return edit(key, ANY_SEQUENCE_NUMBER);
    }

    /**
     * @param key
     * @param expectedSequenceNumber
     * @return
     * @throws IOException
     */
    private synchronized Editor edit(String key, long expectedSequenceNumber) throws IOException {
        checkNotClosed();
        validateKey(key);
        Entry entry = lruEntries.get(key);
        if (expectedSequenceNumber != ANY_SEQUENCE_NUMBER && (entry == null || entry.sequenceNumber != expectedSequenceNumber)) {
            return null;
        }
        if (entry == null) {
            entry = new Entry(key);
            lruEntries.put(key, entry);
        } else if (entry.currentEditor != null) {
            return null;
        }
        Editor editor = new Editor(entry);
        entry.currentEditor = editor;
        journalWriter.write(DIRTY + ' ' + key + '\n');
        journalWriter.flush();
        return editor;
    }

    public File getDirectory() {
        return directory;
    }

    /**
     * 最大缓存大小
     *
     * @return
     */
    public long maxSize() {
        return maxSize;
    }

    /**
     * 获取实际使用的缓存大小
     *
     * @return
     */
    public synchronized long size() {
        return size;
    }

    private synchronized void completeEdit(Editor editor, boolean success) throws IOException {
        Entry entry = editor.entry;
        if (entry.currentEditor != editor) {
            throw new IllegalArgumentException();
        }

        if (success && !entry.readable) {
            for (int i = 0; i < valueCount; i++) {
                if (!entry.getDirtyFile(i).exists()) {
                    editor.abort();
                    throw new IllegalStateException("edit didn't create file " + i);
                }
            }
        }

        for (int i = 0; i < valueCount; i++) {
            File dirty = entry.getDirtyFile(i);
            if (success) {
                if (dirty.exists()) {
                    File clean = entry.getCleanFile(i);
                    dirty.renameTo(clean);
                    long oldLength = entry.lengths[i];
                    long newLength = clean.length();
                    entry.lengths[i] = newLength;
                    size = size - oldLength + newLength;
                }
            } else {
                deleteIfExists(dirty);
            }
        }

        redundantOpCount++;
        entry.currentEditor = null;
        if (entry.readable | success) {
            entry.readable = true;
            journalWriter.write(CLEAN + ' ' + entry.key + entry.getLengths() + '\n');
            if (success) {
                entry.sequenceNumber = nextSequenceNumber++;
            }
        } else {
            lruEntries.remove(entry.key);
            journalWriter.write(REMOVE + ' ' + entry.key + '\n');
        }

        if (size > maxSize || journalRebuildRequired()) {
            executorService.submit(cleanupCallable);
        }
    }

    private boolean journalRebuildRequired() {
        final int REDUNDANT_OP_COMPACT_THRESHOLD = 2000;
        return redundantOpCount >= REDUNDANT_OP_COMPACT_THRESHOLD
                && redundantOpCount >= lruEntries.size();
    }

    /**
     * Drops the entry for {@code key} if it exists and can be removed. Entries
     * actively being edited cannot be removed.
     *
     * @return true if an entry was removed.
     */
    public synchronized boolean remove(String key) throws IOException {
        checkNotClosed();
        validateKey(key);

        Entry entry = lruEntries.get(key);
        if (entry == null || entry.currentEditor != null) {
            return false;
        }
        for (int i = 0; i < valueCount; i++) {
            File file = entry.getCleanFile(i);
            if (!file.delete()) {
                throw new IOException("delete file error");
            }
            size -= entry.lengths[i];
            entry.lengths[i] = 0;
        }
        redundantOpCount++;
        journalWriter.append(REMOVE + ' ' + key + '\n');
        lruEntries.remove(key);

        if (journalRebuildRequired()) {
            executorService.submit(cleanupCallable);
        }
        return true;
    }


    public boolean isClosed() {
        return journalWriter == null;
    }

    private void checkNotClosed() {
        if (journalWriter == null) {
            throw new IllegalStateException("cache is closed");
        }
    }

    /**
     * Force buffered operations to the filesystem.
     */
    public synchronized void flush() throws IOException {
        checkNotClosed();
        trimToSize();
        journalWriter.flush();
    }

    @Override
    public void close() throws IOException {
        if (journalWriter == null) {
            return;//已经关闭了
        }
        for (Entry entry : new ArrayList<Entry>(lruEntries.values())) {
            if (entry.currentEditor != null) {
                entry.currentEditor.abort();
            }
        }
        trimToSize();
        journalWriter.close();
        journalWriter = null;
    }

    private void trimToSize() throws IOException {
        while (size > maxSize) {
//            Map.Entry<String, Entry> toEvict = lruEntries.eldest();
            final Map.Entry<String, Entry> toEvict = lruEntries.entrySet().iterator().next();
            remove(toEvict.getKey());
        }
    }

    public void delete() throws IOException {
        close();
        deleteContents(directory);
    }

    private void validateKey(String key) {
        if (key.contains(" ") || key.contains("\n") || key.contains("\r")) {
            throw new IllegalArgumentException(
                    "keys must not contain spaces or newlines: \"" + key + "\"");
        }
    }

    private static String inputStreamToString(InputStream in) throws IOException {
        return readFully(new InputStreamReader(in, UTF_8));
    }


    /**
     * A snapshot(快照) of the values for an entry.
     */
    public final class Snapshot implements Closeable {
        private final String key;
        private final long sequenceNumber;
        private final InputStream[] ins;

        private Snapshot(String key, long sequenceNumber, InputStream[] ins) {
            this.key = key;
            this.sequenceNumber = sequenceNumber;
            this.ins = ins;
        }

        /**
         * Returns an editor for this snapshot's entry, or null if either the
         * entry has changed since this snapshot was created or if another edit
         * is in progress.
         */
        public Editor edit() throws IOException {
            return DiskLruCache.this.edit(key, sequenceNumber);
        }

        /**
         * Returns the unbuffered stream with the value for {@code index}.
         *
         * @param index
         * @return
         */
        public InputStream getInputStream(int index) {
            return ins[index];
        }

        /**
         * Returns the string value for {@code index}.
         *
         * @param index
         * @return
         * @throws IOException
         */
        public String getString(int index) throws IOException {
            return inputStreamToString(getInputStream(index));
        }


        @Override
        public void close() throws IOException {
            for (InputStream in : ins) {
                closeQuietly(in);
            }
        }
    }


    /**
     * Edits the values for an entry.
     */
    public final class Editor {
        private final Entry entry;
        private boolean hasErrors;

        private Editor(Entry entry) {
            this.entry = entry;
        }

        /**
         * Returns an unbuffered input stream to read the last committed value,
         * or null if no value has been committed.
         *
         * @param index
         * @return
         * @throws IOException
         */
        public InputStream newIntputStream(int index) throws IOException {
            synchronized (DiskLruCache.this) {
                if (entry.currentEditor != this) {
                    throw new IllegalStateException();
                }
                if (!entry.readable) {
                    return null;
                }
                return new FileInputStream(entry.getCleanFile(index));
            }
        }

        /**
         * Returns the last committed value as a string, or null if no value
         * has been committed.
         */
        public String getString(int index) throws IOException {
            InputStream in = newIntputStream(index);
            return in != null ? inputStreamToString(in) : null;
        }

        /**
         * Returns a new unbuffered output stream to write the value at
         * {@code index}. If the underlying output stream encounters errors
         * when writing to the filesystem, this edit will be aborted when
         * is called. The returned output stream does not throw
         */
        public OutputStream newOutputStream(int index) throws IOException {
            synchronized (DiskLruCache.this) {
                if (entry.currentEditor != this) {
                    throw new IllegalStateException();
                }
                return new FaultHidingOutputStream(new FileOutputStream(entry.getDirtyFile(index)));
            }
        }

        /**
         * @param index
         * @param value
         * @throws IOException
         */
        public void set(int index, String value) throws IOException {
            Writer writer = null;
            try {
                writer = new OutputStreamWriter(newOutputStream(index), UTF_8);
                writer.write(value);
            } finally {
                closeQuietly(writer);
            }
        }

        /**
         * Commits this edit so it is visible to readers.  This releases the
         * edit lock so another edit may be started on the same key.
         */
        public void commit() throws IOException {
            if (hasErrors) {
                completeEdit(this, false);
                remove(entry.key); // the previous entry is stale
            } else {
                completeEdit(this, true);
            }
        }

        /**
         * Aborts this edit. This releases the edit lock so another edit may be
         * started on the same key.
         */
        public void abort() throws IOException {
            completeEdit(this, false);
        }

        /**
         * 自定义一个FilterOutputStream,添加处理异常逻辑
         */
        private class FaultHidingOutputStream extends FilterOutputStream {

            public FaultHidingOutputStream(OutputStream out) {
                super(out);
            }

            @Override
            public void write(int b) {
                try {
                    out.write(b);
                } catch (IOException e) {
                    hasErrors = true;
                }
            }

            @Override
            public void write(byte[] buffer, int offset, int length) {
                try {
                    out.write(buffer, offset, length);
                } catch (IOException e) {
                    hasErrors = true;
                }
            }

            @Override
            public void close() {
                try {
                    out.close();
                } catch (IOException e) {
                    hasErrors = true;
                }
            }

            @Override
            public void flush() {
                try {
                    out.flush();
                } catch (IOException e) {
                    hasErrors = true;
                }
            }
        }

    }

    /**
     * 定义一个文件Entry
     */
    private final class Entry {
        private final String key;
        /**
         * Lengths of this entry's files.
         */
        private final long[] lengths;
        /**
         * True if this entry has ever been published
         */
        private boolean readable;
        /**
         * The ongoing edit or null if this entry is not being edited.
         */
        private Editor currentEditor;
        /**
         * The sequence number of the most recently committed edit to this entry.
         */
        private long sequenceNumber;

        private Entry(String key) {
            this.key = key;
            this.lengths = new long[valueCount];
        }

        public String getLengths() throws IOException {
            StringBuilder sb = new StringBuilder();
            for (long size : lengths) {
                sb.append(' ').append(size);
            }
            return sb.toString();
        }

        private void setLengths(String[] strings) throws IOException {
            if (strings.length != valueCount) {
                throw invalidLengths(strings);
            }
            try {
                for (int i = 0; i < strings.length; i++) {
                    lengths[i] = Long.parseLong(strings[i]);
                }
            } catch (NumberFormatException e) {
                throw invalidLengths(strings);
            }

        }

        private IOException invalidLengths(String[] strings) throws IOException {
            throw new IOException("unexpected journal line: " + Arrays.toString(strings));
        }

        public File getCleanFile(int i) {
            return new File(directory, key + "." + i);
        }

        public File getDirtyFile(int i) {
            return new File(directory, key + "." + i + ".tmp");
        }

    }
}
