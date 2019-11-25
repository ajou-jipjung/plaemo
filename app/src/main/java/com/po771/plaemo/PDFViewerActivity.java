package com.po771.plaemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_book;
import com.shockwave.pdfium.PdfDocument;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class PDFViewerActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener, OnPageErrorListener {

    private static final String TAG = PDFViewerActivity.class.getSimpleName();
    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;
    Item_book item_book;
    BaseHelper baseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);
        int bookId = getIntent().getIntExtra("bookId",1);
        String readState = getIntent().getStringExtra("readState");

        baseHelper = BaseHelper.getInstance(this);
        item_book = baseHelper.getBook(bookId);
        if(readState.equals("resume")){
            pageNumber=item_book.getCurrent_page();
        }
        else if(readState.equals("first")){
            baseHelper.changePage(item_book.get_id(),1);
            pageNumber=1;
        }
        pdfView = findViewById(R.id.pdfView);
        try {
            FileInputStream fis = new FileInputStream(item_book.getBook_uri());
            pdfView.fromStream(fis)
                    .defaultPage(pageNumber-1)
                    .onPageChange(this)
                    .enableAnnotationRendering(true)
                    .onLoad(this)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .spacing(10) // in dp
                    .onPageError(this)
                    .load();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
        baseHelper.changePage(item_book.get_id(),page+1);
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        Log.e(TAG, "title = " + meta.getTitle());
        Log.e(TAG, "author = " + meta.getAuthor());
        Log.e(TAG, "subject = " + meta.getSubject());
        Log.e(TAG, "keywords = " + meta.getKeywords());
        Log.e(TAG, "creator = " + meta.getCreator());
        Log.e(TAG, "producer = " + meta.getProducer());
        Log.e(TAG, "creationDate = " + meta.getCreationDate());
        Log.e(TAG, "modDate = " + meta.getModDate());

        printBookmarksTree(pdfView.getTableOfContents(), "-");
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    @Override
    public void onPageError(int page, Throwable t) {
        Log.e(TAG, "Cannot load page " + page);
    }
}
