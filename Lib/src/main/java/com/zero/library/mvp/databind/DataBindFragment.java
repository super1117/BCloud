package com.zero.library.mvp.databind;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zero.library.mvp.model.IModel;
import com.zero.library.mvp.presenter.FragmentPresenter;
import com.zero.library.mvp.view.IDelegate;

public abstract class DataBindFragment<T extends IDelegate> extends FragmentPresenter<T> {

    protected DataBinder binding;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.binding = this.getDataBinder();
    }

    public abstract DataBinder getDataBinder();

    public <D extends IModel> void notifyModelChanged(D data){
        if(this.binding != null){
            this.binding.viewBindModel(this.viewDelegate, data);
        }
    }
}
