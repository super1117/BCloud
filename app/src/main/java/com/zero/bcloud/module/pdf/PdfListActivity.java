package com.zero.bcloud.module.pdf;

import com.zero.bcloud.R;
import com.zero.bcloud.application.Config;
import com.zero.bcloud.module.base.BaseActivityPresenter;
import com.zero.library.utils.FileUtils;
import com.zero.library.utils.SdCard;
import com.zero.library.widget.snakebar.Prompt;

import java.io.File;

public class PdfListActivity extends BaseActivityPresenter<PdfListDelegate> {

    private PdfListModel listModel;

    @Override
    public void doMain() {
        this.listModel = new PdfListModel();
        this.listModel.setData(this.viewDelegate.getAdapter().getData());
        File file=new File(SdCard.getExternalAppPath(Config.PDF));
        File[] files=file.listFiles();
        long length = 0L;
        for(File item : files){
            PdfListItemModel itemModel = new PdfListItemModel();
            itemModel.setFile(item);
            length += item.length();
            this.listModel.getData().add(itemModel);
        }
        this.listModel.setFileLengthTotal(length);
        this.viewDelegate.refreshPdfList();
        this.viewDelegate.setBottomContent(0, files.length, "0B");
    }

    @Override
    protected void bindEventListener() {
        this.viewDelegate.getAdapter().setOnItemClickListener((parent, itemView, position) ->{
            try{
                this.startActivity(FileUtils.getPdfFileIntent(listModel.getData().get(position).getFile().getAbsolutePath()));
            } catch (Exception e){
                this.viewDelegate.snakebar("没有找到可以打开的应用", Prompt.ERROR);
            }
        });
        this.viewDelegate.getCheckAll().setOnClickListener((v)->this.checkAll(!this.listModel.isCheckAll()));
        this.viewDelegate.getAdapter().setOnItemChildClickListener((parent, itemView, position) -> {
            switch (itemView.getId()){
                case R.id.pdf_check:
                    PdfListItemModel itemModel = this.listModel.getData().get(position);
                    boolean checked = !itemModel.isChecked();
                    this.listModel.getData().get(position).setChecked(checked);
                    int checkedCount = this.listModel.getWaitOperatingCount();
                    this.listModel.setWaitOperatingCount(checked ? ++checkedCount : --checkedCount);
                    long currentCheckSize = this.listModel.getCheckedSize();
                    long currentFileSize = itemModel.getFile().length();
                    this.listModel.setCheckedSize(checked ? currentCheckSize + currentFileSize : currentCheckSize - currentFileSize);
                    if(this.listModel.isCheckAll() && this.listModel.getWaitOperatingCount() < this.listModel.getData().size()){
                        this.viewDelegate.getCheckAll().setChecked(false);
                        this.listModel.setCheckAll(false);
                    }
                    if(this.listModel.getWaitOperatingCount() == this.listModel.getData().size()){
                        this.viewDelegate.getCheckAll().setChecked(true);
                        this.listModel.setCheckAll(true);
                    }
                    this.viewDelegate.setBottomContent(this.listModel.getWaitOperatingCount(), this.listModel.getData().size(), FileUtils.getFileSize(Math.abs(this.listModel.getCheckedSize())));
                    break;
                default: break;
            }
        });
    }

    private void checkAll(boolean checked){
        for(PdfListItemModel item : this.listModel.getData()){
            item.setChecked(checked);
        }
        this.listModel.setWaitOperatingCount(checked ? this.listModel.getData().size() : 0);
        this.listModel.setCheckAll(checked);
        this.listModel.setCheckedSize(checked ? listModel.getFileLengthTotal() : 0L);
        this.viewDelegate.refreshPdfList();
        this.viewDelegate.setBottomContent(this.listModel.getWaitOperatingCount(), this.listModel.getData().size(), checked ? FileUtils.getFileSize(this.listModel.getFileLengthTotal()) : "0B");
    }
}
