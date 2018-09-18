package com.pleng.healthy.healthy;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by LAB203_13 on 20/8/2561.
 */

public class RegisterFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    boolean checkPassword(String passwrod, String rePassword){
        if(passwrod.length() > 6){
            if(passwrod.equals(rePassword)) {
                return true;
            }else{
                Toast.makeText(getActivity(),"Password ไม่ตรงกัน", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getActivity(),"Password ต้องมากกว่า 6 ตัวอักษร", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    void sendVerifiedEmail(FirebaseUser _user) {
        _user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Button signUpButton = (Button) getView().findViewById(R.id.sign_up_button_sign_up_page);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView email = (TextView) getView().findViewById(R.id.email_sign_up_page);
                TextView passwordId = (TextView) getView().findViewById(R.id.password_id_sign_up_page);
                TextView rePassword = (TextView) getView().findViewById(R.id.re_password_id_sign_up_page);

                String emailStr = email.getText().toString();
                String passwordIdStr = passwordId.getText().toString();
                String rePasswordStr = rePassword.getText().toString();
                boolean checkPasswordBool = checkPassword(passwordIdStr, rePasswordStr);


                if(emailStr.isEmpty() || passwordIdStr.isEmpty() || rePasswordStr.isEmpty()){
                    Toast.makeText(getActivity(),"กรุณาระบุข้อมูลให้ครบถ้วน",Toast.LENGTH_SHORT).show();
                    Log.i("REGISTER", "Field Name is Empyty");
                }else if(emailStr.equals("admin@admin.com")){
                    Toast.makeText(getActivity(), "user นี้มีอยู่ในระบบแล้ว", Toast.LENGTH_SHORT).show();
                    Log.i("REGISTER","USER ALREADY EXITS");
                }else{
                    if(checkPasswordBool) {
                        //Firebase Register
                        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        mAuth.createUserWithEmailAndPassword(emailStr, passwordIdStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Log.i("Register", "Complete");
                                sendVerifiedEmail(authResult.getUser());
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new LogoutFragment()).addToBackStack(null).commit();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });


    }
}
