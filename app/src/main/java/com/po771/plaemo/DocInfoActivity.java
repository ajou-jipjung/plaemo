package com.po771.plaemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_book;

public class DocInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_info);

        String book_name = getIntent().getStringExtra("book_name");
        int book_id = getIntent().getIntExtra("book_id",1);

        setTitle(book_name+ " - 정보");

        BaseHelper baseHelper = BaseHelper.getInstance(this);
        Item_book item_book = baseHelper.getBook(book_id);

    }
}
