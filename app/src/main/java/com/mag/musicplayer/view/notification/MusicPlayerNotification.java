package com.mag.musicplayer.view.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;

import com.mag.musicplayer.R;

public class MusicPlayerNotification {

    private static MusicPlayerNotification instance;

    public static final int NOTIFICATION_ID = 20002;

    private static final String MAIN_MUSIC_CHANNEL_ID = "MAIN_MUSIC_CHANNEL_ID";
    private static final String CHANNEL_NAME = "MusicPlayer";

    public static MusicPlayerNotification getInstance() {

        if (instance == null)
            instance = new MusicPlayerNotification();

        return instance;
    }

    private MusicPlayerNotification() {
    }

    private Context context;
    private NotificationManager notificationManager;
    private Notification notification;
    private PendingIntent nextPI;
    private PendingIntent pausePI;
    private PendingIntent previousPI;

    public void makeNotificationChannel() {
        NotificationChannel channel =
                new NotificationChannel(
                        MAIN_MUSIC_CHANNEL_ID,
                        CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_DEFAULT
                );

        notificationManager.createNotificationChannel(channel);
    }

    public void buildNotification() {

        this.notification =
                new Notification.Builder(context, MAIN_MUSIC_CHANNEL_ID)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setVisibility(Notification.VISIBILITY_PUBLIC)
                        .addAction(R.drawable.ic_skip_previous, "Previous", previousPI) // #0
                        .addAction(R.drawable.ic_pause, "Pause", pausePI)  // #1
                        .addAction(R.drawable.ic_skip_next, "Next", nextPI)     // #2
                        .setContentTitle("Wonderful music")
                        .setContentText("My Awesome Band")
                        .setProgress(100, 50, false)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo))
                        .setStyle(new Notification.MediaStyle())
                        .build();

    }

    public void prepare(Context context, NotificationManager notificationManager, PendingIntent previousPI, PendingIntent pausePI, PendingIntent nextPI) {

        this.context = context;
        this.previousPI = previousPI;
        this.notificationManager = notificationManager;
        this.pausePI = pausePI;
        this.nextPI = nextPI;

        makeNotificationChannel();
        buildNotification();
    }

    public void run() {
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    public Notification getNotification() {
        return notification;
    }
}
