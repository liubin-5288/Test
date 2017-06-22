package com.example.rxjava

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.content_base.*

abstract class BaseActivity : AppCompatActivity() {

    //获取子布局的id
   abstract fun getChildId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        addChildView()

        setSupportActionBar(setupToolbar(toolbar))

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        initView()

        initData()

    }


    open fun initView(){

    }

    open fun initData(){

    }


    //添加子布局
    fun addChildView() = {
        val view = layoutInflater.inflate(getChildId(), fl_base_content)
        fl_base_content.addView(view)
    }

    //设置 toolbar
    open fun setupToolbar(toolbar: Toolbar) : Toolbar{
        return toolbar
    }

}
