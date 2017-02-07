package com.aliouswang.wechat.ninegridimage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aliouswang.wechat.library.AbstractGridImageViewGroup;
import com.aliouswang.wechat.library.NormalGridImageViewGroup;

public class MainActivity extends AppCompatActivity {

    private String [] images = {
            "http://img1.imgtn.bdimg.com/it/u=1883048880,1803302773&fm=23&gp=0.jpg",
            "http://pic14.nipic.com/20110526/3705884_232628071000_2.jpg",
            "http://p3.image.hiapk.com/uploads/allimg/131024/23-131024152647.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3061147669,2918452020&fm=23&gp=0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1486459982336&di=d1b7416b3978c3e5e5a6801f02af95e3&imgtype=0&src=http%3A%2F%2Fpic12.nipic.com%2F20110113%2F3556745_174423703113_2.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1486459982336&di=c97e2094c0d22e96c3d7b8530f2456fc&imgtype=0&src=http%3A%2F%2Fimg.qqai.net%2Fuploads%2Fi_5_3544084446x2761685049_21.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1486459982336&di=a147e75799ab1ad259bdd73dd7cfcbf1&imgtype=0&src=http%3A%2F%2Fm.qqzhi.com%2Fupload%2Fimg_0_856539028D1917385808_23.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1486459982335&di=e918a176a09a98ddf9a3407a4b9cae47&imgtype=0&src=http%3A%2F%2Fpic12.nipic.com%2F20110117%2F3556745_180118662109_2.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1486459982335&di=f45ccdd1de1290da4220cdcda9750fbd&imgtype=0&src=http%3A%2F%2Fpic12.nipic.com%2F20110117%2F3556745_180117290107_2.jpg"
    };

    NormalGridImageViewGroup mNormalGridImageViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNormalGridImageViewGroup = (NormalGridImageViewGroup) findViewById(R.id.gird_image);

        mNormalGridImageViewGroup.setImageAdapter(new AbstractGridImageViewGroup.Adapter() {
            @Override
            public int getCount() {
                return images.length;
            }

            @Override
            public String getImageUrl(int index) {
                return images[index];
            }
        });
    }
}
