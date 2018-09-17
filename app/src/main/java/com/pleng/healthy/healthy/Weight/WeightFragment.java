package com.pleng.healthy.healthy.Weight;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.pleng.healthy.healthy.CurrentUser;
import com.pleng.healthy.healthy.R;

import java.util.ArrayList;

public class WeightFragment extends Fragment {
    String uid;
    CurrentUser currentUser = new CurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<WeightStore> weightStore = new ArrayList<WeightStore>();
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        weightStore.add(new WeightStore(50,"10-jul-50","UP"));
        this.uid = currentUser.getUidStr();
        final ListView weightList = (ListView) getView().findViewById(R.id.weight_list);
        final WeightConfigItem weightConfigItem =  new WeightConfigItem(
                getActivity(),
                R.layout.fragment_weight_items,
                weightStore
        );
        weightList.setAdapter(weightConfigItem);
        weightConfigItem.clear();


        db.collection("myfitness").document(uid).collection("weight").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for(DocumentSnapshot doc : documentSnapshots){
                    if(doc.get("date") != null){
                        weightStore.add(doc.toObject(WeightStore.class));
                    }
                }
                weightConfigItem.notifyDataSetChanged();

            }
        });



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight,container,false);
    }
}
