package com.baselibrary.activity.dagger.scopes;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import dagger.Reusable;

/**
 * 作者：Malone
 * 时间：2018/11/28  星期三
 * 邮箱：178917169@qq.com
 * 功能描述：
 */
@Documented
@Reusable
@Retention(RetentionPolicy.RUNTIME)
public @interface FragmentReusable {
}