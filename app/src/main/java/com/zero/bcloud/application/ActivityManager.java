package com.zero.bcloud.application;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * create by szl on 2018/9/27
 */

public class ActivityManager {

    private static Stack<Activity> mActivityStack;

    private static ActivityManager mActivityManager;

    private ActivityManager(){}

    public static ActivityManager getInstance(){
        if(mActivityManager == null){
            mActivityManager = new ActivityManager();
        }
        return mActivityManager;
    }

    /**
     * 添加activity到堆栈
     * @param activity
     */
    public void addActivity(Activity activity){
        if(mActivityStack == null){
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 获取当前activity
     * @return
     */
    public Activity getCurrentActivity(){
        return mActivityStack.size() > 0 ? mActivityStack.lastElement() : null;
    }

    /**
     * 结束指定activity
     * @param activity
     */
    public void finishActivity(Activity activity){
        if(activity == null){
            return;
        }
        mActivityStack.remove(activity);
        activity.finish();
        activity = null;
    }

    /**
     * 移除指定activity
     * @param activity
     */
    public void removeActivity(Activity activity){
        if(activity != null){
            mActivityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 结束指定类名的activity
     * @param cls
     */
    public void finishActivity(Class<?> cls){
        for(Activity activity : mActivityStack){
            if(activity.getClass().equals(cls)){
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有的activity
     */
    public void finishAllActivity(){
        try{
            for(int i = 0, j = mActivityStack.size(); i < j; i++){
                if(mActivityStack.get(i) != null){
                    mActivityStack.get(i).finish();
                }
            }
            mActivityStack.clear();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 退出应用程序
     * @param hasService
     */
    public void exit(Boolean hasService){
        finishAllActivity();
        if(!hasService){//如果没有后台运行程序就完全退出
            System.exit(0);
        }
    }

}