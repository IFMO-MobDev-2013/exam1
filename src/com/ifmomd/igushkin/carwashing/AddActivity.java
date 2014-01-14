package com.ifmomd.igushkin.carwashing;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 1/14/14.
 */
public class AddActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    EditText edt_model, edt_color, edt_number, edt_phone;
    Spinner  spn_time;
    TextView tv_box;
    Button   btn_done;

    CarWashingDatabaseHelper mDb;

    int boxesCount;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_add);

        findViews();

        mDb = new CarWashingDatabaseHelper(this);
        mDb.open();

        init();

        spn_time.setOnItemSelectedListener(this);
        btn_done.setOnClickListener(this);
    }

    public static final int MAX_TIME = 28;

    private void init() {
        if (getIntent().hasExtra(MainListActivity.EXTRA_ID))
        {
            long id = getIntent().getLongExtra(MainListActivity.EXTRA_ID, -1);
            Cursor cursor = mDb.getCarById(id);

            int modelColumn = cursor.getColumnIndex(CarWashingDatabaseHelper.KEY_CARS_MODEL);
            int colorColumn = cursor.getColumnIndex(CarWashingDatabaseHelper.KEY_CARS_COLOR);
            int numberColumn = cursor.getColumnIndex(CarWashingDatabaseHelper.KEY_CARS_NUMBER);
            int phoneColumn = cursor.getColumnIndex(CarWashingDatabaseHelper.KEY_CARS_PHONE);
            int boxColumn = cursor.getColumnIndex(CarWashingDatabaseHelper.KEY_CARS_BOX);

            cursor.moveToFirst();
            edt_model.setText(cursor.getString(modelColumn));
            edt_color.setText(cursor.getString(colorColumn));
            edt_number.setText(cursor.getString(numberColumn));
            edt_phone.setText(cursor.getString(phoneColumn));
            String box = getString(R.string.txt_box_number) + Integer.toString(cursor.getInt(boxColumn) + 1);

        }

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boxesCount = sp.getInt(MainListActivity.prfBoxesCount, 0);
        fillTimes();
    }

    private void chooseBox(TimeAndBoxes t, int position) {
        boolean[] takenBoxes = mDb.getBoxesByTime(t.time, boxesCount);
        int box = 0;
        for (; box<takenBoxes.length; ++box)
            if (!takenBoxes[box])
                break;
        tv_box.setText(Integer.toString(box+1));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (view == spn_time)
            if (((parent.getItemAtPosition(position))) != null) {
                chooseBox((TimeAndBoxes)parent.getItemAtPosition(position), position);
            }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v == btn_done)
        {
            if (!getIntent().hasExtra(MainListActivity.EXTRA_ID)) {
            mDb.putCar(edt_model.getText().toString(),
                       edt_color.getText().toString(),
                       edt_number.getText().toString(),
                       edt_phone.getText().toString(),
                       ((TimeAndBoxes)spn_time.getItemAtPosition(spn_time.getSelectedItemPosition())).time,
                       boxesCount);
            }
            else
            mDb.editCar(getIntent().getLongExtra(MainListActivity.EXTRA_ID, -1),
                        edt_model.getText().toString(),
                        edt_color.getText().toString(),
                        edt_number.getText().toString(),
                        edt_phone.getText().toString(),
                        ((TimeAndBoxes)spn_time.getItemAtPosition(spn_time.getSelectedItemPosition())).time,
                        boxesCount);

            setResult(RESULT_OK);
            finish();
        }
    }

    class TimeAndBoxes {
        int time, boxes;

        TimeAndBoxes(int time, int boxes) {
            this.time = time;
            this.boxes = boxes;
        }

        @Override
        public String toString() {
            return MainListActivity.getTimeFromInt(time) + " " +(
                   boxes == -1 ? "(tap to edit)" :
                   String.format(getString(R.string.free_boxes), boxes));
        }
    }

    private void fillTimes() {
        List<TimeAndBoxes> times = new ArrayList<TimeAndBoxes>();

        if (getIntent().hasExtra(MainListActivity.EXTRA_ID))
        {
            long id = getIntent().getLongExtra(MainListActivity.EXTRA_ID, -1);
            Cursor cursor = mDb.getCarById(id);
            cursor.moveToFirst();
            int time = cursor.getInt(cursor.getColumnIndex(CarWashingDatabaseHelper.KEY_CARS_TIME));
            times.add(new TimeAndBoxes(time, -1));
        }

        for (int i = 0; i < MAX_TIME; ++i) {
            boolean[] boxes = mDb.getBoxesByTime(i, boxesCount);
            int freeBoxes = 0;
            for (int j = 0; j < boxesCount; ++j)
                if (!boxes[j])
                    freeBoxes++;
            if (freeBoxes > 0)
                times.add(new TimeAndBoxes(i, freeBoxes));
        }

        spn_time.setAdapter(new ArrayAdapter<TimeAndBoxes>(this, android.R.layout.simple_list_item_1, times));
        if (spn_time.getAdapter().getCount() > 0)
            spn_time.setSelection(0);
    }

    private void findViews() {
        edt_model = (EditText) findViewById(R.id.edt_model);
        edt_color = (EditText) findViewById(R.id.edt_color);
        edt_number = (EditText) findViewById(R.id.edt_number);
        edt_phone = (EditText) findViewById(R.id.edt_phone);

        spn_time = (Spinner) findViewById(R.id.spn_time);

        tv_box = (TextView) findViewById(R.id.tv_box);

        btn_done = (Button) findViewById(R.id.btn_done);
    }


}