package com.po771.plaemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_book;

import java.util.List;

public class PlaemoMainDocActivity extends AppCompatActivity {

    String folder_name;
    BaseHelper baseHelper;
    List<Item_book> bookList;
    RecyclerView recyclerView;
    PlaemoMainDoc_Adapter docListAdapter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plaemomaindoc_action, menu);
        return true;
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.folderaction_memolist:
                Intent settingIntent = new Intent(this, PlaemoMemoListActivity.class);
                startActivity(settingIntent);
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaemo_doc);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        folder_name = getIntent().getStringExtra("folder_name");
        this.setTitle(folder_name);

        baseHelper = BaseHelper.getInstance(this);
        bookList= baseHelper.getAllbookinfolder(folder_name);

        recyclerView = findViewById(R.id.plemodoc_recylcerview);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        docListAdapter = new PlaemoMainDoc_Adapter(bookList);
        recyclerView.setAdapter(docListAdapter);

//        GridLayoutManager manager = new GridLayoutManager(this,5);
//        recyclerView.setLayoutManager(manager);
//        PlaemoMainFolder_Adapter adapter = new PlaemoMainFolder_Adapter(folderList);
//        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==200){
//            Toast.makeText(this,"check!",Toast.LENGTH_SHORT).show();
            bookList= baseHelper.getAllbookinfolder(folder_name);
            docListAdapter.update(bookList);
        }
    }
}
