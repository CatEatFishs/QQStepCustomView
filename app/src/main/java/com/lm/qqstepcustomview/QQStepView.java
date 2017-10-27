package com.lm.qqstepcustomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Lm on 2017/10/25.
 * Email:1002464056@qq.com
 * 仿QQ运动步数
 */

public class QQStepView extends View {

    private int mOuterColor= Color.RED;
    private int mInnerColor=Color.BLUE;
    private int mBorderWidth=20;//20px
    private int mStepTextSize;
    private int mStepTextColor=Color.RED;
    private Paint mOuterPaint;
    private Paint mInnerPaint;
    private Paint mTextPaint;
    private int mStepMax=100;
    private int mCurrentStep=50;

    public QQStepView(Context context) {
        this(context,null);
    }

    public QQStepView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QQStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //1.分析效果
        //2.确定自定义属性，写attrs.xml
        //3.在布局中使用
        //4.在自定义View中获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mOuterColor=array.getColor(R.styleable.QQStepView_outerColor,mOuterColor);
        mInnerColor=array.getColor(R.styleable.QQStepView_innerColor,mInnerColor);
        mBorderWidth= (int) array.getDimension(R.styleable.QQStepView_borderWidth,mBorderWidth);
        mStepTextColor=array.getColor(R.styleable.QQStepView_stepTextColor,mStepTextColor);
        mStepTextSize=array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize,mStepTextSize);
        array.recycle();

        mOuterPaint=new Paint();
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setStrokeWidth(mBorderWidth);
        mOuterPaint.setColor(mOuterColor);
        mOuterPaint.setStyle(Paint.Style.STROKE);
        mOuterPaint.setStrokeCap(Paint.Cap.ROUND);

        mInnerPaint=new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(mBorderWidth);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);


        mTextPaint=new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mStepTextSize);
        mTextPaint.setColor(mStepTextColor);

        //7.其他

    }
    //5.onMeasure
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //高度不一致 取最小值
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int heigh=MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width>heigh?heigh:width,width>heigh?heigh:width);
    }

    //6.画外圆弧 画内圆弧 画文字
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //6.1 画外圆弧
        int center=getWidth()/2;
        int radius=getWidth()/2-mBorderWidth/2;
        RectF rectF=new RectF(center-radius,center-radius,center+radius,center+radius);
        canvas.drawArc(rectF,135,270,false,mOuterPaint);

        //6.2 画内圆弧

        if (mStepMax==0) return;
        float sweepAngle=(float)mCurrentStep/mStepMax;
        canvas.drawArc(rectF,135,sweepAngle*270,false,mInnerPaint);

        //6.3画文字
        String setpText=mCurrentStep+"";
        Rect textBounds=new Rect();
        mTextPaint.getTextBounds(setpText,0,setpText.length(),textBounds);
        float dx=getWidth()/2-textBounds.width()/2;
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        float dy=(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
        int baseLine= (int) (getHeight()/2+dy);
        canvas.drawText(setpText,dx,baseLine,mTextPaint);
    }
    public synchronized void setStepMax(int stepMax){
        this.mStepMax=stepMax;
    }
    public synchronized void setCurrentStep(int currentStep){
        this.mCurrentStep=currentStep;
        invalidate();

    }
}
