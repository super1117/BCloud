package com.zero.bcloud.module.pdf;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zero.bcloud.R;
import com.zero.library.mvp.view.AppDelegate;

public class PdfListDelegate extends AppDelegate {

    private PdfListAdapter adapter;

    private RecyclerView recyclerView;

    private CheckBox checkAll;

    private TextView bottomContent;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_list_pdf;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        this.checkAll = this.get(R.id.pdf_check_all);
        this.recyclerView = this.get(R.id.pdf_rv);
        this.bottomContent = this.get(R.id.pdf_desc);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.recyclerView.setLayoutManager(layoutManager);
        this.adapter = new PdfListAdapter(this.recyclerView);
        this.adapter.setShowFooterView(false);
        this.recyclerView.setAdapter(this.getAdapter());
    }

    public PdfListAdapter getAdapter() {
        return this.adapter;
    }

    public void refreshPdfList(){
        this.getAdapter().notifyDataSetChanged();
    }

    public CheckBox getCheckAll() {
        return this.checkAll;
    }

    public void setBottomContent(int current, int total, String size){
        this.bottomContent.setText("(" + current + "/" + total + ")  " + size);
    }

    public void showMessage(String message, DialogInterface.OnClickListener clickListener){
        new AlertDialog.Builder(this.getActivity())
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton(getActivity().getResources().getString(R.string.ok), clickListener)
                .setNegativeButton(getActivity().getResources().getString(R.string.cancel), null)
                .show();
    }
}
