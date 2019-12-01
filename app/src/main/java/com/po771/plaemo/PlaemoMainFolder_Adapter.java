package com.po771.plaemo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.po771.plaemo.item.Item_book;

import java.util.List;

public class PlaemoMainFolder_Adapter extends RecyclerView.Adapter<PlaemoMainFolder_Adapter.ViewHolder> {

    private List<String> folderList;
    private Context context;

    public PlaemoMainFolder_Adapter(List<String> folderList) {
        this.folderList = folderList;
    }

    @NonNull
    @Override
    public PlaemoMainFolder_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.plaemofolder_item,parent,false);
        PlaemoMainFolder_Adapter.ViewHolder vh = new PlaemoMainFolder_Adapter.ViewHolder(view);

        return vh;
    }

    public void update( List<String> folderList){
        this.folderList=folderList;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull PlaemoMainFolder_Adapter.ViewHolder holder, int position) {
        String folder_name = folderList.get(position);
        if(folder_name.length()>8){
            folder_name = folder_name.substring(0,5);
            folder_name=folder_name+"...";
        }
        final String text = folder_name;
        holder.textView.setText(folder_name);
        holder.imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlaemoMainDocActivity.class);
                intent.putExtra("folder_name",text);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.folder_image);
            imageView.setImageResource(R.drawable.folderyellow_92963);
            textView = itemView.findViewById(R.id.folder_name);
        }
    }
}
