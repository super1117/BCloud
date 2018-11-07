package com.zero.bcloud.module.createpdf;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import com.zero.bcloud.R;
import com.zero.bcloud.bean.ImageItem;
import com.zero.bcloud.module.base.BaseActivityPresenter;
import com.zero.library.widget.snakebar.Prompt;
import com.zhihu.matisse.Matisse;

import java.util.List;

public class CreatePdfActivity extends BaseActivityPresenter<CreatePdfDelegate> implements View.OnClickListener{

        private CreatePdfModel pdfModel;

        @Override
        public void doMain() {
            this.pdfModel = new CreatePdfModel();
            this.pdfModel.setDataList(this.viewDelegate.getAdapter().getData());
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
                    this.viewDelegate.intoAlbum();
                    break;
                case R.id.delete_holder:
                    this.delItems();
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
                this.viewDelegate.exitImageListEditMode();
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

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(resultCode == RESULT_OK && requestCode == this.viewDelegate.REQUEST_CODE_ALBUM){
                List<String> imgs = Matisse.obtainPathResult(data);
                for(String path : imgs){
                    ImageItem imageItem = new ImageItem();
                    imageItem.setPath(path);
                    this.pdfModel.getDataList().add(imageItem);
                }
                this.viewDelegate.refreshImageList();
            }
        }

        private void delItems(){
            int size = this.pdfModel.getDataList().size();
            for(int i = size - 1; i >= 0; i --){
                ImageItem imgItem = this.pdfModel.getDataList().get(i);
                if(imgItem.isCheck()){
                    this.pdfModel.getDataList().remove(i);
                }
            }
            this.viewDelegate.refreshImageList();
            this.viewDelegate.exitImageListEditMode();
        }
}
