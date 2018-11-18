package com.lixin.rxjava2demo.rxjavaimageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.lixin.rxjava2demo.CacheObservable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author LiXin
 * @date 2018/11/7 22:11
 * @description NetworkCache
 * @file RxJava2Demo
 */
public class NetworkCache extends CacheObservable {

    private static final String TAG = "NetworkCache";

    @Override
    public ImageBean getDataFromCache(String url) {


        Bitmap bitmap = downloadImage(url);


        if (bitmap != null) {

            return  new ImageBean(url, bitmap);

        }

        return null;
    }

    @Override
    public void putDataFromCache(ImageBean imageBean) {

    }

    /**
     * 下载文件
     * @param url
     * @return
     */

    private Bitmap downloadImage(String url){
        Log.d(TAG, "downloadImage: 下载图片");
        Bitmap bitmap = null;
        InputStream in = null;
        URLConnection connection =  null;


        try {

            URL imageUrl = new URL(url);

            connection = (HttpURLConnection)imageUrl.openConnection();

             in = connection.getInputStream();

            bitmap = BitmapFactory.decodeStream(in);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return bitmap;


    }


}
