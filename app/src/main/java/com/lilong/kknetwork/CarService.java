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


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getSpeedData();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
                    Message message = new Message();
                    message.obj = speed;
                    mHandler.sendMessage(message);
                    // 创建并发布事件
                    EventBus.getDefault().post(speed);
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