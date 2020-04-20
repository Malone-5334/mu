package com.baselibrary.activity.dagger.scopes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Scope;

/**
 * 作者：Malone
 * 时间：2018/11/28  星期三
 * 邮箱：178917169@qq.com
 * 功能描述：
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface FragmentScoped {
}