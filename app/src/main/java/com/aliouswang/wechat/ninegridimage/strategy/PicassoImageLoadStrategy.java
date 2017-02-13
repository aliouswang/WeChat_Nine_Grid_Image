package com.aliouswang.wechat.ninegridimage.strategy;

import com.aliouswang.wechat.library.IImageLoadStrategy;
import com.aliouswang.wechat.library.ScaleImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by aliouswang on 17/2/13.
 */

public class PicassoImageLoadStrategy implements IImageLoadStrategy<ScaleImageView>{


    @Override
    public void loadImage(ScaleImageView scaleImageView, String imageUrl) {
        Picasso.with(scaleImageView.getContext()).load(imageUrl).into(scaleImageView);
    }
}
