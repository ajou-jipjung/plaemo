package com.po771.plaemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat.Builder;

import com.po771.plaemo.DB.BaseHelper;

public class AlarmService extends Service {

    BaseHelper baseHelper;
    String Channel_id = "default_channel_id";
    String Channel_name = "default_channel_name";
    Builder builder;
    RemoteViews remoteViews;
    NotificationManager mNotificationManager;
    @Override
    public void onCreate() {
        super.onCreate();
        startForegroundService();
        Log.d("AlarmService", "oncreate");
    }



    void startForegroundService() {
        Intent notificationIntent = new Intent(this, PlaemoMainDocActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        remoteViews = new RemoteViews(getPackageName(), R.layout.notification_foreground);

        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(Channel_id,
                    Channel_name,
                    NotificationManager.IMPORTANCE_DEFAULT);

//            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
//                    .createNotificationChannel(channel);

            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(channel);

            builder = new Builder(this, Channel_id);
        } else {
            builder = new Builder(this);
        }
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContent(remoteViews)
                .setContentIntent(pendingIntent);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int alarm_id = intent.getExtras().getInt("alarm_id",-1);
//        int book_id = intent.getExtras().getInt("book_id",-1);

        remoteViews.setTextViewText(R.id.popup_alarmname,"alarm id : "+alarm_id);

        builder.setContent(remoteViews);

        startForeground(alarm_id, builder.build());

        Bundle bundle = intent.getExtras();
        Intent popupIntent = new Intent(this, PlaemoAlarmPopupActivity.class);
        popupIntent.putExtras(bundle);
        Log.d("AlarmService","get alarm id "+alarm_id);
//        Log.d("AlarmService","get book id "+book_id);


        startActivity(popupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("onDestory() 실행", "서비스 파괴");

        super.onDestroy();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



}
