package com.example.test;

import android.app.Application;

import com.example.baselibrary.utils.Utils;


/**
 * Created by liubin on 2017/6/22.
 */

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
