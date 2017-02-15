package com.aliouswang.wechat.library.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.aliouswang.wechat.library.R;
import com.aliouswang.wechat.library.interfaces.IRatio;
import com.aliouswang.wechat.library.interfaces.NineImageUrl;
import com.aliouswang.wechat.library.model.Point;
import com.aliouswang.wechat.library.widget.PageIndicatorView;

import java.util.ArrayList;

/**
 * Created by aliouswang on 17/2/15.
 */

public abstract class AbstractImageBrowseActivity<T extends View> extends Activity{

    public static final int ANIM_DURATION = 300;

    public static final String LEFT_LOCATION = "left_location";
    public static final String TOP_LOCATION = "right_location";
    public static final String THUMBNAIL_WIDTH = "thumbnail_width";
    public static final String THUMBNAIL_HEIGHT = "thumbnail_height";
    public static final String CLICK_INDEX = "click_index";
    public static final String THUMBNAIL_IMAGE_POINTS = "thumbnail_image_points";
    public static final String THUMBNAIL_IMAGE_URLS = "thumbnail_image_urls";
    public static final String THUMBNAIL_RATIO = "thumbnail_ratio";
    public static final String HORIZONTAL_GAP = "horizontal_gap";
    public static final String VERTICAL_GAP = "vertical_gap";

    protected Context mContext;

    protected T mMaskImageView;

    private View mMainBackground;
    private ColorDrawable mColorDrawable;
    private PageIndicatorView mPageIndicatorView;

    private int mLeftDelta;
    private int mTopDelta;
    private float mRatio;
    private int mHorizontalGap;
    private int mVerticalGap;

    private int mThumbnailTop;
    private int mThumbnailLeft;
    private int mThumbnailWidth;
    private int mThumbnailHeight;

    private String mCurrentImageUrl;
    private int mCurrentPosition;

    private int mScreenHeight;
    private int mScreenWidth;

    private ViewPager mViewPager;

    protected ArrayList<Point> mNineImagePoints;
    protected ArrayList<NineImageUrl> mNineImageUrls;
    protected ArrayList<ImageDelta> mNineImageDeltas;

    private volatile boolean bEnterAnimGoing;
    private volatile boolean bExitAnimGoing;

