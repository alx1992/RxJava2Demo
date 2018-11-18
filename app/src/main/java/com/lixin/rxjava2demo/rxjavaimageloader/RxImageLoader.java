package com.lixin.rxjava2demo.rxjavaimageloader;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;


/**
 * @author LiXin
 * @date 2018/11/7 18:37
 * @description RxImageLoader
 * @file RxJava2Demo
 */
public class RxImageLoader {

    private static final String TAG = "MyRxImageLoader";

    static RxImageLoader singleton;

    private String mUrl;

    private RequestCreator mRequestCreator;


    private RxImageLoader(Builder builder){

        mRequestCreator = new RequestCreator(builder.context);

    }

    public static RxImageLoader with(Context context){

        if (singleton==null) {
            synchronized (RxImageLoader.class){
                if (singleton == null) {
                    singleton = new Builder(context).build();
                }
            }
        }

        return singleton;

    }

    /**
     * 加载URL
     * @param url
     */
    public RxImageLoader load(String url){

        this.mUrl = url;

        return singleton;
    }

    public void into(final ImageView imageView) {

        Observable
                .concat(mRequestCreator.getImageFromMemory(mUrl), mRequestCreator.getImageFromDisk(mUrl), mRequestCreator.getImageFromNetwork(mUrl))
                .filter(new Predicate<ImageBean>() {
                    @Override
                    public boolean test(ImageBean imageBean) throws Exception {
                        return imageBean != null;
                    }
                })
                .first(new ImageBean("", null))
                .toObservable()
//                .firstElement().toObservable()
                .subscribe(new Observer<ImageBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ImageBean imageBean) {
                        if (imageBean.getBitmap() != null) {
                            imageView.setImageBitmap(imageBean.getBitmap());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: 完成");
                    }
                });

    }




    public static class Builder {

        private Context context;

        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context.getApplicationContext();
        }

        /** Create the {@lin RxImageLoader} instance. */
        public RxImageLoader build() {

            return new RxImageLoader(this);
        }



    }
}
