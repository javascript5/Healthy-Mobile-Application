package com.pleng.healthy.healthy;


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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        dateField = (TextView) getView().findViewById(R.id.date_for_weighting);
        weight = (TextView) getView().findViewById(R.id.weight_for_weighting);
        super.onActivityCreated(savedInstanceState);
        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);


        dateField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    datePickerPopup(dateField);
                }
            }
        });

        dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerPopup(dateField);
            }
        });

        Button saveWeightButton = (Button) getView().findViewById(R.id.save_weight_form);

        //Get Current User
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        saveWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put(dateField.getText().toString(), weight.getText().toString());
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("myfitness").document(currentFirebaseUser.getUid()).collection("weight").document(dateField.getText().toString()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new WeightFragment()).addToBackStack(null).commit();
                    }
                }).addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(),Toast.LENGTH_LONG);
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
