package ru.marsermd.IpsumLotis;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: misch_000
 * Date: 14.01.14
 * Time: 18:18
 * To change this template use File | Settings | File Templates.
 */
public class CarAdapter extends BaseAdapter{
    private List<CarModel> list;
    private int page = 0;

    public void init() {
        list.clear();
        page = 0;
        count = 0;
    }

    private Activity context;
    private int count;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public CarAdapter(Activity context, String type) {
        super();
        this.context = context;
        list = MainScreenActivity.db.getAllCars();
    }

    private class ViewHolder {
        TextView nameView;
        TextView timeView;
        TextView boxView;
        CarModel model;

        public ViewHolder(TextView nameView, TextView timeView, TextView boxView) {
            this.nameView = nameView;
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.car_row, null);
            TextView name = (TextView) convertView.findViewById(R.id.car_name);
            TextView time = (TextView) convertView.findViewById(R.id.car_time);
            TextView box = (TextView) convertView.findViewById(R.id.car_box);
            ViewHolder viewHolder = new ViewHolder(name, time, box);
            convertView.setTag(viewHolder);
            holder = viewHolder;
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (holder.model != null) {
            //holder.model.cancel();
        }
        holder.model = list.get(position);
        holder.model.setNameV(holder.nameView);
        holder.model.setBoxV(holder.boxView);
        holder.model.setTimeV(holder.timeView);
        return convertView;
    }
}
