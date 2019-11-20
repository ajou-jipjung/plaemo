package com.po771.plaemo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_book;

import java.util.List;

public class PlaemoMainDocActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaemo_doc);

        String folder_name = getIntent().getStringExtra("folder_name");
        this.setTitle(folder_name);

        BaseHelper baseHelper = BaseHelper.getInstance(this);
        List<Item_book> bookList= baseHelper.getAllbookinfolder(folder_name);

        RecyclerView recyclerView = findViewById(R.id.plemodoc_recylcerview);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        PlaemoMainDoc_Adapter docListAdapter = new PlaemoMainDoc_Adapter(bookList);
        recyclerView.setAdapter(docListAdapter);

//        GridLayoutManager manager = new GridLayoutManager(this,5);
//        recyclerView.setLayoutManager(manager);
//        PlaemoMainFolder_Adapter adapter = new PlaemoMainFolder_Adapter(folderList);
//        recyclerView.setAdapter(adapter);

    }
}
