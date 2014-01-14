package com.ctd.CarWash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alexei on 14.01.14.
 */
public class OrderAdapter extends ArrayAdapter<Order> {
    Context context;
    ArrayList<Order> arrayList;

    public OrderAdapter(Context context, ArrayList<Order> objects) {
        super(context, R.layout.main, objects);
        this.context = context;
        this.arrayList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item, parent, false);
        TextView tvCarName = (TextView) view.findViewById(R.id.tvCarName);
        TextView tvCarColor = (TextView) view.findViewById(R.id.tvCarColor);
        TextView tvTime = (TextView) view.findViewById(R.id.tvTime);
        TextView tvBox = (TextView) view.findViewById(R.id.tvBox);
        tvCarName.setText("Название машины \n" + arrayList.get(position).carName);
        tvCarColor.setText("Цвет машины \n" + arrayList.get(position).carColor);
        tvTime.setText("Время \n" + arrayList.get(position).time);
        tvBox.setText("Бокс \n" + String.valueOf(arrayList.get(position).box + 1));
        return view;
    }

}
