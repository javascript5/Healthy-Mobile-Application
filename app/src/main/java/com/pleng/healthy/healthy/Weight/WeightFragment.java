package com.pleng.healthy.healthy.Weight;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.pleng.healthy.healthy.CheckStatus;
import com.pleng.healthy.healthy.R;
import com.pleng.healthy.healthy.UpdateStatusToFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class WeightFragment extends Fragment {
    String uid;
    FirebaseUser _user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<WeightStore> weightStore = new ArrayList<WeightStore>();

    private DocumentSnapshot doc;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.uid = _user.getUid();
        final ListView weightList = (ListView) getView().findViewById(R.id.weight_list);
        final WeightConfigItem weightConfigItem =  new WeightConfigItem(
                getActivity(),
                R.layout.fragment_weight_items,
                weightStore
        );


        weightList.setAdapter(weightConfigItem);
        weightConfigItem.clear();

//        db.collection("myfitness").document(uid).collection("weight").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//
//                for(DocumentSnapshot doc : documentSnapshots){
//                    if(doc.get("date") != null){
//                        weightStore.add(doc.toObject(WeightStore.class));
//                    }
//                }
//                CheckStatus checkStatus = new CheckStatus(weightStore);
//                weightStore = checkStatus.getWeightStore();
//                weightConfigItem.notifyDataSetChanged();
////                Log.i("WeightFragment", "Test");
//
//
//
//            }
//
//        });

        db.collection("myfitness").document(uid).collection("weight").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        weightStore.add(document.toObject(WeightStore.class));
                    }
                }

                CheckStatus checkStatus = new CheckStatus(weightStore);
                weightStore = checkStatus.getWeightStore();
                weightConfigItem.notifyDataSetChanged();

                UpdateStatusToFirestore updateStatusToFirestore = new UpdateStatusToFirestore(weightStore);
            }
        });




        weightConfigItem.sort(new Comparator<WeightStore>() {
            @Override
            public int compare(WeightStore o1, WeightStore o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        weightConfigItem.notifyDataSetChanged();










    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight,container,false);
    }

}
