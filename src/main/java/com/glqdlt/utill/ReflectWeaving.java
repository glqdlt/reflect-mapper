package com.glqdlt.utill;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectWeaving {

    public <T> T makeInstance(Class<T> type) throws IllegalAccessException {
        try {
            return type.newInstance();
        } catch (InstantiationException e) {
            throw new ReflectError(String.format("'%s' is not have default constructor. check please ", type.getName()), e);
        }
    }

    public <T> T deepCopy(T target, Class<T> type) {
        try {
            T result = makeInstance(type);

            Field[] privateFields = type.getDeclaredFields();


            Method[] publicMethods = type.getMethods();
            for (Method m : publicMethods) {

            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
