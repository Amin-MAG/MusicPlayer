package com.mag.musicplayer.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.mag.musicplayer.Broadcasts.NotificationReceiver;
import com.mag.musicplayer.view.activity.MusicPlayerActivity;
import com.mag.musicplayer.view.notification.MusicPlayerNotification;

public class MusicPlayerService extends Service {

    public static final String GO_NEXT = "GO_NEXT";
    public static final String PLAY_PAUSE = "PLAY_PAUSE";
    public static final String GO_BACK = "GO_BACK";

    // Pending Intents
    private PendingIntent goPreviousPI;
    private PendingIntent playPausePI;
    private PendingIntent goNextPI;
    private PendingIntent openMusicPlayerPI;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createActionsPI();
        // Track player notification
        final NotificationManager notificationManager = getSystemService(NotificationManager.class);
        MusicPlayerNotification.getInstance().prepare(MusicPlayerService.this, notificationManager, openMusicPlayerPI, goPreviousPI, playPausePI, goNextPI);
        MusicPlayerNotification.getInstance().run();

        startForeground(MusicPlayerNotification.NOTIFICATION_ID, MusicPlayerNotification.getInstance().getNotification());

        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void  createActionsPI() {
        // Actions and intents
        Intent goPreviousIntent = new Intent(MusicPlayerService.this, NotificationReceiver.class);
        goPreviousIntent.setAction(GO_BACK);
        goPreviousPI = PendingIntent.getBroadcast(MusicPlayerService.this, 0, goPreviousIntent, 0);

        Intent playPauseIntent = new Intent(MusicPlayerService.this, NotificationReceiver.class);
        playPauseIntent.setAction(PLAY_PAUSE);
        playPausePI = PendingIntent.getBroadcast(MusicPlayerService.this, 0, playPauseIntent, 0);

        Intent goNextIntent = new Intent(MusicPlayerService.this, NotificationReceiver.class);
        goNextIntent.setAction(GO_NEXT);
        goNextPI = PendingIntent.getBroadcast(MusicPlayerService.this, 0, goNextIntent, 0);

        Intent openMusicPlayerIntent = new Intent(MusicPlayerService.this, MusicPlayerActivity.class);
        openMusicPlayerPI = PendingIntent.getActivity(MusicPlayerService.this, 0, openMusicPlayerIntent, 0);
    }
}
