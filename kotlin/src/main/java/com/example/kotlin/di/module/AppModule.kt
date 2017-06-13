package com.example.kotlin.di.module

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created by liubin on 2017/6/13.
 */
@Module
class AppModule(private var context: Context) {

    @Provides fun privodeContext() = context
}