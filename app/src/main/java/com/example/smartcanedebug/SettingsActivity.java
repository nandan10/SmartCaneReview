package com.example.smartcanedebug;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    Button settings1,settings2,settings3;
    private static final int REQUEST_ENABLE_BT = 11;
    public static BluetoothAdapter BA;
    private BLEController bleController;
    private Common common;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        initButtons();
        this.bleController = BLEController.getInstance(this);
        this.common = Common.getInstance();

    }
    private void initButtons() {
        this.settings1 = findViewById(R.id.settings1);
        this.settings1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentIntensity = new Intent(getBaseContext(), IntensityActivity.class);
               // intentProfileCustomize.putExtra("CustomProfileNumber", 1);
                startActivity(intentIntensity);
            }
        });

        this.settings2 = findViewById(R.id.settings2);
        this.settings2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEmergencySettings = new Intent(getBaseContext(), EmergencyMainActivity.class);
              //  intentProfileCustomize.putExtra("CustomProfileNumber", 2);
                startActivity(intentEmergencySettings);
            }
        });
        this.settings3 = findViewById(R.id.settings3);
        this.settings3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("initButtons", "onClick: Disconnecting...");
                Toast.makeText(getApplicationContext(), "You Clicked Disconnect", Toast.LENGTH_LONG).show();
                //bleController.disconnect();
            }
        });
    }


  public void goBackPressed(View view) {
      onBackPressed();
  }
}