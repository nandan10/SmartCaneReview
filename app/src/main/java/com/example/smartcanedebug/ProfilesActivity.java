package com.example.smartcanedebug;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class ProfilesActivity extends AppCompatActivity{
    private BLEController bleController;
    private Common common;

    private Button beginnerProfileButton;
    private Button beginnerNavigationProfileButton;
    private Button regularProfileButton;
    private Button regularNavigationProfileButton;
    private Button customProfile1Button;
    private Button customProfile2Button;
    private Button customizeProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.bleController = BLEController.getInstance(this);
        this.common = Common.getInstance();
        setContentView(R.layout.activity_profiles);
        initButtons();
    }
    public void goBackPressed(View view) {
        onBackPressed();
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

    private void initButtons() {
        this.beginnerProfileButton = findViewById(R.id.btnProfileBeginner);
        this.beginnerProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bleController.writeBLEData(bleController.profileChar, common.ACTIVATE_PROFILE_BEGINNER);
            }
        });

        this.beginnerNavigationProfileButton = findViewById(R.id.btnProfileBeginnerNavi);
        this.beginnerNavigationProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bleController.writeBLEData(bleController.profileChar, common.ACTIVATE_PROFILE_BEGINNER_NAVI);
            }
        });

        this.regularProfileButton = findViewById(R.id.btnProfileRegular);
        this.regularProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bleController.writeBLEData(bleController.profileChar, common.ACTIVATE_PROFILE_REGULAR);
            }
        });

        this.regularNavigationProfileButton = findViewById(R.id.btnProfileRegularNavi);
        this.regularNavigationProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bleController.writeBLEData(bleController.profileChar, common.ACTIVATE_PROFILE_REGULAR_NAVI);
            }
        });

        this.customProfile1Button = findViewById(R.id.btnProfileCustom1);
        this.customProfile1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bleController.writeBLEData(bleController.profileChar, common.ACTIVATE_PROFILE_CUSTOM1);
            }
        });

        this.customProfile2Button = findViewById(R.id.btnProfileCustom2);
        this.customProfile2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bleController.writeBLEData(bleController.profileChar, common.ACTIVATE_PROFILE_CUSTOM2);
            }
        });

        this.customizeProfileButton = findViewById(R.id.btnProfileCustomize);
        this.customizeProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfileToCustomize = new Intent(getBaseContext(), ProfileToCustomizeActivity.class);
//                intentProfileToCustomize.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
                startActivity(intentProfileToCustomize);
            }
        });
    }

}
