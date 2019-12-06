package com.po771.plaemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_alarm;
import com.po771.plaemo.item.Item_book;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class AlarmReceiver extends BroadcastReceiver {

    Context context;
    BaseHelper baseHelper;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
        int default_channel_id = 1122;
        String Channel_id = "default_channel_id";
        String Channel_name = "default_channel_name";
        baseHelper = BaseHelper.getInstance(context);
        Bundle bundle = intent.getExtras();
        int alarm_id = intent.getExtras().getInt("alarm_id",-1);
        int book_id = intent.getExtras().getInt("book_id",-1);
        Log.d("AlarmReceiver","get alarm id"+alarm_id);
        Item_alarm item_alarm = baseHelper.getAlarm(alarm_id);
        Item_book item_book = baseHelper.getBook(book_id);

        clearExistingNotifications(22);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(Channel_id,Channel_name,importance);
        channel.setDescription("test");
        channel.setShowBadge(false);
        notificationManager.createNotificationChannel(channel);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        Intent mainfolder = new Intent(context.getApplicationContext(),PlaemoMainFolderActivity.class);
        Intent maindoc = new Intent(context.getApplicationContext(),PlaemoMainDocActivity.class);
        maindoc.putExtra("folder_name","전체");
        Intent docinfo = new Intent(context.getApplicationContext(),DocInfoActivity.class);
        docinfo.putExtra("book_id",(item_book.get_id()));
        Intent notificationIntent = new Intent(context.getApplicationContext(), PDFViewerActivity.class);
        notificationIntent.putExtra("alarm_id",alarm_id);
        notificationIntent.putExtra("bookId",book_id);
        notificationIntent.putExtra("readState","resume");

        stackBuilder.addParentStack(PlaemoMainFolderActivity.class);
        stackBuilder.addNextIntent(mainfolder);
        stackBuilder.addNextIntent(maindoc);
        stackBuilder.addNextIntent(docinfo);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_foreground);

        Bitmap bitmap = loadImageFromInternalStorage(item_alarm.getBook_id());
        remoteViews.setImageViewBitmap(R.id.popup_alarmimage,bitmap);

        remoteViews.setTextViewText(R.id.popup_alarmname,item_alarm.getAlarm_name());

        String alarmtext = ""+item_book.getCurrent_page() + " 페이지부터 읽을 차례에요!";
        remoteViews.setTextViewText(R.id.popup_alarmtext,alarmtext);

        int percent;
        if(item_book.getCurrent_page()==1){
            percent = (int) ((item_book.getCurrent_page()-1)*100) / item_book.getTotal_page();
        }
        else{
            percent = (int) (item_book.getCurrent_page()*100) / item_book.getTotal_page();
        }

        remoteViews.setTextViewText(R.id.popup_percent,percent+"%");
        remoteViews.setInt(R.id.popup_progressBar,"setProgress",percent);

        remoteViews.setOnClickPendingIntent(R.id.popup_alarmbutton2,pendingIntent);


//        remoteViews.setTextViewText(R.id.popup_alarmcontent,item_alarm.getAlarm_name());
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(), Channel_id);
//        builder.setContentTitle("sdffsd");
//        builder.setContentText("dfdf");
        builder.setContent(remoteViews);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        builder.setAutoCancel(true);
        builder.setVibrate(new long[]{500,500,500,500});
        builder.setSmallIcon(R.mipmap.ic_launcher);


        notificationManager.notify(22, builder.build());

//
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        //오레오 대응
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            NotificationChannel notificationChannel = new NotificationChannel(Channel_id, Channel_name, importance);
//            notificationManager.createNotificationChannel(notificationChannel);
//        }
//
//        Intent notificationIntent = new Intent(context.getApplicationContext(), PDFViewerActivity.class);
//        notificationIntent.putExtra("bookId",book_id);
//        notificationIntent.putExtra("readState","resume");
//        // 알림 클릭 시 이동할 액티비티 지정
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        // FLAG_UPDATE_CURRENT : 이미 생성된 PendingIntent가 존재하면 해당 Intent의 Extra Data만 변경한다.
//        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(), Channel_id);
//        builder.setContentTitle("알람") //제목
//                .setContentText("알람 테스트입니다") //내용
//                .setDefaults(Notification.DEFAULT_ALL) //알림 설정(사운드, 진동)
//                .setAutoCancel(true) //터치 시 자동으로 삭제할 지 여부
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setFullScreenIntent(pendingIntent,true)
//                .setSmallIcon(R.mipmap.ic_launcher);
//        notificationManager.notify(1, builder.build());

//        Intent popupIntent = new Intent(context.getApplicationContext(),PlaemoAlarmPopupActivity.class);
//        popupIntent.putExtras(bundle);
//        context.startActivity(popupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    private Bitmap loadImageFromInternalStorage(int fileName)
    {

        try {
            File f=new File(context.getDataDir().getAbsolutePath()+"/app_imageDir", fileName+".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private void clearExistingNotifications(int notificationId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);
    }

//        Intent serviceIntent = new Intent(context, AlarmService.class);
//        serviceIntent.setAction("start_action");
//        serviceIntent.putExtra("alarm_id",alarm_id);
//        serviceIntent.putExtra("book_id",book_id);
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
//            context.startForegroundService(serviceIntent);
//        }else{
//            context.startService(serviceIntent);
//        }
}