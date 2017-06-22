package com.example.rxjava

import android.support.v7.widget.Toolbar
import rx.Observable
import rx.functions.Action1
import rx.functions.Func1
import java.util.*


class MainActivity : BaseActivity() {

    override fun getChildId(): Int {
        return R.layout.content_main
    }

    override fun setupToolbar(toolbar: Toolbar): Toolbar {
        toolbar.setTitle("tttt")
        return toolbar
    }


    override fun initData() {
        Observable.just("images/logo.png") // 输入类型 String
                .map(object : Func1<String, ArrayList<String>>{

                    override fun call(t: String): ArrayList<String> {
                        val list = ArrayList<String>()
                        t.replace("/","")
                       return list
                    }
                })
                .subscribe(Action1<ArrayList<String>>() {

                })
    }


}
