package com.zero.bcloud.module.imgpreview;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zero.bcloud.R;
import com.zero.bcloud.widget.gallery.GalleryViewPager;
import com.zero.library.mvp.view.AppDelegate;
import com.zero.library.utils.DensityUtils;
import com.zero.library.utils.ScreenUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ImagePreviewDelegate extends AppDelegate {

    private LinearLayout actionBar;

    private GalleryViewPager contentView;

    private TextView countTv;

    private ImageAdapter mAdapter;

    private boolean isOperateShow = true;

    private int currentItem;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_image_preview;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        this.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        this.initView();
        this.initEvents();
    }

    private void initView() {
        this.actionBar = this.get(R.id.toolbar_layout);
        this.contentView = this.get(R.id.content);
        this.countTv = this.get(R.id.count);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) actionBar.getLayoutParams();
            layoutParams.height = DensityUtils.dp2px(this.getActivity(), 48f) + ScreenUtils.getStatusHeight(this.getActivity());
            this.actionBar.setLayoutParams(layoutParams);
            this.actionBar.setPadding(0, ScreenUtils.getStatusHeight(this.getActivity()), 0, 0);
        }

        this.mAdapter = new ImageAdapter(this.getContentView());
        this.contentView.setPageMargin(DensityUtils.dp2px(this.getActivity(), 10f));
        this.contentView.setAdapter(this.mAdapter);
        this.contentView.setOffscreenPageLimit(1);
    }

    public void setCrrentItem(int position){
        this.currentItem = position;
        this.contentView.setCurrentItem(position, false);
        this.countTv.setText(String.format("%1$d / %2$d", (position + 1), this.getAdapter().getCount()));
    }

    private void initEvents() {
        this.contentView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getAdapter().getViewMap().get(currentItem).reset();
                setCrrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        this.contentView.setOnCustomClickListener(() -> {
            if (this.isOperateShow) {
                hideOperateHolder();
            } else {
                showOperateHolder();
            }
        });
    }

    public ImageAdapter getAdapter() {
        return this.mAdapter;
    }

    public GalleryViewPager getContentView() {
        return this.contentView;
    }

    private void showOperateHolder() {
        this.isOperateShow = true;
        this.actionBar.setVisibility(View.VISIBLE);
        Animation startAnim = AnimationUtils.loadAnimation(this.getActivity(), R.anim.action_bar_show);
        startAnim.setFillAfter(true);
        this.actionBar.startAnimation(startAnim);
    }

    private void hideOperateHolder() {
        this.isOperateShow = false;
        Animation hideAnim = AnimationUtils.loadAnimation(this.getActivity(), R.anim.action_bar_hide);
        hideAnim.setFillAfter(true);
        this.actionBar.startAnimation(hideAnim);
        Observable
                .timer(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((aLong) ->{
                    this.actionBar.setVisibility(View.GONE);
                    this.actionBar.clearAnimation();
                });
    }
}
