package com.glqdlt.utill;

import java.util.LinkedList;
import java.util.List;

public class WeavingFilter {
    public static <T> List<T> filter(T[] resource, AddOnFunction<T> addOnFunction) {

        List<T> result = new LinkedList<T>();

        for (T r : resource) {
            if (addOnFunction.addOnCondition(r)) {
                result.add(r);
            }
        }
        return result;

    }

    public static <T> List<T> filter(List<T> resource, AddOnFunction<T> addOnFunction) {

        List<T> aaa = new LinkedList<T>();

        for (T r : resource) {
            if (!addOnFunction.addOnCondition(r)) {
                aaa.add(r);
            }
        }
        return aaa;

    }

}
