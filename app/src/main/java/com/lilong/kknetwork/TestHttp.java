package com.lilong.kknetwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.lilong.kknetwork.network.NetworkApiKt;
import com.lilong.kknetwork.network.response.AmapResponse;
import com.lilong.kknetwork.network.response.Regeocode;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TestHttp extends Activity implements CarService.OnSpeedChangeListener {

    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // 注册订阅者
        EventBus.getDefault().register(this);
        textView = findViewById(R.id.tv_test);



        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestRx();
//                Intent intent = new Intent(TestHttp.this, CarService.class);
//                startService(intent);
            }
        });
    }



    // 定义处理MessageEvent的方法，并用@Subscribe注解标记
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Integer speed) {
        textView.setText(String.valueOf(speed));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * get请求
     */
    public void requestRx() {
        Map<String, String> map = new HashMap<>();
        //固定key
        map.put("key", "ca6ca4ad54fff4ea7c1e070ebf7d83d5");
//        map.put("location","116.480881,39.989410")
        //小数点后不能超过6位
        map.put("location", "106.743723,29.640969");
        NetworkApiKt.getApiServiceRx().getAddress(map).
                subscribeOn(Schedulers.computation()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<AmapResponse<Regeocode>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AmapResponse<Regeocode> regeocodeAmapResponse) {

                        Log.d("TAG", "onNext" + regeocodeAmapResponse.toString());
                        textView.setText(regeocodeAmapResponse.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void postRequst() {
        NetworkApiKt.getApiServiceRx().postWitchForm("parameter1", "parameter2", "parameter3")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AmapResponse<Regeocode>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(AmapResponse<Regeocode> regeocodeAmapResponse) {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void onSpeedChange(int speed) {
        textView.setText(String.valueOf(speed));
    }
}
