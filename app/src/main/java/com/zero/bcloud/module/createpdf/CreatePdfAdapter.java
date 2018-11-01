package com.zero.bcloud.module.createpdf;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zero.bcloud.R;
import com.zero.bcloud.module.base.BaseRvAdapter;
import com.zero.bcloud.module.base.BaseViewHolder;
import com.zero.library.utils.ImageUtils;
import com.zero.library.utils.ScreenUtils;

public class CreatePdfAdapter extends BaseRvAdapter<String>{

    private int mWidth = 0;
    private int mHeight = 0;
    private int mInfoHeight = 0;

    public CreatePdfAdapter(RecyclerView recyclerView){
        super(recyclerView);
        this.setShowFooterView(false);
        mWidth = ScreenUtils.getScreenWidth(recyclerView.getContext()) / 2;
        mHeight = (int) ((float) mWidth * 1.33F);
        mInfoHeight = (int) ((float) mWidth * 0.13F);
    }

    @Override
    protected int onLayoutRes(int position) {
        return R.layout.item_pdf_img;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position, String model) {
        holder.getItemView().setLayoutParams(new ViewGroup.LayoutParams(mWidth, mHeight));
        ImageView imageView = holder.getView(R.id.pdf_img);
        ImageUtils.imageLoader(imageView, model);
    }

    @Override
    protected void setItemChildListener(BaseViewHolder holder, int viewType) {
//        holder.setChildClickById(-1);
    }
}
