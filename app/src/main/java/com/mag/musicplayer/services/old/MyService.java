package com.mag.musicplayer.services.old;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;


public class MyService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ServiceRunning", "onStartCommand: ");
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("ServiceRunning", "onBind: ");
        return null;
    }


    @Override
    public void onDestroy() {
        Log.d("ServiceRunning", "onDestroy: ");
        super.onDestroy();
    }


}
