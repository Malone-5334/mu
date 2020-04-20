package com.baselibrary.activity.dagger.module;


import com.baselibrary.activity.dagger.scopes.ActivityScoped;
import com.baselibrary.activity.dagger.scopes.RemoteQualifier;

import dagger.Binds;
import dagger.Module;

/**
 * 作者：Malone
 * 时间：2018/11/28  星期三
 * 邮箱：178917169@qq.com
 * 功能描述：
 */
@Module
public abstract class DataManageModule {

    @Binds
    @RemoteQualifier
    @ActivityScoped
    abstract LocalDataSource provideLocalDataSource(LocalDataSource localDataSource);


    @Binds
    @RemoteQualifier
    @ActivityScoped
    abstract HttpDataSource provideHttpDataSource(HttpDataSource httpDataSource);

}