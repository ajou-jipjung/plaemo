package com.po771.plaemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Switch;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_book;

import java.util.List;

public class PlaemoAlarmSetDocActivity extends AppCompatActivity {

    String folder_name;
    BaseHelper baseHelper;
    List<Item_book> bookList;
    RecyclerView recyclerView;
    PlaemoAlarmSetDoc_Adapter docListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaemo_alarm_set_doc);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        folder_name = getIntent().getStringExtra("folder_name");
        this.setTitle(folder_name);

        baseHelper = BaseHelper.getInstance(this);
        bookList= baseHelper.getAllbookinfolder(folder_name);

        recyclerView = findViewById(R.id.alarmset_doc);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        docListAdapter = new PlaemoAlarmSetDoc_Adapter(bookList);
        recyclerView.setAdapter(docListAdapter);
    }
}
