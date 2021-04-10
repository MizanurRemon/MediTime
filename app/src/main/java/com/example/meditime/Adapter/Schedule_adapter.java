package com.example.meditime.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meditime.Interfaces.RecyclerviewClickInterfaces;
import com.example.meditime.Model.SchedulePOJO;
import com.example.meditime.R;

import java.util.ArrayList;

public class Schedule_adapter extends RecyclerView.Adapter<Schedule_adapter.AppViewholder> {

    ArrayList<SchedulePOJO> arrayList;
    RecyclerviewClickInterfaces recyclerviewClickInterfaces;

    public Schedule_adapter(ArrayList<SchedulePOJO> arrayList, RecyclerviewClickInterfaces recyclerviewClickInterfaces) {
        this.arrayList = arrayList;
        this.recyclerviewClickInterfaces = recyclerviewClickInterfaces;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.schedule_adapter_card, parent, false);
        return new Schedule_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        SchedulePOJO getData = arrayList.get(position);
        holder.scheduleName.setText(getData.getSchedule_name());
        holder.scheduleTime.setText(getData.getSchedule_time());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView scheduleName, scheduleTime;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            scheduleName = (TextView) itemView.findViewById(R.id.scheduleNameID);
            scheduleTime = (TextView) itemView.findViewById(R.id.scheduleTimeID);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerviewClickInterfaces.OnItemClick(getAdapterPosition());
                }
            });

        }
    }
}
