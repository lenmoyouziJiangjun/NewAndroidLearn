package com.lll.common.util.collection;

import java.util.Arrays;
import java.util.Collections;

/**
 * Version 1.0
 * Created by lll on 17/6/5.
 * Description Implements a growing array of int primitives.
 * copyright generalray4239@gmail.com
 */
public class IntArray implements Cloneable {
    private static final int MIN_CAPACITY_INCREMENT = 12;

    private int[] mValues;
    private int mSize;

    public IntArray() {
        this(10);
    }

    public IntArray(int initialCapacity) {
        if (initialCapacity == 0) {
            mValues = EmptyArray.INT;
        } else {
            mValues = ArrayUtils.newUnpaddedIntArray(initialCapacity);
        }
        mSize = 0;
    }

    public void add(int value) {
        add(mSize, value);
    }

    public void add(int index, int value) {
        if (index < 0 || index > mSize) {
            throw new IndexOutOfBoundsException();
        }
        ensureCapacity(1);

        if (mSize - index != 0) {
            System.arraycopy(mValues, index, mValues, index + 1, mSize - index);
        }

        mValues[index] = value;
        mSize++;
    }

    public int binarySearch(int value) {
        return ArrayUtils.binarySearch(mValues, mSize, value);
    }

    public void addAll(IntArray values) {
        final int count = values.mSize;
        ensureCapacity(count);

        System.arraycopy(values.mValues, 0, mValues, mSize, count);
        mSize += count;
    }

    private void ensureCapacity(int count) {
        final int currentSize = mSize;
        final int minCapacity = currentSize + count;
        if (minCapacity >= mValues.length) {//空间不够，申请更多空间
            final int targetCap = currentSize + (currentSize < (MIN_CAPACITY_INCREMENT / 2) ?
                    MIN_CAPACITY_INCREMENT : currentSize >> 1);
            final int newCapacity = targetCap > minCapacity ? targetCap : minCapacity;
            final int[] newValues = ArrayUtils.newUnpaddedIntArray(newCapacity);
            System.arraycopy(mValues, 0, newValues, 0, currentSize);
            mValues = newValues;
        }
    }

    public void clear() {
        mSize = 0;
    }

    @Override
    public IntArray clone() throws CloneNotSupportedException {
        final IntArray clone = (IntArray) super.clone();
        clone.mValues = mValues.clone();
        return clone;
    }

    /**
     * Returns the value at the specified position in this array.
     */
    public int get(int index) {
        if (index >= mSize) {
            throw new ArrayIndexOutOfBoundsException(mSize);
        }
        return mValues[index];
    }

    /**
     * Returns the index of the first occurrence of the specified value in this
     * array, or -1 if this array does not contain the value.
     */
    public int indexOf(int value) {
        final int n = mSize;
        for (int i = 0; i < n; i++) {
            if (mValues[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public void remove(int index) {
        if (index >= mSize) {
            throw new ArrayIndexOutOfBoundsException(mSize);
        }
        System.arraycopy(mValues, index + 1, mValues, index, mSize - index - 1);
    }

    /**
     * Returns the number of values in this array.
     */
    public int size() {
        return mSize;
    }

    /**
     * Returns a new array with the contents of this IntArray.
     */
    public int[] toArray() {
        return Arrays.copyOf(mValues, mSize);
    }
}
