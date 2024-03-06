package com.lilong.kknetwork;

import android.os.Binder;

public class MyBinder extends Binder {
    private final CarService mService;

    public MyBinder(CarService service) {
        this.mService = service;
    }


    public CarService getmService(){
        return mService;
    }


}