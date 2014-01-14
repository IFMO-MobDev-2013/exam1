package com.blumonk.Carwash;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by blumonk on 1/14/14.
 */
public class EditOrderActivity extends Activity {

    private EditText modelField;
    private EditText colourField;
    private EditText numberField;
    private EditText phoneField;
    private Spinner box;
    private Spinner time;
    private int maxBoxes = 3;
    private int id;
    private String action = "";
    private DbAdapter dbAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        modelField = (EditText) findViewById(R.id.editmodel);
        colourField = (EditText) findViewById(R.id.editcolour);
        numberField = (EditText) findViewById(R.id.editcarnumber);
        phoneField = (EditText) findViewById(R.id.editphone);
        box = (Spinner) findViewById(R.id.spinnerbox);
        time = (Spinner) findViewById(R.id.spinnertime);
        dbAdapter = new DbAdapter(this);

        Intent intent = getIntent();
        action = intent.getStringExtra("action");

        ArrayAdapter <CharSequence> boxAdapter =
                new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
        boxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (int i = 1; i <= maxBoxes; ++i) {
            boxAdapter.add("Box number " + i);
        }
        box.setAdapter(boxAdapter);

        ArrayAdapter <CharSequence> timeAdapter =
                new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (int i = 8; i <= 21; ++i) {
            timeAdapter.add(i + ":00");
            timeAdapter.add(i + ":30");
        }
        timeAdapter.add("22:00");
        time.setAdapter(timeAdapter);

        maxBoxes = intent.getIntExtra("maxboxes", 3);
        if ("edit".equals(action)) {
            String sentModel = intent.getStringExtra("model");
            String sentColour = intent.getStringExtra("colour");
            id = intent.getIntExtra("id", 0);
            modelField.setText(sentModel);
            colourField.setText(sentColour);
        }

    }

    public void addOrder(View view) {
        if (modelField.getText().length() == 0 ||
                colourField.getText().length() == 0 ||
                numberField.getText().length() == 0 ||
                phoneField.getText().length() == 0) {
            Toast t = Toast.makeText(getApplicationContext(), "Fill all the fields, please", Toast.LENGTH_SHORT);
            t.show();
        } else {
            dbAdapter.open();

            String model = modelField.getText().toString();
            String colour = colourField.getText().toString();
            String number = numberField.getText().toString();
            String phone = phoneField.getText().toString();
            int pos = time.getSelectedItemPosition();
            int selectedTime = pos * 30 + 8 * 60;
            String minutes = (selectedTime % 60 == 0 ? "00" : "30");
            if (boxIsFree()) {
                if (action.equals("edit")) {
                    dbAdapter.deleteOrder(id);
                }
                dbAdapter.addOrder(box.getSelectedItemPosition() + 1, model, colour, number,
                        phone, selectedTime / 60 + ":" + minutes);
                dbAdapter.close();
                finish();
            } else {
                Toast t = Toast.makeText(getApplicationContext(), "Sorry, but the box is busy at this time", Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    public boolean boxIsFree() {
        int minutes = time.getSelectedItemPosition() * 30 + 8 * 60;
        int boxId = box.getSelectedItemPosition() + 1;
        String selectedTime = Integer.toString(minutes / 60) + ":" + (minutes % 60 == 0 ? "00" : "30");
        dbAdapter.open();
        Cursor cursor = dbAdapter.getAllOrders();
        if (cursor.moveToFirst()) {
            do {
                int currBox = cursor.getInt(cursor.getColumnIndexOrThrow("box"));
                String currTime = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                if (currBox == boxId && selectedTime.equals(currTime)) {
                    return false;
                }
            } while (cursor.moveToNext());
        }
        return true;
    }

}