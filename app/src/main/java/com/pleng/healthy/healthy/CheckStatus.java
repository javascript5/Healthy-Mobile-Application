package com.pleng.healthy.healthy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.pleng.healthy.healthy.Weight.WeightStore;

import java.util.ArrayList;

public class CheckStatus{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String uid;
    CurrentUser currentUser;

    ArrayList<WeightStore> weightStores;

    public CheckStatus() {
        weightStores  = new ArrayList<>();
        currentUser = new CurrentUser();
        this.uid = currentUser.getUidStr();
        db.collection("myfitness").document(uid).collection("weight").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for (DocumentSnapshot doc : documentSnapshots) {
                    if (doc.get("date") != null) {
                        weightStores.add(doc.toObject(WeightStore.class));
                    }
                }

                for (int i = 0; i < weightStores.size(); i++) {

                    DocumentReference contact = db.collection("myfitness").document(uid).collection("weight").document(weightStores.get(i).getDate());
                    if(i != 0) {
                        if(weightStores.get(i - 1).getWeight() > weightStores.get(i).getWeight()) {
                            contact.update("status", "down");
                        }else{
                            contact.update("status", "up");
                        }
                    }
                }




            }
        });



    }
}

