package com.lixin.rxjava2demo.rxjavaimageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.lixin.rxjava2demo.CacheObservable;

/**
 * @author LiXin
 * @date 2018/11/7 22:09
 * @description MemoryCache
 * @file RxJava2Demo
 */
public class MemoryCache extends CacheObservable {




        // 1. 获得虚拟机能提供的最大内存
        // 注：超过该大小会抛出OutOfMemory的异常
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // 2. 设置LruCache缓存的大小 = 一般为当前进程可用容量的1/8
        // 注：单位 = Kb
        // 设置准则
        //  a. 还剩余多少内存给你的activity或应用使用
        //  b. 屏幕上需要一次性显示多少张图片和多少图片在等待显示
        //  c. 手机的大小和密度是多少（密度越高的设备需要越大的 缓存）
        //  d. 图片的尺寸（决定了所占用的内存大小）
        //  e. 图片的访问频率（频率高的在内存中一直保存）
        //  f. 保存图片的质量（不同像素的在不同情况下显示）
        final int cacheSize = maxMemory / 8;

        // 3. 重写sizeOf方法：计算缓存对象的大小（此处的缓存对象 = 图片）
        LruCache<String, Bitmap> mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {

            @Override
            protected int sizeOf(String key, Bitmap bitmap) {

                return bitmap.getByteCount() / 1024;
                // 此处返回的是缓存对象的缓存大小（单位 = Kb） ，而不是item的个数
                // 注：缓存的总容量和每个缓存对象的大小所用单位要一致
                // 此处除1024是为了让缓存对象的大小单位 = Kb

            }
        };


    /**
     * 从内存中去图片
     * @param url
     * @return
     */
    @Override
    public ImageBean getDataFromCache(String url) {

        Bitmap bitmap = mMemoryCache.get(url);

        if (bitmap != null) {

            return new ImageBean(url, bitmap);
        }

        return null;
    }

    /**
     * 保存图片到内存中
     * @param imageBean
     */
    @Override
    public void putDataFromCache(ImageBean imageBean) {

        mMemoryCache.put(imageBean.getUrl(), imageBean.getBitmap());

    }




}
