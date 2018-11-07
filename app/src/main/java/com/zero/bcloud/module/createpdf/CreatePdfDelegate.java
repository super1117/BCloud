package com.zero.bcloud.module.createpdf;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.zero.bcloud.R;
import com.zero.bcloud.utils.drag.OnContractImageDragCallback;
import com.zero.library.mvp.view.AppDelegate;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

public class CreatePdfDelegate extends AppDelegate{

    public static final int REQUEST_CODE_ALBUM = 0x0001;

    private RecyclerView recyclerView;

    private CreatePdfAdapter adapter;

    private boolean isDelBtnShow;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_pdf_crreate;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        this.recyclerView = this.get(R.id.pdf_rv);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));
        this.adapter = new CreatePdfAdapter(this.recyclerView);
        this.recyclerView.setAdapter(this.adapter);
        this.setDelBtn(0);

        ((DefaultItemAnimator)this.recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        OnContractImageDragCallback imageDragCallback = new OnContractImageDragCallback(this.adapter);
        ItemTouchHelper helper = new ItemTouchHelper(imageDragCallback);
        helper.attachToRecyclerView(this.recyclerView);
        imageDragCallback.setCanDrag(true);
        imageDragCallback.setCanSwipe(false);
        this.recyclerView.setFocusable(false);

        this.setOnItemListener();
    }

    @Override
    public int getOptionMenuId() {
        return R.menu.menu_pdf;
    }

    public CreatePdfAdapter getAdapter() {
        return adapter;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    private void setOnItemListener(){
        this.adapter.setOnItemLongClickListener((parent, itemView, position) -> {
            this.adapter.intoEditMode();
            this.hideDelBtn();
            return true;
        });
        this.adapter.setOnItemDragCompleteListener(()-> showDelBtn());
    }

    private void showDelBtn(){
        if(!isDelBtnShow){
            isDelBtnShow = true;
            this.get(R.id.delete_holder).setVisibility(View.VISIBLE);
            Animation delAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.up_to_show);
            delAnim.setFillAfter(true);
            this.get(R.id.delete_holder).startAnimation(delAnim);
        }
    }

    public void hideDelBtn(){
        if(isDelBtnShow){
            isDelBtnShow = false;
            this.get(R.id.delete_holder).setVisibility(View.GONE);
            Animation delAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.down_to_hide);
            delAnim.setFillAfter(true);
            this.get(R.id.delete_holder).startAnimation(delAnim);
        }
    }

    public boolean isDelBtnShow(){
        return isDelBtnShow;
    }

    public void setDelBtn(int delCount){
        ((TextView)this.get(R.id.delete_count)).setText("(" + delCount + ")");
        this.get(R.id.delete_holder).setEnabled(delCount != 0);
    }

    public void intoAlbum(){
        Matisse.from(this.getActivity())
                .choose(MimeType.ofImage())
                .theme(R.style.Matisse_QiYueSuo)
                .countable(true)
                .maxSelectable(CreatePdfModel.IMAGE_COUNT_LIMIT)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_ALBUM);
    }

    public void refreshImageList(){
        this.getAdapter().notifyDataSetChanged();
    }

    public void exitImageListEditMode() {
        this.getAdapter().quitEditMode();
        this.setDelBtn(0);
        this.hideDelBtn();
    }

}
