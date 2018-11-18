package com.lixin.rxjava2demo.rxjavaimageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.lixin.rxjava2demo.CacheObservable;
import com.lixin.rxjava2demo.dislrucache.DiskLruCache;
import com.lixin.rxjava2demo.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author LiXin
 * @date 2018/11/7 22:11
 * @description DiskCache
 * @file RxJava2Demo
 */
public class DiskCache extends CacheObservable {

    private Context mContext;

    private DiskLruCache mDiskLruCache;

    public DiskCache(Context context){

        this.mContext = context;

        this.mDiskLruCache = Utils.getDiskLruCacheInstance(context);

    }

    @Override
    public ImageBean getDataFromCache(String url) {

        Bitmap bitmap = getDataFromDisLruCache(url);

        if (bitmap != null) {

            return new ImageBean(url, bitmap);

        }


        return null;
    }

    @Override
    public void putDataFromCache(final ImageBean imageBean) {

        Observable
                .create(new ObservableOnSubscribe<ImageBean>() {
            @Override
            public void subscribe(ObservableEmitter<ImageBean> emitter) throws Exception {
                putDataToDisLruCache(imageBean.getUrl());
            }
        })
                .subscribeOn(Schedulers.io())
                .subscribe();



    }

    /**
     * 往DisLruCache中写入图片
     */
    public void putDataToDisLruCache(String imageUrl){

        try {
            //第一步：获取将要缓存的图片的对应的唯一key值
            String key = Utils.hashKeyForDisk(imageUrl);
            //第二步：获取diskLruCache的Editor
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);

            if (editor != null) {
                //第三步：从Editor中获取OutputStream
                OutputStream outputStream = editor.newOutputStream(0);
                //第四步：下载网络图片切保存至DiskLruCache图片缓存中
                if (Utils.downloadUrlToStream(imageUrl, outputStream)) {
                    editor.commit();
                } else {
                    editor.abort();
                }
            }
            mDiskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 从DisLruCache中获取图片
     * @param imageUrl
     * @return
     */
    public  Bitmap getDataFromDisLruCache(String imageUrl){

        InputStream is = null;
        Bitmap bitmap = null;
        try {
            //String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
            String key = Utils.hashKeyForDisk(imageUrl);
            DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);

            if (snapShot != null) {
                is = snapShot.getInputStream(0);
                bitmap =  BitmapFactory.decodeStream(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return  bitmap;
    }

}
