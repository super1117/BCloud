package com.zero.bcloud.module.pdf;

import java.util.List;

public class PdfListModel {

    private List<PdfListItemModel> data;

    private int waitOperatingCount;

    private boolean checkAll;

    public List<PdfListItemModel> getData() {
        return data;
    }

    public void setData(List<PdfListItemModel> data) {
        this.data = data;
    }

    public int getWaitOperatingCount() {
        return waitOperatingCount;
    }

    public void setWaitOperatingCount(int waitOperatingCount) {
        this.waitOperatingCount = waitOperatingCount;
    }

    public boolean isCheckAll() {
        return checkAll;
    }

    public void setCheckAll(boolean checkAll) {
        this.checkAll = checkAll;
    }
}
