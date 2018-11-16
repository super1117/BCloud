package com.zero.bcloud.module.pdf;

import android.os.Handler;
import android.os.Message;

import com.zero.bcloud.R;
import com.zero.bcloud.application.Config;
import com.zero.bcloud.module.base.BaseActivityPresenter;
import com.zero.bcloud.utils.ZipUtil;
import com.zero.library.utils.FileUtils;
import com.zero.library.utils.SdCard;
import com.zero.library.widget.snakebar.Prompt;

import java.io.File;

public class PdfListActivity extends BaseActivityPresenter<PdfListDelegate> {

    private PdfListModel listModel;

    private final static int COMPRESS_SUCCESS = 0x0000;

    private final static int COMPRESS_FAIL = 0x0001;

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
                    this.itemChecked(position);
                    break;
                default: break;
            }
        });
        this.viewDelegate.get(R.id.pdf_del).setOnClickListener(v -> {
            if(this.listModel.getCheckedSize() <= 0){
                this.viewDelegate.prompt(this.getResources().getString(R.string.msg_del_file));
            }else{
                this.viewDelegate.showMessage(String.format(this.getResources().getString(R.string.del_data), this.listModel.getCheckedSize()), (dialog, which) -> this.itemDel());
            }
        });
        this.viewDelegate.get(R.id.pdf_upload).setOnClickListener(v -> this.compress());
    }

    /**
     * item 被选中事件
     * @param position
     */
    private void itemChecked(int position){
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
    }

    /**
     * 全选事件
     * @param checked
     */
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

    /**
     * item 删除事件
     */
    private void itemDel(){
        if(this.listModel.getCheckedSize() == 0){
            return;
        }
        for(int i = this.listModel.getData().size() - 1; i >= 0; i--){
            PdfListItemModel item = this.listModel.getData().get(i);
            if(item.isChecked()){
                SdCard.deleteFile(item.getFile());
                this.listModel.getData().remove(i);
                this.listModel.setFileLengthTotal(this.listModel.getFileLengthTotal() - item.getFile().length());
                this.listModel.setCheckedSize(this.listModel.getCheckedSize() - item.getFile().length());
                this.listModel.setWaitOperatingCount(this.listModel.getWaitOperatingCount() - 1);
            }
        }
        this.viewDelegate.setBottomContent(this.listModel.getWaitOperatingCount(), this.listModel.getData().size(), FileUtils.getFileSize(this.listModel.getFileLengthTotal()));
        this.viewDelegate.getCheckAll().setChecked(false);
        this.viewDelegate.refreshPdfList();
    }

    /**
     * 压缩文件
     */
    private void compress(){
        if(this.listModel.getCheckedSize() == 0){
            return;
        }
        this.viewDelegate.showLoading();
        new Thread(){
            @Override
            public void run() {
                String zip = SdCard.getExternalAppPath(Config.ZIP);
                String temporary = SdCard.getExternalAppPath(Config.PDF + File.separator + "temporary");
                File tempFolder = new File(temporary);
                try{
                    for(PdfListItemModel itemModel : listModel.getData()){
                        if(itemModel.isChecked()){
                            SdCard.copyFile(itemModel.getFile().getAbsolutePath(), temporary + File.separator + itemModel.getFile().getName() );
                        }
                    }
                    ZipUtil.zipFolder(temporary, zip + File.separator + System.currentTimeMillis() + ".zip");
                    SdCard.deleteFile(tempFolder);
                    mHandler.sendEmptyMessage(COMPRESS_SUCCESS);
                } catch (Exception e){
                    e.printStackTrace();
                    if(SdCard.isFileExists(temporary)){
                        SdCard.deleteFile(tempFolder);
                    }
                    mHandler.sendEmptyMessage(COMPRESS_FAIL);
                }
            }
        }.start();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case COMPRESS_SUCCESS:
                    viewDelegate.dismissLoading();
                    viewDelegate.snakebar("压缩完成", Prompt.SUCCESS);
                    break;
                case COMPRESS_FAIL:
                    viewDelegate.dismissLoading();
                    viewDelegate.snakebar("压缩失败", Prompt.ERROR);
                    break;
                default:break;
            }
        }
    };
}
