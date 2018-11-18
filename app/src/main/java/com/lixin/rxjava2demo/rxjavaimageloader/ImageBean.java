package com.lixin.rxjava2demo.rxjavaimageloader;

import android.graphics.Bitmap;

/**
 * @author LiXin
 * @date 2018/11/7 21:48
 * @description ImageBean
 * @file RxJava2Demo
 */
public class ImageBean {

    private String url;

    private Bitmap mBitmap;


    public ImageBean(String url, Bitmap bitmap) {

        this.url = url;
        mBitmap = bitmap;
    }

    public ImageBean() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }
}
