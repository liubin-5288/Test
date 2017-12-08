package com.example.kotlin

import android.app.Application
import com.example.kotlin.di.component.ApiComponent
import com.example.kotlin.di.module.ApiModule
import com.example.kotlin.di.module.AppModule

/**
 * Created by liubin on 2017/6/13.
 */
class App : Application() {



    override fun onCreate() {
        super.onCreate()
        instance = this
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