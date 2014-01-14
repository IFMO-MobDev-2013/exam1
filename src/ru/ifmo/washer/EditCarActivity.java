package ru.ifmo.washer;

/**
 * Created by asus on 13.01.14.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by asus on 27.12.13.
 */
public class EditCarActivity extends Activity {

    Button readyButton, deleteButton;
    EditText name, alt;
    int id, type;
    CarsDatabase db;
    Car item;
    AlertDialog.Builder ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_car);

        db = new CarsDatabase(this);
        db.open();

        readyButton = (Button) findViewById(R.id.ReadyButton);


        id = getIntent().getExtras().getInt("id");
        item = db.getItem(id);
        name.setText(item.param[Car.NAME]);
        alt.setText(item.param[Car.COLOR]);

        readyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().isEmpty() || alt.getText().toString().isEmpty()) return;
                item.param[Car.NAME] = name.getText().toString();
                item.param[Car.COLOR] = alt.getText().toString();
                db.updateItem(item);
                setResult(RESULT_OK);
                finish();
            }
        });
        /*deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(AddCarActivity.this)
                        .setTitle("Confirm")
                        .setMessage("Delete " + carItem.param[Car.NAME] + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                db.deleteItem(carItem);
                                setResult(RESULT_OK);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {

                            }
                        })
                        .setCancelable(true)
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            public void onCancel(DialogInterface dialog) {

                            }
                        }).show();
            }
        }); */


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

}
