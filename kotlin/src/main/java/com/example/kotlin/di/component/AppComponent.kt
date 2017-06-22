package com.example.kotlin.di.component

import com.example.kotlin.di.module.AppModule
import com.example.kotlin.ui.activity.MainActivity
import dagger.Component

/**
 * Created by liubin on 2017/6/14.
 */

@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(app : MainActivity)
}