package com.zero.bcloud.module.imgpreview;

import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.zero.bcloud.widget.gallery.GalleryViewPager;
import com.zero.bcloud.widget.gallery.ZoomImageView;
import com.zero.library.bean.ImageItem;
import com.zero.library.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends PagerAdapter {

    private List<ImageItem> mDataList;

    private SparseArrayCompat<ZoomImageView> viewMap;

    private Context context;

    private GalleryViewPager viewPager;

    public ImageAdapter(GalleryViewPager viewPager){
        this.viewPager = viewPager;
        this.context = this.viewPager.getContext();
        this.mDataList = new ArrayList<>();
        this.viewMap = new SparseArrayCompat<>();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        ZoomImageView zoomImage = viewMap.get(position);
        ((GalleryViewPager) container).setZoomView(zoomImage);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ZoomImageView zoomImage = viewMap.get(position);
        if (zoomImage == null) {
            zoomImage = setImageToIndex(position);
        }
        container.addView(zoomImage);
        return zoomImage;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        ZoomImageView zoomImage = viewMap.get(position);
        if (zoomImage != null) {
            zoomImage.setImageBitmap(null);
            viewMap.remove(position);
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    private ZoomImageView setImageToIndex(int index) {
        ZoomImageView zoomImage = new ZoomImageView(this.context);
        String path = mDataList.get(index).getImagePath();
        ImageUtils.imageLoader(zoomImage, path);
        viewMap.put(index, zoomImage);
        return zoomImage;
    }

    public void setData(List<ImageItem> data){
        this.mDataList.clear();
        this.mDataList.addAll(data);
        this.notifyDataSetChanged();
    }

    public List<ImageItem> getData(){
        return this.mDataList;
    }

    public SparseArrayCompat<ZoomImageView> getViewMap() {
        return viewMap;
    }
}