    private void parseIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        mThumbnailTop = bundle.getInt(TOP_LOCATION);
        mThumbnailLeft = bundle.getInt(LEFT_LOCATION);
        mThumbnailWidth = bundle.getInt(THUMBNAIL_WIDTH);
        mThumbnailHeight = bundle.getInt(THUMBNAIL_HEIGHT);
        mCurrentPosition = bundle.getInt(CLICK_INDEX);
        mNineImageUrls = (ArrayList<NineImageUrl>) bundle.getSerializable(THUMBNAIL_IMAGE_URLS);
        mNineImagePoints = (ArrayList<Point>) bundle.getSerializable(THUMBNAIL_IMAGE_POINTS);
        mRatio = bundle.getFloat(THUMBNAIL_RATIO);
        mHorizontalGap = bundle.getInt(HORIZONTAL_GAP);
        mVerticalGap = bundle.getInt(VERTICAL_GAP);
        mCurrentImageUrl = mNineImageUrls.get(mCurrentPosition).getNineImageUrl();
    }

    private void calculateImageDeltas() {
        mNineImageDeltas = new ArrayList<>();
        if (mNineImageUrls == null || mNineImageUrls.isEmpty()) return;
        int size = mNineImageUrls.size();
        for (int i = 0; i < size; i++) {
            Point point = mNineImagePoints.get(i);
            Point curPoint = mNineImagePoints.get(mCurrentPosition);
            ImageDelta imageDelta = new ImageDelta();
            imageDelta.left = mThumbnailLeft -
                    ((curPoint.column - point.column) * (mHorizontalGap + mThumbnailWidth));
            imageDelta.top = mThumbnailTop -
                    ((curPoint.row - point.row) * (mVerticalGap + mThumbnailHeight));
            mNineImageDeltas.add(imageDelta);
        }
    }

    private int[] mInitScreenLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent(getIntent());
        mContext = this;
        setContentView(getInflateLayout());
        getScreenMetrics();
        initView();
    }

    private void getScreenMetrics() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mScreenHeight = displaymetrics.heightPixels;
        mScreenWidth = displaymetrics.widthPixels;
    }

    protected abstract PagerAdapter getPagerAdapter();

    protected abstract int getInflateLayout();
    protected abstract void initMaskImageView();
    protected abstract void loadImage(T view, String imageUrl);

    private void initView() {
        mMainBackground = findViewById(R.id.main_background);
        mColorDrawable = new ColorDrawable(Color.BLACK);
        mMainBackground.setBackgroundDrawable(mColorDrawable);

        mPageIndicatorView = (PageIndicatorView) findViewById(R.id.page_indicator);
        mPageIndicatorView.setCircle(mNineImageUrls.size(), mCurrentPosition);

        initMaskImageView();

        RelativeLayout.LayoutParams flp = (RelativeLayout.LayoutParams) mMaskImageView.getLayoutParams();
        flp.width = mThumbnailWidth;
        flp.height = mThumbnailHeight;
        mMaskImageView.setLayoutParams(flp);
//        loadImage(mMaskImageView, mCurrentImageUrl);
        mMaskImageView.requestLayout();

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setVisibility(View.GONE);
//        FrescoPhotoPageAdapter photoPageAdapter = new
//                FrescoPhotoPageAdapter(mNineImageUrls);
        mViewPager.setAdapter(getPagerAdapter());
        mViewPager.setCurrentItem(mCurrentPosition);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                loadImage(mMaskImageView, mNineImageUrls.get(mCurrentPosition).getNineImageUrl());
                mPageIndicatorView.notifyFocusChanged(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void enterValueAnimation() {
        if (bEnterAnimGoing) return;
        bEnterAnimGoing = true;
        AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        ViewWrapper viewWrapper = new ViewWrapper(mMaskImageView);

        ValueAnimator animator = ObjectAnimator.ofInt(viewWrapper, "width", mScreenWidth);
        ValueAnimator scaleAnimator =
                ObjectAnimator.ofFloat(viewWrapper, "ratio", 1, mRatio);
        ValueAnimator translateXAnim = ObjectAnimator.ofFloat(mMaskImageView, "translationX", mLeftDelta, 0);
        ValueAnimator translateYAnim = ObjectAnimator.ofFloat(mMaskImageView, "translationY", mTopDelta, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator, scaleAnimator, translateXAnim, translateYAnim);
        animatorSet.setInterpolator(interpolator);
        animatorSet.setStartDelay(10);
        animatorSet.setDuration(ANIM_DURATION).start();

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
//                mMaskImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mViewPager.setVisibility(View.VISIBLE);
                mMaskImageView.setVisibility(View.INVISIBLE);
                bEnterAnimGoing = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    private static class ViewWrapper {
        private View mTarget;

        private float mScale = 1;

        public ViewWrapper(View target) {
            mTarget = target;
        }

        public int getWidth() {
            return mTarget.getLayoutParams().width;
        }

        public void setWidth(int width) {
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }

        public float getRatio() {
            return mScale;
        }

        public void setRatio(float scale) {
            this.mScale = scale;
            ((IRatio) mTarget).setRatio(scale);
        }

    }

    public void exitAnimation(final Runnable endAction) {
        if (bExitAnimGoing) return;
        bExitAnimGoing = true;

        mRatio = ((IRatio)mMaskImageView).getRatio();
        mLeftDelta = mNineImageDeltas.get(mCurrentPosition).left - mInitScreenLocation[0];
        mTopDelta = mNineImageDeltas.get(mCurrentPosition).top - mInitScreenLocation[1];

        mPageIndicatorView.setVisibility(View.GONE);
        mMaskImageView.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);
        AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        ViewWrapper viewWrapper = new ViewWrapper(mMaskImageView);

        ValueAnimator animator = ObjectAnimator.ofInt(viewWrapper, "width", mThumbnailWidth);
        ValueAnimator scaleAnimator =
                ObjectAnimator.ofFloat(viewWrapper, "ratio", mRatio, 1);
        ValueAnimator translateXAnim = ObjectAnimator.ofFloat(mMaskImageView, "translationX", 0, mLeftDelta);
        ValueAnimator translateYAnim = ObjectAnimator.ofFloat(mMaskImageView, "translationY", 0, mTopDelta);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator, scaleAnimator, translateXAnim, translateYAnim);
        animatorSet.setInterpolator(interpolator);
        animatorSet.setStartDelay(10);
        animatorSet.setDuration(ANIM_DURATION).start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                endAction.run();
                bExitAnimGoing = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });


        // Fade out background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(mColorDrawable, "alpha", 0);
        bgAnim.setDuration(ANIM_DURATION);
        bgAnim.start();
    }

    @Override
    public void onBackPressed() {
        exitAnimation(new Runnable() {
            public void run() {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    protected class ImageDelta {
        public int left;
        public int top;
    }

}
