package com.example.exam1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: satori
 * Date: 1/14/14
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class EditEntry extends Activity {
    private Spinner timeEdit;
    private EditText brand_color_edit;
    private EditText box_edit;
    private EditText car_number_edit;
    private EditText phone_number_edit;
    private Button submit_button;
    private String brand_color;
    private int time;
    private int box;
    private String car_number;
    private String phone_number;
    private ScheduleDataSource dataSource;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_entry);
        brand_color_edit = (EditText)findViewById(R.id.edit_brand_color);
        box_edit = (EditText)findViewById(R.id.edit_box);
        car_number_edit = (EditText)findViewById(R.id.edit_car_number);
        phone_number_edit = (EditText)findViewById(R.id.edit_phone_number);
        timeEdit   = (Spinner) findViewById(R.id.edit_time);
        submit_button = (Button) findViewById(R.id.submit);
        dataSource = new ScheduleDataSource(this);
        dataSource.open();


        ArrayAdapter<CharSequence> adapter1 = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        List<MyTime> times = dataSource.getAvailableEntries();



        ArrayAdapter<MyTime> adapter = new ArrayAdapter<MyTime>(this, android.R.layout.simple_spinner_item, times);
        timeEdit.setAdapter(adapter);
        timeEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //To change body of implemented methods use File | Settings | File Templates.
                MyTime t = (MyTime) parent.getItemAtPosition(position);
                time = t.time;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brand_color = brand_color_edit.getText().toString();
                box = Integer.parseInt(box_edit.getText().toString());
                car_number = car_number_edit.getText().toString();
                phone_number = phone_number_edit.getText().toString();
                dataSource.createEntry(brand_color, time, box, car_number, phone_number);
                Intent returnIntent = new Intent();
                //returnIntent.putExtra("result",result);
                setResult(RESULT_OK,returnIntent);
                dataSource.close();
                finish();

            }
        });






    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        parent.getItemAtPosition(pos);
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


}