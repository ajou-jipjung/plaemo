package com.po771.plaemo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.po771.plaemo.DB.BaseHelper;



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
        Intent intent = new Intent(this, PlemoMainActivity.class);
        startActivity(intent);

        finish();
    }


    private void initThing(){
        BaseHelper baseHelper = BaseHelper.getInstance(this);
        baseHelper.initDB();
    }
}
