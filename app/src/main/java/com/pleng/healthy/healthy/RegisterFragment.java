package com.pleng.healthy.healthy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by LAB203_13 on 20/8/2561.
 */

public class RegisterFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Button signUpButton = (Button) getView().findViewById(R.id.sign_up_button_sign_up_page);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView userId = (TextView) getView().findViewById(R.id.user_id_sign_up_page);
                TextView passwordId = (TextView) getView().findViewById(R.id.password_id_sign_up_page);
                TextView fullName = (TextView) getView().findViewById(R.id.fullname_sign_up_page);
                TextView age = (TextView) getView().findViewById(R.id.age__sign_up_page);

                String userIdStr = userId.getText().toString();
                String passwordIdStr = passwordId.getText().toString();
                String fullNameStr = fullName.getText().toString();
                String ageStr = age.getText().toString();

                if(userIdStr.isEmpty() || passwordIdStr.isEmpty() || fullNameStr.isEmpty() || ageStr.isEmpty()){
                    Toast.makeText(getActivity(),"กรุณาระบุข้อมูลให้ครบถ้วน",Toast.LENGTH_SHORT).show();
                    Log.i("REGISTER", "Field Name is Empyty");
                }else if(userIdStr.equals("admin")){
                    Toast.makeText(getActivity(), "user นี้มีอยู่ในระบบแล้ว", Toast.LENGTH_SHORT).show();
                    Log.i("REGISTER","USER ALREADY EXITS");
                }else{
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new BMIFragment()).addToBackStack(null).commit();
                    Log.i("REGISTER","GO TO BMI");
                }
            }
        });


    }
}
