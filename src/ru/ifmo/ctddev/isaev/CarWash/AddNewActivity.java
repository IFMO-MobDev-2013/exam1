package ru.ifmo.ctddev.isaev.CarWash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.j256.ormlite.dao.Dao;
import ru.ifmo.ctddev.isaev.CarWash.orm.ScheduleEntry;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Xottab
 * Date: 14.01.14
 */
public class AddNewActivity extends Activity {
    EditText brand;
    EditText color;
    EditText carNumber;
    EditText phoneNumber;
    Spinner time;
    Button addNew;
    DatabaseHelper helper;
    Dao scheduleDao;

    @Override
    public void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.add_new);
        helper = DatabaseManager.getInstance().getHelper();
        try {
            scheduleDao = helper.getEntriesDao();
        } catch (SQLException e) {
            Log.e("error", "error", e);
        }
        brand = (EditText) findViewById(R.id.editText3);
        color = (EditText) findViewById(R.id.editText4);
        carNumber = (EditText) findViewById(R.id.editText);
        phoneNumber = (EditText) findViewById(R.id.editText2);
        addNew = (Button) findViewById(R.id.button);
        time = (Spinner) findViewById(R.id.spinner);
        time.setAdapter(new TimeChooseAdapter(this, android.R.layout.simple_spinner_item, new ArrayList<Integer>(General.freeTime.keySet())));
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer selectedTime = (Integer) time.getSelectedItem();
                ScheduleEntry entry = new ScheduleEntry(
                        brand.getText().toString(),
                        color.getText().toString(),
                        carNumber.getText().toString(),
                        phoneNumber.getText().toString(),
                        selectedTime,
                        General.freeTime.get(selectedTime).remove(0));
                try {
                    scheduleDao.create(entry);
                } catch (SQLException e) {
                    Log.e("error", "error", e);
                }
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });
    }

    public class TimeChooseAdapter extends ArrayAdapter<Integer> {

        public TimeChooseAdapter(Context context, int resource, List<Integer> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            TextView textView = new TextView(getContext());
            textView.setTextSize(15);
            int time = getItem(position);
            textView.setText(General.formatTime(time));
            return textView;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
            TextView textView = new TextView(getContext());
            int time = getItem(pos);
            textView.setText(General.formatTime(time));
            return textView;
        }
    }
}
