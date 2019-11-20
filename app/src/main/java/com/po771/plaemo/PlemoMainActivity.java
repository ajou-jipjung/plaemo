package com.po771.plaemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

import com.po771.plaemo.DB.BaseHelper;

public class PlemoMainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plemomain_action, menu);
        return true;
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_memo) {
            Intent settingIntent = new Intent(this, PlemoMemoListActivity.class);
            startActivity(settingIntent);
        }

        return super.onOptionsItemSelected(item);
    }
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
