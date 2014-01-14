package ru.marsermd.IpsumLotis;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created with IntelliJ IDEA.
 * User: misch_000
 * Date: 14.01.14
 * Time: 17:09
 * To change this template use File | Settings | File Templates.
 */
public class AddCarActivity extends Activity{

    Spinner washTime;
    EditText model, color, number, phoneNumber;
    Button carAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_car);

        Integer[] tmpTime = MainScreenActivity.db.getFreeTime();
        String[] freeTime = new String[tmpTime.length];
        for (int i = 0; i < freeTime.length; i++) {
            freeTime[i] = CarWashModel.getFormatedTime(tmpTime[i]);
        }

        washTime = (Spinner) findViewById(R.id.wash_time_edit);
        washTime.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, freeTime));

        model = (EditText)findViewById(R.id.car_model_edit);
        color = (EditText)findViewById(R.id.car_color_edit);
        number = (EditText)findViewById(R.id.car_number_edit);
        phoneNumber = (EditText)findViewById(R.id.phone_number_edit);

        carAdd = (Button)findViewById(R.id.add_car_edit);
        carAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CarModel car = new CarModel();
                car.setModel(model.getText().toString());
                car.setColor(color.getText().toString());
                car.setNumber(number.getText().toString());
                car.setPhone(phoneNumber.getText().toString());
                int time = CarWashModel.deformatTime(washTime.getSelectedItem().toString());
                car.setTime(time);
                car.setAssignedBox(MainScreenActivity.db.getBoxForTime(time));
                MainScreenActivity.db.updateCar(car);
            }
        });

    }
}
