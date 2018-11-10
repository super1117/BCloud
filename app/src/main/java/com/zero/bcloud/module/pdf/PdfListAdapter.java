package com.zero.bcloud.module.pdf;

import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;

import com.zero.bcloud.R;
import com.zero.bcloud.module.base.BaseRvAdapter;
import com.zero.bcloud.module.base.BaseViewHolder;
import com.zero.library.utils.FileUtils;
import com.zero.library.utils.TimeUtils;

public class PdfListAdapter extends BaseRvAdapter<PdfListItemModel> {

    public PdfListAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    protected int onLayoutRes(int position) {
        return R.layout.item_list_pdf;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position, PdfListItemModel data) {
        ((CheckBox) holder.getView(R.id.pdf_check)).setChecked(data.isChecked());
        holder.setText(R.id.pdf_name, data.getFile().getName());
        holder.setText(R.id.pdf_date, TimeUtils.getSendTime(data.getFile().lastModified()));
        holder.setText(R.id.pdf_size, FileUtils.getFileSize(data.getFile().length()));
    }

    @Override
    protected void setItemChildListener(BaseViewHolder holder, int viewType) {
        super.setItemChildListener(holder, viewType);
        holder.setOnItemChildClickById(R.id.pdf_check);
    }
}