package com.example.meditime.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.meditime.Adapter.Schedule_adapter;
import com.example.meditime.Interfaces.RecyclerviewClickInterfaces;
import com.example.meditime.Model.SchedulePOJO;
import com.example.meditime.R;
import com.example.meditime.Utils.SQLiteHelper;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RecyclerviewClickInterfaces {

    SQLiteHelper dbhelper;
    RecyclerView recyclerView;
    ExtendedFloatingActionButton addTimeButton;
    TextInputEditText scheduleName;
    TextInputLayout scheduleError;
    TextView showTime;
    private LinearLayoutManager layoutManager;
    ArrayList<SchedulePOJO> arrayList;
    Schedule_adapter adapter;
    Cursor cursor;
    SchedulePOJO model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            this.getSupportActionBar().hide();
        } catch (Exception e) {
        }

        dbhelper = new SQLiteHelper(this);
        layoutManager = new LinearLayoutManager(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycleviewID);
        addTimeButton = (ExtendedFloatingActionButton) findViewById(R.id.addTimeID);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && addTimeButton.getVisibility() == View.VISIBLE) {
                    addTimeButton.hide();
                } else if (dy < 0 && addTimeButton.getVisibility() != View.VISIBLE) {
                    addTimeButton.show();
                }
            }
        });


        data();
        addTimeButton.setOnClickListener(this);
    }

    private void data() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<SchedulePOJO>();
        cursor = dbhelper.readData();

        while (cursor.moveToNext()) {
            model = new SchedulePOJO(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            arrayList.add(0, model);
        }
        adapter = new Schedule_adapter(arrayList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addTimeID) {
            Dialog alert = new Dialog(this);
            alert.setContentView(R.layout.add_alert);
            alert.show();
            alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alert.setCancelable(false);

            TextView cancelButton = (TextView) alert.findViewById(R.id.cancelID);
            TextView saveButton = (TextView) alert.findViewById(R.id.saveButtonID);
            ImageView selectTime = (ImageView) alert.findViewById(R.id.selectTimeID);
            showTime = (TextView) alert.findViewById(R.id.showTimeID);
            scheduleName = (TextInputEditText) alert.findViewById(R.id.scheduleTextID);
            scheduleError = (TextInputLayout) alert.findViewById(R.id.scheduleErrorID);

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert.dismiss();
                }
            });

            selectTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog timeAlert = new Dialog(MainActivity.this);
                    timeAlert.setContentView(R.layout.time_picker_alert);
                    timeAlert.show();
                    timeAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    //timeAlert.setCancelable(false);
                    TimePicker timePicker = (TimePicker) timeAlert.findViewById(R.id.TimePickerID);
                    //timePicker.setIs24HourView(true);
                    TextView doneButton = (TextView) timeAlert.findViewById(R.id.doneButtonID);
                    doneButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int hour, minute;
                            String am_pm;
                            hour = timePicker.getCurrentHour();
                            minute = timePicker.getCurrentMinute();

                            if (hour > 12) {
                                am_pm = "PM";
                                hour = hour - 12;
                            } else {
                                am_pm = "AM";
                            }

                            String HOUR = String.valueOf(hour), MINUTE = String.valueOf(minute);

                            if (HOUR == "00") {
                                HOUR = "12";
                            }

                            if (HOUR.length() < 2) {
                                HOUR = "0" + HOUR;
                            }
                            if (MINUTE.length() < 2) {
                                MINUTE = "0" + MINUTE;
                            }


                            String time = HOUR + ":" + MINUTE + am_pm;
                            timeAlert.dismiss();
                            showTime.setText(time);

                        }
                    });

                }
            });

            saveButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String schedule_name = scheduleName.getText().toString().trim();
                    String scheduleTime = showTime.getText().toString().trim();

                    scheduleError.setErrorEnabled(false);
                    if (TextUtils.isEmpty(schedule_name) || scheduleTime.equals("--:--")) {
                        //scheduleError.setErrorEnabled(false);
                        if (TextUtils.isEmpty(schedule_name)) {
                            scheduleError.setError(" ");
                        } else if (scheduleTime.equals("--:--")) {
                            Toast toast = Toast.makeText(MainActivity.this, "Select Time", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    } else {
                        dbhelper.insertData(schedule_name, scheduleTime);
                        alert.dismiss();
                        data();
                    }

                }
            });
        }
    }

    @Override
    public void OnItemClick(int position) {
        model = arrayList.get(position);
        String id = model.getId();
        String name = model.getSchedule_name();
        String time = model.getSchedule_time();

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);


        Intent intent = new Intent(this, Ontime_medicine_list.class);
        intent.putExtra("ID", id);
        intent.putExtra("schedule_name", name);
        intent.putExtra("schedule_time", time);
        startActivity(intent, options.toBundle());

    }
}