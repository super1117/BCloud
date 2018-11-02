package com.zero.bcloud.module.createpdf;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zero.bcloud.R;
import com.zero.bcloud.bean.ImageItem;
import com.zero.bcloud.module.base.BaseRvAdapter;
import com.zero.bcloud.module.base.BaseViewHolder;
import com.zero.bcloud.utils.drag.OnItemDragCallbackListener;
import com.zero.library.utils.DensityUtils;
import com.zero.library.utils.ImageUtils;
import com.zero.library.utils.ScreenUtils;

public class CreatePdfAdapter extends BaseRvAdapter<ImageItem> implements OnItemDragCallbackListener{

    private int mWidth;

    private int mHeight;

    private int mInfoHeight;

    private OnItemDragCompleteListener completeListener;

    public CreatePdfAdapter(RecyclerView recyclerView){
        super(recyclerView);
        this.setShowFooterView(false);
        mWidth = ScreenUtils.getScreenWidth(recyclerView.getContext()) / 2 - DensityUtils.dp2px(recyclerView.getContext(), 8);
        mHeight = (int) ((float) mWidth * 1.33F);
        mInfoHeight = (int) ((float) mWidth * 0.13F);
    }

    @Override
    protected int onLayoutRes(int position) {
        return R.layout.item_pdf_img;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position, ImageItem model) {
        holder.getItemView().setLayoutParams(new ViewGroup.LayoutParams(mWidth, mHeight));
        ImageView imageView = holder.getView(R.id.pdf_img);
        ImageUtils.imageLoader(imageView, model.getPath());
        ((CheckBox) holder.getView(R.id.check)).setChecked(model.isCheck());
        TextView info = holder.getView(R.id.info);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) info.getLayoutParams();
        params.width = mWidth;
        params.height = mInfoHeight;
        info.setLayoutParams(params);
        info.setText(String.format("%02d", (position + 1)));
    }

    @Override
    protected void setItemChildListener(BaseViewHolder holder, int viewType) {
        holder.setItemChildCheckChangeById(R.id.check);
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        ImageItem imageItem = mData.get(fromPosition);
        mData.remove(fromPosition);
        mData.add(toPosition, imageItem);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwipe(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onComplete() {
        notifyDataSetChanged();
        if(this.completeListener != null){
            this.completeListener.onItemDragComplete();
        }
    }

    public void setOnItemDragCompleteListener(OnItemDragCompleteListener completeListener){
        this.completeListener = completeListener;
    }

    public interface OnItemDragCompleteListener{
        void onItemDragComplete();
    }
}
