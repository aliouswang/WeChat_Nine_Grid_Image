package com.aliouswang.wechat.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by aliouswang on 17/2/8.
 */

public class ScaleImageView extends ImageView{

    private float scale = 1.0f;

    public ScaleImageView(Context context) {
        this(context, null);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray t = context.obtainStyledAttributes(attrs,
                R.styleable.ScaleImageView, 0, 0);
        scale = t.getFloat(R.styleable.ScaleImageView_siv_scale, 1.0f);
        t.recycle();
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
//        if (drawable instanceof BitmapDrawable) {
            this.scale = (float)drawable.getIntrinsicHeight() / (float)drawable.getIntrinsicWidth();
//        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float width = MeasureSpec.getSize(widthMeasureSpec);
        float height = width * scale;
        setMeasuredDimension((int)width, (int)height);
    }

}
