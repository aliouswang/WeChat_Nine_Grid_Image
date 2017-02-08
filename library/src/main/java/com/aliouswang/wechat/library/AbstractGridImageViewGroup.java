package com.aliouswang.wechat.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by aliouswang on 17/2/7.
 */

public abstract class AbstractGridImageViewGroup<T extends View> extends ViewGroup{

    private static final int DEFAULT_HORIZONTAL_GAP = 15;
    private static final int DEFAULT_VERTICAL_GAP = 15;
    private static final int DEFAULT_COLUMN_COUNT = 3;
    private static final float DEFAULT_RATIO = 1.0f;
    private static final float DEFAULT_SINGLE_IMAGE_RATIO = 2.0f;
    private static final int DEFAULT_MAX_IMAGE_COUNT = 9;

    private int mHorizontalGap;
    private int mVerticalGap;
    private int mColumnCount = DEFAULT_COLUMN_COUNT;
    private float mRatio = DEFAULT_RATIO;
    private float mSingleImageRatio = DEFAULT_SINGLE_IMAGE_RATIO;
    private int mMaxImageCount = DEFAULT_MAX_IMAGE_COUNT;

    private Adapter mImageAdapter;
    private float mCellWidth;
    private float mCellHeight;

    public AbstractGridImageViewGroup(Context context) {
        this(context, null);
    }

    public AbstractGridImageViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbstractGridImageViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyleAttr(context, attrs);
    }

    private void initStyleAttr(Context context, AttributeSet attrs) {
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.AbstractGridImageViewGroup, 0, 0);
        mHorizontalGap = t.getDimensionPixelSize(R.styleable.AbstractGridImageViewGroup_horizontal_gap, DEFAULT_HORIZONTAL_GAP);
        mVerticalGap = t.getDimensionPixelSize(R.styleable.AbstractGridImageViewGroup_vertical_gap, DEFAULT_VERTICAL_GAP);
        mColumnCount = t.getInt(R.styleable.AbstractGridImageViewGroup_column_count, DEFAULT_COLUMN_COUNT);
        mRatio = t.getFloat(R.styleable.AbstractGridImageViewGroup_radio, DEFAULT_RATIO);
        mSingleImageRatio = t.getFloat(R.styleable.AbstractGridImageViewGroup_single_image_ratio, DEFAULT_SINGLE_IMAGE_RATIO);
        mMaxImageCount = t.getInt(R.styleable.AbstractGridImageViewGroup_max_image_count, DEFAULT_MAX_IMAGE_COUNT);
        t.recycle();
    }

    public void setImageAdapter(Adapter imageAdapter) {
        mImageAdapter = imageAdapter;
        reuseChildView();
        requestLayout();
    }

    private void reuseChildView() {
        int childCount = getChildCount();
        int cellCount = getImageCount();
        int diff = childCount - cellCount;
        if (diff < 0) {
            for (int i = 0; i < childCount; i ++) {
                T childView = (T) getChildAt(i);
                childView.setVisibility(VISIBLE);
                loadImage(childView, mImageAdapter.getImageUrl(i));
            }
            for (int i = 0; i < Math.abs(diff); i++) {
                T childView = createView();
                addView(childView);
                loadImage(childView, mImageAdapter.getImageUrl(childCount + i));
            }
        }else if (diff == 0) {
            for (int i = 0; i < childCount; i ++) {
                T childView = (T) getChildAt(i);
                childView.setVisibility(VISIBLE);
                loadImage(childView, mImageAdapter.getImageUrl(i));
            }
        }else {
            for (int i = 0; i < childCount; i ++) {
                T childView = (T) getChildAt(i);
                if (i < cellCount) {
                    childView.setVisibility(VISIBLE);
                    loadImage(childView, mImageAdapter.getImageUrl(i));
                }else {
                    childView.setVisibility(GONE);
                }
            }
        }
    }

    protected abstract T createView();

    protected abstract void loadImage(T childView, String imageUrl);

    public int getImageCount() {
        return mImageAdapter != null ? Math.min(mImageAdapter.getCount(), mMaxImageCount) : 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int cellCount = getImageCount();
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (cellCount == 0) {
            setMeasuredDimension(0, 0);
        }else if (cellCount == 1) {
            float cellWidth = (width - getPaddingLeft() - getPaddingRight()) / mSingleImageRatio;
            float cellHeight = cellWidth * mRatio;
            int height = (int) (cellHeight + getPaddingTop() + getPaddingBottom());
            setMeasuredDimension(width, height);
            mCellWidth = cellWidth;
            mCellHeight = cellHeight;
        }else {
            int row = (int) Math.ceil(cellCount / mColumnCount);
            float cellWidth = (width - getPaddingLeft() - getPaddingRight() - (mHorizontalGap * (mColumnCount - 1))) / mColumnCount;
            float cellHeight = cellWidth * mRatio;
            int height = (int) (cellHeight * row) + getPaddingTop() + getPaddingBottom() + ((row - 1) * mVerticalGap);
            setMeasuredDimension(width, height);
            mCellWidth = cellWidth;
            mCellHeight = cellHeight;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int cellCount = getImageCount();
//        Logger.e("cellWidth:" + mCellWidth + ";cellHeight:" + mCellHeight
//                + ";width:" + getMeasuredWidth() + ";height:" + getMeasuredHeight()
//        );
        for (int i = 0; i < cellCount; i++) {
            int row = i / mColumnCount;
            int column = i % mColumnCount;
            View childView = getChildAt(i);
            int l = (int) (left + getPaddingLeft() + (column * (mCellWidth + mHorizontalGap)));
            int r = (int) (l + mCellWidth);
            int t = (int) ( getPaddingTop() + (row * (mCellHeight + mVerticalGap)));
            int b = (int) (t + mCellHeight);
            childView.layout(l, t, r, b);

        }
    }

    public interface Adapter {
        int getCount();
        String getImageUrl(int index);
    }
}
