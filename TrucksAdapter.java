package com.example.foodtruck;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TrucksAdapter extends ArrayAdapter {
    List list = new ArrayList<>();
    public TrucksAdapter(Context context, int resource, int reso) {
        super(context, resource, reso);
    }

    public void add( Trucks object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View line = convertView;
        TruckHolder truckHolder;

        if(line==null){

            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            line = layoutInflater.inflate(R.layout.line_activity, parent, false);
            truckHolder = new TruckHolder();
            truckHolder.applicant = (TextView) line.findViewById(R.id.applicant);
            truckHolder.address = (TextView) line.findViewById(R.id.address);
            truckHolder.items = (TextView) line.findViewById(R.id.items);
            truckHolder.timing = (TextView) line.findViewById(R.id.timing);
            line.setTag(truckHolder);
        }
        else{
            truckHolder = (TruckHolder)line.getTag();
        }
        System.out.println("is truckholder there ==========" + truckHolder);
        Trucks trucks = (Trucks)this.getItem(position);
        truckHolder.applicant.setText(trucks.getApp());
        truckHolder.address.setText(trucks.getAdd());
        truckHolder.items.setText(trucks.getItem());
        truckHolder.timing.setText(trucks.getTime());

        return line;
//        return super.getView(position, convertView, parent);
    }

    static class TruckHolder{
        TextView applicant, address, items, timing;
    }
}
