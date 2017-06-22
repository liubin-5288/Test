package com.example.kotlin.bean

/**
 * Created by liubin on 2017/6/13.
 */
class FuckGoods(
        val id:String,
        val createAt:String,
        val desc:String,
        val images:Array<String>?,
        val publishedAt:String,
        val source:String,
        val type:String,
        val used :Boolean,
        val who:String

) {
    fun hasImg() = images != null

    fun create() = createAt.substring(0,10)
}