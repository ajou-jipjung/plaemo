package com.po771.plaemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_book;

import org.w3c.dom.Text;

import java.util.List;

public class PlaemoMainDocActivity extends AppCompatActivity {

    String folder_name;
    BaseHelper baseHelper;
    List<Item_book> bookList;
    RecyclerView recyclerView;
    PlaemoMainDoc_Adapter docListAdapter;
    Switch percent_switch;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plaemomaindoc_action, menu);
        MenuItem item = menu.findItem(R.id.folderaction_switch);
        percent_switch = (Switch)item.getActionView().findViewById(R.id.cSwitch);
        final TextView tv_switch = (TextView)item.getActionView().findViewById(R.id.cSwitch_textView);
        percent_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tv_switch.setGravity(Gravity.LEFT);
                    docListAdapter.updatevisible(true);
                }else{
                    tv_switch.setGravity(Gravity.RIGHT);
                    docListAdapter.updatevisible(false);
                }
            }
        });

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
                Intent settingIntent = new Intent(this, PlemoMemoListActivity.class);
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
