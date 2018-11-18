package com.lixin.rxjava2demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixin.rxjava2demo.rxjavaimageloader.ImageBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * @author LiXin
 * @date 2018-11-5
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.imageView)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    public String getDataFromMemory(){

        return "内存缓存";
//        return "";
    }
    public String getDataFromDisk(){

//        return "文件缓存";
        return "";
    }
    public String getDataFromNetWork(){

        return "网络缓存";
//        return "";
    }

    @OnClick(R.id.button)
    public void onViewClicked() {

        Observable<String> memoryObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = getDataFromMemory();
//                if (!TextUtils.isEmpty(result)) {
//                }
                    emitter.onNext(result);
                    emitter.onComplete();


            }
        });


        Observable<String> diskObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                String result = getDataFromDisk();
//
//                if (!TextUtils.isEmpty(result)) {
//                }
                    emitter.onNext(result);
                    emitter.onComplete();
            }
        });


        Observable<String> networkObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                String result = getDataFromNetWork();

//                if (!TextUtils.isEmpty(result)) {
//                }
                    emitter.onNext(result);
                    emitter.onComplete();
            }
        });


        Observable
                .concat(memoryObservable,diskObservable,networkObservable)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return !TextUtils.isEmpty(s);
                    }
                })
                .first("")
//                .firstElement()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

                Log.d(TAG, "onNext: " + s );

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }



}
