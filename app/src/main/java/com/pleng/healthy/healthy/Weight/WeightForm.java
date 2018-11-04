package com.pleng.healthy.healthy.Weight;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pleng.healthy.healthy.MenuFragment;
import com.pleng.healthy.healthy.R;

import java.util.ArrayList;
import java.util.Calendar;

public class WeightForm extends Fragment {
    ArrayList<WeightStore> weightStores = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weight_form, container, false);
    }
    TextView dateField, weight;
    Calendar mCurrentDate;
    int mYear, mMonth, mDay;
    String dayStr, monthStr;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String uid, dateStr, weightStr;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        dateField = (TextView) getView().findViewById(R.id.date_for_weight_page);
        weight = (TextView) getView().findViewById(R.id.weight_for_weight_page);
        super.onActivityCreated(savedInstanceState);
        mCurrentDate = Calendar.getInstance();
        mYear = mCurrentDate.get(Calendar.YEAR);
        mMonth = mCurrentDate.get(Calendar.MONTH);
        mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);

        //When Click and OnFoucus Date Field Show Date Picker

        dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerPopup(dateField);
            }
        });


        Button backBtn = (Button)getView().findViewById(R.id.weightform_back_to_menu_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new MenuFragment()).addToBackStack(null).commit();

            }
        });

        Button saveWeightButton = (Button) getView().findViewById(R.id.save_weight_form);
        uid = currentFirebaseUser.getUid().toString();

        //When Click Save Button
        saveWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateStr = dateField.getText().toString();
                weightStr = weight.getText().toString();

                Log.i("WeightForm",  weight+""+dateStr);


                if(dateStr.isEmpty() || weightStr.isEmpty()){
                    Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();
                }else {

                    //Create Fire Store
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    //Get Weight Obj
                    final WeightStore weightStore = new WeightStore(Float.parseFloat(weight.getText().toString()), dateField.getText().toString(), "-");
                    //Send Data To Database
                    db.collection("myfitness")
                            .document(uid)
                            .collection("weight")
                            .document(dateField.getText().toString())
                            .set(weightStore)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("WeightFormFagment", "The information was saved");
                                    Toast.makeText(getActivity(), "เพิ่มข้อมูลเรียบร้อยแล้ว", Toast.LENGTH_LONG).show();
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new WeightFragment()).addToBackStack(null).commit();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });


    }

    private void datePickerPopup(final TextView field){
        //Get Current Date



        DatePickerDialog  datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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


}
