package com.zero.bcloud.module.pdf;

import java.io.File;

public class PdfListItemModel {

    private File file;

    private boolean isChecked;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


}
