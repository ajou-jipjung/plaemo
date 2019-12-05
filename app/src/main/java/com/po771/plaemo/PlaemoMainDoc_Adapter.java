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
    private boolean visibleState;
    private Context context;

    public PlaemoMainDoc_Adapter(List<Item_book> bookList) {
        this.bookList = bookList;
        this.visibleState=false;
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

    public void updatevisible(boolean visibleSate){
        this.visibleState = visibleSate;
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
//        if(doc_name.length()>8){
//            doc_name = doc_name.substring(0,6);
//            doc_name=doc_name+"...";
//        }

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int linearLayoutwidth = displaymetrics.widthPixels/3;
        int linearLayoutheight = (int)(linearLayoutwidth*1.5);
        holder.linearLayout.getLayoutParams().width=linearLayoutwidth;
        holder.relativeLayout.getLayoutParams().height=linearLayoutheight;

        Bitmap bitmap = loadImageFromInternalStorage(item_book.get_id());
        holder.imageView.setImageBitmap(bitmap);
        int bitwidth = holder.imageView.getLayoutParams().width;
        int bitheight = holder.imageView.getLayoutParams().height;
        if(bitwidth>bitheight){
            bitwidth=bitheight;
        }
        holder.textView_title.setText(doc_name);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DocInfoActivity.class);
                intent.putExtra("book_id",(item_book.get_id()));
                intent.putExtra("book_name",(item_book.getBook_name()));
                ((Activity) context).startActivityForResult(intent,200);
                ((Activity) context).overridePendingTransition(0, 0);
            }
        });
        holder.textView_percent.setText(String.valueOf(Math.round(percent))+"%");
//        holder.circularProgressBar.getLayoutParams().width=bitwidth;
//        holder.circularProgressBar.getLayoutParams().height=bitwidth;

//        int circularsize = layoutwidth-100;
//        holder.circularProgressBar.getLayoutParams().width=circularsize;
//        holder.circularProgressBar.getLayoutParams().height=circularsize;
//        holder.circularProgressBar.setProgress(percent);

        if(!visibleState){
            holder.imageView.getDrawable().setAlpha(255);
            holder.textView_percent.setVisibility(View.INVISIBLE);
//            holder.circularProgressBar.setVisibility(View.INVISIBLE);
        }
        else{
            holder.imageView.getDrawable().setAlpha(70);
            holder.textView_percent.setVisibility(View.VISIBLE);
//            holder.circularProgressBar.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_percent;
        TextView textView_title;
//        CircularProgressBar circularProgressBar;
        ImageView imageView;
        RelativeLayout relativeLayout;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.doc_linearlayout);
            relativeLayout = itemView.findViewById(R.id.doc_relativelayout);
            textView_percent=itemView.findViewById(R.id.doc_textView_percent);
            textView_title=itemView.findViewById(R.id.doc_textView_title);
//            circularProgressBar = itemView.findViewById(R.id.doc_circularProgressBar);
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
