package com.po771.plaemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PlemoDocActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plemo_doc);

        String folder_name = getIntent().getStringExtra("folder_name");
        this.setTitle(folder_name);
    }
}
