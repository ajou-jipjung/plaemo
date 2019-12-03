package com.po771.plaemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
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

    int spinner_num = 0;

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
                Intent settingIntent = new Intent(this, PlaemoMemoListActivity.class);
                settingIntent.putExtra("folder_name",folder_name);
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

        Spinner book_sort_spinner = (Spinner)findViewById(R.id.book_sort_spinner);
        book_sort_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //0. 정렬(내림차순) 1. 등록순(오름차순) 2. 이름(오름차순) 3. 이름(내림차순)
                spinner_num = position;
                BookSort(folder_name, spinner_num);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                BookSort(folder_name, spinner_num);
            }
        });
    }

    protected void BookSort(String folder_name, int spinner_num){
        bookList= baseHelper.getAllbookinfolder(folder_name, spinner_num);
        recyclerView = findViewById(R.id.plemodoc_recylcerview);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        docListAdapter = new PlaemoMainDoc_Adapter(bookList);
        recyclerView.setAdapter(docListAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==200){
//            Toast.makeText(this,"check!",Toast.LENGTH_SHORT).show();
            bookList= baseHelper.getAllbookinfolder(folder_name, spinner_num);
            docListAdapter.update(bookList);
        }
    }
}
