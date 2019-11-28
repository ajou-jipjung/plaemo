package com.po771.plaemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_book;
import com.po771.plaemo.item.Item_folder;

import java.io.ByteArrayOutputStream;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long startTime = System.currentTimeMillis();

        initThing();
        long endTime = System.currentTimeMillis();

        long delayMax = 2000;
        long delayTime = endTime-startTime;
        if(delayMax>delayTime){
            try {Thread.sleep(delayMax-delayTime);} catch (InterruptedException e) {}


        }
        Intent intent = new Intent(this, PlaemoMainFolderActivity.class);
        startActivity(intent);

        finish();
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

            //여기서부턴 더미
            folder.setBook_id(1);
            folder.setFolder_name("집교1");
            baseHelper.insertFolder(folder);

            folder.setBook_id(1);
            folder.setFolder_name("집교2");
            baseHelper.insertFolder(folder);

            folder.setBook_id(2);
            folder.setFolder_name("집교1");
            baseHelper.insertFolder(folder);

            folder.setBook_id(2);
            folder.setFolder_name("집교2");
            baseHelper.insertFolder(folder);

            folder.setBook_id(2);
            folder.setFolder_name("HCI");
            baseHelper.insertFolder(folder);

            folder.setBook_id(3);
            folder.setFolder_name("집교1");
            baseHelper.insertFolder(folder);

            folder.setBook_id(3);
            folder.setFolder_name("HCI");
            baseHelper.insertFolder(folder);

            folder.setBook_id(4);
            folder.setFolder_name("집교1");
            baseHelper.insertFolder(folder);

            folder.setBook_id(4);
            folder.setFolder_name("집교2");
            baseHelper.insertFolder(folder);

            folder.setBook_id(4);
            folder.setFolder_name("HCI");
            baseHelper.insertFolder(folder);

            Item_book item_book= new Item_book();
            item_book.setBook_name("집교책1");
            item_book.setBook_uri("책 위치");
            item_book.setCurrent_page(20);
            item_book.setTotal_page(100);
            item_book.setBook_info("책1");
            item_book.setBook_star(1);
            item_book.setFolder("집교1/집교2");
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.book1);
            item_book.setImage_bitmap(bitmap);
            baseHelper.insertBook(item_book);

            item_book.setBook_name("집교책2");
            item_book.setBook_uri("책 위치2");
            item_book.setCurrent_page(40);
            item_book.setTotal_page(100);
            item_book.setBook_info("책2");
            item_book.setBook_star(1);
            item_book.setFolder("집교1/집교2/HCI");
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.book2);
            item_book.setImage_bitmap(bitmap);
            baseHelper.insertBook(item_book);

            item_book.setBook_name("집교책3");
            item_book.setBook_uri("책 위치3");
            item_book.setCurrent_page(99);
            item_book.setTotal_page(100);
            item_book.setBook_info("책3");
            item_book.setBook_star(1);
            item_book.setFolder("집교1/HCI");
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.book3);
            item_book.setImage_bitmap(bitmap);
            baseHelper.insertBook(item_book);

            item_book.setBook_name("집교책4");
            item_book.setBook_uri("책 위치4");
            item_book.setCurrent_page(99);
            item_book.setTotal_page(200);
            item_book.setBook_info("책4");
            item_book.setBook_star(1);
            item_book.setFolder("집교1/집교2/HCI");
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.book4);
            item_book.setImage_bitmap(bitmap);
            baseHelper.insertBook(item_book);
        }
    }
}
