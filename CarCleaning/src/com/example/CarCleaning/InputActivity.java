package com.example.CarCleaning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class InputActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input);
    }

    public void onSubmit(View view) {
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        EditText name = (EditText) findViewById(R.id.editName);
        EditText num = (EditText) findViewById(R.id.editNumber);
        databaseHandler.setCC(name.getText().toString(), Integer.parseInt(num.getText().toString()));
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
