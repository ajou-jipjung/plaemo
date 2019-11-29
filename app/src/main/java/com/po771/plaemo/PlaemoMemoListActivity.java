package com.po771.plaemo;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_memo;

import java.util.List;

public class PlaemoMemoListActivity extends AppCompatActivity {
    String folder_name;
    String search_text;
    int now_spin = 0;
    boolean search_now = false;
    BaseHelper baseHelper = BaseHelper.getInstance(this);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plaemomemolist_action, menu);
        SearchView searchView = (SearchView)menu.findItem(R.id.memo_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("검색할 내용을 입력해주세요.");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) { //검색 완료시
                search_text = s;
                search_now = true;
                MeMoFind(search_text, now_spin);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) { //검색어 입력시
                MemoListSort(now_spin);
                return false;
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaemo_memo_list);

        //액션바 설정하기//
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle("메모리스트");
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        folder_name = getIntent().getStringExtra("folder_name");
        Log.w("폴더이름", folder_name);

        Spinner memo_spinner = (Spinner)findViewById(R.id.book_memo_spinner);
        memo_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                now_spin = position;
                //0. 정렬(내림차순) 1. 등록순(오름차순) 2. 최종수정순(내림차순) 3. 시작페이지순(오름차순) 4. 종료페이지순(내림차순)
                if(search_now == false){
                    MemoListSort(now_spin);
                }else{
                    MeMoFind(search_text, now_spin);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MemoListSort(0);
            }
        });
    }

    protected void MemoListSort(int spinner_num){
        List<Item_memo> memolistList= baseHelper.getMemos(spinner_num, folder_name);

        RecyclerView recyclerView = findViewById(R.id.plemomemolist_recylcerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        PlaemoMemoList_Adapter adapter = new PlaemoMemoList_Adapter(memolistList);
        recyclerView.setAdapter(adapter);
    }

    protected void MeMoFind(String keyword, int spinner_num){
        List<Item_memo> memolistList= baseHelper.getMemosFind(keyword, folder_name, spinner_num);

        RecyclerView recyclerView = findViewById(R.id.plemomemolist_recylcerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        PlaemoMemoList_Adapter adapter = new PlaemoMemoList_Adapter(memolistList);
        recyclerView.setAdapter(adapter);
    }

}