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

        remoteViews = new RemoteViews(getPackageName(), R.layout.notification_foreground);

        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(Channel_id,
                    Channel_name,
                    NotificationManager.IMPORTANCE_DEFAULT);

//            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
//                    .createNotificationChannel(channel);

            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(channel);
            mNotificationManager.cancelAll();

            builder = new Builder(this, Channel_id);
        } else {
            builder = new Builder(this);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        if(intent.getAction().equals("start_action")){
//            int alarm_id = intent.getExtras().getInt("alarm_id",-1);
//            int book_id = intent.getExtras().getInt("book_id",-1);
//            Intent notificationIntent = new Intent(this, PDFViewerActivity.class);
//            notificationIntent.putExtra("bookId",book_id);
//            notificationIntent.putExtra("readState","resume");
//            notificationIntent.putExtra("alarm_id",alarm_id);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,  PendingIntent.FLAG_CANCEL_CURRENT);
//            remoteViews.setTextViewText(R.id.popup_alarmname,"alarm id : "+alarm_id);
//
//            builder.setSmallIcon(R.mipmap.ic_launcher)
//                    .setDefaults(Notification.DEFAULT_ALL) // 알림, 사운드 진동 설정
//                    .setContentTitle("sdfsf")
//                    .setContentText("ggg")
//                    .setContentIntent(pendingIntent);
//            Notification notification = builder.build();
//            notification.flags |= Notification.FLAG_AUTO_CANCEL;
////            mNotificationManager.notify(11,notification);
//            startForeground(1, notification);
//        }
//        else if(intent.getAction().equals("stop_action")){
//            stopForeground(true);
//            stopSelf();
//        }
        Bundle bundle = intent.getExtras();
        Intent popupIntent = new Intent(this, PlaemoAlarmPopupActivity.class);
        popupIntent.putExtras(bundle);
//        Log.d("AlarmService","get alarm id "+alarm_id);
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
