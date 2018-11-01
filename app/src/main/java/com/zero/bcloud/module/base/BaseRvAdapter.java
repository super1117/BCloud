package com.zero.bcloud.module.base;

import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zero.bcloud.module.listener.OnItemChildCheckChangeListener;
import com.zero.bcloud.module.listener.OnItemChildClickListener;
import com.zero.bcloud.module.listener.OnItemClickListener;
import com.zero.bcloud.module.listener.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private RecyclerView recyclerView;

    protected List<T> mData;

    private LayoutInflater inflater;

    private boolean showFooterView = true;

    private final static int FOOTER = android.R.layout.test_list_item;

    private OnItemChildCheckChangeListener childCheckChangeListener;

    private OnItemChildClickListener childClickListener;

    private OnItemLongClickListener longClickListener;

    private OnItemClickListener clickListener;

    public BaseRvAdapter(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
        this.mData = new ArrayList<>();
        this.inflater = LayoutInflater.from(recyclerView.getContext());
    }

    @Override
    public int getItemCount() {
        return showFooterView ? (this.mData.size() > 0 ? this.mData.size() + 1 : this.mData.size()) : this.mData.size();
    }

    @CallSuper
    @Override
    public int getItemViewType(int position) {
        return showFooterView && position == getItemCount() ? FOOTER : onLayoutRes(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(viewType, parent, false);
        BaseViewHolder holder = new BaseViewHolder(this.recyclerView, view);
        holder.setOnItemChildCheckChangeListener(this.childCheckChangeListener);
        holder.setOnItemChildClickListener(this.childClickListener);
        holder.setOnItemClickListener(this.clickListener);
        holder.setOnItemLongClickListener(this.longClickListener);
        this.setItemChildListener(holder, viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindData(holder, position, getItemData(position));
    }

    /**
     * 设置子控件的点击事件
     * @param holder
     * @param viewType
     */
    protected void setItemChildListener(BaseViewHolder holder, int viewType) {}

    /**
     * list item layout
     * @param position
     * @return
     */
    protected abstract int onLayoutRes(int position);

    /**
     * 给item绑定数据
     * @param holder
     * @param position
     * @param model
     */
    protected abstract void bindData(BaseViewHolder holder, int position, T model);

    /**
     * 设置列表数据
     * @param data
     */
    public void setData(List<T> data){
        this.mData.clear();
        this.mData.addAll(data);
        this.notifyDataSetChanged();
    }

    /**
     * 获取列表数据
     * @return
     */
    public List<T> getData(){
        return this.mData;
    }

    /**
     * 设置是否展示加载更多布局
     * @param showFooterView
     */
    public void setShowFooterView(boolean showFooterView){
        this.showFooterView = showFooterView;
    }

    /**
     * 获取指定位置的数据
     * @param position
     * @return
     */
    public T getItemData(int position){
        return this.mData.get(position);
    }

    /**
     * item点击事件
     * @param clickListener
     */
    public void setOnItemClickListener(OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    /**
     * item 长按事件
     * @param longClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener){
        this.longClickListener = longClickListener;
    }

    /**
     * item子控件点击事件
     * @param childClickListener
     */
    public void setOnItemChildClickListener(OnItemChildClickListener childClickListener){
        this.childClickListener = childClickListener;
    }

    /**
     * item子控件check事件
     * @param childChangeListener
     */
    public void setOnItemChildChangeListener(OnItemChildCheckChangeListener childChangeListener){
        this.childCheckChangeListener = childCheckChangeListener;
    }
}