package com.pleng.healthy.healthy;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CurrentUser {


    FirebaseUser currentFirebaseUser;
    String uidStr;
    public CurrentUser(){
        this.currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        this.uidStr = currentFirebaseUser.getUid().toString();
    }

    public FirebaseUser getCurrentFirebaseUser() {
        return currentFirebaseUser;
    }

    public String getUidStr() {
        return uidStr;
    }
}
