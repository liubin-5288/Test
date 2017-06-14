package com.example.kotlin

import android.app.Application
import com.example.kotlin.di.component.ApiComponent
import com.example.kotlin.di.component.DaggerApiComponent
import com.example.kotlin.di.module.ApiModule
import com.example.kotlin.di.module.AppModule
import javax.inject.Inject

/**
 * Created by liubin on 2017/6/13.
 */
class App : Application() {


    init {
        instance = this
    }


    @Inject
    lateinit var apiComponent: ApiComponent

    override fun onCreate() {
        super.onCreate()
        DaggerApiComponent.builder().apiModule(ApiModule()).appModule(AppModule(this)).build().inject(this)
    }

    /**
     * 相当于 java 中的 static 常量
     */
    companion object{
        /**
         * lateinit只能在不可null的对象上使用，比须为var，
         * 不能为primitives（Int、Float之类）
         */
        lateinit var instance : App
    }


}