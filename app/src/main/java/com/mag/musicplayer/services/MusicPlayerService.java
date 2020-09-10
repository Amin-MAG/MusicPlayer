package com.mag.musicplayer.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.mag.musicplayer.data.repository.MusicPlayer;
import com.mag.musicplayer.view.activity.MusicPlayerActivity;
import com.mag.musicplayer.view.notification.MusicPlayerNotification;

public class MusicPlayerService extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The button intents
        Intent notificationIntent = new Intent(this, MusicPlayerActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        // Track player notification
        final NotificationManager notificationManager = getSystemService(NotificationManager.class);
        MusicPlayerNotification.getInstance().prepare(MusicPlayerService.this, notificationManager, pendingIntent, pendingIntent, pendingIntent);
        MusicPlayerNotification.getInstance().run();

        startForeground(MusicPlayerNotification.NOTIFICATION_ID, MusicPlayerNotification.getInstance().getNotification());

        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
