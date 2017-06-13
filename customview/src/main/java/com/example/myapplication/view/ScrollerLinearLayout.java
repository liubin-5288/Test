package com.example.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by liubin on 2017/6/4.
 */

public class ScrollerLinearLayout extends LinearLayout {

    Scroller scroller;

    public ScrollerLinearLayout(Context context) {
        super(context);
    }

    public ScrollerLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    public ScrollerLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void startScoll(){
        scroller.startScroll(getScrollX(),getScrollY(),-50,-50);
        invalidate();
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollBy(scroller.getCurrX(),scroller.getCurrY());
            invalidate();
        }
    }
}
