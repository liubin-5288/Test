package com.example.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;

public class Practice10HistogramView extends View {

    private Paint paint;
    private Path path;

    public Practice10HistogramView(Context context) {
        super(context);
        init();
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图

        //画 x y 轴
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(6);
        paint.setStyle(Paint.Style.STROKE);
        path.moveTo(100,100);
        path.lineTo(100,600);
        path.rLineTo(800,0);
        canvas.drawPath(path,paint);


        int xStart = 130;
        int xEnd = 130 + 90;
        int road = 100;
        Rect rect;
        String text = "";
        for(int i =0;i< 6; i++){
            road = road *(new Random().nextInt(2)%(2 - 1 + 1) + 1);
            Log.d("Practice10HistogramView","road =" + road);
            canvas.save();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.GREEN);
            canvas.drawRect(xStart,600 - road,xEnd,600,paint);
            canvas.restore();

            canvas.save();
            paint.setColor(Color.WHITE);
            paint.setTextSize(24);
            text = road+"";
//            测量文字大小
            rect = new Rect();
            paint.getTextBounds(text,0,text.length(),rect);

            //绘制文字
            canvas.drawText(text,xStart + rect.width() / 2,600 + rect.height(),paint);


            canvas.restore();
            xStart = xEnd + 30;
            xEnd = xStart + 90;




        }




    }
}
