package com.pleng.healthy.healthy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pleng.healthy.healthy.Weight.WeightForm;
import com.pleng.healthy.healthy.Weight.WeightFragment;

import java.util.ArrayList;

public class MenuFragment extends Fragment {
    ArrayList<String> menu = new ArrayList<>();

    public MenuFragment(){
        menu.add("BMI");
        menu.add("Weight");
        menu.add("Add Weight");
        menu.add("Logout");
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView menuList = (ListView) getView().findViewById(R.id.menu_list);
        ArrayAdapter<String> menuAdapter = new ArrayAdapter<String>(
          getActivity(),
          android.R.layout.simple_list_item_1,
                menu
        );

        menuList.setAdapter(menuAdapter);
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String menuPosition = menu.get(position);
                if(menuPosition.equals("BMI")){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new BMIFragment()).addToBackStack(null).commit();
                }else if(menuPosition.equals("Add Weight")){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new WeightForm()).addToBackStack(null).commit();
                }else if(menuPosition.equals("Logout")){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new LogoutFragment()).addToBackStack(null).commit();
                }else{
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new WeightFragment()).addToBackStack(null).commit();
                }
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container,false);
    }
}
