package com.example.myapplication.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by liubin on 2017/6/10.
 */

public class TouchPullView extends View {

    private static final String TAG = "TouchPullView";

    Paint mCirclePaint;

    //花园的起始位置
    float sx,sy;
    int circleRadius = 30;
    private float mProgress;
    private float drapHeight = 400f;
    //目标宽度
    private int targetWidth = 300;
    //贝塞尔曲线画笔与路径
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path mPath = new Path();
    //重点点，决定控制点的 Y 点
    private int gravityHeight;
    //角度的变化最大值
    private int mTangentAngle = 100;
    //进度差值器
    private Interpolator progressInterpolator = new DecelerateInterpolator();
    private Interpolator tangentAngleInterpolator;
    private ValueAnimator valueAnimator;

    public TouchPullView(Context context) {
        super(context);
        init();
    }

    public TouchPullView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TouchPullView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TouchPullView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init(){
        mCirclePaint = new Paint();
        //抗锯齿
        mCirclePaint.setAntiAlias(true);
        //设置颜色
        mCirclePaint.setColor(0xff00ff00);
        //设置实心
        mCirclePaint.setStyle(Paint.Style.FILL);
        //设置抗抖动
        mCirclePaint.setDither(true);

        //控制点
        mPaint.setAntiAlias(true);
        //设置颜色
        mPaint.setColor(0xff00ff00);
        //设置实心
        mPaint.setStyle(Paint.Style.FILL);
        //设置抗抖动
        mPaint.setDither(true);

        //切角差值器
        tangentAngleInterpolator = PathInterpolatorCompat.create((circleRadius * 2f) / drapHeight,90f / mTangentAngle);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int measureWidth = 0;
        int measureHeight = 0;
        //表示固定值
        if(widthMode == MeasureSpec.EXACTLY){
            measureWidth = width;
        }else //表示最大值
        if(widthMode == MeasureSpec.AT_MOST){

            measureWidth = Math.min(2*circleRadius + (getPaddingLeft() + getPaddingRight()),width);
        }else //
        if(widthMode == MeasureSpec.UNSPECIFIED){
            measureWidth = width;
        }
        //表示固定值
        if(heightMode == MeasureSpec.EXACTLY){
            measureHeight = height;
        }else //表示最大值
        if(heightMode == MeasureSpec.AT_MOST){
            measureHeight = Math.min((int)(drapHeight * mProgress + (getPaddingTop() + getPaddingBottom())),width);
        }else //
        if(heightMode == MeasureSpec.UNSPECIFIED){
            measureHeight = height;
        }

        setMeasuredDimension(measureWidth,measureHeight);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        updatePathLayout();
    }

    //释放动画
    public void release(){
        if(valueAnimator == null){
            valueAnimator = ValueAnimator.ofFloat(mProgress,0f);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.setDuration(400);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Object val = animation.getAnimatedValue();
                    if(val instanceof Float){
                        setProgress((Float) val);
                    }
                }
            });
        }else {
            valueAnimator.cancel();
            valueAnimator.setFloatValues(mProgress,0f);
        }
        valueAnimator.start();
    }


    //大小改变是更新路径
    private void updatePathLayout() {
        final float progress = progressInterpolator.getInterpolation(mProgress);
        //绘制区域的宽高
        float w = getValueByLine(getWidth(),targetWidth,mProgress);
        float h = getValueByLine(0,drapHeight,mProgress);
        //x 的中心点
        float cPointX = w / 2f;
        //圆半径
        float cRadius = circleRadius;
        //圆的 Y 坐标
        float cPointY = h - cRadius;
        //控制点 Y 结束值
        float endControlY = gravityHeight;

        //更新圆心坐标
        sx = cPointX;
        sy = cPointY;

        final Path path = mPath;
        path.reset();
        path.moveTo(0,0);

        //左边控制点与结束点
        float lControlPointX,lControlPointY;
        float lEndPointX,lEndPointY;

        //获取切线的弧度
        float angle = mTangentAngle * tangentAngleInterpolator.getInterpolation(progress);
        double radian = Math.toRadians(angle);
        float x = (float) (Math.sin(radian) * cRadius);
        float y = (float) (Math.cos(radian) * cRadius);

        lEndPointX = cPointX - x;
        lEndPointY = cPointY + y;

        //控制点的Y 变化
        lControlPointY = getValueByLine(0,endControlY,progress);
        //控制点与结束点的高度
        float tHegith = cPointY - lControlPointY;
        //控制点与X的距离
        float tWeight = (float) (tHegith / Math.tan(radian));

        lControlPointX = lEndPointX - tWeight;
        //左边的贝塞尔曲线
        path.quadTo(lControlPointX,lControlPointY,lEndPointX,lEndPointY);
        //移动位置
        path.lineTo(cPointX + (cPointX - lEndPointX),lEndPointY);
        //右边的曲线
        path.quadTo(cPointX + (cPointX - lControlPointX),lControlPointY,w,0);
    }

    /**
     * 获取当前值
     * @param start
     * @param end
     * @param progress
     * @return
     */
    private float getValueByLine(float start,float end,float progress){
        return start +(end - start) * progress;
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        int count = canvas.save();
        float tranX = (getWidth() - getValueByLine(getWidth(),targetWidth,mProgress)) / 2;

        canvas.translate(tranX,0);

        //画贝塞尔曲线
        canvas.drawPath(mPath,mPaint);

        //画园
        canvas.drawCircle(sx,sy,circleRadius,mCirclePaint);

        canvas.restoreToCount(count);
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
        Log.d(TAG, "setProgress: "+progress);
        //重绘布局 onMeasure 方法会调用
        requestLayout();
    }
}
