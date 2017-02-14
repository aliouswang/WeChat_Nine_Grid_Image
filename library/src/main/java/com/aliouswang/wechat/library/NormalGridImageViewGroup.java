package com.aliouswang.wechat.library;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by aliouswang on 17/2/7.
 */

public class NormalGridImageViewGroup extends AbstractGridImageViewGroup<ScaleImageView>{

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
    protected ScaleImageView createView() {
        ScaleImageView imageView = new ScaleImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

}
