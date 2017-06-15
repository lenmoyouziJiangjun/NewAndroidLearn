package com.lll.core.communicate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Version 1.0
 * Created by lll on 17/5/3.
 * Description 组件通信工具类
 * 组件通信有两种情况，一种是操作公共数据，通知与数据有关的界面刷新
 * 一种是由于业务需要组件A需要调用组件B的接口方法
 * copyright generalray4239@gmail.com
 */
public class ComponentCommunicateUtils {

    private void validateServiceInterface(Class service) {
        if (!service.isInterface()) {
            throw new RuntimeException(String.format("clz %s should be a interface class",service.getCanonicalName()));
        }
    }

    /**
     * 这种用来方式用来跨组件调用方法
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T createComponentCommunicateService(final Class<T> clazz) {
        validateServiceInterface(clazz);
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if(method.getDeclaringClass()==Object.class){
                    return method.invoke(this,args);
                }
                return null;
            }
        });
    }

    /**
     * 反射调用
     * @param classFullName
     * @param <T>
     * @return
     * @throws ClassNotFoundException
     */
    public <T> T createComponentCommunicateService(String classFullName) throws ClassNotFoundException {
        Class<T> clazz = (Class<T>) Class.forName(classFullName);
        validateServiceInterface(clazz);
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if(method.getDeclaringClass()==Object.class){
                    return method.invoke(this,args);
                }
                return null;
            }
        });
    }


}
