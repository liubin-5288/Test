package com.example.kotlin

import android.content.Context
import android.widget.Toast

/**
 * Created by liubin on 2017/6/14.
 */

fun Context.getMainComponent() = App.instance.apiComponent

fun Context.toast(msg :String , length : Int = Toast.LENGTH_SHORT){
    Toast.makeText(this,msg,length).show()
}