package com.pleng.healthy.healthy;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CurrentUser{
    FirebaseUser currentFirebaseUser;
    String uidStr;

    public CurrentUser(){
        this.currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        this.uidStr = this.currentFirebaseUser.getUid().toString();
    }

    public String getUidStr() {
        return this.uidStr;
    }
}
