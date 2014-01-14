package ru.marsermd.IpsumLotis;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: misch_000
 * Date: 14.01.14
 * Time: 15:48
 * To change this template use File | Settings | File Templates.
 */
public class MainScreenActivity extends Activity{

    TextView name;
    public static DatabaseHelper db;

    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        name = (TextView)findViewById(R.id.name);
        name.setText(CarWashModel.getName());

        initDatabase();
        addButton = (Button)findViewById(R.id.adding_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddCarActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getBaseContext().startActivity(intent);
            }
        });

    }
    private void initDatabase() {
        db = new DatabaseHelper(getBaseContext());
        for(int time = 0; time < 48; time++) {
            if (!CarWashModel.isWorkingTime(time))
                continue;
            for (int box = 0; box < CarWashModel.getBoxCount(); box++) {
                CarModel tmp = new CarModel();
                tmp.setAssignedBox(box);
                tmp.setTime(time);
                db.addCar(tmp);
            }

        }
    }
}
