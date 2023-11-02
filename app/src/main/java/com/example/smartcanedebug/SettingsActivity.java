package com.example.smartcanedebug;

import android.content.Intent;
import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    Button settings1,settings2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        initButtons();

    }
    private void initButtons() {
        this.settings1 = findViewById(R.id.settings1);
        this.settings1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfile = new Intent(getBaseContext(), ProfilesActivity.class);
               // intentProfileCustomize.putExtra("CustomProfileNumber", 1);
                startActivity(intentProfile);
            }
        });

        this.settings2 = findViewById(R.id.settings2);
        this.settings2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentIntensity = new Intent(getBaseContext(), IntensityActivity.class);
              //  intentProfileCustomize.putExtra("CustomProfileNumber", 2);
                startActivity(intentIntensity);
            }
        });
    }


  public void goBackPressed(View view) {
      onBackPressed();
  }
}