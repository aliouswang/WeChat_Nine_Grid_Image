package com.aliouswang.wechat.ninegridimage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aliouswang.wechat.ninegridimage.strategy.PicassoImageLoadStrategy;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        FeedAdapter feedAdapter = new FeedAdapter();
        feedAdapter.setFeeds(ImageFactory.mockFeeds(20));
//        feedAdapter.setImageLoadStrategy(new GlideImageLoadStrategy());
        feedAdapter.setImageLoadStrategy(new PicassoImageLoadStrategy());
        mRecyclerView.setAdapter(feedAdapter);
    }
}
