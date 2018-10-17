package com.linewell.core.common;

import android.support.annotation.LayoutRes;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Describe
 * Created by chenjl on 2017/10/16.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentLayout {
    @LayoutRes int value() default 0;
}