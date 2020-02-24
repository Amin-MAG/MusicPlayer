package com.mag.musicplayer.services.old;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MusicPlayerService extends Service {


    private MediaPlayerHolder mediaPlayerHolder;
    private MusicNotificationManager musicNotificationManager;

    private final IBinder binder = new LocalBinder();


    private class LocalBinder extends Binder {
        private MusicPlayerService getService() {
            return MusicPlayerService.this;
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ServiceRunning", "onStartCommand: ");
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("ServiceRunning", "onBind: ");
        if (mediaPlayerHolder == null) {
            mediaPlayerHolder = new MediaPlayerHolder(this);
            musicNotificationManager = new MusicNotificationManager(this);
            mediaPlayerHolder.registerNotificationActionsReceiver(true);
        }
        return binder;
    }


    @Override
    public void onDestroy() {
        Log.d("ServiceRunning", "onDestroy: ");
        mediaPlayerHolder.registerNotificationActionsReceiver(false);
        musicNotificationManager = null;
        mediaPlayerHolder.release();
        super.onDestroy();
    }

    public MediaPlayerHolder getMediaPlayerHolder() {
        return mediaPlayerHolder;
    }

    public MusicNotificationManager getMusicNotificationManager() {
        return musicNotificationManager;
    }

}
