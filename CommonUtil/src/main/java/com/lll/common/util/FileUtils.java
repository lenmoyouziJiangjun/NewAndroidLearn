package com.lll.common.util;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Version 1.0
 * Created by lll on 16/11/7.
 * Description 文件工具类。call sub thread
 * copyright generalray4239@gmail.com
 */

public class FileUtils {
    /**
     * 创建文件
     *
     * @param filePath
     * @param fileName
     * @param fileContent
     * @throws Exception
     */
    public static void createFile(String filePath, String fileName, String fileContent) throws Exception {

    }

    /**
     * 往文件末尾追加内容
     *
     * @param fileName
     * @param content
     */
    public static void appendFile(String fileName, String content) {

    }

    /**
     * 拷贝文件
     *
     * @param destFile
     * @param sourceFile
     */
    public static void copyFile(String destFile, String sourceFile) {

    }

    /**
     * 压缩文件
     *
     * @param fileName    文件全路径名
     * @param zipFileName
     */
    public static void zipFile(String fileName, String zipFileName) {

    }

    /**
     * 解压缩文件
     *
     * @param zipFileName
     * @param fileName
     */
    public static void unzipFile(String zipFileName, String fileName) {

    }

    /**
     * 删除文件
     * @param fileName
     */
    public static void deleteFile(String fileName) {
        File file = new File(fileName);
        file.deleteOnExit();
    }

    /**
     * should call in finally
     * @param stream
     */
    private static void closeInputStream(InputStream stream){
        try {
            if(stream!=null){
                stream.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * should call in finally
     * @param stream
     */
    private static void closeOutputStream(OutputStream stream){
        try {
            if(stream!=null){
                stream.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
