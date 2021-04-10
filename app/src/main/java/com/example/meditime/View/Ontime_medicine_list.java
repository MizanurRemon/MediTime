package com.example.meditime.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.LinkAddress;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meditime.Adapter.Medicine_adapter;
import com.example.meditime.Adapter.Schedule_adapter;
import com.example.meditime.Model.MedicinePOJO;
import com.example.meditime.Model.SchedulePOJO;
import com.example.meditime.R;
import com.example.meditime.Utils.SQLiteHelper;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Ontime_medicine_list extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ExtendedFloatingActionButton addMedicineButtton;
    TextView scheduleName, scheduleTime, noMedicine;
    String id, schedule_name, schedule_time;
    RecyclerView medicineView;
    Spinner amountSpinner, typeSpinner;
    String amount, type;
    SQLiteHelper dbhelper;
    ArrayList<MedicinePOJO> arrayList;
    MedicinePOJO medicinePOJO;
    Medicine_adapter adapter;
    LinearLayout layout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ontime_medicine_list);

        dbhelper = new SQLiteHelper(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        schedule_name = intent.getStringExtra("schedule_name");
        schedule_time = intent.getStringExtra("schedule_time");

        scheduleName = (TextView) findViewById(R.id.scheduleNameID);
        scheduleTime = (TextView) findViewById(R.id.scheduleTimeID);

        layout2 = (LinearLayout) findViewById(R.id.layout2);

        medicineView = (RecyclerView) findViewById(R.id.recyclerviewID);
        addMedicineButtton = (ExtendedFloatingActionButton) findViewById(R.id.addMedicineID);

        scheduleName.setText(schedule_name);
        scheduleTime.setText(schedule_time);

        medicineView.setHasFixedSize(true);
        medicineView.setLayoutManager(new LinearLayoutManager(this));

        medicineData();

        addMedicineButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog alert = new Dialog(Ontime_medicine_list.this);
                alert.setContentView(R.layout.add_medicine_alert);
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alert.setCancelable(false);
                alert.show();

                TextView cancelButton = (TextView) alert.findViewById(R.id.cancelID);
                TextView addButton = (TextView) alert.findViewById(R.id.saveButtonID);

                TextInputEditText medicineNameText = (TextInputEditText) alert.findViewById(R.id.medicineNameTextID);
                TextInputLayout medicineNameError = (TextInputLayout) alert.findViewById(R.id.medicineNameErrorID);

                amountSpinner = (Spinner) alert.findViewById(R.id.amountSpinnerID);
                typeSpinner = (Spinner) alert.findViewById(R.id.typeSpinnerID);

                String[] amountArray = {"0", "1/2", "1", "2"};
                String[] typeArray = {"none", "spoon", "piece"};

                ArrayAdapter amountAdapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_item, amountArray);
                amountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                amountSpinner.setAdapter(amountAdapter);

                ArrayAdapter typeAdapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_item, typeArray);
                typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                typeSpinner.setAdapter(typeAdapter);

                amountSpinner.setOnItemSelectedListener(Ontime_medicine_list.this);
                typeSpinner.setOnItemSelectedListener(Ontime_medicine_list.this);


                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String medicineName = medicineNameText.getText().toString().trim();

                        medicineNameError.setErrorEnabled(false);
                        if (TextUtils.isEmpty(medicineName) || amount.equals("0") || type.equals("none")) {

                            if (TextUtils.isEmpty(medicineName)) {
                                medicineNameError.setError(" ");
                            } else if (amount.equals("0")) {
                                Toast.makeText(getBaseContext(), "Select amount", Toast.LENGTH_SHORT).show();
                            } else if (type.equals("none")) {
                                Toast.makeText(getBaseContext(), "Select type", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            dbhelper.insertMedicine(id, medicineName, amount, type);
                            alert.dismiss();
                            medicineData();
                        }
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });
            }
        });


    }

    private void medicineData() {

        arrayList = new ArrayList<MedicinePOJO>();
        Cursor cursor = dbhelper.readMedicine(id);

        while (cursor.moveToNext()) {
            medicinePOJO = new MedicinePOJO(cursor.getString(2), cursor.getString(3), cursor.getString(4));
            arrayList.add(0, medicinePOJO);
        }
        adapter = new Medicine_adapter(arrayList);
        medicineView.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.amountSpinnerID) {
            amount = parent.getItemAtPosition(position).toString().trim();
        } else if (parent.getId() == R.id.typeSpinnerID) {
            type = parent.getItemAtPosition(position).toString().trim();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}