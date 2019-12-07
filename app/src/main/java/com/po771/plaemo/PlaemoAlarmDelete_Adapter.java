package com.po771.plaemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_alarm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;


public class PlaemoAlarmDelete_Adapter extends RecyclerView.Adapter<PlaemoAlarmDelete_Adapter.ViewHolder>{

    static int[] select_id_list = new int[100];
    Context context;
    BaseHelper baseHelper;
    AlarmLoader alarmLoader;
    private List<Item_alarm> alarmList;
//    private Context context;



    public void update(List<Item_alarm> alarmList){
        this.alarmList=alarmList;
        this.notifyDataSetChanged();
    }

    public PlaemoAlarmDelete_Adapter(List<Item_alarm> alarmList, BaseHelper baseHelper) {
        this.alarmList = alarmList;
        this.baseHelper =baseHelper;
    }

    @NonNull
    @Override
    public PlaemoAlarmDelete_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
//        switch (viewType) {
//            case 0:
                View view = inflater.inflate(R.layout.plaemoalarmdeletelist_item, parent, false);
                PlaemoAlarmDelete_Adapter.ViewHolder vh = new PlaemoAlarmDelete_Adapter.ViewHolder(view);
                return vh;
//            case 2:
//                View view1 = inflater.inflate(R.layout.activity_plaemo_alarm_delete, parent, false);
//                PlaemoAlarmDelete_Adapter.ViewHolder vh1 = new PlaemoAlarmDelete_Adapter.ViewHolder(view1);
//                return vh1;
//        }
//        return null;
    }

//    @NonNull
//    @Override
//    public PlaemoAlarmDelete_Adapter.ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        context=parent.getContext();
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.activity_plaemo_alarm_delete,parent, false);
//
//        PlaemoAlarmDelete_Adapter.ViewHolder vh = new PlaemoAlarmDelete_Adapter.ViewHolder(view);
//
//        return vh;
//    }

    @Override
    public void onBindViewHolder(@NonNull final PlaemoAlarmDelete_Adapter.ViewHolder holder, final int position) {

        Item_alarm item_alarm  = alarmList.get(position);
        String alarm_time;
        if(item_alarm.getHour()>12){
            alarm_time="오후";
            alarm_time += " "+(item_alarm.getHour()-12) + " :" ;
        }
        else{
            alarm_time="오전";
            alarm_time += " "+item_alarm.getHour() + " :" ;
        }

        if (item_alarm.getMinute() >=10) {
            alarm_time += " " + item_alarm.getMinute();
        }
        else{
            alarm_time += " 0" + item_alarm.getMinute();
        }



        Bitmap bitmap = loadImageFromInternalStorage(item_alarm.getBook_id());
        holder.imageView.setImageBitmap(bitmap);
        holder.alarm_name.setText(item_alarm.getAlarm_name());
        holder.alarm_time.setText(alarm_time);
        String days = item_alarm.getDaysoftheweek();
        if(days.length()>1){
            days=days.substring(0,days.length()-1);
        }
        holder.alarm_day.setText(days);
        if (alarmList.get(position).getIson() == 1){
            holder.ison.setChecked(true);
        }
        else {
            holder.ison.setChecked(false);
        }
        holder.ison.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(context,"check",Toast.LENGTH_SHORT);
                Log.d("switchcheck","switchcheck");
                Log.d("switchcheck","state "+alarmList.get(position).getIson());
                if(b){
                    alarmList.get(position).setIson(1);
                }
                else{
                    alarmList.get(position).setIson(-1);
                }
                Log.d("switchcheck","state "+alarmList.get(position).getIson());
                baseHelper.editAlarmOnOff(alarmList.get(position));
            }
        });

        holder.select_alarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            int a=0;
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    a = alarmList.get(position).get_id();
                    select_id_list[a-1] = a;
                    Log.d("select_id_list","id "+select_id_list[a-1]);
                }
                else{
                    a = alarmList.get(position).get_id();
                    select_id_list[a-1] = 0;
                    Log.d("select_id_list", "id " + select_id_list[a-1]);
                }
            }
        });

    }



    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    private PlaemoAlarmDelete_Adapter.OnItemClickListener onItemClickListener;
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.mListener = listener;
//    }







    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {

        TextView alarm_name;
        TextView alarm_time;
        TextView alarm_day;
        ImageView imageView;
        Switch ison;
        CheckBox select_alarm;


        public Switch getAlarmOn() {
            return ison;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pdfimg);
            alarm_name = itemView.findViewById(R.id.alarmname);
            alarm_time = itemView.findViewById(R.id.alarmtime);
            alarm_day = itemView.findViewById(R.id.alarmday);
            ison = itemView.findViewById(R.id.alarmonoff);
            select_alarm = itemView.findViewById(R.id.select_delete_alarm);

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
