package com.zero.bcloud.module.createpdf;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;

import com.zero.bcloud.R;
import com.zero.bcloud.module.base.BaseActivityPresenter;
import com.zero.bcloud.module.imgpreview.ImagePreviewActivity;
import com.zero.library.bean.ImageItem;
import com.zero.library.utils.PermissionManager;
import com.zero.library.widget.snakebar.Prompt;
import com.zhihu.matisse.Matisse;

import java.io.Serializable;
import java.util.List;

public class CreatePdfActivity extends BaseActivityPresenter<CreatePdfDelegate> implements View.OnClickListener{

        private CreatePdfModel pdfModel;

        private PermissionManager permissionManager;

        @Override
        public void doMain() {
            this.pdfModel = new CreatePdfModel();
            this.pdfModel.setDataList(this.viewDelegate.getAdapter().getData());

            this.permissionManager = new PermissionManager(this, this);
        }

        @Override
        protected void bindEventListener() {
            this.setItemListener();
            this.viewDelegate.get(R.id.album).setOnClickListener(this::onClick);
            this.viewDelegate.get(R.id.delete_holder).setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.album:
                    if(!this.permissionManager.isRequestPermissions(CreatePdfDelegate.PERMISSON_REQUESTCODE, Manifest.permission.READ_EXTERNAL_STORAGE)){
                        this.viewDelegate.intoAlbum();
                    }
                    break;
                case R.id.delete_holder:
                    this.delItems();
                    break;
            }
        }

        private void setItemListener(){
            this.viewDelegate.getAdapter().setOnItemClickListener(((parent, itemView, position) -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", (Serializable) this.pdfModel.getDataList());
                bundle.putInt("position", position);
                this.start(ImagePreviewActivity.class, bundle);
            }));
            this.viewDelegate.getAdapter().setOnItemChildClickListener(((parent, childView, position) -> {
                boolean checked = !this.pdfModel.getDataList().get(position).isChecked();
                this.pdfModel.getDataList().get(position).setChecked(checked);
                int checkCount = this.pdfModel.getWaitToDeleteCount();
                this.pdfModel.setWaitToDeleteCount(checked ? ++checkCount : --checkCount);
                this.viewDelegate.setDelBtn(this.pdfModel.getWaitToDeleteCount());
            }));
        }

        @Override
        public void onBackPressed() {
            if(viewDelegate.isDelBtnShow()){
                this.viewDelegate.exitImageListEditMode();
            }else{
                super.onBackPressed();
            }
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if(item.getItemId() == R.id.menu_pdf){
                if(this.pdfModel.getDataList().isEmpty()){
                        this.viewDelegate.snakebar("请先选择要生成PDF的图片", Prompt.WARNING);
                }else{
                    this.crreatePdf();
                }
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(resultCode == RESULT_OK && requestCode == this.viewDelegate.REQUEST_CODE_ALBUM){
                List<String> imgs = Matisse.obtainPathResult(data);
                for(String path : imgs){
                    ImageItem imageItem = new ImageItem();
                    imageItem.setImagePath(path);
                    this.pdfModel.getDataList().add(imageItem);
                }
                this.viewDelegate.refreshImageList();
            }
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CreatePdfDelegate.PERMISSON_REQUESTCODE) {
            if (!this.permissionManager.verifyPermissions(grantResults)) {
                this.permissionManager.showMissingPermissionDialog(null);
            }else{
                this.viewDelegate.intoAlbum();
            }
        }
    }

    /**
     * 批量删除图片
     */
    private void delItems(){
            int size = this.pdfModel.getDataList().size();
            for(int i = size - 1; i >= 0; i --){
                ImageItem imgItem = this.pdfModel.getDataList().get(i);
                if(imgItem.isChecked()){
                    this.pdfModel.getDataList().remove(i);
                }
            }
            this.viewDelegate.refreshImageList();
            this.viewDelegate.exitImageListEditMode();
    }

    /**
     * 创建PDF文件
     */
    private void crreatePdf(){

    }
}
