package com.example.exam1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateWashing extends Activity {
    /**
     * Called when the activity is first created.
     *
     */
    public static final String MY_PREFS = "MyPrefsFile";

    private EditText company;
    private EditText boxes;
    private Button add_company;
    private MySQLiteHelper myDBHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getSharedPreferences(MY_PREFS, 0);
        boolean existCompany = settings.getBoolean("isEntered", false);




        if (existCompany) {
            goToSchedule();
        }
        setContentView(R.layout.main);
        company = (EditText)findViewById(R.id.name);
        boxes = (EditText)findViewById(R.id.boxes);
        add_company = (Button)findViewById(R.id.add_company);
        add_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String washing = company.getText().toString();
                int box_number = Integer.parseInt(boxes.getText().toString());
                SharedPreferences settings = getSharedPreferences(MY_PREFS, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("isEntered", true);
                editor.putInt("Boxes", box_number);
                // Commit the edits!
                editor.commit();
                goToSchedule();

            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context

    }
    private void goToSchedule() {
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
        finish();
    }

}
