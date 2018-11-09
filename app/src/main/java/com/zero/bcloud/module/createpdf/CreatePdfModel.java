package com.zero.bcloud.module.createpdf;

import com.zero.library.bean.ImageItem;

import java.util.List;

public class CreatePdfModel {

    private List<ImageItem> dataList;

    private int waitToDeleteCount;

    public final static int IMAGE_COUNT_LIMIT = 10;

    public List<ImageItem> getDataList() {
        return dataList;
    }

    public void setDataList(List<ImageItem> dataList) {
        this.dataList = dataList;
    }

    public int getWaitToDeleteCount() {
        return waitToDeleteCount;
    }

    public void setWaitToDeleteCount(int waitToDeleteCount) {
        this.waitToDeleteCount = waitToDeleteCount;
    }
}
