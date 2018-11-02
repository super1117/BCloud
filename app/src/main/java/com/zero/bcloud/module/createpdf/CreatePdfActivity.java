package com.zero.bcloud.module.createpdf;

import android.util.Log;

import com.zero.bcloud.bean.ImageItem;
import com.zero.bcloud.module.base.BaseActivityPresenter;

import java.util.ArrayList;
import java.util.List;

public class CreatePdfActivity extends BaseActivityPresenter<CreatePdfDelegate>{

    @Override
    public void doMain() {
        List<ImageItem> list = new ArrayList<>();
        for(int i=0; i<20; i++){
            ImageItem imageItem = new ImageItem();
            imageItem.setPath("http://www.285868.com/uploadfile/2016/1207/20161207022455195.jpg");
            list.add(imageItem);
        }
        this.viewDelegate.getAdapter().setData(list);
        this.viewDelegate.getAdapter().setOnItemClickListener((parent, itemView, position) -> {
            Log.i("hunter", "item position = " + position);
        });
    }
}