package ru.ifmo.ctddev.koval.carwash;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class OrdersCursorAdapter extends CursorAdapter {

    private LayoutInflater mInflater;
    private Store store;

    public OrdersCursorAdapter(Context context, Cursor c) {
        super(context, c);
        mInflater = LayoutInflater.from(context);
        store = new Store(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return mInflater.inflate(R.layout.order_list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int carId = cursor.getInt(cursor.getColumnIndex(DatabaseOpenHelper.CAR_ID_COLUMN));
        String time = cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.TIME_COLUMN));
        int box = cursor.getInt(cursor.getColumnIndex(DatabaseOpenHelper.BOX_COLUMN));

        Car car = store.getCar(carId);

        ((TextView) view.findViewById(R.id.car_info)).setText(car.getMake() + " - " + car.getColor());
        ((TextView) view.findViewById(R.id.time)).setText(time);
        ((TextView) view.findViewById(R.id.box)).setText(box + 1 + "");
    }
}
