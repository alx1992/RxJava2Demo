package com.lixin.rxjava2demo.rxjavaimageloader;


import android.content.Context;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * @author LiXin
 * @date 2018/11/7 22:15
 * @description RequestCreator
 * @file RxJava2Demo
 */
public class RequestCreator {

    private static final String TAG = "MyRequestCreator";
    
    private MemoryCache mMemoryCache;
    private DiskCache mDiskCache;
    private NetworkCache mNetworkCache;


    public RequestCreator(Context context){

        mMemoryCache = new MemoryCache();
        mDiskCache = new DiskCache(context);
        mNetworkCache = new NetworkCache();

    }


    public Observable<ImageBean> getImageFromMemory(String url){

        return mMemoryCache
                .getImage(url)
                .filter(new Predicate<ImageBean>() {
                    @Override
                    public boolean test(ImageBean imageBean) throws Exception {
                        return imageBean != null;
                    }
                }).doOnNext(new Consumer<ImageBean>() {
                    @Override
                    public void accept(ImageBean imageBean) throws Exception {
                        Log.d(TAG, "accept: get data from memory");
                    }
                });

    }


    public Observable<ImageBean> getImageFromDisk(String url){

        return mDiskCache
                .getImage(url)
                .filter(new Predicate<ImageBean>() {
                    @Override
                    public boolean test(ImageBean imageBean) throws Exception {
                        return imageBean != null;
                    }
                }).doOnNext(new Consumer<ImageBean>() {
                    @Override
                    public void accept(ImageBean imageBean) throws Exception {
                        Log.d(TAG, "accept: get data from disk");
                        mMemoryCache.putDataFromCache(imageBean);
                    }
                });

    }

    public Observable<ImageBean> getImageFromNetwork(String url){


        return mNetworkCache
                .getImage(url)
                .filter(new Predicate<ImageBean>() {
                    @Override
                    public boolean test(ImageBean imageBean) throws Exception {
                        return imageBean != null;
                    }
                })
                .doOnNext(new Consumer<ImageBean>() {
            @Override
            public void accept(ImageBean imageBean) throws Exception {
                Log.d(TAG, "accept: get data from network");
                mDiskCache.putDataFromCache(imageBean);
                mMemoryCache.putDataFromCache(imageBean);

            }
        });

    }

}
