package com.zero.bcloud.module.createpdf;

import com.zero.bcloud.bean.ImageItem;
import com.zero.bcloud.module.base.BaseActivityPresenter;

public class CreatePdfActivity extends BaseActivityPresenter<CreatePdfDelegate>{

    private CreatePdfModel pdfModel;

    @Override
    public void doMain() {
        this.pdfModel = new CreatePdfModel();
        pdfModel.setDataList(this.viewDelegate.getAdapter().getData());
        for(int i=0; i<20; i++){
            ImageItem imageItem = new ImageItem();
            imageItem.setPath("http://www.285868.com/uploadfile/2016/1207/20161207022455195.jpg");
            this.pdfModel.getDataList().add(imageItem);
        }
        this.setItemListener();
    }

    private void setItemListener(){
        this.viewDelegate.getAdapter().setOnItemClickListener(((parent, itemView, position) -> {

        }));
        this.viewDelegate.getAdapter().setOnItemChildClickListener(((parent, childView, position) -> {
            boolean checked = !this.pdfModel.getDataList().get(position).isCheck();
            this.pdfModel.getDataList().get(position).setCheck(checked);
            int checkCount = this.pdfModel.getWaitToDeleteCount();
            this.pdfModel.setWaitToDeleteCount(checked ? ++checkCount : --checkCount);
            this.viewDelegate.setDelBtn(this.pdfModel.getWaitToDeleteCount());
        }));
    }

    @Override
    public void onBackPressed() {
        if(viewDelegate.isDelBtnShow()){
            this.viewDelegate.getAdapter().quitEditMode();
            this.viewDelegate.hideDelBtn();
        }else{
            super.onBackPressed();
        }
    }
}
