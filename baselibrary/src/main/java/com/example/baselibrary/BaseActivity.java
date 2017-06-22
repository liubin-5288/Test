package com.example.baselibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by liubin on 2017/6/21.
 */

public class BaseActivity extends AppCompatActivity {

    protected Intent startActivityIntent(String className){
        Intent intent = new Intent();
        intent.setClassName(this,className);
        return intent;
    }

}
