package com.po771.plaemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_book;

public class DocInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String book_name = getIntent().getStringExtra("book_name");
        int book_id = getIntent().getIntExtra("book_id",1);

        setTitle(book_name+ " - 정보");

        BaseHelper baseHelper = BaseHelper.getInstance(this);
        Item_book item_book = baseHelper.getBook(book_id);

        TextView info_bookname = (TextView)findViewById(R.id.info_bookname);
        TextView info_bookpage = (TextView)findViewById(R.id.info_bookpage);
        TextView info_bookinfo = (TextView)findViewById(R.id.info_bookinfo);
        ImageView imageView = (ImageView)findViewById(R.id.info_bookimage);

        info_bookname.setText(item_book.getBook_name());
        String pageState=""+item_book.getCurrent_page() + " / "+item_book.getTotal_page();
        info_bookpage.setText(pageState);
        info_bookinfo.setText(item_book.getBook_info());
        imageView.setImageBitmap(item_book.getImage_bitmap());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plemomaindoc_action, menu);
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
}
