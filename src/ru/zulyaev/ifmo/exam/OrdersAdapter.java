package ru.zulyaev.ifmo.exam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import ru.zulyaev.ifmo.exam.db.Order;
import ru.zulyaev.ifmo.exam.db.OrderTable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrdersAdapter extends BaseAdapter {
    private static final DateFormat FORMAT = new SimpleDateFormat("k:mm");

    private List<Order> orders;
    private final LayoutInflater inflater;
    private final OrderTable table;

    public OrdersAdapter(Context context, OrderTable table) {
        inflater = LayoutInflater.from(context);
        this.table = table;

        orders = table.getTodayOrders();
    }

    public void refresh() {
        orders = table.getTodayOrders();
        notifyDataSetChanged();
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
        Order order = orders.get(position);
        holder.model.setText(order.model);
        holder.color.setText(order.color);
        holder.time.setText(FORMAT.format(new Date(order.time)));
        holder.box.setText(Long.toString(order.box));

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
