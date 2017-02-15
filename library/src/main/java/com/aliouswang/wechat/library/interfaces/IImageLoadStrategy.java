package com.aliouswang.wechat.library.interfaces;

/**
 * Image load strategy, such as : fresco, glide, picasso, universal image loader, volley.
 *
 * Created by aliouswang on 17/2/8.
 */

public interface IImageLoadStrategy<T> {

    void loadImage(T t, String imageUrl);

}
