package com.po771.plaemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.po771.plaemo.DB.BaseHelper;

public class PlaemoMainFolderActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plemomainfolder_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mainfolderaction_book:
                Intent addDoc_intent = new Intent(this, AddDocActivity.class);
                startActivity(addDoc_intent);
                return true;
            case R.id.mainfolderaction_alarm:
                Intent alarmList_intent = new Intent(this, PlaemoAlarmListActivity.class);
                startActivity(alarmList_intent);
                return true;
            case R.id.mainfolderaction_setting:
                Intent setting_intent = new Intent(this, PlaemoMainSettingActivity.class);
                startActivity(setting_intent);
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaemo_main);

        BaseHelper baseHelper = BaseHelper.getInstance(this);
        List<String> folderList= baseHelper.getAllmemo();

        RecyclerView recyclerView = findViewById(R.id.plemofolder_recylcerview);
//        int width =
        GridLayoutManager manager = new GridLayoutManager(this,5);
        recyclerView.setLayoutManager(manager);
        PlaemoMainFolder_Adapter adapter = new PlaemoMainFolder_Adapter(folderList);
        recyclerView.setAdapter(adapter);
    }

}
