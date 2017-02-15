package com.aliouswang.wechat.library.utils;

import android.content.Context;

/**
 * Created by aliouswang on 16/9/5.
 */
public class DpUtil {

    /**
     * dip to dp
     *
     * @param context
     * @param dip
     * @return
     */
    public static int dipToPx(Context context, int dip) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

}
