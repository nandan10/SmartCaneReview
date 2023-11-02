package com.example.smartcanedebug;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.andremion.counterfab.CounterFab;


public class IntensityActivity extends AppCompatActivity{
    private BLEController bleController;
    private Common common;

    private Button VibrationIntensity;
    private Button BuzzerIntensity;
    //CounterFab fabOne1, fabTwo1, fabOne2, fabTwo2;
    private int mCounter0 = 5;
    private int mCounter1 = 5;
    Button btn1,btn2,btn3,btn4;
    TextView txv1,txv2;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.bleController = BLEController.getInstance(this);
        this.common = Common.getInstance();
        setContentView(R.layout.intensity_activity);
       // initButtons();
        btn1 = (Button) findViewById(R.id.bt1);
        btn2 = (Button) findViewById(R.id.bt2);
        btn3 = (Button) findViewById(R.id.bt3);
        btn4 = (Button) findViewById(R.id.bt4);
        txv1 = (TextView) findViewById(R.id.tx1);
        txv2 = (TextView) findViewById(R.id.tx2);

    }

    public void count(View view) {
        mCounter0++;
        txv1.setText(Integer.toString(mCounter0));
       // bleController.writeBLEData(bleController.vibrationChar, common.vibration);
    }
    public void count1(View view) {
        mCounter0--;
        txv1.setText(Integer.toString(mCounter0));
       // bleController.writeBLEData(bleController.vibrationChar, common.vibration);
    }
    public void count3(View view) {
        mCounter1++;
        txv2.setText(Integer.toString(mCounter1));
        //bleController.writeBLEData(bleController.buzzerChar, common.buzzer);
    }
    public void count4(View view) {
        mCounter1--;
        txv2.setText(Integer.toString(mCounter1));
        //bleController.writeBLEData(bleController.buzzerChar, common.buzzer);
    }


//    @Override
//    public void onBackPressed() {
//        Intent intentMain = new Intent(this, MainActivity.class);
//        startActivity(intentMain);
//        finish();
//    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)  {
//        if (keyCode == KeyEvent.KEYCODE_BACK ) {
//            // do something on back.
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }

  /*  private void initButtons() {
        this.VibrationIntensity = findViewById(R.id.vibration);
        this.VibrationIntensity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bleController.writeBLEData(bleController.vibrationChar, common.vibration);
            }
        });

        this.BuzzerIntensity = findViewById(R.id.buzzer);
        this.BuzzerIntensity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bleController.writeBLEData(bleController.buzzerChar, common.buzzer);
            }
        });


    }*/
  public void goBackPressed(View view) {
      onBackPressed();
  }
}

