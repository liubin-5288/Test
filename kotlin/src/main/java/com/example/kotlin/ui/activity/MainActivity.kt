package com.example.kotlin.ui.activity

import android.graphics.Color
import android.support.v7.widget.Toolbar
import android.util.Log
import com.example.kotlin.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun setupToolbar(toolbar: Toolbar): Toolbar {
        toolbar.setNavigationIcon(R.drawable.abc_ab_share_pack_mtrl_alpha)
        toolbar.title = "测试专用"
        return super.setupToolbar(toolbar)
    }

    override fun initView() {
        text.text = "修改后的内容"
        text.textSize = 30f
        text.setTextColor(Color.RED)
        val text = text.text.toString().trim()
        Log.d("initView",text)

    }

    override fun initData() {
//        Log.d("initData",(getMainComponent() == null).toString())
    }

    override fun contentViewId(): Int {
        return 0
    }


}
