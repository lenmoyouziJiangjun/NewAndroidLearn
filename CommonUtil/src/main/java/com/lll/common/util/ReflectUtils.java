package com.lll.common.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * Version 1.0
 * Created by lll on 16/11/25.
 * Description
 * copyright generalray4239@gmail.com
 */

public class ReflectUtils {

    /**
     * 反射类字段
     *
     * @param instance
     * @param name
     * @return
     * @throws NoSuchFieldException
     */
    public static Field findField(Object instance, String name) throws NoSuchFieldException {
        for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                Field field = clazz.getDeclaredField(name);

                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }

                return field;
            } catch (NoSuchFieldException e) {
                // ignore and search next
            }
        }
        throw new NoSuchFieldException("Field " + name + " not found in " + instance.getClass());
    }

    /**
     * 获取字段值
     *
     * @param obj
     * @param field
     * @return
     * @throws Exception
     */
    public static Object getFieldValue(Object obj, Field field) throws Exception {
        return field.get(obj);
    }

    /**
     * 设置字段值
     *
     * @param obj    对象
     * @param field  字段
     * @param values 字段对应的值
     * @throws Exception
     */
    public static void setFieldValue(Object obj, Field field, Object... values) throws Exception {

    }

    /**
     * 设置数组属性的值
     *
     * @throws Exception
     */
    private static void setArraysFieldValue(Object instance, String fieldName, Object[] extraElements) throws Exception {
        Field jlrField = findField(instance, fieldName);
        Object[] original = (Object[]) jlrField.get(instance);
        Object[] combined = (Object[]) Array.newInstance(original.getClass().getComponentType(), original.length + extraElements.length);
        System.arraycopy(original, 0, combined, 0, original.length);
        System.arraycopy(extraElements, 0, combined, original.length, extraElements.length);
        jlrField.set(instance, combined);
    }
}
