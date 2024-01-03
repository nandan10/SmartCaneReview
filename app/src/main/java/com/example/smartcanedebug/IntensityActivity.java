package com.example.smartcanedebug;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;


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


    private Button beginnerProfileButton;

    private Button regularProfileButton;


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
        this.bleController = BLEController.getInstance(this);
        this.common = Common.getInstance();

        initButtons();
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation3);

        // Set Home selected
       // bottomNavigationView.setSelectedItemId(R.id.home1);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {
                    case R.id.settings3:
                        // startActivity(new Intent(getApplicationContext(),SearchPOI.class));
                        overridePendingTransition(0, 0);
                        Toast.makeText(getApplicationContext(), "You Clicked Settings", Toast.LENGTH_LONG).show();
                        Intent intentProfiles = new Intent(getBaseContext(), SettingsActivity.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
                        startActivity(intentProfiles);
                        return true;
                    case R.id.home1:
                        overridePendingTransition(0, 0);
                        Toast.makeText(getApplicationContext(), "You Clicked Home", Toast.LENGTH_LONG).show();
                        Intent intentMainActivity = new Intent(getBaseContext(), MainActivity.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
                        startActivity(intentMainActivity);
                        return true;
                    case R.id.call:
                        overridePendingTransition(0, 0);
                        Toast.makeText(getApplicationContext(), "You Clicked Emergency Call", Toast.LENGTH_LONG).show();
                        Intent intentEmergencyCall = new Intent(getBaseContext(), EmergencyCall.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
                        startActivity(intentEmergencyCall);
                        return true;
                }
                return false;
            }
        });
    }

    public void count(View view) {
        mCounter0++;
       txv1.setText(Integer.toString(mCounter0));
        Toast.makeText(getApplicationContext(),Integer.toString(mCounter0), Toast.LENGTH_LONG).show();
        //bleController.writeBLEData(bleController.vibrationLevel, new byte[]{(byte) 0});
        bleController.writeBLEData(bleController.vibration, new byte[]{(byte) mCounter0});
    }
    public void count1(View view) {
        mCounter0--;
        txv1.setText(Integer.toString(mCounter0));
        Toast.makeText(getApplicationContext(),Integer.toString(mCounter0), Toast.LENGTH_LONG).show();
        bleController.writeBLEData(bleController.vibration,  new byte[]{(byte) mCounter0});
       // bleController.writeBLEData(bleController.vibrationLevel,  new byte[]{(byte) mCounter0});
    }
    public void count3(View view) {
        mCounter1++;
        txv2.setText(Integer.toString(mCounter1));
        Toast.makeText(getApplicationContext(),Integer.toString(mCounter1), Toast.LENGTH_LONG).show();
        bleController.writeBLEData(bleController.buzzer,  new byte[]{(byte) mCounter1});
    }
    public void count4(View view) {
        mCounter1--;
        txv2.setText(Integer.toString(mCounter1));
        Toast.makeText(getApplicationContext(), Integer.toString(mCounter1), Toast.LENGTH_LONG).show();
        bleController.writeBLEData(bleController.buzzer,  new byte[]{(byte) mCounter1});
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
  private void initButtons() {
      this.beginnerProfileButton = findViewById(R.id.btnProfileBeginner);
      this.beginnerProfileButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              bleController.writeBLEData(bleController.operatingMode, new byte[]{(byte) 0});
             // bleController.writeBLEData(bleController.misc,new byte[]{(byte) 0});
          }
      });


      this.regularProfileButton = findViewById(R.id.btnProfileRegular);
      this.regularProfileButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
             bleController.writeBLEData(bleController.operatingMode,new byte[]{(byte) 1});
             // bleController.writeBLEData(bleController.vibrationLevel, new byte[]{(byte) 1});
          }
      });}
      public void goBackPressed (View view){
          onBackPressed();
      }

  }
