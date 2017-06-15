package com.lll.core.hook;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class HookHandler implements InvocationHandler {

    private Object mObject;

    public HookHandler(Object object) {
        mObject = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.e("lll", mObject.toString()+"----------method name-----==" + method.getName() + "------args===" + Arrays.toString(args));
        Object obj = null;
        try {
            obj = method.invoke(mObject, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
