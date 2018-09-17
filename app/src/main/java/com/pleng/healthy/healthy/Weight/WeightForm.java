package com.pleng.healthy.healthy.Weight;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pleng.healthy.healthy.CurrentUser;
import com.pleng.healthy.healthy.R;
import com.pleng.healthy.healthy.Weight.WeightFragment;
import com.pleng.healthy.healthy.Weight.WeightStore;

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
    int year, month, day;
    FirebaseUser currentFirebaseUser;
    String uid, dateStr;
    CurrentUser currentUser = new CurrentUser();
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        dateField = (TextView) getView().findViewById(R.id.date_for_weighting);
        weight = (TextView) getView().findViewById(R.id.weight_for_weighting);
        super.onActivityCreated(savedInstanceState);

        //Get Current Date
        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        //When Click and OnFoucus Date Field Show Date Picker
        dateField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){ datePickerPopup(dateField);}
            }
        });
        dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerPopup(dateField);
            }
        });


        Button saveWeightButton = (Button) getView().findViewById(R.id.save_weight_form);
        uid = currentUser.getUidStr();

        //When Click Save Button
        saveWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Create Fire Store
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                //Get Weight Obj
                WeightStore weightStore = new WeightStore(Float.parseFloat(weight.getText().toString()) ,dateField.getText().toString(),"up");

                //Send Data To Database
                firestore.collection("myfitness")
                        .document(uid)
                        .collection("weight")
                        .document(dateField.getText().toString())
                        .set(weightStore)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "เพิ่มข้อมูลเรียบร้อยแล้ว",Toast.LENGTH_LONG).show();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new WeightFragment()).addToBackStack(null).commit();
                    }
                }).addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


    }

    void datePickerPopup(final TextView field){
        DatePickerDialog  datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                field.setText(dayOfMonth+"-"+month+"-"+year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }


}
