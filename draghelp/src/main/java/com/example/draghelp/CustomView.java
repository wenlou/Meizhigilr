package com.example.draghelp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sxj52 on 2016/10/9.
 */

public class CustomView extends View implements Runnable{
    private Paint mPaint;
    private int radiu;
    private Context mContext;
    public CustomView(Context context) {
        this(context,null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private void init() {
        // 实例化画笔并打开抗锯齿
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
         /*
         * 设置画笔样式为描边，圆环嘛……当然不能填充不然就么意思了
         *
         * 画笔样式分三种：
         * 1.Paint.Style.STROKE：描边
         * 2.Paint.Style.FILL_AND_STROKE：描边并填充
         * 3.Paint.Style.FILL：填充
         */
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.argb(255, 255, 128, 103));
        /*
         * 设置描边的粗细，单位：像素px
         * 注意：当setStrokeWidth(0)的时候描边宽度并不为0而是只占一个像素
         */
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawCircle(MeasureUtil.getScreenSize((Activity) mContext)[0] / 2, MeasureUtil.getScreenSize((Activity) mContext)[1] / 2, radiu, mPaint);
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, radiu,mPaint);
        super.onDraw(canvas);
    }
//    protected  synchronized void setraid(int radiu){
//            this.radiu=radiu;
//        invalidate();
//    }

    @Override
    public void run() {
        /*
		 * 确保线程不断执行不断刷新界面
		 */
        while (true) {
            try {
				/*
				 * 如果半径小于200则自加否则大于200后重置半径值以实现往复
				 */
                if (radiu <= 200) {
                    radiu += 10;

                    // 刷新View
                    postInvalidate();
                } else {
                    radiu = 0;
                }

                // 每执行一次暂停40毫秒
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
