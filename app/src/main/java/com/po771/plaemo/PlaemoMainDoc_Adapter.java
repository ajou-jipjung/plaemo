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

public class PlaemoMainDoc_Adapter extends RecyclerView.Adapter<PlaemoMainDoc_Adapter.ViewHolder> {

    private List<Item_book> bookList = null;

    private Context context;

    public PlaemoMainDoc_Adapter(List<Item_book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public PlaemoMainDoc_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);


        View view = inflater.inflate(R.layout.plaemodoc_item,parent,false);
        PlaemoMainDoc_Adapter.ViewHolder vh = new PlaemoMainDoc_Adapter.ViewHolder(view);

        return vh;
    }

    public void update(List<Item_book> bookList){
        this.bookList=bookList;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Item_book item_book = bookList.get(position);
        float percent;
        if(item_book.getCurrent_page()==1){
            percent = (float) ((item_book.getCurrent_page()-1)*100) / item_book.getTotal_page();
        }
        else{
            percent = (float) (item_book.getCurrent_page()*100) / item_book.getTotal_page();
        }
        String doc_name = item_book.getBook_name();
        if(doc_name.length()>8){
            doc_name = doc_name.substring(0,6);
            doc_name=doc_name+"...";
        }

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int layoutwidth = displaymetrics.widthPixels/3;
        int layoutheight = displaymetrics.heightPixels/3;
        holder.linearLayout.getLayoutParams().width=layoutwidth;
        holder.linearLayout.getLayoutParams().height=layoutheight;
        holder.relativeLayout.getLayoutParams().height=layoutheight-30;

        Bitmap bitmap = loadImageFromInternalStorage(item_book.get_id());
        holder.imageView.setImageBitmap(bitmap);
        int bitwidth = holder.imageView.getLayoutParams().width;
        int bitheight = holder.imageView.getLayoutParams().height;
        if(bitwidth>bitheight){
            bitwidth=bitheight;
        }
        holder.imageView.getDrawable().setAlpha(50);
        holder.textView_title.setText(doc_name);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DocInfoActivity.class);
                intent.putExtra("book_id",(item_book.get_id()));
                intent.putExtra("book_name",(item_book.getBook_name()));
                ((Activity) context).startActivityForResult(intent,200);
            }
        });
        holder.textView_percent.setText(String.valueOf(Math.round(percent))+"%");
//        holder.circularProgressBar.getLayoutParams().width=bitwidth;
//        holder.circularProgressBar.getLayoutParams().height=bitwidth;
        holder.circularProgressBar.setProgress(percent);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_percent;
        TextView textView_title;
        CircularProgressBar circularProgressBar;
        ImageView imageView;
        RelativeLayout relativeLayout;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.doc_linearlayout);
            relativeLayout = itemView.findViewById(R.id.doc_relativelayout);
            textView_percent=itemView.findViewById(R.id.doc_textView_percent);
            textView_title=itemView.findViewById(R.id.doc_textView_title);
            circularProgressBar = itemView.findViewById(R.id.doc_circularProgressBar);
            imageView = itemView.findViewById(R.id.doc_image);
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
