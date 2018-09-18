package com.pleng.healthy.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.pleng.healthy.healthy.Weight.WeightFragment;
import com.pleng.healthy.healthy.Weight.WeightStore;

import java.util.ArrayList;

public class CheckStatus{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String uid;
    ArrayList<WeightStore> weightStore = new ArrayList<>();
FirebaseUser _user = FirebaseAuth.getInstance().getCurrentUser();
    public CheckStatus(){

    }



    public CheckStatus(ArrayList<WeightStore> weightStore) {
        this.uid = _user.getUid();

                for (int i = 1; i < weightStore.size(); i++) {
                        if(weightStore.get(i - 1).getWeight() > weightStore.get(i).getWeight()) {
                            weightStore.get(i).setStatus("Down");
                        }else{
                            weightStore.get(i).setStatus("Up");
                        }
                }
                for(WeightStore items:weightStore) {
                    Log.i("CheckStatus", items.getDate());
                }

        Log.i("CheckStatus", weightStore.size()+"");
//                db.collection("myfitness").document(uid).collection("weight").document(weightStore.get(0).getDate()).set(weightStore);

        this.weightStore = weightStore;
    }



    public ArrayList<WeightStore> getWeightStore() {
        return weightStore;
    }
}

