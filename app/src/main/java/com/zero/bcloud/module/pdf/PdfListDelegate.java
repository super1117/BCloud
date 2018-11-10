package com.zero.bcloud.module.pdf;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zero.bcloud.R;
import com.zero.library.mvp.view.AppDelegate;

public class PdfListDelegate extends AppDelegate {

    private PdfListAdapter adapter;

    private RecyclerView recyclerView;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_list_pdf;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        this.recyclerView = this.get(R.id.pdf_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.recyclerView.setLayoutManager(layoutManager);
        this.adapter = new PdfListAdapter(this.recyclerView);
        this.recyclerView.setAdapter(this.getAdapter());
    }

    public PdfListAdapter getAdapter() {
        return this.adapter;
    }

    public void refreshPdfList(){
        this.getAdapter().notifyDataSetChanged();
    }
}
