package com.zero.bcloud.utils.drag;

public interface OnItemDragCallbackListener {

    void onMove(int fromPosition, int toPosition);

    void onSwipe(int position);

    void onComplete();

}
