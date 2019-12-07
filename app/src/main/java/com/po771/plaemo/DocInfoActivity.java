package com.po771.plaemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_book;
import com.po771.plaemo.item.Item_memo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class DocInfoActivity extends AppCompatActivity implements View.OnClickListener {

    String search_text; //검색어
    int now_spin = 0; //현재 위치
    boolean search_now = false; //검색 여부

    ProgressBar doc_progress;
    TextView doc_percent;

    ImageView imageView;
    Item_book item_book;
    Button btn_star;
    BaseHelper baseHelper;
    TextView info_bookpage;

    TextView info_bookname;
    TextView info_bookinfo;
    ChipGroup chipGroup;
    int book_id;
    PlameoDocInfoMemo_Adapter adapter;
    List<Item_memo> memolistList;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plaemodocinfo_action, menu);
        SearchView searchView = (SearchView)menu.findItem(R.id.memo_search_book).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("검색할 내용을 입력해주세요.");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {//검색 완료시
                search_text = s;
                Log.d("plaemo onQueryTextSubmit","search text "+search_text);
                search_now = true;
                MeMoFind(search_text, now_spin);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) { //검색어 입력시
                search_text=s;
                Log.d("plaemo onQueryTextChange","search text "+search_text);

                if(search_text==null || search_text.equals("")){
                    MemoListSort(now_spin);
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_OK);
                finish();
                return true;
            case R.id.bookaction_addmemo:
                Intent settingIntent = new Intent(this, PlaemoBookNewMemoActivity.class);
                settingIntent.putExtra("book_id",(item_book.get_id()));

                startActivityForResult(settingIntent,400);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        book_id = getIntent().getIntExtra("book_id",1);



        baseHelper = BaseHelper.getInstance(this);
        item_book = baseHelper.getBook(book_id);
        setTitle(item_book.getBook_name()+ " - 정보");
        info_bookname = (TextView)findViewById(R.id.info_bookname);
        info_bookpage = (TextView)findViewById(R.id.info_bookpage);
        info_bookinfo = (TextView)findViewById(R.id.info_bookinfo);
        imageView = (ImageView)findViewById(R.id.info_bookimage);
        imageView.setOnClickListener(this);
        doc_percent = findViewById(R.id.doc_percent);
        doc_progress = findViewById(R.id.doc_progress);

        btn_star = (Button)findViewById(R.id.info_star);
        btn_star.setOnClickListener(this);
        if(item_book.getBook_star()==1){
            btn_star.setBackground(getDrawable(R.drawable.ic_blackstar_24px));
        }
        findViewById(R.id.info_setting).setOnClickListener(this);
        findViewById(R.id.info_readfirst).setOnClickListener(this);
        findViewById(R.id.info_readresume).setOnClickListener(this);

        info_bookname.setText(item_book.getBook_name());
        String pageState="page : "+item_book.getCurrent_page() + " / "+item_book.getTotal_page();
        info_bookpage.setText(pageState);

        String info = item_book.getBook_info();
        info_bookinfo.setText("정보"+"\n"+info);
        if(info.equals("")){
            info_bookinfo.setVisibility(View.GONE);
        }
        else{
            info_bookinfo.setVisibility(View.VISIBLE);
        }

        imageView.setImageBitmap(loadImageFromInternalStorage(item_book.get_id()));

        float percent;
        if(item_book.getCurrent_page()==1){
            percent = (float) ((item_book.getCurrent_page()-1)*100) / item_book.getTotal_page();
        }
        else{
            percent = (float) (item_book.getCurrent_page()*100) / item_book.getTotal_page();
        }
        int p = (int)percent;
        Log.w("퍼센트", String.valueOf(p));
        String per = String.valueOf(p);
        per = per + "%";
        doc_percent.setText(per);
        doc_progress.setProgress(p);


        RecyclerView recyclerView = findViewById(R.id.info_bookmemolist_recylcerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        memolistList= baseHelper.getBookMemo(book_id, 0);
        adapter = new PlameoDocInfoMemo_Adapter(memolistList);
        recyclerView.setAdapter(adapter);

        Spinner memo_spinner = (Spinner)findViewById(R.id.book_memo_spinner);
        memo_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //0. 정렬(내림차순) 1. 등록순(오름차순) 2. 최종수정순(내림차순) 3. 시작페이지순(오름차순) 4. 종료페이지순(내림차순)
                Log.d("plaemo onItemSelected","serch text "+search_text);
                if(search_text==null){
                    Log.d("plaemo onItemSelected","search_now to false");
                    search_now=false;
                }
                now_spin = position;
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

        chipGroup = findViewById(R.id.info_folderchips);
        String[] folders = item_book.getFolder().split("/");
        for(int i=0;i<folders.length;i++){
            if(folders[i].equals("")){
                break;
            }
            Chip chip = new Chip(this);
            chip.setText(folders[i]);
            chip.setTextAppearanceResource(R.style.ChipTextStyle);
            chip.setChipBackgroundColorResource(R.color.chipbackground);
            chipGroup.addView(chip);
        }
    }

    protected void MemoListSort(int now_spin){
        memolistList= baseHelper.getBookMemo(book_id, now_spin);
        if(memolistList.size()>0){
            findViewById(R.id.info_bookmemolist_state).setVisibility(View.INVISIBLE);
        }
        else{
            findViewById(R.id.info_bookmemolist_state).setVisibility(View.VISIBLE);
        }
        Log.d("plaemo MemoListSort","count"+memolistList.size());
        adapter.update(memolistList);
    }

    protected void MeMoFind(String keyword, int now_spin){
        memolistList= baseHelper.getBookMemoFind(keyword, book_id, now_spin);
        Log.d("plaemo MeMoFind","count"+memolistList.size());
        adapter.update(memolistList);
    }


    private Bitmap loadImageFromInternalStorage(int fileName)
    {

        try {
            File f=new File(getDataDir().getAbsolutePath()+"/app_imageDir", fileName+".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.info_setting:
                Intent intent = new Intent(this,DocInfoSettingActivity.class);
                intent.putExtra("book_id",(item_book.get_id()));
                intent.putExtra("book_name",(item_book.getBook_name()));
                startActivityForResult(intent,600);
                break;
            case R.id.info_star:
                if(item_book.getBook_star()==1){
                    btn_star.setBackground(getDrawable(R.drawable.ic_star_border_24px));
                    item_book.setBook_star(-1);
                    baseHelper.changeStar(item_book.get_id(),-1);
                }
                else{
                    btn_star.setBackground(getDrawable(R.drawable.ic_blackstar_24px));
                    item_book.setBook_star(1);
                    baseHelper.changeStar(item_book.get_id(),1);
                }
                break;
            case R.id.info_bookimage:
            case R.id.info_readresume:
                Intent pdfintent = new Intent(this, PDFViewerActivity.class);
//                pdfintent.setAction(Intent.)
                pdfintent.putExtra("bookId",item_book.get_id());
                pdfintent.putExtra("readState","resume");
                startActivityForResult(pdfintent,200);
                break;
            case R.id.info_readfirst:
                Intent pdfintent2 = new Intent(this, PDFViewerActivity.class);
//                pdfintent.setAction(Intent.)
                pdfintent2.putExtra("bookId",item_book.get_id());
                pdfintent2.putExtra("readState","first");
                startActivityForResult(pdfintent2,200);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 200: //책 상태 변경 + 아래 메모 변경
                item_book = baseHelper.getBook(book_id);

                String pageState="page : "+item_book.getCurrent_page() + " / "+item_book.getTotal_page();
                info_bookpage.setText(pageState);

                float percent;
                if(item_book.getCurrent_page()==1){
                    percent = (float) ((item_book.getCurrent_page()-1)*100) / item_book.getTotal_page();
                }
                else{
                    percent = (float) (item_book.getCurrent_page()*100) / item_book.getTotal_page();
                }
                int p = (int)percent;
                Log.w("퍼센트", String.valueOf(p));
                String per = String.valueOf(p);
                per = per + "%";
                doc_percent.setText(per);
                doc_progress.setProgress(p);

            case 400://메모 변경
                memolistList= baseHelper.getBookMemo(book_id, now_spin);
                if(memolistList.size()>0){
                    findViewById(R.id.info_bookmemolist_state).setVisibility(View.INVISIBLE);
                }
                else{
                    findViewById(R.id.info_bookmemolist_state).setVisibility(View.VISIBLE);
                }
                adapter.update(memolistList);
                break;
            case 600:
                item_book = baseHelper.getBook(book_id);
                info_bookname.setText(item_book.getBook_name());
                String info = item_book.getBook_info();
                info_bookinfo.setText("정보"+"\n"+info);
                if(info.equals("")){
                    info_bookinfo.setVisibility(View.GONE);
                }
                else{
                    info_bookinfo.setVisibility(View.VISIBLE);
                }
                chipGroup.removeAllViews();
                String[] folders = item_book.getFolder().split("/");
                for(int i=0;i<folders.length;i++){
                    if(folders[i].equals("")){
                        break;
                    }
                    Chip chip = new Chip(this);
                    chip.setText(folders[i]);
                    chip.setTextAppearanceResource(R.style.ChipTextStyle);
                    chip.setChipBackgroundColorResource(R.color.chipbackground);
                    chipGroup.addView(chip);
                }
                break;
        }

    }
}
