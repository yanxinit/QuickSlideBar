package com.yanxin.quickslidebar.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by YanXin on 2016/6/30.
 */
public class QuickSlideBarTip extends View {

    private int mTextColor;
    private int mTextSize;
    private String mText;

    private Paint mPaint;

    public QuickSlideBarTip(Context context) {
        this(context, null);
    }

    public QuickSlideBarTip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(MeasureSpec.getMode(widthMeasureSpec), MeasureSpec.getSize(widthMeasureSpec));
        int height = measureHeight(MeasureSpec.getMode(heightMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        setMeasuredDimension(width, height);
    }

    private int measureHeight(int mode, int size) {
        switch (mode) {
            case MeasureSpec.AT_MOST:
                int width = (int) (mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top + getPaddingTop() + getPaddingTop());
                return Math.min(size, width);
            default:
                return size;
        }
    }

    private int measureWidth(int mode, int size) {
        switch (mode) {
            case MeasureSpec.AT_MOST:
                int width = (int) (mPaint.measureText(mText) + getPaddingLeft() + getPaddingRight());
                return Math.min(size, width);
            default:
                return size;
        }
    }

    public QuickSlideBarTip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.QuickSlideBarTip, defStyleAttr, 0);
        mTextColor = typedArray.getColor(R.styleable.QuickSlideBarTip_tipTextColor, Color.BLACK);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.QuickSlideBarTip_tipTextSize, 32);
        mText = typedArray.getString(R.styleable.QuickSlideBarTip_tipText);
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);

        setVisibility(View.GONE);
    }

    public void setText(String text) {
        mText = text;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Rect rect = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), rect);

        canvas.drawText(mText, 0, mText.length(),
                getMeasuredWidth() / 2 - rect.width() / 2 - rect.left,
                getMeasuredHeight() / 2 + rect.height() / 2 - rect.bottom,
                mPaint);
    }

}
