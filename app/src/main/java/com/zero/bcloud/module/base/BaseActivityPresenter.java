package com.zero.bcloud.module.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.zero.bcloud.R;
import com.zero.bcloud.application.ActivityManager;
import com.zero.library.mvp.presenter.ActivityPresenter;
import com.zero.library.mvp.view.IDelegate;

public abstract class BaseActivityPresenter<T extends IDelegate> extends ActivityPresenter<T> {

    protected Toolbar toolbar;

    public View getContentView(){
        return this.viewDelegate.getRootView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
        this.setItitle();
        this.doMain();
    }

    private void setItitle(){
        toolbar = this.findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().removeActivity(this);
        super.onDestroy();
    }

    protected void start(Class<?> cls){
        startActivity(new Intent(this, cls));
    }

    protected void start(Class<?> cls, Bundle bundle){
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void startForResult(Class<?> cls, int requesetCode){
        startActivityForResult(new Intent(this, cls), requesetCode);
    }

    protected void startForResult(Class<?> cls, int requestCode, Bundle bundle){
        Intent intent = new Intent(this, cls);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public abstract void doMain();
}