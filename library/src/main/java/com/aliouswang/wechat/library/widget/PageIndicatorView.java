package com.aliouswang.wechat.library.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.aliouswang.wechat.library.utils.DpUtil;

/**
 * Page indicator drawable
 *
 * Created by aliouswang on 16/9/8.
 */
public class PageIndicatorView extends View {
    private static final int DEFAULT_HEIGHT_DP = 30;
    private static final int DEFAULT_RADIUS_DP = 3;
    private static final int DEFAULT_GAP_DP = 8;

    private int mDrawWidth;
    private int mDrawHeight;
    private Paint mNormalPaint;
    private Paint mFocusPaint;

    private int mCircleCount;
    private int mCurrentFocus;

    private int mNormalColor = Color.GRAY;
    private int mFocusColor = Color.GREEN;

    public PageIndicatorView(Context context) {
        this(context, null);
    }

    public PageIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mNormalPaint = new Paint();
        mNormalPaint.setColor(mNormalColor);
        mNormalPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        mFocusPaint = new Paint();
        mFocusPaint.setColor(mFocusColor);
        mFocusPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    public void setCircle(int count, int focus) {
        this.mCircleCount = count;
        this.mCurrentFocus = focus;
    }

    public void notifyFocusChanged(int focus) {
        this.mCurrentFocus = focus;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        mDrawWidth = width;
        switch (heightMode) {
            case MeasureSpec.AT_MOST:
                mDrawHeight = DpUtil.dipToPx(getContext(), DEFAULT_HEIGHT_DP);
                break;
            case MeasureSpec.EXACTLY:
                mDrawHeight = height;
                break;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int radius = DpUtil.dipToPx(getContext(), DEFAULT_RADIUS_DP);
        int gap = DpUtil.dipToPx(getContext(), DEFAULT_GAP_DP);
        int totalSpace = (radius * 2 * mCircleCount) + (gap * (mCircleCount - 1));
        int startX = (mDrawWidth - totalSpace) / 2;
        int startY = mDrawHeight / 2;
        for (int i = 0; i < mCircleCount; i++) {
            if (i == mCurrentFocus) {
                canvas.drawCircle(startX + radius, startY, radius, mFocusPaint);
            }else {
                canvas.drawCircle(startX + radius, startY, radius, mNormalPaint);
            }
            startX += gap + 2 * radius;
        }
    }
}
