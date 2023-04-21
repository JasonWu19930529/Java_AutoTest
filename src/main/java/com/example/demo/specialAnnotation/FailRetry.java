package com.example.demo.specialAnnotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FailRetry {
    //重试次数，这里默认15
    int value() default 3;
}
