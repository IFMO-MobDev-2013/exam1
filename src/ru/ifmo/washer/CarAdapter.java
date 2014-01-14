package ru.ifmo.washer;

/**
 * Created by asus on 13.01.14.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by asus on 27.12.13.
 */
public class CarAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Car> objects;
    CarsDatabase db;
    Context context;

    CarAdapter(Context context, ArrayList<Car> items, CarsDatabase carsDatabase) {
        ctx = context;
        objects = items;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        db = carsDatabase;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.car_item, parent, false);
        }

        Car l = (Car) getItem(position);

        ((TextView) view.findViewById(R.id.itemMainText)).setText(l.param[Car.NAME]);
        ((TextView) view.findViewById(R.id.itemAltText)).setText(l.param[Car.COLOR]);
        ((TextView) view.findViewById(R.id.itemBoxText)).setText("Box: " + l.param[Car.BOX]);
        ((TextView) view.findViewById(R.id.itemTimeText)).setText(l.getTime());

        return view;
    }

}


