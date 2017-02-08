package com.aliouswang.wechat.ninegridimage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliouswang.wechat.library.AbstractGridImageViewGroup;
import com.aliouswang.wechat.library.IImageLoadStrategy;
import com.aliouswang.wechat.library.NormalGridImageViewGroup;
import com.aliouswang.wechat.ninegridimage.model.Feed;

import java.util.ArrayList;

/**
 * Created by aliouswang on 17/2/7.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder>{

    private ArrayList<Feed> mFeeds;

    private IImageLoadStrategy mImageLoadStrategy;

    public void setImageLoadStrategy(IImageLoadStrategy imageLoadStrategy) {
        mImageLoadStrategy = imageLoadStrategy;
    }

    public void setFeeds(ArrayList<Feed> feeds) {
        mFeeds = feeds;
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_item_layout, parent, false);
        return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, final int position) {
        NormalGridImageViewGroup imageViewGroup = holder.mNormalGridImageViewGroup;
        imageViewGroup.setImageLoadStrategy(mImageLoadStrategy);
        imageViewGroup.setImageAdapter(new AbstractGridImageViewGroup.Adapter() {
            @Override
            public int getCount() {
                return mFeeds.get(position).getImageUrls().size();
            }

            @Override
            public String getImageUrl(int index) {
                return mFeeds.get(position).getImageUrls().get(index);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFeeds.size();
    }
}
