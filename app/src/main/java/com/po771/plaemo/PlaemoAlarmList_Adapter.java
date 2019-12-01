package com.po771.plaemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.po771.plaemo.DB.BaseHelper;
import com.po771.plaemo.item.Item_AlarmList;

import java.util.List;

public class PlaemoAlarmList_Adapter extends RecyclerView.Adapter<PlaemoAlarmList_Adapter.ViewHolder> {

    BaseHelper baseHelper;
    Item_AlarmList alarm = new Item_AlarmList();

    private List<Item_AlarmList> alarmList;
//    private Context context;


    public void update(List<Item_AlarmList> alarmList){
        this.alarmList=alarmList;
        this.notifyDataSetChanged();
    }

    public PlaemoAlarmList_Adapter(List<Item_AlarmList> alarmList) {
        this.alarmList = alarmList;
    }

    public PlaemoAlarmList_Adapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PlaemoAlarmList_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.plaemoalarmlist_item,parent, false);

        PlaemoAlarmList_Adapter.ViewHolder vh = new PlaemoAlarmList_Adapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final PlaemoAlarmList_Adapter.ViewHolder holder, final int position) {
//        String alarm_name = ;

        String alarm_time = alarmList.get(position).getHour() + " : " + alarmList.get(position).getMinute();
        if (alarmList.get(position).getMinute() <10) {
            alarm_time = alarmList.get(position).getHour() + " : " + "0" + alarmList.get(position).getMinute();
        }
//        String alarm_day =
        Item_AlarmList item  = alarmList.get(position);

        holder.imageView.setImageResource(R.drawable.pdf_icon);
        holder.alarm_name.setText(alarmList.get(position).getAlarm_name());
        holder.alarm_time.setText(alarm_time);
        holder.alarm_day.setText(alarmList.get(position).getDaysoftheweek());
        if (alarmList.get(position).getIson() == 1){
            holder.ison.setChecked(true);
        }
        else {
            holder.ison.setChecked(false);
        }

    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;
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


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    // TODO : process click event.
//                    int pos = getAdapterPosition();
//                    if (pos != RecyclerView.NO_POSITION) {
//                        if (mListener != null) {
//                            mListener.onItemClick(view, pos);
//                        }
//                    }
//                }
//            });

            // 알람 on/off

        }

        public void onClick(View view) {
            if (view.getId() == R.id.alarmonoff){
                baseHelper.editAlarmOnOff(alarm);
            }
        }
    }

}

