package com.lixin.rxjava2demo;

import android.util.Log;

import com.lixin.rxjava2demo.rxjavaimageloader.ImageBean;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * @author LiXin
 * @date 2018/11/7 21:54
 * @description CacheObservable
 * @file RxJava2Demo
 */
public abstract class CacheObservable {

    private static final String TAG = "MyCacheObservable";

    /**
     * 得到图片
     * @param url
     * @return
     */
    public Observable<ImageBean> getImage(final String url){


        return Observable.create(new ObservableOnSubscribe<ImageBean>() {
            @Override
            public void subscribe(ObservableEmitter<ImageBean> emitter) throws Exception {
                if (!emitter.isDisposed()) {

                    ImageBean imageBean = getDataFromCache(url);

                    if (imageBean != null) {

                        emitter.onNext(imageBean);

                    }
                        emitter.onComplete();

                }



            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 从缓存中得到数据
     * @param url
     * @return
     */
    public abstract ImageBean getDataFromCache(String url);

    /**
     * 将数据放入缓存
     * @param imageBean
     */
    public abstract void putDataFromCache(ImageBean imageBean);

}
