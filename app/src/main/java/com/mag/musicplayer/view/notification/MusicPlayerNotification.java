package com.mag.musicplayer.view.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.mag.musicplayer.R;
import com.mag.musicplayer.data.model.Track;
import com.mag.musicplayer.data.repository.MusicPlayer;

import java.io.IOException;

public class MusicPlayerNotification {

    private static MusicPlayerNotification instance;

    public static final int NOTIFICATION_ID = 20002;

    private static final String MAIN_MUSIC_CHANNEL_ID = "MAIN_MUSIC_CHANNEL_ID";
    private static final String CHANNEL_NAME = "MusicPlayer";

    public static MusicPlayerNotification getInstance() {
        if (instance == null) {
            instance = new MusicPlayerNotification();
        }
        return instance;
    }

    private MusicPlayerNotification() {
    }

    private MusicPlayer musicPlayer = MusicPlayer.getInstance();

    private Context context;
    private NotificationManager notificationManager;
    private Notification notification;
    private PendingIntent openAppPI;
    private PendingIntent nextPI;
    private PendingIntent pausePI;
    private PendingIntent previousPI;

    public void makeNotificationChannel() {
        NotificationChannel channel =
                new NotificationChannel(
                        MAIN_MUSIC_CHANNEL_ID,
                        CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_LOW
                );
         //Configure the notification channel, NO SOUND
        channel.setDescription("no sound");
        channel.setSound(null,null);
        channel.enableLights(false);
        channel.enableVibration(false);
        notificationManager.createNotificationChannel(channel);
    }

    public void buildNotification() {

        // Get Data
        Track playingTrack = musicPlayer.getPlayingTrack().getValue();
        String title = playingTrack.getTitle();
        String artist = playingTrack.getArtist();
        Uri coverUri = playingTrack.getImagePath();
        // Open image
        Bitmap trackCover;
        try {
            trackCover = MediaStore.Images.Media.getBitmap(context.getContentResolver(), coverUri);
        } catch (IOException e) {
            trackCover = BitmapFactory.decodeResource(context.getResources(), R.drawable.music_icon);
        }

        this.notification =
                new Notification.Builder(context, MAIN_MUSIC_CHANNEL_ID)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setPriority(Notification.PRIORITY_LOW)
                        .setVisibility(Notification.VISIBILITY_PUBLIC)
                        .addAction(R.drawable.ic_skip_previous, "Previous", previousPI) // #0
                        .addAction(musicPlayer.isPlaying().getValue() ? R.drawable.ic_pause : R.drawable.ic_play, "Pause", pausePI)  // #1
                        .addAction(R.drawable.ic_skip_next, "Next", nextPI)     // #2
                        .setContentIntent(openAppPI)
                        .setContentTitle(title)
                        .setContentText(artist)
                        .setLargeIcon(trackCover)
                        .setStyle(new Notification.MediaStyle().setShowActionsInCompactView(0, 1, 2))
                        .build();

    }

    public void prepare(Context context, NotificationManager notificationManager, PendingIntent openAppPI, PendingIntent previousPI, PendingIntent pausePI, PendingIntent nextPI) {

        this.context = context;
        this.openAppPI = openAppPI;
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
