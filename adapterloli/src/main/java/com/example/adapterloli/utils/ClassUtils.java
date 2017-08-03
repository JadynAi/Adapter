package com.example.adapterloli.utils;

import android.support.annotation.NonNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClassUtils {

    public static Class getGenericsClass(@NonNull Class curClazz,
                             @NonNull Class genericsDeclaredSuperClazz,
                             int genericsIndex) {

        Type superType = curClazz.getGenericSuperclass();
        while (true) {
            if (superType instanceof Class) {
                superType = ((Class) superType).getGenericSuperclass();
                continue;
            }
            if (superType instanceof ParameterizedType) {
                ParameterizedType superParamType = (ParameterizedType) superType;
                if (!genericsDeclaredSuperClazz.equals(superParamType.getRawType())){
                    superType = ((Class)superParamType.getRawType()).getGenericSuperclass();
                    continue;
                }
                Type[] actualTypeArguments = superParamType.getActualTypeArguments();
                Type typeArgument = actualTypeArguments[genericsIndex];
                if (typeArgument instanceof Class) {
                    return (Class) typeArgument;
                } else if (typeArgument instanceof ParameterizedType) {
                    return (Class) ((ParameterizedType) typeArgument).getRawType();
                }
            }
            throw new IllegalArgumentException("curClazz:" + curClazz + " not find superclassParam");
        }
    }
}
