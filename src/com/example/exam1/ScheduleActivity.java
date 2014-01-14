package com.example.exam1;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: satori
 * Date: 1/14/14
 * Time: 3:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleActivity extends ListActivity {
    private List<Entry> entries;
    private ScheduleDataSource dataSource;
    private ArrayAdapter<Entry> entryArrayAdapter;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new ScheduleDataSource(this);
        dataSource.open();
        entries = dataSource.getAllEntries();
        entryArrayAdapter = new ArrayAdapter<Entry>(this, R.layout.entry, entries);
        setListAdapter(entryArrayAdapter);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_entry:
                addEntry();
                return true;
//            case R.id.help:
//                showHelp();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addEntry() {
        Intent intent = new Intent(this, EditEntry.class);
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == RESULT_OK){
                this.entries = dataSource.getAllEntries();
                entryArrayAdapter = new ArrayAdapter<Entry>(this, R.layout.entry, entries);
                setListAdapter(entryArrayAdapter);

            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
        Log.d("Travel", "You choosed!");
        Entry e = (Entry)this.getListAdapter().getItem(position);
        Intent intent1 = new Intent(this, ShowAcitivity.class);
        intent1.putExtra("brand", e.brand_color);
        intent1.putExtra("time", e.time);
        intent1.putExtra("box", e.box);
        intent1.putExtra("number", e.car_number);
        intent1.putExtra("phone", e.phone_number);
        startActivity(intent1);

    }
}