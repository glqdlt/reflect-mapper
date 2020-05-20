package com.glqdlt.utill;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WeavingDescription {
    //TODO 아래 옵션에 따른 필터 처리 필요, ver 0.0.3 에서 처리
    String[] ignoreMethodName() default {""};
}
