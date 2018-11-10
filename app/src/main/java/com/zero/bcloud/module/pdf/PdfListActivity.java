package com.zero.bcloud.module.pdf;

import com.zero.bcloud.application.Config;
import com.zero.bcloud.module.base.BaseActivityPresenter;
import com.zero.library.utils.FileUtils;
import com.zero.library.utils.SdCard;

import java.io.File;

public class PdfListActivity extends BaseActivityPresenter<PdfListDelegate> {

    private PdfListModel listModel;

    @Override
    public void doMain() {
        this.listModel = new PdfListModel();
        this.listModel.setData(this.viewDelegate.getAdapter().getData());
        File file=new File(SdCard.getExternalAppPath(Config.PDF));
        File[] files=file.listFiles();
        for(File item : files){
            PdfListItemModel itemModel = new PdfListItemModel();
            itemModel.setFile(item);
            this.listModel.getData().add(itemModel);
        }
        this.viewDelegate.refreshPdfList();
    }

    @Override
    protected void bindEventListener() {
        this.viewDelegate.getAdapter().setOnItemClickListener((parent, itemView, position) ->
                this.startActivity(FileUtils.getPdfFileIntent(listModel.getData().get(position).getFile().getAbsolutePath())));
    }
}
