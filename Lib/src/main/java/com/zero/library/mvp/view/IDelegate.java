package com.zero.library.mvp.view;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * View delegate base class
 * 视图层代理的接口协议
 */
public interface IDelegate {

    void create(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState);


    int getOptionMenuId();

    Toolbar getToolbar();

    View getRootView();

    void initWidget();
}
