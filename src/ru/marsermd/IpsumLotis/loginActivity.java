package ru.marsermd.IpsumLotis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class loginActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    public static final String APP_SETTINGS = "settings";
    public static final String APP_SETTINGS_BOX_COUNT = "count";
    public static final String APP_SETTINGS_NAME = "name";

    SharedPreferences mSettings;

    EditText name, count;
    Button submit;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettings = getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
        setContentView(R.layout.login);
        if (mSettings.contains(APP_SETTINGS_NAME)) {
            login();
        }

        count = (EditText)findViewById(R.id.box_count);
        name = (EditText)findViewById(R.id.car_wash_name);

        submit = (Button)findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putInt(APP_SETTINGS_BOX_COUNT, Integer.parseInt(count.getText().toString()));
                editor.putString(APP_SETTINGS_NAME, name.getText().toString());
                editor.apply();
                login();
            }
        });

    }

    private void login() {
        CarWashModel.setBoxCount(mSettings.getInt(APP_SETTINGS_BOX_COUNT, 0));
        CarWashModel.setName(mSettings.getString(APP_SETTINGS_NAME, "mr. Vasily"));
        Intent intent = new Intent(getBaseContext(), MainScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getBaseContext().startActivity(intent);
    }

}
