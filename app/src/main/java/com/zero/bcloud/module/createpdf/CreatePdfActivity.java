package com.zero.bcloud.module.createpdf;

import android.view.MenuItem;
import android.view.View;

import com.zero.bcloud.R;
import com.zero.bcloud.bean.ImageItem;
import com.zero.bcloud.module.base.BaseActivityPresenter;
import com.zero.library.widget.snakebar.Prompt;

public class CreatePdfActivity extends BaseActivityPresenter<CreatePdfDelegate> implements View.OnClickListener{

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
    }

    @Override
    protected void bindEventListener() {
        this.setItemListener();
        this.viewDelegate.get(R.id.album).setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.album:
                this.viewDelegate.intoAlbum();
                break;
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_pdf){
            if(!this.pdfModel.getDataList().isEmpty()){
                this.viewDelegate.snakebar("请先选择要生成PDF的图片", Prompt.WARNING);
            }else{

            }
        }
        return super.onOptionsItemSelected(item);
    }
}
