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

import java.util.List;

public class PlemoMainfolder_Adapter extends RecyclerView.Adapter<PlemoMainfolder_Adapter.ViewHolder> {

    private List<String> folderList;
    private Context context;

    public PlemoMainfolder_Adapter(List<String> folderList) {
        this.folderList = folderList;
    }


    @NonNull
    @Override
    public PlemoMainfolder_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.plemofolder_item,parent,false);
        PlemoMainfolder_Adapter.ViewHolder vh = new PlemoMainfolder_Adapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PlemoMainfolder_Adapter.ViewHolder holder, int position) {
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
                Intent intent = new Intent(context,PlemoDocActivity.class);
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
            imageView.setImageResource(R.drawable.blue_download_alt_folder_12391);
            textView = itemView.findViewById(R.id.folder_name);
        }
    }
}
