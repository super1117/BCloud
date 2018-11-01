package com.zero.bcloud.module.createpdf;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zero.bcloud.R;
import com.zero.library.mvp.view.AppDelegate;

public class CreatePdfDelegate extends AppDelegate{

    private RecyclerView recyclerView;

    private CreatePdfAdapter adapter;

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
    }

    public CreatePdfAdapter getAdapter() {
        return adapter;
    }
}
