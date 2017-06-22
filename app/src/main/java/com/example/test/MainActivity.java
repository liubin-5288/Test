package com.example.test;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.baselibrary.AppConfig;
import com.example.baselibrary.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_kotlin,R.id.btn_customview})
    public void onClickView(View view){
        switch (view.getId()){
            case R.id.btn_kotlin:
                Intent intent = startActivityIntent(AppConfig.CLASS_NAME_KOTLIN_MAIN_ACTIVITY);
                try {
                    startActivityForResult(intent, 1);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    showToast("该功能尚未开放");
                }
                break;
            case R.id.btn_customview:
                Intent intent1 = startActivityIntent(AppConfig.CLASS_NAME_CUSTOMVIEW_MAIN_ACTIVITY);
                try {
                    startActivityForResult(intent1, 2);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    showToast("该功能尚未开放");
                }
                break;
        }
    }




    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


}
