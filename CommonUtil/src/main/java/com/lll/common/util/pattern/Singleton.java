package com.lll.common.util.pattern;

/**
 * 单例模式工具类
 *
 * @param <T>
 */
public abstract class Singleton<T> {
    private T mInstance;

    public final T get() {
        synchronized (this) {
            if (mInstance == null) {
                mInstance = createObj();
            }
            return mInstance;
        }
    }

    public abstract T createObj();
}
