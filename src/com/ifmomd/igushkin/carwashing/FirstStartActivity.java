package com.ifmomd.igushkin.carwashing;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Sergey on 1/14/14.
 */
public class FirstStartActivity extends Activity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener, TextWatcher {
    TextView tv_boxes;
    SeekBar  sb_boxes;
    Button   btn_done;
    EditText edt_company;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_first_start);

        findViewsById();
        sb_boxes.setOnSeekBarChangeListener(this);
        btn_done.setOnClickListener(this);
        edt_company.addTextChangedListener(this);
    }

    private void findViewsById() {
        sb_boxes = (SeekBar) findViewById(R.id.skb_boxes);
        tv_boxes = (TextView) findViewById(R.id.tv_boxes);
        btn_done = (Button) findViewById(R.id.btn_done);
        edt_company = (EditText) findViewById(R.id.edt_company);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == sb_boxes)
        {
            tv_boxes.setText(Integer.toString(progress+1));
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btn_done)
        {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            prefs.edit().putBoolean(MainListActivity.prfFirstStart, false)
                        .putInt(MainListActivity.prfBoxesCount, sb_boxes.getProgress()+1)
                        .putString(MainListActivity.prfCompanyName, edt_company.getText().toString())
                        .commit();
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        btn_done.setEnabled(s.length()>0);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}