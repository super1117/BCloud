package com.zero.bcloud.module.imgpreview;

import com.zero.bcloud.module.base.BaseActivityPresenter;
import com.zero.library.bean.ImageItem;

import java.util.ArrayList;

public class ImagePreviewActivity extends BaseActivityPresenter<ImagePreviewDelegate> {

    @Override
    public void doMain() {
        if(this.getIntent() != null && getIntent().getSerializableExtra("images") != null){
            this.viewDelegate.getAdapter().setData((ArrayList<ImageItem>) getIntent().getSerializableExtra("images"));
            this.viewDelegate.setCrrentItem(this.getIntent().getIntExtra("position", 0));
        }
    }
}
