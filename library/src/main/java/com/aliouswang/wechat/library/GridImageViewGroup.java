package com.aliouswang.wechat.library;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by aliouswang on 17/2/7.
 */

public class GridImageViewGroup extends ViewGroup{

    public GridImageViewGroup(Context context) {
        this(context, null);
    }

    public GridImageViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridImageViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }
}
