package com.aliouswang.wechat.library;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by aliouswang on 17/2/7.
 */

public class NormalGridImageViewGroup extends AbstractGridImageViewGroup<ImageView>{

    public NormalGridImageViewGroup(Context context) {
        super(context);
    }

    public NormalGridImageViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NormalGridImageViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected ImageView createView() {
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    protected void loadImage(ImageView childView, String imageUrl) {
        Glide.with(getContext()).load(imageUrl).into(childView);
    }
}