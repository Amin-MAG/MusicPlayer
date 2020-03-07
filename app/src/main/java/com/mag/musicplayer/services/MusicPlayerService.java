package com.mag.musicplayer.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MusicPlayerService extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


//        // Channel
//
//        NotificationChannel channel =
//                new NotificationChannel(
//                        MAIN_CHANNEL_ID,
//                        getString(R.string.channel_name),
//                        NotificationManager.IMPORTANCE_DEFAULT
//                );
//        channel.setDescription(getString(R.string.channel_description));
//
//        final NotificationManager notificationManager = getSystemService(NotificationManager.class);
//        notificationManager.createNotificationChannel(channel);
//
//
//        // Main Notification
//
//
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//
//
//        Notification notification =
//                new Notification.Builder(this, MAIN_CHANNEL_ID)
//                        .setContentTitle(getText(R.string.notification_title))
//                        .setContentText(getText(R.string.notification_message))
//                        .setSmallIcon(R.drawable.icon)
//                        .setContentIntent(pendingIntent)
//                        .setPriority(Notification.PRIORITY_HIGH)
//                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon))
//                        .setTicker(getText(R.string.ticker_text))
//                        .build();


//        notificationManager.notify(20002, notification);

//        startForeground(20002, notification);


        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
