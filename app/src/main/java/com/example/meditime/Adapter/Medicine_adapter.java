package com.example.meditime.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meditime.Model.MedicinePOJO;
import com.example.meditime.Model.SchedulePOJO;
import com.example.meditime.R;

import java.util.ArrayList;

public class Medicine_adapter extends RecyclerView.Adapter<Medicine_adapter.AppViewholder> {
    ArrayList<MedicinePOJO> arrayList;

    public Medicine_adapter(ArrayList<MedicinePOJO> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Medicine_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.medicine_list_card, parent, false);
        return new Medicine_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Medicine_adapter.AppViewholder holder, int position) {
        MedicinePOJO medicinePOJO = arrayList.get(position);
        holder.medicineNameText.setText(medicinePOJO.getMedcineName());
        holder.amountText.setText(medicinePOJO.getAmount());
        holder.typeText.setText(medicinePOJO.getType());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView medicineNameText, amountText, typeText;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            medicineNameText = itemView.findViewById(R.id.medicineNameID);
            amountText = itemView.findViewById(R.id.medicineAmountID);
            typeText = itemView.findViewById(R.id.medicineTypeID);
        }
    }
}
