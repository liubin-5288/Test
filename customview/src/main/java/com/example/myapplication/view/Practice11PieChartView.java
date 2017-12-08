package com.example.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice11PieChartView extends View {

    public Practice11PieChartView(Context context) {
        super(context);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        Path path = new Path();
        Rect rect = new Rect();


//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图

//        圆点坐标：(x0,y0)
//        半径：r
//        角度：a0
//        弧度 = 度 * π / 180
//        x1   =   x0   +   r   *   Math.cos(ao   *   3.14   /180   )
//        y1   =   y0   +   r   *   Math.sin(ao   *   3.14   /180   )

//        Math.toRadians() 是将角度转换为弧度

        paint.setColor(Color.RED);
        RectF rectF = new RectF(100,50,400,350);
        canvas.drawArc(rectF,-180,140,true,paint);

        int x0 = (int)rectF.centerX();
        int y0 = (int)rectF.centerY();
        int r = ((400 - 100) / 2);
        //计算弧度上面的坐标
        float sX = (float) (x0 + r * Math.cos(Math.toRadians(-180 + 140/2)));
        float sY = (float) (y0 + r * Math.sin(Math.toRadians(-180 + 140/2)));
        //超出圆半径 20 的x,y坐标
        float eX = (float) (x0 + (r + 20) * Math.cos(Math.toRadians(-180 + 140/2)));
        float eY = (float) (y0 + (r + 20) * Math.sin(Math.toRadians(-180 + 140/2)));

        //画线
        paint.setColor(Color.BLACK);
        canvas.save();
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        path.moveTo(sX,sY);
        path.lineTo(eX,eY);
        path.rLineTo(-50,0);
        canvas.drawPath(path, paint);
        canvas.restore();

        //画文字
        canvas.save();
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(42);
        String text = (int)((140 / 360f) * 100) + "%";
        paint.getTextBounds(text,0,text.length(),rect);
        //文字位置 x = eX - 50线长 - 文字宽度      y = eY + 文字一半高度 （文字绘制从 左下角开始）
        canvas.drawText(text,0,text.length(),eX - 50 - rect.width(),rect.height() /2 + eY,paint);
        canvas.restore();

        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);
        rectF = new RectF(120,70,420,370);
        canvas.drawArc(rectF,-40,60,true,paint);

        //划线
        r = (int)rectF.width() / 2;
        x0 = (int)rectF.centerX();
        y0 = (int)rectF.centerY();
        sX = (float)(x0 + r * Math.cos(Math.toRadians(-40 + 60 /2)));
        sY = (float)(y0 + r * Math.sin(Math.toRadians(-40 + 60 /2)));
        eX = (float)(x0 + (r + 20) * Math.cos(Math.toRadians(-40 + 60 /2)));
        eY = (float)(y0 + (r + 20) * Math.sin(Math.toRadians(-40 + 60 /2)));
        path.moveTo(sX,sY);
        path.lineTo(eX,eY);
        path.rLineTo(50,0);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        canvas.drawPath(path,paint);

        //画文字
        text = (int)((60 / 360f) * 100) + "%";
        paint.setStrokeWidth(1);
        paint.getTextBounds(text,0,text.length(),rect);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(text,0,text.length(),eX + 50,eY + rect.height() / 2,paint);

        paint.setColor(Color.BLUE);
        rectF = new RectF(120,70,420,370);
        canvas.drawArc(rectF,20,30,true,paint);
        //画线
        r = (int)rectF.width() / 2;
        x0 = (int)rectF.centerX();
        y0 = (int)rectF.centerY();
        sX = (float)(x0 + r * Math.cos(Math.toRadians(20 + 30/2)));
        sY = (float)(y0 + r * Math.sin(Math.toRadians(20 + 30/2)));
        eX = (float)(x0 + (r + 20) * Math.cos(Math.toRadians(20 + 30 / 2)));
        eY = (float)(y0 + (r + 20) * Math.sin(Math.toRadians(20 + 30 / 2)));
        path.moveTo(sX,sY);
        path.lineTo(eX,eY);
        path.rLineTo(50,0);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        canvas.drawPath(path,paint);
        //画字
        text = (int)((30 / 360f) * 100) + "%";
        paint.setStrokeWidth(1);
        paint.getTextBounds(text,0,text.length(),rect);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(text,0,text.length(),eX + 50,eY + rect.height() / 2,paint);


        paint.setColor(Color.GRAY);
        rectF = new RectF(120,70,420,370);
        canvas.drawArc(rectF,50,80,true,paint);

        paint.setColor(Color.GREEN);
        rectF = new RectF(120,70,420,370);
        canvas.drawArc(rectF,130,50,true,paint);

    }
}
