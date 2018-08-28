package com.pleng.healthy.healthy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class WeightFragment extends Fragment {
    ArrayList<WeightStore> weightsStore = new ArrayList<>();
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView weightListView = (ListView) getView().findViewById(R.id.weight_list);
        weightsStore.add(new WeightStore(5,"10-jul-50","Up"));
        ConfigWeightListItem _weightAdapter = new ConfigWeightListItem(
                getActivity(),
                R.layout.fragment_weight_item,
                weightsStore
        );
        weightListView.setAdapter(_weightAdapter);
    }
}
