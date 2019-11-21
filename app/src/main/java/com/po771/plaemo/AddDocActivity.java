package com.po771.plaemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.po771.plaemo.item.Item_book;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import org.w3c.dom.Text;

public class AddDocActivity extends AppCompatActivity implements View.OnClickListener {

    private Item_book item_book = new Item_book();
    Uri uri=null;

    TextView tv_bookname;
    TextView tv_page;
    EditText et_bookinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doc);

        setTitle("새 문서");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.adddoc_image).setOnClickListener(this);

        tv_bookname=(TextView)findViewById(R.id.adddoc_title);
        tv_page=(TextView)findViewById(R.id.adddoc_pages);
        et_bookinfo=(EditText)findViewById(R.id.adddoc_info);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.adddoc_image:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                try {
                    startActivityForResult(intent, 3000);
                } catch (ActivityNotFoundException e) {
                    //alert user that file manager not working
                    Toast.makeText(this, "Unable to pick file. Check status of file manager.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Toast.makeText(getApplicationContext(),String.valueOf(resultCode), Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(),String.valueOf(resultCode), Toast.LENGTH_SHORT).show();
        switch (requestCode) {
            case 3000:
                uri = data.getData();
                setItem_book(uri);
                break;

        }
    }

    private String uri2filename(Uri uri) {

        String ret=null;
        String scheme = uri.getScheme();

        if (scheme.equals("file")) {
            ret = uri.getLastPathSegment();
        }
        else if (scheme.equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                ret = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            }
        }
        return ret;
    }

    void setItem_book(Uri pdfUri) {
        String book_name = uri2filename(pdfUri);
        int total_page=0;
        item_book.setBook_uri(pdfUri.toString());
        item_book.setBook_name(book_name);
        tv_bookname.setText(book_name);

        item_book.setCurrent_page(1);

        int pageNumber = 1;
        PdfiumCore pdfiumCore = new PdfiumCore(this);
        Bitmap bmp=null;
        try {
            //http://www.programcreek.com/java-api-examples/index.php?api=android.os.ParcelFileDescriptor
            ParcelFileDescriptor fd = getContentResolver().openFileDescriptor(pdfUri, "r");
            PdfDocument pdfDocument = pdfiumCore.newDocument(fd);
            total_page = pdfiumCore.getPageCount(pdfDocument);
            item_book.setTotal_page(total_page);
            tv_page.setText("총 페이지 : "+total_page + " page");
            pdfiumCore.openPage(pdfDocument, pageNumber);
            int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNumber);
            int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNumber);
            bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            pdfiumCore.renderPageBitmap(pdfDocument, bmp, pageNumber, 0, 0, width, height);
            item_book.setImage_bitmap(bmp);
            pdfiumCore.closeDocument(pdfDocument); // important!
        } catch(Exception e) {
            //todo with exception
        }
    }
}
