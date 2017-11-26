package com.example.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.example.myapplication.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by liubin on 2017/11/25.
 */

public class LineView extends View{

    private Context mContext;
    private Paint mPaint;
    private Path mPath;

    //设置间距
    private int lineSpace;
    //线条方向
    private int orientation = LINE_HORIZONTAL;
    //线条模式
    private int lineMode = LINE_DOT;
    //线条颜色
    private int lineBackground = Color.BLACK;
    //线条宽度
    private int lineWidth;
    //线条装饰
    private Bitmap lineBitmap;


    public LineView(Context context) {
        this(context,null);
    }

    public LineView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttr(attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if(orientation == LINE_HORIZONTAL){
            //如果横线是横向并且高度是 wrap_content
            //则设置固定高度
            if(heightMode == MeasureSpec.AT_MOST){
                heightSize = lineWidth;
                //如果是圆点,则设置圆点的直径为高度
                if(lineMode == LINE_DOT){
                    heightSize = lineWidth * 2;
                }else if(lineBitmap != null){
                    heightSize = lineBitmap.getWidth();
                }
                heightSize = heightSize + getPaddingTop() + getPaddingBottom();
            }
        }else {
            if(widthMode == MeasureSpec.AT_MOST){
                widthSize = lineWidth;
                if(lineMode == LINE_DOT){
                    widthSize = lineWidth * 2;
                }else if(lineBitmap != null){
                    widthSize = lineBitmap.getHeight();
                }
                widthSize = widthSize + getPaddingLeft() + getPaddingRight();
            }
        }
        setMeasuredDimension(widthSize,heightSize);
    }

    /**
     * 设置属性
     */
    private void initAttr(AttributeSet attrs){
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.LineView);
        lineSpace = typedArray.getDimensionPixelSize(R.styleable.LineView_lineSpace,lineSpace);
        orientation = typedArray.getInt(R.styleable.LineView_lineOrientation,orientation);
        lineMode = typedArray.getInt(R.styleable.LineView_lineMode,lineMode);
        lineBackground = typedArray.getColor(R.styleable.LineView_lineBackground,lineBackground);
        lineWidth = typedArray.getDimensionPixelSize(R.styleable.LineView_lineWidth,lineWidth);
        Drawable drawable = typedArray.getDrawable(R.styleable.LineView_lineDrawable);
        if(drawable != null && drawable instanceof BitmapDrawable){
            lineBitmap = ((BitmapDrawable)drawable).getBitmap();
        }
        typedArray.recycle();
    }

    private void init(){
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(lineBackground);
        if(lineWidth <= 0){
            lineWidth = dp2px(1);
        }
        if(lineSpace <= 0){
            lineSpace = dp2px(3);
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(lineBitmap != null){
            drawBitmap(canvas);
        }else if(lineMode == LINE_NORMAL){
            drawLine(canvas);
        }else if(lineMode == LINE_DASHED){
            drawDashedLine(canvas);
        }else if(lineMode == LINE_DOT){
            drawDotLine(canvas);
        }
    }

    /**
     * 画背景图片为分界线
     * @param canvas
     */
    private void drawBitmap(Canvas canvas){
        if(lineBitmap == null) return;
        int bitmapWidth = lineBitmap.getWidth();
        int bitmapHeight = lineBitmap.getHeight();
        if(orientation == LINE_HORIZONTAL){
            for(int i = 0;;i++){
                canvas.drawBitmap(lineBitmap,i,(getHeight() - bitmapHeight) / 2,mPaint);
                i += bitmapWidth + lineSpace ;
                if(i > getWidth()){
                    break;
                }
            }
        }else {
            for(int i = 0;;i++){
                if(i > getHeight()){
                    break;
                }
                canvas.drawBitmap(lineBitmap,(getWidth() - bitmapWidth) / 2,i,mPaint);
                i += bitmapHeight + lineSpace ;
            }
        }

    }


    /**
     * 画普通直线
     */
    private void drawLine(Canvas canvas){
        int center = 0;
        mPath.reset();
        mPaint.setStrokeWidth(lineWidth);

        if(orientation == LINE_HORIZONTAL){
            center = getHeight() / 2;
            mPath.moveTo(0, center);
            mPath.lineTo(getWidth(),center);
        }else {
            center = getWidth() / 2;
            mPath.moveTo(center, 0);
            mPath.lineTo(center,getHeight());
        }
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 画虚线
     * @param canvas
     */
    private void drawDashedLine(Canvas canvas){
        int center = 0;
        mPath.reset();
        mPaint.setStrokeWidth(lineWidth);
        if(orientation == LINE_HORIZONTAL){
            center = getHeight() / 2;
            mPath.moveTo(0, center);
            mPath.lineTo(getWidth(),center);
        }else {
            center = getWidth() / 2;
            mPath.moveTo(center, 0);
            mPath.lineTo(center,getHeight());
        }
        mPaint.setPathEffect(new DashPathEffect(new float[] {lineSpace, lineSpace}, 0));
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 画圆点虚线
     * @param canvas
     */
    private void drawDotLine(Canvas canvas){
        int center = 0;
        mPath.reset();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(lineWidth);
        for(int i = lineWidth; ;i++){
            if(orientation == LINE_HORIZONTAL){
                center = getHeight() / 2;
                canvas.drawCircle(i,center,lineWidth,mPaint);
                if(i > getWidth()){
                    break;
                }
            }else {
                center = getWidth() / 2; // 如果当前宽度小于圆点宽度 则设置圆点直径为宽度
                canvas.drawCircle(center,i,lineWidth,mPaint);
                Log.d("xy","x="+center+"y ="+i);
                if(i > getHeight()){
                    break;
                }
            }
            i += lineWidth * 2 + lineSpace ;
        }

    }

    /**
     * 设置线条类型
     * @param lineMode
     */
    public void setLineMode(@LineMode int lineMode){
        this.lineMode = lineMode;
        invalidate();
    }



    private int dp2px(float dp){
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int)(dp * density + 0.5);
    }


    private int px2dp(float px){
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int)(px / density + 0.5);
    }

    @IntDef({LINE_HORIZONTAL,LINE_VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LineOrientation{}

    //横向
    private static final int LINE_HORIZONTAL = 0;
    //竖向
    private static final int LINE_VERTICAL = 1;


    @IntDef({LINE_NORMAL,LINE_DASHED, LINE_DOT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LineMode {}

    /**
     * 正常模式(——————)
     */
    private static final int LINE_NORMAL = 0;

    /**
     * 虚线模式(-----)
     */
    private static final int LINE_DASHED = 1;

    /**
     * 原点模式(......)
     */
    private static final int LINE_DOT = 2;


}
