package ru.ifmo.washer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by asus on 14.01.14.
 */
public class AddCarActivity extends Activity {

    Button readyButton;
    EditText name, color, carNumber, phoneNumber;
    TextView time;
    CarsDatabase db;
    Car carItem;
    ArrayList<Car> cars;
    AlertDialog.Builder ad;
    int boxes = 9999;
    int[] nextFreeBox = new int[9999];
    int selectedBox;

    InfoDatabase dbInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_car);

        db = new CarsDatabase(this);
        db.open();
        dbInfo = new InfoDatabase(this);
        dbInfo.open();

        readyButton = (Button) findViewById(R.id.ReadyButton);
        name = (EditText) findViewById(R.id.editMarkText);
        color = (EditText) findViewById(R.id.editColorText);
        carNumber = (EditText) findViewById(R.id.editNumberText);
        phoneNumber = (EditText) findViewById(R.id.editPhoneText);
        time = (TextView) findViewById(R.id.editTimeText);

        cars = db.getAllItems();
        try {
            boxes = Integer.parseInt(dbInfo.getAllItems().get(0).param[Info.BOXES]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        readyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().isEmpty() || name.getText().toString().isEmpty()) return;
                carItem = new Car();
                carItem.param[Car.NAME] = name.getText().toString();
                carItem.param[Car.COLOR] = color.getText().toString();
                carItem.param[Car.NUMBER] = carNumber.getText().toString();
                carItem.param[Car.PHONE] = phoneNumber.getText().toString();
                carItem.param[Car.BOX] = ""+Car.fromTime(selectedBox + "");
                carItem.param[Car.TIME] = ""+Car.fromTime(time.getText().toString());
                db.addItem(carItem);
                ArrayList<Car> a = db.getAllItems();
                setResult(RESULT_OK);
                finish();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(0);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        dbInfo.close();
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < MainActivity.TOTAL_TIME_SPACES; i++) {
            int j;
            for (j = 0; j < cars.size() && !Car.getTime(i).equals(cars.get(j).getTime()); j++);
            if (j == cars.size()){
                nextFreeBox[temp.size()] = 0;
                temp.add(Car.getTime(i));
                continue;
            }

            int k = j;
            for (; j < cars.size() && (Car.getTime(i).equals(cars.get(j).getTime())); j++);

            if (j - k < boxes){
                nextFreeBox[temp.size()] = j - k + 1;
                temp.add(Car.getTime(i));
                continue;
            }
        }

        final String[] freeTimes = new String[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            freeTimes[i] = temp.get(i);
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose time");

        builder.setItems(freeTimes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                // TODO Auto-generated method stub
                time.setText(freeTimes[item]);
                selectedBox = nextFreeBox[item];
            }
        });
        builder.setCancelable(true);
        return builder.create();

    }
}

