package com.po771.plaemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import com.po771.plaemo.DB.BaseHelper;

public class PlaemoMainFolderActivity extends AppCompatActivity {

    ImageView imageView;
    BaseHelper baseHelper;
    List<String> folderList;
    PlaemoMainFolder_Adapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plaemomainfolder_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mainfolderaction_book:
                Intent addDoc_intent = new Intent(this, AddDocActivity.class);
                startActivityForResult(addDoc_intent,200);
                overridePendingTransition(0, 0);
                return true;
            case R.id.mainfolderaction_alarm:
                Intent alarmList_intent = new Intent(this, PlaemoAlarmListActivity.class);
                startActivity(alarmList_intent);
                overridePendingTransition(0, 0);
                return true;
            case R.id.mainfolderaction_setting:
                Intent setting_intent = new Intent(this, PlaemoMainSettingActivity.class);
                startActivity(setting_intent);
                overridePendingTransition(0, 0);
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaemo_main);

        baseHelper = BaseHelper.getInstance(this);
        folderList= baseHelper.getAllmemo();

        RecyclerView recyclerView = findViewById(R.id.plemofolder_recylcerview);
//        int width =
        GridLayoutManager manager = new GridLayoutManager(this,5);
        recyclerView.setLayoutManager(manager);
        adapter = new PlaemoMainFolder_Adapter(folderList);
        recyclerView.setAdapter(adapter);

//        imageView = (ImageView)findViewById(R.id.test);
//        loadImageFromStorage("집교책1");

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 200: //책 상태 변경 + 아래 메모 변경
                folderList = baseHelper.getAllmemo();
                adapter.update(folderList);
                break;
        }
    }
}
