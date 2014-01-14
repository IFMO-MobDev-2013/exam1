package com.example.exam1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import com.example.exam1.databases.CarsSchedule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dronov
 * Date: 14.01.14
 * Time: 16:29
 * To change this template use File | Settings | File Templates.
 */
public class AddCarActivity extends Activity {
    private EditText editText1, editText2;
    private Cars car = new Cars();
    private CarsSchedule database;
    private Spinner spinner;
    private Button button;
    int firstNotBusyWindows = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_car_activity);

        editText1 = (EditText) findViewById(R.id.editTextAdd1);
        editText2 = (EditText) findViewById(R.id.editTextAdd2);
        button = (Button) findViewById(R.id.button);


        spinner = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        for (int i = 8; i < 22; i++) {
            String value = null;
            if (i < 10)
                value = "0" + String.valueOf(i) + ":00";
            else
                value = String.valueOf(i) + ":00";
            String halfValue = null;
            if (i < 10)
                halfValue = "0" + String.valueOf(i) + ":30";
            else
                halfValue = String.valueOf(i) + ":30";
            boolean ok = false;
            for (int j = 1; j <= MyActivity.windowsNumber; j++)
                if (free(String.valueOf(j), value))
                    ok = true;
            if (ok)
                list.add(value);

            for (int j = 1; j <= MyActivity.windowsNumber; j++)
                if (free(String.valueOf(j), halfValue))
                    ok = true;
            if (ok)
                list.add(halfValue);
        }
        if (list.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Извините, всё время занято!",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            button.setEnabled(false);
            toast.show();
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String current = String.valueOf(spinner.getSelectedItem());
                for (int j = 1; j <= MyActivity.windowsNumber; j++)
                    if (free(String.valueOf(j), current)) {
                        car.setWindow(String.valueOf(j));
                        break;
                    }

                car.setTime(String.valueOf(spinner.getSelectedItem()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                car.setCarMark(editText1.getText().toString());
                car.setColor(editText2.getText().toString());
                if (!car.getWindow().equals("") &&
                    !car.getTime().equals("") &&
                    !car.getCarMark().equals("") &&
                    !car.getColor().equals("")) {

                    button.setEnabled(true);
                    database = new CarsSchedule(view.getContext());
                    database.open();
                    database.addChannel(car);
                    database.close();
                    Intent intent = new Intent(view.getContext(), CarwsList.class);
                    intent.putExtra("name", MyActivity.NAME);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean free(String s, String time) {
        for (Cars car : CarwsList.carses) {
            if (car.getWindow().equals(s) && car.getTime().equals(time))
                return false;
        }
        return true;
    }

}
