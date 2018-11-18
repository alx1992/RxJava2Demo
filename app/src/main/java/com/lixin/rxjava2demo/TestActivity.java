package com.lixin.rxjava2demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.lixin.rxjava2demo.rxjavaimageloader.RxImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.button2)
    Button button2;

    String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button2)
    public void onViewClicked() {

        RxImageLoader.with(this).load(imageUrl).into(imageView2);

    }
}
