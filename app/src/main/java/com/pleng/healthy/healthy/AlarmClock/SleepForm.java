package com.pleng.healthy.healthy.AlarmClock;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.pleng.healthy.healthy.DBHelper;
import com.pleng.healthy.healthy.R;
import com.pleng.healthy.healthy.Weight.WeightFragment;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SleepForm extends Fragment{
    private TextView sleepTime, wakeUpTime , dateField;
    private Calendar mCurrentDate;
    private int mYear, mMonth, mDay;
    private String dayStr, monthStr;
    private Button addTimeButton,backToListViewButton;
    private String differenceTime, time1, time2, dateStr;
    private ParsePosition pp1,pp2,pp3,pp4;
    private SleepStore sleepStore;
    private DBHelper myDB;
    private boolean passingValue;
    private Bundle bundle;
    public SleepForm(){
        mCurrentDate = Calendar.getInstance();
        mYear = mCurrentDate.get(Calendar.YEAR);
        mMonth = mCurrentDate.get(Calendar.MONTH);
        mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myDB = new DBHelper(getContext());
        dateField = (TextView) getView().findViewById(R.id.fragment_sleep_form_datetime);
        wakeUpTime = (TextView) getView().findViewById(R.id.fragment_sleep_form_wakeUpTime);
        sleepTime = (TextView) getView().findViewById(R.id.fragment_sleep_form_sleepTime);
        addTimeButton = (Button) getView().findViewById(R.id.fragment_sleep_form_addTimeButton);
        backToListViewButton = (Button) getView().findViewById(R.id.fragment_sleep_form_backToSleepListView);

        if(getArguments() != null){
            bundle = getArguments();

            passingValue = true;
            dateField.setText(bundle.getString("bundleDate"));
            wakeUpTime.setText(bundle.getString("bundleWakeUpTime"));
            sleepTime.setText(bundle.getString("bundleSleepTime"));
            differenceTime = FindTheDifferenceTime(sleepTime.getText().toString(), wakeUpTime.getText().toString());
            sleepStore = new SleepStore(dateField.getText().toString() , sleepTime.getText().toString(), wakeUpTime.getText().toString(), differenceTime);

        }


        dateField.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        datePickerPopup(dateField);
                                    }
                                });
        sleepTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerPopup(sleepTime);

            }
        });

        wakeUpTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerPopup(wakeUpTime);

            }
        });



        addTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time1 = sleepTime.getText().toString();
                time2 = wakeUpTime.getText().toString();
                dateStr = dateField.getText().toString();


                if(!time1.isEmpty() && !time2.isEmpty() && !dateStr.isEmpty()) {

                    differenceTime = FindTheDifferenceTime(time1, time2);
                    if(!differenceTime.isEmpty()) {
                        sleepStore = new SleepStore(dateStr, time1,time2,differenceTime);
                        sleepStore.setDifferenceTime(differenceTime);
                        if(passingValue) {
                            myDB.updateTime(sleepStore);
                        }else {
                            myDB.addTime(sleepStore);
                        }
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new SleepFragment()).addToBackStack(null).commit();
                    }

                }else{
                    Toast.makeText(getActivity(),"กรุณากรอกข้อมูลให้ครบถ้วน",Toast.LENGTH_SHORT).show();
                }




            }
        });


        backToListViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new SleepFragment()).addToBackStack(null).commit();
            }
        });
    }






     private String FindTheDifferenceTime(String timeLocal1, String timeLocal2){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
         pp1 = new ParsePosition(0);
         pp2 = new ParsePosition(0);
         pp3 = new ParsePosition(0);
         pp4 = new ParsePosition(0);
        Date startDate = simpleDateFormat.parse(timeLocal1, pp1);
        Date endDate = simpleDateFormat.parse(timeLocal2, pp2);
        long difference = endDate.getTime() - startDate.getTime();
        if(difference<0)
        {
            Date dateMax = simpleDateFormat.parse("24:00", pp3);
            Date dateMin = simpleDateFormat.parse("00:00", pp4);
            difference = (dateMax.getTime() -startDate.getTime() )+(endDate.getTime()-dateMin.getTime());
        }
        int days = (int) (difference / (1000*60*60*24));
        int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
        int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
        String curTime = String.format("%02d:%02d", hours, min);
        return curTime;


    }

    private void timePickerPopup(final TextView field) {
        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                field.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
    private void datePickerPopup(final TextView field){
        //Get Current Date
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if(month < 10){
                    monthStr = "0" + (month + 1);
                }else{
                    monthStr = (month + 1)+"";
                }

                if(dayOfMonth < 10){
                    dayStr  = "0" + dayOfMonth ;
                }else{
                    dayStr = dayOfMonth+"";
                }
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
                field.setText(year+"-"+monthStr+"-"+dayStr);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep_form, container, false);
    }


}
