package com.example.WashYourCars;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class MakingOrder extends Activity {

    boolean newOrder;
    boolean editable;
    Order order;
    EditText modelEditText;
    EditText colorEditText;
    EditText phoneEditText;
    EditText signEditText;
    TextView boxTextView;
    Spinner spinner;
    int uses[];
    int curTime;
    int ourBoxes;
    ArrayList<CharSequence> array;
    ArrayAdapter<CharSequence> adapter;


    View.OnClickListener okClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            order.model = modelEditText.getText().toString();
            order.color = colorEditText.getText().toString();
            order.phone = phoneEditText.getText().toString();
            order.sign = signEditText.getText().toString();
            order.time = curTime + "";
            getIntent().putExtra(Order.TIME, order.time);
            getIntent().putExtra(Order.MODEL, order.model);
            getIntent().putExtra(Order.COLOR, order.color);
            getIntent().putExtra(Order.PHONE, order.phone);
            getIntent().putExtra(Order.SIGN, order.sign);
            getIntent().putExtra(Order.HAVENEWORDER, true);

            ContentValues contentValues = order.getContentValues();
            MyDataBaseCarsHelper myDataBaseCarsHelper = new MyDataBaseCarsHelper(getApplicationContext());
            SQLiteDatabase sqLiteDatabase = myDataBaseCarsHelper.getWritableDatabase();
            sqLiteDatabase.insert(MyDataBaseCarsHelper.DATABASE_NAME, null, contentValues);

            sqLiteDatabase.close();
            myDataBaseCarsHelper.close();

            finish();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makeorder);

        newOrder = getIntent().getBooleanExtra(Order.NEWORDER, true);
        editable = getIntent().getBooleanExtra(Order.EDITABLE, true);
        ourBoxes = getIntent().getIntExtra(Order.OURBOXES, 3);

        if (newOrder)
            order = new Order();
        else
            order = new Order(getIntent());

        modelEditText = (EditText) findViewById(R.id.editTextMakeOrderModel);
        colorEditText = (EditText) findViewById(R.id.editTextMakeOrderColor);
        phoneEditText = (EditText) findViewById(R.id.editTextMakeOrderPhone);
        signEditText = (EditText) findViewById(R.id.editTextMakeOrderSign);
        boxTextView = (TextView) findViewById(R.id.textViewMakeOrderBox);
        spinner = (Spinner) findViewById(R.id.spinnerMakeOrder);
        uses = getIntent().getIntArrayExtra(Order.USESTABLE);

        modelEditText.setText(order.model);
        colorEditText.setText(order.color);
        phoneEditText.setText(order.phone);
        signEditText.setText(order.sign);

        array = new ArrayList<CharSequence>();
        if (newOrder) {
            for (int i = 0; i < 28; i++)
                if (uses[i] < ourBoxes)
                    array.add(Order.makeTime(i));
        } else
            array.add(Order.makeTime(order.time));

        adapter = new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_spinner_item, array);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                curTime = Order.makeIntFromTime(adapter.getItem(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        modelEditText.setEnabled(editable);
        colorEditText.setEnabled(editable);
        phoneEditText.setEnabled(editable);
        signEditText.setEnabled(editable);
        spinner.setEnabled(editable);

        Button button = (Button) findViewById(R.id.buttonMakeOrderOk);
        button.setOnClickListener(okClick);
    }

}
