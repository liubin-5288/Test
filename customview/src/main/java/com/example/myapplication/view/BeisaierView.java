package com.example.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liubin on 2017/6/10.
 * 贝塞尔曲线
 */
public class BeisaierView extends View {

    //画笔
    private final Paint bsePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint bsePaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    //路径
    private final Path path = new Path();
    private Path path1 = new Path();

    public BeisaierView(Context context) {
        super(context);
        init();
    }

    public BeisaierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BeisaierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        Paint paint = bsePaint;
        //抗锯齿
        paint.setAntiAlias(true);
        //画线
        paint.setStyle(Paint.Style.STROKE);
        //抗抖动
        paint.setDither(true);
        //颜色
        paint.setColor(Color.GRAY);
        //线的宽度
        paint.setStrokeWidth(10);

        threeLine();



        heightLine();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        //一阶
//        oneLine();
//        twoLine();
        bsePaint.setColor(Color.GRAY);
        canvas.drawPath(path,bsePaint);

        bsePaint.setColor(Color.RED);
        canvas.drawPath(path1,bsePaint);
//        canvas.drawPoint(400,250,bsePaint);
//
//        canvas.drawPoint(250,600,bsePaint);
//        canvas.drawPoint(500,800,bsePaint);

    }


    private void heightLine(){
//        Path mPath = path1;

        //250,600,500,800,750,600,1000,700
        //250,600,500,800,700,700
        //刷新频率
        float fps = 1000;
        path1 = createBezier(fps,new Point[]{
                new Point(100,700),
                new Point(250,600),
                new Point(500,800),
                new Point(750,600),
                new Point(1000,700)});
        path1.moveTo(100,700);
    }

    //创建线路
    private Path createBezier(float fps,Point... points){
        Path path = new Path();
        if(points == null || points.length == 0){
            return path;
        }
        if(fps <= 0){
            fps = 100;
        }
        //分别获取 x,y 坐标
        float[] xs = getXPoints(points);
        float[] ys = getYPoints(points);

        //起点位置
        path.moveTo(xs[0],ys[0]);

        float pro = 0f;
        float cx = 0f;
        float cy = 0f;

        for(int i = 0;i <= fps; i++){
            pro = i / fps;
            cx = calculateBezier(pro,xs);
            cy = calculateBezier(pro,ys);
            path.lineTo(cx,cy);
        }

        return path;
    }

    //获取所以的x坐标集合
    private float[] getXPoints(Point... points){
        float[] xs = new float[points.length];
        for (int i = 0; i < xs.length; i++) {
            xs[i] = points[i].x;
        }
        return xs;
    }

    //获取所以的y坐标集合
    private float[] getYPoints(Point... points){
        float[] ys = new float[points.length];
        for (int i = 0; i < ys.length; i++) {
            ys[i] = points[i].y;
        }
        return ys;
    }

    /**
     * 计算某时刻的贝塞尔坐标(x 或 y)
     * @param t 时间
     * @param values 集合
     * @return 当前 t 时间的贝塞尔坐标点(x 或 y)
     */
    private float calculateBezier(float t,float... values ){
        int len = values.length;
        for(int i = len - 1; i > 0; i--){
            for(int j = 0; j < i; j++){
                values[j] = values[j] + (values[j + 1] - values[j]) * t;
            }
        }
        return values[0];
    }


    private void threeLine(){
        Path mPath = path;
        mPath.moveTo(100,700);
        //绝对位置，同二阶
//        mPath.cubicTo(250,600,500,800,700,700);
        //相对位置
        mPath.rCubicTo(150,-100,400,100,600,0);
    }


    private void twoLine(){
        Path mPath = path;
        mPath.moveTo(100,500);
        //绝对位置（相对于左上角)
//        mPath.quadTo(400,250,700,500);
        //相对于起点的位置
        mPath.rQuadTo(300,-250,600,0);
    }

    private void oneLine(){
        Path mPath = path;
        /**
         * 起点位置(画笔移动到 x = 50 y = 50 的位置)
         */
        mPath.moveTo(100,100);
        /**
         * 终点位置
         */
        mPath.lineTo(700,300);
    }

}
