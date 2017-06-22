package com.example.baselibrary.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;


/**
 *
 *  View类提供了setSystemUiVisibility和getSystemUiVisibility方法，这两个方法实现对状态栏的动态显示或隐藏的操作，以及获取状态栏当前可见性。
 *  setSystemUiVisibility方法传入的实参分析：
 *  setSystemUiVisibility(int visibility)方法可传入的实参为：
 *  1. View.SYSTEM_UI_FLAG_VISIBLE：显示状态栏，Activity不全屏显示(恢复到有状态的正常情况)。
 *  2. View.INVISIBLE：隐藏状态栏，同时Activity会伸展全屏显示。
 *  3. View.SYSTEM_UI_FLAG_FULLSCREEN：Activity全屏显示，且状态栏被隐藏覆盖掉。
 *  4. View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住。
 *  5. View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION：效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
 *  6. View.SYSTEM_UI_LAYOUT_FLAGS：效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
 *  7. View.SYSTEM_UI_FLAG_HIDE_NAVIGATION：隐藏虚拟按键(导航栏)。有些手机会用虚拟按键来代替物理按键。
 *  8. View.SYSTEM_UI_FLAG_LOW_PROFILE：状态栏显示处于低能显示状态(low profile模式)，状态栏上一些图标显示会被隐藏。
 *
 *  =====================================================================================================
 *
 * Created by liubin on 2017/6/12.
 * 解决部分手机底部软键盘适配问题(如华为，nexus)
 *
 * 使用方式：
 * 在 Activity 中的 setContentView(R.layout.XXXX) 之后添加
 * AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content));
 */

public class AndroidBug54971Workaround {
    /**
     * 关联要监听的视图
     *
     * @param viewObserving
     */
    public static void assistActivity(Object viewObserving) {
        new AndroidBug54971Workaround(viewObserving);
    }

    private View mViewObserved;//被监听的视图
    private int usableHeightPrevious;//视图变化前的可用高度
    private ViewGroup.LayoutParams frameLayoutParams;

    private AndroidBug54971Workaround(Object viewObserving) {
        if(viewObserving instanceof Activity){
            Activity activity = (Activity) viewObserving;
            FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
            mViewObserved = content.getChildAt(0);
        }else if(viewObserving instanceof View){
            mViewObserved = (View) viewObserving;
        }else {
            throw new IllegalArgumentException("参数异常");
        }
        //给View添加全局的布局监听器
        mViewObserved.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                resetLayoutByUsableHeight(computeUsableHeight());
            }
        });
        frameLayoutParams = mViewObserved.getLayoutParams();
    }

    private void resetLayoutByUsableHeight(int usableHeightNow) {
        //比较布局变化前后的View的可用高度
        if (usableHeightNow != usableHeightPrevious) {
            //如果两次高度不一致
            //将当前的View的可用高度设置成View的实际高度
            frameLayoutParams.height = usableHeightNow;
            mViewObserved.requestLayout();//请求重新布局
            usableHeightPrevious = usableHeightNow;
        }
    }

    /**
     * 计算视图可视高度
     *
     * @return
     */
    private int computeUsableHeight() {
        Rect r = new Rect();
        mViewObserved.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }
}