package com.aliouswang.wechat.ninegridimage;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aliouswang.wechat.library.widget.NormalGridImageViewGroup;

/**
 * Created by aliouswang on 17/2/7.
 */

public class FeedViewHolder extends RecyclerView.ViewHolder{

    public NormalGridImageViewGroup mNormalGridImageViewGroup;

    public FeedViewHolder(View itemView) {
        super(itemView);
        mNormalGridImageViewGroup = (NormalGridImageViewGroup) itemView.findViewById(R.id.grid_imageview);
    }
}
