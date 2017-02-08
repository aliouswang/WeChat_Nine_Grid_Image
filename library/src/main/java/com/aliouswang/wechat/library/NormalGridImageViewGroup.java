package com.aliouswang.wechat.library;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

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
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

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
        final GenericRequestBuilder<Uri, InputStream, BitmapFactory.Options, BitmapFactory.Options>
                SIZE_REQUEST = Glide.with(getContext())
                .using(new StreamUriLoader(getContext()), InputStream.class)
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

        Glide.with(getContext()).load(imageUrl).into(childView);
        SIZE_REQUEST.load(Uri.parse(imageUrl))
                .into(new SimpleTarget<BitmapFactory.Options>() {
                    @Override
                    public void onResourceReady(BitmapFactory.Options resource, GlideAnimation<? super BitmapFactory.Options> glideAnimation) {
                        Logger.e(String.format(Locale.ROOT, "%dx%d", resource.outWidth, resource.outHeight));
                    }
                });
    }
}
