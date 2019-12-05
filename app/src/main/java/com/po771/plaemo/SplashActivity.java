package com.po771.plaemo;


import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_book;
import com.po771.plaemo.item.Item_folder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class SplashActivity extends AppCompatActivity {

//    DataManager dataManager;
    long startTime;
    long endTime;
    private final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startTime = System.currentTimeMillis();

        if(request()){
            initThing();
            endTime = System.currentTimeMillis();

            long delayMax = 2000;
            long delayTime = endTime - startTime;
            if (delayMax > delayTime) {
                try {
                    Thread.sleep(delayMax - delayTime);
                } catch (InterruptedException e) {
                }


            }
            Intent intent = new Intent(this, PlaemoMainFolderActivity.class);
            startActivity(intent);

            finish();
        }
    }
    private boolean request(){
        int permssionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permssionCheck != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initThing();
                    endTime = System.currentTimeMillis();

                    long delayMax = 2000;
                    long delayTime = endTime - startTime;
                    if (delayMax > delayTime) {
                        try {
                            Thread.sleep(delayMax - delayTime);
                        } catch (InterruptedException e) {
                        }


                    }
                    Intent intent = new Intent(this, PlaemoMainFolderActivity.class);
                    startActivity(intent);

                    finish();
                } else {
                    finish();
                }
                return;
            }
        }
    }

    private void initThing(){

        BaseHelper baseHelper = BaseHelper.getInstance(this);

        if(baseHelper.initDB()){


            Item_folder folder = new Item_folder();

            folder.setBook_id(-1);//더미값
            folder.setFolder_name("전체");
            baseHelper.insertFolder(folder);

            folder.setBook_id(-2);//더미값
            folder.setFolder_name("즐겨찾기");
            baseHelper.insertFolder(folder);
//
//            //여기서부턴 더미
//            folder.setBook_id(1);
//            folder.setFolder_name("집교1");
//            baseHelper.insertFolder(folder);
//
//            folder.setBook_id(1);
//            folder.setFolder_name("집교2");
//            baseHelper.insertFolder(folder);
//
//            folder.setBook_id(2);
//            folder.setFolder_name("집교1");
//            baseHelper.insertFolder(folder);
//
//            folder.setBook_id(2);
//            folder.setFolder_name("집교2");
//            baseHelper.insertFolder(folder);
//
//            folder.setBook_id(2);
//            folder.setFolder_name("HCI");
//            baseHelper.insertFolder(folder);
//
//            folder.setBook_id(3);
//            folder.setFolder_name("집교1");
//            baseHelper.insertFolder(folder);
//
//            folder.setBook_id(3);
//            folder.setFolder_name("HCI");
//            baseHelper.insertFolder(folder);
//
//            folder.setBook_id(4);
//            folder.setFolder_name("집교1");
//            baseHelper.insertFolder(folder);
//
//            folder.setBook_id(4);
//            folder.setFolder_name("집교2");
//            baseHelper.insertFolder(folder);
//
//            folder.setBook_id(4);
//            folder.setFolder_name("HCI");
//            baseHelper.insertFolder(folder);
//
//            Item_book item_book= new Item_book();
//            item_book.setBook_name("집교책1");
//            item_book.setBook_uri("책 위치");
//            item_book.setCurrent_page(20);
//            item_book.setTotal_page(100);
//            item_book.setBook_info("책1");
//            item_book.setBook_star(1);
//            item_book.setFolder("집교1/집교2");
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.book1);
//            saveToInternalStorage(bitmap,"1");
//            baseHelper.insertBook(item_book);
//
//            item_book.setBook_name("집교책2");
//            item_book.setBook_uri("책 위치2");
//            item_book.setCurrent_page(40);
//            item_book.setTotal_page(100);
//            item_book.setBook_info("책2");
//            item_book.setBook_star(1);
//            item_book.setFolder("집교1/집교2/HCI");
//            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.book2);
//            saveToInternalStorage(bitmap,"2");
//            baseHelper.insertBook(item_book);
//
//            item_book.setBook_name("집교책3");
//            item_book.setBook_uri("책 위치3");
//            item_book.setCurrent_page(99);
//            item_book.setTotal_page(100);
//            item_book.setBook_info("책3");
//            item_book.setBook_star(1);
//            item_book.setFolder("집교1/HCI");
//            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.book3);
//            saveToInternalStorage(bitmap,"3");
//            baseHelper.insertBook(item_book);
//
//            item_book.setBook_name("집교책4");
//            item_book.setBook_uri("책 위치4");
//            item_book.setCurrent_page(99);
//            item_book.setTotal_page(200);
//            item_book.setBook_info("책4");
//            item_book.setBook_star(1);
//            item_book.setFolder("집교1/집교2/HCI");
//            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.book4);
//            saveToInternalStorage(bitmap,"4");
//            baseHelper.insertBook(item_book);
        }
        AlarmLoader alarmLoader = AlarmLoader.getInstance(this);
        alarmLoader.initAlarm(baseHelper.getAllalarm());
    }

//    private String saveToInternalStorage(Bitmap bitmapImage,String fileName) {
//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//        int layoutwidth = displaymetrics.widthPixels / 3;
//
//        Bitmap resized = resizeBitmapImage(bitmapImage,layoutwidth);
//
//
//        ContextWrapper cw = new ContextWrapper(getApplicationContext());
//        // path to /data/data/yourapp/app_data/imageDir
//        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
//        // Create imageDir
//        File mypath = new File(directory, fileName + ".jpg");
//
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(mypath);
//            // Use the compress method on the BitMap object to write image to the OutputStream
//            resized.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return directory.getAbsolutePath();
//    }

    public Bitmap resizeBitmapImage(Bitmap source, int maxResolution)
    {
        int width = source.getWidth();
        int height = source.getHeight();
        int newWidth = width;
        int newHeight = height;
        float rate = 0.0f;

        if(width > height)
        {
            if(maxResolution < width)
            {
                rate = maxResolution / (float) width;
                newHeight = (int) (height * rate);
                newWidth = maxResolution;
            }
        }
        else
        {
            if(maxResolution < height)
            {
                rate = maxResolution / (float) height;
                newWidth = (int) (width * rate);
                newHeight = maxResolution;
            }
        }

        return Bitmap.createScaledBitmap(source, newWidth, newHeight, true);
    }
}
