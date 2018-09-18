package com.pleng.healthy.healthy;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pleng.healthy.healthy.Weight.WeightStore;

import java.util.ArrayList;

public class UpdateStatusToFirestore {
    ArrayList<WeightStore> weightStore;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String uid;
    FirebaseUser _user = FirebaseAuth.getInstance().getCurrentUser();
    public UpdateStatusToFirestore(){

    }

    public UpdateStatusToFirestore(ArrayList<WeightStore> weightStore){
        uid = _user.getUid().toString();
        this.weightStore = weightStore;
        int index = 0;
        for(WeightStore items: this.weightStore ){
            db.collection("myfitness").document(uid).collection("weight").document(items.getDate()).set(this.weightStore.get(index++));
        }


    }


}
