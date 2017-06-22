package com.example.myapplication;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.baselibrary.BaseActivity;
import com.example.myapplication.view.TouchPullView;

public class CustomViewMainActivity extends BaseActivity {

    float downY;
    float maxDown = 400f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_main);
        final TouchPullView pullView = (TouchPullView) findViewById(R.id.pullView);
        findViewById(R.id.activity_main).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        downY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float cy = event.getY();
                        if(cy >= downY){ //向下滑动
                            float progress = (cy - downY) >= maxDown ? 1 : (cy - downY) / maxDown;
                            pullView.setProgress(progress);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        pullView.release();
                        break;
                }
                return true;
            }
        });
    }
}
