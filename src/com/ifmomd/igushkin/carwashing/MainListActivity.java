package com.ifmomd.igushkin.carwashing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.SparseBooleanArray;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class MainListActivity extends Activity implements AdapterView.OnItemClickListener {
    /**
     * Called when the activity is first created.
     */
    public static final String prfFirstStart  = "prfFirstStart";
    public static final String prfCompanyName = "prfCompanyName";
    public static final String prfBoxesCount  = "prfBoxesCount";

    CarWashingDatabaseHelper mDb;

    ListView lv_cars_list;

    int    boxesCount;
    String companyName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean firstStart = sp.getBoolean(prfFirstStart, true);

        if (firstStart)
            performFirstStart();
        else
            initActivity();

        lv_cars_list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lv_cars_list.setOnItemClickListener(this);
        lv_cars_list.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                mode.setTitle(String.format(getString(R.string.format_am_title), lv_cars_list.getCheckedItemCount()));
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.action_mode_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item.getItemId() == R.id.mnu_delete) {
                    ArrayList<Long> idsToDelete = new ArrayList<Long>();
                    SparseBooleanArray checked = lv_cars_list.getCheckedItemPositions();
                    for (int i = 0; i < checked.size(); i++) {
                        if (checked.valueAt(i))
                            idsToDelete.add(lv_cars_list.getAdapter().getItemId(checked.keyAt(i)));
                    }
                    mDb.batchDelete(idsToDelete);
                    mode.finish();
                    MainListActivity.this.updateListContent();
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
    }

    private void initActivity() {SharedPreferences sp;
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        companyName = sp.getString(prfCompanyName, null);
        boxesCount = sp.getInt(prfBoxesCount, 0);

        mDb = new CarWashingDatabaseHelper(this);
        mDb.open();

        setTitle(companyName);

        findViews();

        updateListContent();
    }

    private void findViews() {
        lv_cars_list = (ListView) findViewById(R.id.lv_cars_list);
    }

    public static final int REQUEST_FIRST_START = 13256;

    private void performFirstStart() {
        Intent i = new Intent(this, FirstStartActivity.class);
        startActivityForResult(i, REQUEST_FIRST_START);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FIRST_START)
        {
            if (resultCode == RESULT_OK)
                initActivity();
            else
                finish();
        }
        if (requestCode == REQUEST_ADD)
        {
            if (resultCode == RESULT_OK)
                updateListContent();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public static final int REQUEST_ADD = 1274;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnu_add)
        {
            Intent i = new Intent(this, AddActivity.class);
            startActivityForResult(i, REQUEST_ADD);
        }
        return super.onOptionsItemSelected(item);
    }

    public static final int first_hour = 8;

    public static String getTimeFromInt(int time) {
        int hour = first_hour + time / 2;
        return hour + ":" + (time % 2 == 1 ? "30" : "00");
    }

    private void updateListContent() {
        Cursor c = mDb.fetchCars();

        lv_cars_list.setAdapter(new CursorAdapter(this, c) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                View v = getLayoutInflater().inflate(R.layout.lyt_car_item, null);
                bindView(v, context, cursor);
                return v;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView tv_model_color = (TextView) view.findViewById(R.id.tv_model_color);
                TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
                TextView tv_box = (TextView) view.findViewById(R.id.tv_box);

                int modelColumn = cursor.getColumnIndex(CarWashingDatabaseHelper.KEY_CARS_MODEL);
                int colorColumn = cursor.getColumnIndex(CarWashingDatabaseHelper.KEY_CARS_COLOR);
                int timeColumn = cursor.getColumnIndex(CarWashingDatabaseHelper.KEY_CARS_TIME);
                int boxColumn = cursor.getColumnIndex(CarWashingDatabaseHelper.KEY_CARS_BOX);

                String model_color = cursor.getString(modelColumn) + "\n" + cursor.getString(colorColumn);
                String time = getTimeFromInt(cursor.getInt(timeColumn));
                String box = getString(R.string.txt_box_number) + Integer.toString(cursor.getInt(boxColumn) + 1);

                tv_model_color.setText(model_color);
                tv_time.setText(time);
                tv_box.setText(box);
            }
        });
    }

    public static final String EXTRA_ID ="ITEM_ID";

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this, AddActivity.class);
        i.putExtra(EXTRA_ID, parent.getItemIdAtPosition(position));
        startActivityForResult(i, REQUEST_ADD);
    }
}
