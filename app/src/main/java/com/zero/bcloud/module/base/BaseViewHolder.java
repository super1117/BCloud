package com.zero.bcloud.module.base;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.zero.bcloud.R;
import com.zero.bcloud.module.listener.OnItemChildCheckChangeListener;
import com.zero.bcloud.module.listener.OnItemChildClickListener;
import com.zero.bcloud.module.listener.OnItemClickListener;
import com.zero.bcloud.module.listener.OnItemLongClickListener;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private View itemView;

    private SparseArrayCompat<View> views;

    private RecyclerView mRecyclerView;

    private OnItemChildClickListener childClickListener;

    private OnItemChildCheckChangeListener childCheckChangeListener;

    public BaseViewHolder(RecyclerView mRecyclerView, View itemView){
        super(itemView);
        this.itemView = itemView;
        this.mRecyclerView = mRecyclerView;
        this.views = new SparseArrayCompat<>();
    }

    /**
     * 通过控件的Id获取对应的控件，如果没有则加入mViews，则从item根控件中查找并保存到mViews中
     *
     * @param viewId
     * @return
     */
    @Nullable
    public <T extends View> T getView(@IdRes int viewId) {
        if (viewId == View.NO_ID) {
            return null;
        }
        View view = this.views.get(viewId);
        if(view == null){
            view = this.itemView.findViewById(viewId);
            this.views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 获取item根view
     * @return
     */
    public View getItemView(){
        return this.itemView;
    }

    /**
     * 设置item点击事件
     * @param clickListener
     */
    public void setOnItemClickListener(OnItemClickListener clickListener){
        if(clickListener == null) return;
        getItemView().setOnClickListener(v -> clickListener.onItemClick(mRecyclerView, this.itemView, this.getAdapterPosition()));
    }

    /**
     * 设置item长按事件
     * @param longClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener){
        if(longClickListener == null) return;
        getItemView().setOnLongClickListener(v -> longClickListener.onItemLongClick(mRecyclerView, this.itemView, this.getAdapterPosition()));
    }

    /**
     * 设置item中子控件的点击监听
     * @param childClickListener
     */
    public void setOnItemChildClickListener(OnItemChildClickListener childClickListener){
        this.childClickListener = childClickListener;
    }

    /**
     * 根据控件的ID设置点击
     * @param id
     */
    public void setOnItemChildClickById(@IdRes int id){
        if(this.childClickListener == null) return;
        View child = getView(id);
        if(child == null) return;
        child.setOnClickListener(v -> this.childClickListener.onItemChildClick((ViewGroup) this.itemView, child, this.getAdapterPosition()));
    }

    /**
     * 设置item子控件的选中事件监听
     * @param childCheckChangeListener
     */
    public void setOnItemChildCheckChangeListener(OnItemChildCheckChangeListener childCheckChangeListener){
        this.childCheckChangeListener = childCheckChangeListener;
    }

    /**
     * 根据控件ID设置check事件
     * @param id
     */
    public void setOnItemChildCheckChangeById(@IdRes int id){
        if(this.childCheckChangeListener == null) return;
        View checkView = getView(id);
        if(checkView == null) return;
        ((CompoundButton) checkView).setOnCheckedChangeListener((buttonView, isChecked) -> this.childCheckChangeListener.onItemChildCheckChange((ViewGroup)this.itemView, buttonView, getAdapterPosition(), isChecked));
    }
}
