package com.aliouswang.wechat.ninegridimage.strategy;

import android.graphics.BitmapFactory;
import android.net.Uri;

import com.aliouswang.wechat.library.interfaces.IImageLoadStrategy;
import com.aliouswang.wechat.library.widget.RatioImageView;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.model.stream.StreamUriLoader;
import com.bumptech.glide.load.resource.SimpleResource;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by aliouswang on 17/2/8.
 */

public class GlideImageLoadStrategy implements IImageLoadStrategy<RatioImageView>{

    @Override
    public void loadImage(final RatioImageView scaleImageView, String imageUrl) {
        final GenericRequestBuilder<Uri, InputStream, BitmapFactory.Options, BitmapFactory.Options>
                SIZE_REQUEST = Glide.with(scaleImageView.getContext())
                .using(new StreamUriLoader(scaleImageView.getContext()), InputStream.class)
                .from(Uri.class)
                .as(BitmapFactory.Options.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new ResourceDecoder<File, BitmapFactory.Options>() {
                    @Override
                    public Resource<BitmapFactory.Options> decode(File source, int width, int height) throws IOException {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(source.getAbsolutePath(), options);
                        return new SimpleResource<>(options);
                    }

                    @Override
                    public String getId() {
                        return getClass().getName();
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<Uri, BitmapFactory.Options>() {
                    @Override
                    public boolean onException(Exception e, Uri model, Target<BitmapFactory.Options> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(BitmapFactory.Options resource, Uri model, Target<BitmapFactory.Options> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                });

        Glide.with(scaleImageView.getContext()).load(imageUrl).into(scaleImageView);
        SIZE_REQUEST.load(Uri.parse(imageUrl))
                .into(new SimpleTarget<BitmapFactory.Options>() {
                    @Override
                    public void onResourceReady(BitmapFactory.Options resource, GlideAnimation<? super BitmapFactory.Options> glideAnimation) {
//                        Logger.e(String.format(Locale.ROOT, "%dx%d", resource.outWidth, resource.outHeight));
                        scaleImageView.setScale((float)resource.outWidth / (float)resource.outHeight);
                    }
                });
    }

}
