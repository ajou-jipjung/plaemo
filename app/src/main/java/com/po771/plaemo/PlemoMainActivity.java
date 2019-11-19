package com.po771.plaemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

import com.po771.plaemo.DB.BaseHelper;

public class PlemoMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plemo_main);

        BaseHelper baseHelper = BaseHelper.getInstance(this);
        List<String> folderList= baseHelper.getAllmemo();

        RecyclerView recyclerView = findViewById(R.id.plemofolder_recylcerview);
//        int width =
        GridLayoutManager manager = new GridLayoutManager(this,5);
        recyclerView.setLayoutManager(manager);
        PlemoMainfolder_Adapter adapter = new PlemoMainfolder_Adapter(folderList);
        recyclerView.setAdapter(adapter);
    }
}
