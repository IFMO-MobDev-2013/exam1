package com.example.CarCleaning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class AddActivity extends Activity {

    int time = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.time, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
                time = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });
    }

    private int getBox(int time) {
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        List<Car> cars = databaseHandler.getCars();
        boolean[] b = new boolean[databaseHandler.getBoxNum() + 1];
        for (int i = 0; i < b.length; i++) {
            b[i] = false;
        }
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).time == time) {
                b[cars.get(i).box] = true;
            }
        }
        for (int i = 1; i < b.length; i++) {
            if (!b[i]) {
                return i;
            }
        }
        return 0;
    }

    public void onAdd(View view) {
        EditText name = (EditText) findViewById(R.id.editText);
        EditText color = (EditText) findViewById(R.id.editText1);
        EditText number = (EditText) findViewById(R.id.editText2);
        EditText telephone = (EditText) findViewById(R.id.editText3);
        Car car = new Car(name.getText().toString(), color.getText().toString(), number.getText().toString(), telephone.getText().toString(), time, getBox(time));
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        databaseHandler.addCar(car);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

}
