package com.po771.plaemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.po771.plaemo.item.Item_book;
import com.po771.plaemo.item.Item_memo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class PlameoDocInfoMemo_Adapter extends RecyclerView.Adapter<PlameoDocInfoMemo_Adapter.ViewHolder> {

    private List<Item_memo> items;
    private Context context;

    public PlameoDocInfoMemo_Adapter(List<Item_memo> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        context = viewGroup.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.plaemobookmemolist_item, viewGroup, false);

        Log.d("qweqwe","qweqwe");
        return new ViewHolder(itemView);
    }

    public void update(List<Item_memo> memolistList){
        this.items=memolistList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView page_start;
        private TextView content;
        private TextView date;
        private ImageView imageView;
        private ImageButton edit;
        private int memoid;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.bookimg);
            page_start = itemView.findViewById(R.id.memopage);
            content = itemView.findViewById(R.id.memocontent);
            date = itemView.findViewById(R.id.memodate);
            edit = (ImageButton)itemView.findViewById(R.id.memo_edit);

            content.setOnClickListener(this);
            edit.setOnClickListener(this);
        }

        public void setItem(Item_memo item){
            page_start.setText(item.getPage_start());
            content.setText(item.getContent());
            date.setText(item.getDate());
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            if (v.getId() == R.id.memocontent){
                if(content.getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT){
                    content.getLayoutParams().height = 128;
                }else {
                    content.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                }
                content.requestLayout();
            }
            if (v.getId() == R.id.memo_edit){
                Intent intent = new Intent(v.getContext(), PlaemoEditMemoActivity.class);
                // 값 넘겨주기 코드 구현
                Log.w("넘겨주는 memo_id", String.valueOf(memoid));
                intent.putExtra("memo_id", memoid);
                ((Activity) context).startActivityForResult(intent,400);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull PlameoDocInfoMemo_Adapter.ViewHolder viewHolder, int position) {
        final Item_memo item = items.get(position);
        viewHolder.imageView.setImageBitmap(loadImageFromInternalStorage(item.getBoook_id()));
        //viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
        viewHolder.imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,PDFViewerActivity.class);
                intent.putExtra("bookId",item.getBoook_id());
                intent.putExtra("readState","memo");
                intent.putExtra("bookstart",item.getPage_start());
                ((Activity) context).startActivityForResult(intent,200);
                ((Activity) context).overridePendingTransition(0, 0);
            }
        });


//        viewHolder.imageView.setImageResource(R.drawable.book1);
        viewHolder.page_start.setText("p."+String.valueOf(item.getPage_start())+" ~ p."+String.valueOf(item.getPage_end()));
        viewHolder.content.setText(item.getContent());
        viewHolder.date.setText(item.getDate());
        viewHolder.memoid = item.get_id();
        Log.w("memo content", item.getDate());
        Log.w("memo_id 첫 설정완료", String.valueOf(item.get_id()));
        //viewHolder.setItem(item);

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
