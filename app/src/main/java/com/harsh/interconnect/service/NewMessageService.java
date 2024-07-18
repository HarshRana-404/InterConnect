package com.harsh.interconnect.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.harsh.interconnect.LoginActivity;
import com.harsh.interconnect.MainActivity;
import com.harsh.interconnect.R;

import java.security.Provider;

public class NewMessageService extends Service {

    MediaPlayer mp;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("ForegroundServiceType")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mp = MediaPlayer.create(getApplicationContext(), Settings.System.DEFAULT_RINGTONE_URI);
        mp.start();
        notifyUserForegroundService();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop();
    }
    @SuppressLint("ForegroundServiceType")
    private void notifyUserForegroundService() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel nfc = new NotificationChannel("ChannelDemo", "InterNoti", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager nfm = getSystemService(NotificationManager.class);
            nfm.createNotificationChannel(nfc);
        }
        Intent in = new Intent(this, LoginActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, in, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder(this, "ChannelDemo")
                .setContentTitle("Playing Flute!")
                .setSmallIcon(R.drawable.ic_icon)
                .setColor(Color.rgb(255, 152, 0))
                .setContentIntent(pi)
                .build();
        startForeground(2004,notification);
    }

    @SuppressLint("MissingPermission")
    public void generateNotification(String title) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel nfc = new NotificationChannel("ChannelDemo", "InterNoti", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager nfm = getSystemService(NotificationManager.class);
            nfm.createNotificationChannel(nfc);
        }
        NotificationCompat.Builder bl = new NotificationCompat.Builder(this,"InterNoti");
        bl.setContentTitle(title);
        bl.setAutoCancel(false);
        bl.setSmallIcon(R.drawable.ic_launcher_foreground);
        Intent in = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, in, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        bl.setContentIntent(pi);
        bl.setAutoCancel(true);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1, bl.build());
    }
}
