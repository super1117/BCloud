package com.zero.bcloud.widget.gallery;

public interface IZoomView {
    void reset();
    boolean isZoomToOriginalSize();
    void setSize(int width, int height);
    void setMargin(int marginLeft, int marginTop);
}
