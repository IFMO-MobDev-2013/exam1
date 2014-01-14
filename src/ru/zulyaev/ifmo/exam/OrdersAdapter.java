package ru.zulyaev.ifmo.exam;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import ru.zulyaev.ifmo.exam.db.Order;

import java.util.Collections;
import java.util.List;

public class OrdersAdapter extends BaseAdapter {
    private List<Order> orders = Collections.emptyList();
    private final LayoutInflater inflater;

    public OrdersAdapter(Activity activity) {
        inflater = activity.getLayoutInflater();
    }


    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Order getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orders.get(position).id;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.order_row, parent, false);
            holder = new ViewHolder(
                    (TextView) view.findViewById(R.id.model),
                    (TextView) view.findViewById(R.id.color),
                    (TextView) view.findViewById(R.id.time),
                    (TextView) view.findViewById(R.id.box)
            );
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        return view;
    }

    static class ViewHolder {
        final TextView model;
        final TextView color;
        final TextView time;
        final TextView box;

        ViewHolder(TextView model, TextView color, TextView time, TextView box) {
            this.model = model;
            this.color = color;
            this.time = time;
            this.box = box;
        }
    }
}
