package com.yanxin.quickslidebar.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by YanXin on 2016/6/30.
 */
public class QuickSlideBar extends View {

    private List<String> mLetters;
    private int mChoose = -1;
    private Paint mPaint = new Paint();

    private int mTextSize;
    private int mTextSizeChoose;
    private int mTextColor;
    private int mTextColorChoose;

    private int mWidth;
    private int mHeight;
    private float mItemHeight;
    private float mLetterContentHeight;
    private float mMinY;
    private float mMaxY;
    private float mFontHeight;

    private int mTopOffset = 0;
    private float mFontPadding = 20;

    private OnSlideBarTouchListener mOnSlideBarTouchListener;

    public QuickSlideBar(Context context) {
        this(context, null);
    }

    public QuickSlideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickSlideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mLetters = new ArrayList<>();

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.QuickSlideBar);
        mTextColor = array.getColor(R.styleable.QuickSlideBar_textColor, Color.BLACK);
        mTextColorChoose = array.getColor(R.styleable.QuickSlideBar_textColorChoose, Color.BLACK);
        mTextSize = array.getDimensionPixelSize(R.styleable.QuickSlideBar_textSize,
                getResources().getDimensionPixelSize(R.dimen.textSize));
        mTextSizeChoose = array.getDimensionPixelSize(R.styleable.QuickSlideBar_textSizeChoose,
                getResources().getDimensionPixelSize(R.dimen.textSize_choose));
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
    }

    private float getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return fontMetrics.bottom - fontMetrics.top;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mLetters.size() == 0)
            return;

        mFontHeight = Math.min(getFontHeight(mTextSize), mHeight / mLetters.size());
        mLetterContentHeight = (mFontHeight + mFontPadding) * mLetters.size() * 1f;

        if (mLetterContentHeight > mHeight) {
            mFontPadding = (mHeight - mFontHeight * mLetters.size()) / mLetters.size();
            mLetterContentHeight = (mFontHeight + mFontPadding) * mLetters.size() * 1f;
        }

        mItemHeight = 1f * mLetterContentHeight / mLetters.size();
        mMinY = (mHeight - mLetterContentHeight) * 1f / 2 - mTopOffset;
        mMaxY = mMinY + mLetterContentHeight;

        for (int i = 0; i < mLetters.size(); i++) {
            mPaint.setColor(mTextColor);
            mPaint.setAntiAlias(true);
            mPaint.setTextSize(mTextSize);

            if (i == mChoose) {
                mPaint.setColor(mTextColorChoose);
                mPaint.setFakeBoldText(true);
                mPaint.setTypeface(Typeface.DEFAULT_BOLD);
                mPaint.setTextSize(mTextSizeChoose);
            }

            Rect rect = new Rect();
            mPaint.getTextBounds(mLetters.get(i), 0, mLetters.get(i).length(), rect);

            float xPos = (mWidth - rect.width()) * 0.5f;
            float yPos = mMinY + 1.0f * mFontPadding / 2 + 1.0f * (mFontPadding + mFontHeight) * i;

            canvas.drawText(mLetters.get(i), xPos, yPos + mFontHeight, mPaint);
            mPaint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float y = event.getY();
        int oldChoose = mChoose;
        int newChoose = (int) Math.floor(1.0f * (y - mMinY) / mItemHeight);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (y < mMinY || y > mMaxY)
                    return super.dispatchTouchEvent(event);
                verifyChanged(oldChoose, newChoose);
                if (mOnSlideBarTouchListener != null)
                    mOnSlideBarTouchListener.onTouch(true);
                break;
            case MotionEvent.ACTION_MOVE:
                verifyChanged(oldChoose, newChoose);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mChoose = -1;
                if (mOnSlideBarTouchListener != null)
                    mOnSlideBarTouchListener.onTouch(false);
                invalidate();
                break;
        }
        return true;
    }

    private void verifyChanged(int oldChoose, int newChoose) {
        if (oldChoose == newChoose)
            return;

        if (newChoose < 0 || newChoose >= mLetters.size())
            return;

        mChoose = newChoose;
        if (mOnSlideBarTouchListener != null)
            mOnSlideBarTouchListener.onLetterChanged(mLetters.get(newChoose), mChoose);
        invalidate();
    }

    public void setOnSlideBarTouchListener(OnSlideBarTouchListener onSlideBarTouchListener) {
        mOnSlideBarTouchListener = onSlideBarTouchListener;
    }

    public void setLetters(List<String> letters) {
        mLetters.clear();
        mLetters.addAll(letters);
        Collections.sort(mLetters);
        invalidate();
    }

    public interface OnSlideBarTouchListener {
        void onTouch(boolean touched);

        void onLetterChanged(String currentLetter, int letterPosition);
    }

}
