package com.lilong.kknetwork;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

import java.util.Random;

public class CarService extends Service {

    MyBinder binder = new MyBinder(this);
    private volatile OnSpeedChangeListener listener;

    public void setOnSpeedChangeListener(OnSpeedChangeListener listener) {
        Log.d("TAG", "listene1111r====: "+listener);
        this.listener = listener;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG", "listene22222====: "+listener);
        getSpeedData();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder ;
    }

    public interface OnSpeedChangeListener{
        void onSpeedChange(int speed);
    }

    // Simulate getting speed data from car
    public void getSpeedData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Random random = new Random();
                    int speed = random.nextInt(100) + 1;
                    Log.d("TAG", "listene3333====: "+listener);
                    // 创建并发布事件
//                    EventBus.getDefault().post(speed);
                    if(listener!=null){
                        listener.onSpeedChange(speed);
                    }
                    try {
                        Thread.sleep(1000); // Update speed every second
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    public  Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

        }
    };

}