package com.zero.bcloud.module.pdf;

import java.util.List;

public class PdfListModel {

    private List<PdfListItemModel> data;//列表数据

    private int waitOperatingCount;//等待操作的数量

    private boolean checkAll;//是否全选

    private long checkedSize;//当前所有选中文件的大小

    private long fileLengthTotal;//所有文件的大小

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

    public long getCheckedSize() {
        return checkedSize;
    }

    public void setCheckedSize(long checkedSize) {
        this.checkedSize = checkedSize;
    }

    public long getFileLengthTotal() {
        return fileLengthTotal;
    }

    public void setFileLengthTotal(long fileLengthTotal) {
        this.fileLengthTotal = fileLengthTotal;
    }
}
