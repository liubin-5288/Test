package com.example.kotlin.ui.activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.kotlin.R
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.content_base.*

abstract class BaseActivity : AppCompatActivity() {


    //初始化view
    abstract fun initView()
    //初始化数据
    abstract fun initData()
    //子页面布局Id
    abstract fun contentViewId() : Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        //添加子页面布局
        layoutInflater.inflate(contentViewId(),content_base)
        setSupportActionBar(setupToolbar(toolbar))
        initView()
        initData()

//        val fab = findViewById(R.id.fab) as FloatingActionButton
//        fab.setOnClickListener(View.OnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        })
    }

    fun setupToolbar(toolbar: Toolbar) : Toolbar{
        return toolbar
    }







}
