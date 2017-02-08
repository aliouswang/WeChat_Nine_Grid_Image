package com.aliouswang.wechat.ninegridimage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.aliouswang.wechat.library.AbstractGridImageViewGroup;

/**
 * Created by aliouswang on 17/2/8.
 */

public class FrescoGridImageViewGroup extends AbstractGridImageViewGroup<ImageView>{
    public FrescoGridImageViewGroup(Context context) {
        super(context);
    }

    public FrescoGridImageViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FrescoGridImageViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected ImageView createView() {
        return null;
    }
}
