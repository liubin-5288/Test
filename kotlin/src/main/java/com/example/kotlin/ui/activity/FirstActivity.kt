package com.example.kotlin.ui.activity

import android.util.Log
import com.example.kotlin.R
import kotlinx.android.synthetic.main.activity_main2.*

class FirstActivity : BaseActivity() {

    override fun initView() {
//        val text = text.text.toString().trim()
//        Log.d("initView",text)
    }

    override fun initData() {
    }

    override fun contentViewId(): Int {
        return R.layout.activity_main2
    }


}
