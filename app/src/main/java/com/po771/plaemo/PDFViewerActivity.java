package com.po771.plaemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_book;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class PDFViewerActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener, OnPageErrorListener, OnTapListener {

    private static final String TAG = PDFViewerActivity.class.getSimpleName();
    BaseHelper baseHelper;
    Item_book item_book;
    ImageView pdfEditImg;

    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;
    String pdfNowName;

    LinearLayout linearLayout;
    SeekBar sb;
    TextView seekbar_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int bookId = getIntent().getIntExtra("bookId",1);
        String readState = getIntent().getStringExtra("readState");
        if(getIntent().getIntExtra("alarm_id",-1)!=-1) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(22);
            Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(it);


        }

        baseHelper = BaseHelper.getInstance(this);
        item_book = baseHelper.getBook(bookId);
        pdfEditImg = (ImageView)findViewById(R.id.pdf_edit_img);

        linearLayout = (LinearLayout)findViewById(R.id.pdfView_linearlayout);
        linearLayout.setVisibility(View.INVISIBLE);
        seekbar_tv = (TextView)findViewById(R.id.pdfView_seekbartext);
        sb = (SeekBar)findViewById(R.id.pdfView_seekbar);
        sb.setMin(1);
        sb.setMax(item_book.getTotal_page());
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekbar_tv.setText(String.valueOf(i)+"/"+item_book.getTotal_page());
                sb.setProgress(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                pdfView.jumpTo(sb.getProgress()-1);
            }
        });

        pdfFileName=item_book.getBook_name();
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
                    .pageSnap(true)
                    .swipeHorizontal(true) //옆으로 슬라이드
                    .autoSpacing(true)
                    .pageFling(true) // 자동으로 한페이지에 들어오도록
                    .onPageChange(this)
                    .onTap(this)
                    .load();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plemoaddnewmemo_action, menu);
        return true;
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.bookaction_addmemo:
                Intent settingIntent = new Intent(this, PlaemoBookNewMemoActivity.class);
                settingIntent.putExtra("book_id",(item_book.get_id()));
                settingIntent.putExtra("current_page",(pageNumber+1));
                startActivityForResult(settingIntent,400);
                return true;
            case R.id.pdfedit:
                Intent intent = new Intent(this, PDFEditActivity.class);
                intent.putExtra("book_id",(item_book.get_id()));
                intent.putExtra("current_page",(pageNumber));
                intent.putExtra("pdfFileName",pdfNowName);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        pdfNowName = String.format("%s %s / %s", pdfFileName, page + 1, pageCount);
        setTitle(pdfNowName);
        baseHelper.changePage(item_book.get_id(),page+1);
        seekbar_tv.setText(String.valueOf(page+1)+"/"+item_book.getTotal_page());
        sb.setProgress(page+1);
        pdfEditImg.setImageBitmap(loadImageFromInternalStorage(item_book.get_id()+"_"+pageNumber));
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

    @Override
    public boolean onTap(MotionEvent e) {
        if (linearLayout.getVisibility() == View.VISIBLE) {
            // Its visible
            linearLayout.setVisibility(View.INVISIBLE);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
        }
        return false;
    }

    private Bitmap loadImageFromInternalStorage(String fileName)
    {

        try {
            File f=new File(getDataDir().getAbsolutePath()+"/app_pdfImageDir", fileName+".png");
            if(f.exists() == true) {
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                Log.w("책 이미지 불러와짐", "YES");
                return b;
            }else {
                Log.w("책 이미지 불러와짐", "NO");
                return null;
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
