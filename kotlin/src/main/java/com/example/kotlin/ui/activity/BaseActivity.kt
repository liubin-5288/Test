package com.example.kotlin.ui.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.example.baselibrary.BaseActivity
import com.example.kotlin.R
import com.example.kotlin.di.component.ApiComponent
import com.example.kotlin.di.component.DaggerApiComponent
import com.example.kotlin.di.module.ApiModule
import com.example.kotlin.di.module.AppModule
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.content_base.*

abstract class BaseActivity : BaseActivity() {

//    lateinit var apiComponent: ApiComponent

    //初始化view
    abstract fun initView()
    //初始化数据
    abstract fun initData()
    //子页面布局Id
    abstract fun contentViewId() : Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
//        apiComponent = DaggerApiComponent.builder().apiModule(ApiModule()).appModule(AppModule(this)).build()
//        apiComponent.inject(this)
        //添加子页面布局
        layoutInflater.inflate(contentViewId(),content_base)
        setSupportActionBar(setupToolbar(toolbar))
        initView()
        initData()

    }

    open fun setupToolbar(toolbar: Toolbar) : Toolbar{
        return toolbar
    }








}
