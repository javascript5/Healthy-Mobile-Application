package com.pleng.healthy.healthy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ConfigWeightListItem extends ArrayAdapter<WeightStore>{
    private Context context;
    List<WeightStore> weightStore = new ArrayList<WeightStore>();
    public ConfigWeightListItem(@NonNull Context context, int resource, List<WeightStore> weightStore) {
        super(context, resource);
        this.weightStore = weightStore;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView weight = (TextView)convertView.findViewById(R.id.weight_weightFragment);
        TextView date = (TextView)convertView.findViewById(R.id.date_weight_weightFragment);
        TextView status = (TextView)convertView.findViewById(R.id.status_weightFragment);
        WeightStore weightGet = weightStore.get(position);
        weight.setText(weightGet.getWeight()+"");
        date.setText(weightGet.getDate());
        status.setText(weightGet.getStatus());
        return LayoutInflater.from(context).inflate(
                R.layout.fragment_weight_item,
                parent,
                false
        );
    }
}
