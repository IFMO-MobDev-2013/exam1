package com.blumonk.Carwash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by blumonk on 1/14/14.
 */
public class LookActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.look);
        TextView modelView = (TextView) findViewById(R.id.lookmodel);
        TextView colourView = (TextView) findViewById(R.id.lookcolour);
        TextView numberView = (TextView) findViewById(R.id.looknumber);
        TextView phoneView = (TextView) findViewById(R.id.lookphone);
        TextView boxView = (TextView) findViewById(R.id.lookbox);
        TextView timeView = (TextView) findViewById(R.id.looktime);
        DbAdapter dbAdapter = new DbAdapter(this);
        dbAdapter.open();
        Intent intent = getIntent();
        modelView.setText("Model: " + intent.getStringExtra("model"));
        colourView.setText("Colour: " + intent.getStringExtra("colour"));
        numberView.setText("Number: " + intent.getStringExtra("number"));
        phoneView.setText("Phone: " + intent.getStringExtra("phone"));
        boxView.setText("Box: " + Integer.toString(intent.getIntExtra("box", 1)));
        timeView.setText("Time: " + intent.getStringExtra("time"));
    }
}
