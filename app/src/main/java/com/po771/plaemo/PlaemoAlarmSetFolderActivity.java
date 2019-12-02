package com.po771.plaemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.po771.plaemo.DB.BaseHelper;

import java.util.List;

public class PlaemoAlarmSetFolderActivity extends AppCompatActivity {

    BaseHelper baseHelper;
    List<String> folderList;
    PlaemoAlarmSetFolder_Adapter adapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode) {
                case 200: //책 선택 완료
                    int getBookid = data.getExtras().getInt("book_id");
                    Log.d("selectbookid",String.valueOf(getBookid));
                    getIntent().putExtra("book_id", getBookid);
                    setResult(RESULT_OK,getIntent());
                    finish();
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaemo_alarm_set_folder);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        baseHelper = BaseHelper.getInstance(this);
        folderList= baseHelper.getAllmemo();

        RecyclerView recyclerView = findViewById(R.id.alarmset_folder);
        GridLayoutManager manager = new GridLayoutManager(this,5);
        recyclerView.setLayoutManager(manager);
        adapter = new PlaemoAlarmSetFolder_Adapter(folderList);
        recyclerView.setAdapter(adapter);
    }
}
