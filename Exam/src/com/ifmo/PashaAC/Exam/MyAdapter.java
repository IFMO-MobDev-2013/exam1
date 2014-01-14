package com.ifmo.PashaAC.Exam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: ПАВЕЛ
 * Date: 14.01.14
 * Time: 17:38
 * To change this template use File | Settings | File Templates.
 */
public class MyAdapter extends BaseAdapter {

    Context context;
    LayoutInflater lInflater;
    ArrayList<Order> objects;

    MyAdapter(Context _context, ArrayList<Order> products) {
        context = _context;
        objects = products;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        Order p = getProduct(position);

        ((TextView) view.findViewById(R.id.textView_item_mark)).setText(p.mark);
        ((TextView) view.findViewById(R.id.textView_item_colour)).setText(p.colour);
        ((TextView) view.findViewById(R.id.textView_item_time)).setText(p.time);
        ((TextView) view.findViewById(R.id.textView_item_box)).setText(p.box);
        return view;
    }

    Order getProduct(int position) {
        return ((Order) getItem(position));
    }

}
