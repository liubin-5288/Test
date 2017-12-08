package com.example.baselibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.baselibrary.di.component.DaggerAppComponent;

/**
 * Created by liubin on 2017/6/21.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected Intent startActivityIntent(String className){
        Intent intent = new Intent();
        intent.setClassName(this,className);
        return intent;
    }

}
