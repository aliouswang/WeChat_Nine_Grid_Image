package com.aliouswang.wechat.ninegridimage.strategy;

import com.aliouswang.wechat.library.interfaces.IImageLoadStrategy;
import com.aliouswang.wechat.library.widget.RatioImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by aliouswang on 17/2/13.
 */

public class PicassoImageLoadStrategy implements IImageLoadStrategy<RatioImageView>{


    @Override
    public void loadImage(RatioImageView scaleImageView, String imageUrl) {
        Picasso.with(scaleImageView.getContext()).load(imageUrl)
                .into(scaleImageView);
    }
}
