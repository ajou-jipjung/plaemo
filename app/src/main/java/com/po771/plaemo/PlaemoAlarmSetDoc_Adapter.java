package com.po771.plaemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.po771.plaemo.item.Item_book;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class PlaemoAlarmSetDoc_Adapter extends RecyclerView.Adapter<PlaemoAlarmSetDoc_Adapter.ViewHolder>{

    private List<Item_book> bookList = null;
    private boolean visibleState;
    private Context context;

    public PlaemoAlarmSetDoc_Adapter(List<Item_book> bookList) {
        this.bookList = bookList;
        this.visibleState=false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);


        View view = inflater.inflate(R.layout.plaemodoc_item_none,parent,false);
        PlaemoAlarmSetDoc_Adapter.ViewHolder vh = new PlaemoAlarmSetDoc_Adapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Item_book item_book = bookList.get(position);

        String doc_name = item_book.getBook_name();
        if(doc_name.length()>8){
            doc_name = doc_name.substring(0,6);
            doc_name=doc_name+"...";
        }
        holder.textView_title.setText(doc_name);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int layoutwidth = displaymetrics.widthPixels/3;
        int layoutheight = displaymetrics.heightPixels/3;
        holder.linearLayout.getLayoutParams().width=layoutwidth;
        holder.linearLayout.getLayoutParams().height=layoutheight-40;
        holder.relativeLayout.getLayoutParams().height=layoutheight-30;

        Bitmap bitmap = loadImageFromInternalStorage(item_book.get_id());
        holder.imageView.setImageBitmap(bitmap);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ((Activity) context).getIntent().putExtra("book_id", item_book.get_id());
                ((Activity) context).setResult(RESULT_OK,((Activity) context).getIntent());
                ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_title;
        ImageView imageView;
        RelativeLayout relativeLayout;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.alarmset_doc_linearlayout);
            relativeLayout = itemView.findViewById(R.id.alarmset_doc_relativelayout);
            textView_title=itemView.findViewById(R.id.alarmset_doc_textView_title);
            imageView = itemView.findViewById(R.id.alarmset_doc_image);
        }
    }

    private Bitmap loadImageFromInternalStorage(int fileName)
    {

        try {
            File f=new File(context.getDataDir().getAbsolutePath()+"/app_imageDir", fileName+".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
