package com.po771.plaemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class AlarmService extends Service {
    private Vibrator vibrator;
    private int repeat = 2;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        //Oreo(26) 버전 이후부터 background 실행X. foreground에서 실행해야함
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = createNotificationChannel();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
            Notification notification = builder.setOngoing(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
            startForeground(1, notification);
        }

//        int ison = intent.getExtras().getInt("ison");
//        int isvibrate = intent.getExtras().getInt("isvibrate");
//        ArrayList<Integer> data = intent.getExtras().getIntegerArrayList("data");
//            IntentData intentData = (IntentData) intent.getSerializableExtra("data");
        Bundle bundle = intent.getExtras();
        int ison = bundle.getInt("ison");
        int isvibrate = bundle.getInt("isvibrate");

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            vibrator.vibrate(VibrationEffect.createOneShot(5000, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(5000);
        }

        Log.d("Alarm Service", "Alarm Start");


//        if(ison == 1 && repeat !=0){
//            // 알람음 재생 OFF, 알람음 시작 상태
//            if(isvibrate == 1) {//진동 ON
//                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
//                    vibrator.vibrate(VibrationEffect.createOneShot(5000, VibrationEffect.DEFAULT_AMPLITUDE));
//                } else {
//                    vibrator.vibrate(5000);
//                }
//            }
//            //진동 OFF면 팝업만
//            Log.d("남은 반복횟수", "남은 반복횟수 : "+(repeat-1));
//            repeat--;
//            Log.d("Alarm Service", "Alarm Start");


//        }
//        else if (ison == 0 || repeat == 0){
//            // 알람음 재생 ON, 알람음 중지 상태
//            vibrator.cancel();
//            Log.d("Alarm Service", "Alarm Stop");
//            Intent popupIntent = new Intent(this, PopupActivity.class);
//            popupIntent.putExtra("data", "Test Popup");
//            startActivityForResult(popupIntent, 1);

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                stopForeground(true);
//            }
//        }

        return START_NOT_STICKY;
        //        return super.onStartCommand(intent, flags, startId);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private  String createNotificationChannel() {
        String channelId = "Alarm";
        String channelName = getString(R.string.app_name);
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE);

        channel.setSound(null, null);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);


        return channelId;
    }

}
