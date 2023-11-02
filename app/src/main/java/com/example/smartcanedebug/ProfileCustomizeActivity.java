package com.example.smartcanedebug;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileCustomizeActivity extends AppCompatActivity{
    private BLEController bleController;
    private Common common;
    private Button saveProfileSettingsButton;

    private Spinner distanceOutputTypeSpinner;
    private Spinner distanceOutputPatternSpinner;
    private Spinner distanceZone1PatternSpinner;
    private Spinner distanceZone2PatternSpinner;
    private Spinner distanceZone3PatternSpinner;
    private Spinner distanceZone4PatternSpinner;

    private Spinner naviOutputTypeSpinner;
    private Spinner naviOutputPatternSpinner;
    private Spinner naviFwdPatternSpinner;
    private Spinner naviStopPatternSpinner;
    private Spinner naviLeftPatternSpinner;
    private Spinner naviRightPatternSpinner;

    SQLiteDatabase db;

    private int currentCustomizeProfileNumber = 0;

    private byte[] tempProfileData = new byte[]{0,0,0,0,0,0,0,0,0,0,0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentCustomizeProfileNumber = getIntent().getIntExtra("CustomProfileNumber",0);
        Log.d("Profiles", "onCreate: " + "currentCustomizeProfileNumber___________________________________________________" + currentCustomizeProfileNumber);

        this.bleController = BLEController.getInstance(this);
        this.common = Common.getInstance();
        setContentView(R.layout.activity_profile_customize);
        db = openOrCreateDatabase("NavMobDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Profiles (ProfileNumber INTEGER PRIMARY KEY, DistType INT, DistPattern INT, DistZone1Pattern INT, DistZone2Pattern INT, DistZone3Pattern INT, DistZone4Pattern INT, NavType INT, NavPattern INT, NavFwdPattern INT, NavStopPattern INT, NavLeftPattern INT, NavRightPattern INT);");
        Cursor c_profiles = db.rawQuery("SELECT * FROM Profiles;", null);
        while (c_profiles.moveToNext()) {
            Log.i("TAG", "onClick: c_profiles:" + Integer.parseInt(c_profiles.getString(0)));
            if (Integer.parseInt(c_profiles.getString(0)) == 1) {
                for (int i = 0; i < 12; i++) {
                    Log.i("TAG", "onClick: c_profiles_data:" + Integer.parseInt(c_profiles.getString(i+1)));
                    common.ACTIVATE_PROFILE_CUSTOM1[i] = (byte) Integer.parseInt(c_profiles.getString(i+1));
                }
            }
            else if (Integer.parseInt(c_profiles.getString(0)) == 2) {
                for (int i = 0; i < 12; i++) {
                    Log.i("TAG", "onClick: c_profiles_data:" + Integer.parseInt(c_profiles.getString(i+1)));
                    common.ACTIVATE_PROFILE_CUSTOM2[i] = (byte) Integer.parseInt(c_profiles.getString(i+1));
                }
            }
        }
//        for(int i=0; i<12; i++) {
//            Log.i("ACTIVATE_PROFILE_CUSTOM1", "initSpinners: " + String.valueOf(common.ACTIVATE_PROFILE_CUSTOM1[i]));
//        }
//        for(int i=0; i<12; i++) {
//            Log.i("ACTIVATE_PROFILE_CUSTOM2", "initSpinners: " + String.valueOf(common.ACTIVATE_PROFILE_CUSTOM2[i]));
//        }
        initButtons();
        initSpinners();
    }

//    @Override
//    public void onBackPressed() {
//        Intent intentProfileToCustomizeActivity = new Intent(this, ProfileToCustomizeActivity.class);
//        startActivity(intentProfileToCustomizeActivity);
//        finish();
//    }

    private void initButtons() {
        this.saveProfileSettingsButton = findViewById(R.id.btnSaveProfileSettings);
        this.saveProfileSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentCustomizeProfileNumber == 1) {
                    Log.i("TAG", "onClick: currentCustomizeProfileNumber:" + currentCustomizeProfileNumber);
                    common.ACTIVATE_PROFILE_CUSTOM1 = tempProfileData;
//                    bleController.writeBLEData(bleController.profileChar, common.ACTIVATE_PROFILE_CUSTOM1);
                    db.execSQL("INSERT OR REPLACE INTO Profiles VALUES(1, " + common.ACTIVATE_PROFILE_CUSTOM1[0] + ", " + common.ACTIVATE_PROFILE_CUSTOM1[1] + ", " + common.ACTIVATE_PROFILE_CUSTOM1[2] + ", " + common.ACTIVATE_PROFILE_CUSTOM1[3] + ", " + common.ACTIVATE_PROFILE_CUSTOM1[4] + ", " + common.ACTIVATE_PROFILE_CUSTOM1[5] + ", " + common.ACTIVATE_PROFILE_CUSTOM1[6] + ", " + common.ACTIVATE_PROFILE_CUSTOM1[7] + ", " + common.ACTIVATE_PROFILE_CUSTOM1[8] + ", " + common.ACTIVATE_PROFILE_CUSTOM1[9] + ", " + common.ACTIVATE_PROFILE_CUSTOM1[10] + ", " + common.ACTIVATE_PROFILE_CUSTOM1[11] + ");");
                    // add code to add name to custom profile with DB
                } else if (currentCustomizeProfileNumber == 2) {
                    Log.i("TAG", "onClick: currentCustomizeProfileNumber:" + currentCustomizeProfileNumber);
                    common.ACTIVATE_PROFILE_CUSTOM2 = tempProfileData;
//                    bleController.writeBLEData(bleController.profileChar, common.ACTIVATE_PROFILE_CUSTOM2);
                    db.execSQL("INSERT OR REPLACE INTO Profiles VALUES(2, " + common.ACTIVATE_PROFILE_CUSTOM2[0] + ", " + common.ACTIVATE_PROFILE_CUSTOM2[1] + ", " + common.ACTIVATE_PROFILE_CUSTOM2[2] + ", " + common.ACTIVATE_PROFILE_CUSTOM2[3] + ", " + common.ACTIVATE_PROFILE_CUSTOM2[4] + ", " + common.ACTIVATE_PROFILE_CUSTOM2[5] + ", " + common.ACTIVATE_PROFILE_CUSTOM2[6] + ", " + common.ACTIVATE_PROFILE_CUSTOM2[7] + ", " + common.ACTIVATE_PROFILE_CUSTOM2[8] + ", " + common.ACTIVATE_PROFILE_CUSTOM2[9] + ", " + common.ACTIVATE_PROFILE_CUSTOM2[10] + ", " + common.ACTIVATE_PROFILE_CUSTOM2[11] + ");");
                }
                Intent intentProfiles = new Intent(getBaseContext(), ProfilesActivity.class);
                startActivity(intentProfiles);
            }
        });
    }

    private void initSpinners(){
        distanceOutputTypeSpinner = findViewById(R.id.spnDistanceOutputType);
        ArrayAdapter<CharSequence> adapterDistanceOutputType = ArrayAdapter.createFromResource(this, R.array.output_type_array, android.R.layout.simple_spinner_item);
        adapterDistanceOutputType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distanceOutputTypeSpinner.setAdapter(adapterDistanceOutputType);
        distanceOutputTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempProfileData[0] = (byte) i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        distanceOutputPatternSpinner = findViewById(R.id.spnDistanceOutputPattern);
        ArrayAdapter<CharSequence> adapterDistanceOutputPattern = ArrayAdapter.createFromResource(this, R.array.output_pattern_array, android.R.layout.simple_spinner_item);
        adapterDistanceOutputPattern.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distanceOutputPatternSpinner.setAdapter(adapterDistanceOutputPattern);
        distanceOutputPatternSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempProfileData[1] = (byte) i;
                if (i == 3) {
                    distanceZone1PatternSpinner.setEnabled(true);
                    distanceZone2PatternSpinner.setEnabled(true);
                    distanceZone3PatternSpinner.setEnabled(true);
                    distanceZone4PatternSpinner.setEnabled(true);
                }
                else{
                    distanceZone1PatternSpinner.setEnabled(false);
                    distanceZone2PatternSpinner.setEnabled(false);
                    distanceZone3PatternSpinner.setEnabled(false);
                    distanceZone4PatternSpinner.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        distanceZone1PatternSpinner = findViewById(R.id.spnDistanceZone1Pattern);
        ArrayAdapter<CharSequence> adapterDistanceZone1Pattern = ArrayAdapter.createFromResource(this, R.array.distance_zone1_array, android.R.layout.simple_spinner_item);
        adapterDistanceZone1Pattern.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distanceZone1PatternSpinner.setAdapter(adapterDistanceZone1Pattern);
        distanceZone1PatternSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempProfileData[2] = (byte) i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        distanceZone2PatternSpinner = findViewById(R.id.spnDistanceZone2Pattern);
        ArrayAdapter<CharSequence> adapterDistanceZone2Pattern = ArrayAdapter.createFromResource(this, R.array.distance_zone2_array, android.R.layout.simple_spinner_item);
        adapterDistanceZone2Pattern.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distanceZone2PatternSpinner.setAdapter(adapterDistanceZone2Pattern);
        distanceZone2PatternSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempProfileData[3] = (byte) i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        distanceZone3PatternSpinner = findViewById(R.id.spnDistanceZone3Pattern);
        ArrayAdapter<CharSequence> adapterDistanceZone3Pattern = ArrayAdapter.createFromResource(this, R.array.distance_zone3_array, android.R.layout.simple_spinner_item);
        adapterDistanceZone3Pattern.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distanceZone3PatternSpinner.setAdapter(adapterDistanceZone3Pattern);
        distanceZone3PatternSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempProfileData[4] = (byte) i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        distanceZone4PatternSpinner = findViewById(R.id.spnDistanceZone4Pattern);
        ArrayAdapter<CharSequence> adapterDistanceZone4Pattern = ArrayAdapter.createFromResource(this, R.array.distance_zone4_array, android.R.layout.simple_spinner_item);
        adapterDistanceZone4Pattern.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distanceZone4PatternSpinner.setAdapter(adapterDistanceZone4Pattern);
        distanceZone4PatternSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempProfileData[5] = (byte) i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        naviOutputTypeSpinner = findViewById(R.id.spnNaviOutputType);
        ArrayAdapter<CharSequence> adaptorNaviOutputType = ArrayAdapter.createFromResource(this, R.array.output_type_array, android.R.layout.simple_spinner_item);
        adaptorNaviOutputType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        naviOutputTypeSpinner.setAdapter(adaptorNaviOutputType);
        naviOutputTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempProfileData[6] = (byte) i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        naviOutputPatternSpinner = findViewById(R.id.spnNaviOutputPattern);
        ArrayAdapter<CharSequence> adapterNaviOutputPattern = ArrayAdapter.createFromResource(this, R.array.output_pattern_array, android.R.layout.simple_spinner_item);
        adapterNaviOutputPattern.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        naviOutputPatternSpinner.setAdapter(adapterNaviOutputPattern);
        naviOutputPatternSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempProfileData[7] = (byte) i;
                if (i == 3) {
                    naviFwdPatternSpinner.setEnabled(true);
                    naviStopPatternSpinner.setEnabled(true);
                    naviLeftPatternSpinner.setEnabled(true);
                    naviRightPatternSpinner.setEnabled(true);
                }
                else{
                    naviFwdPatternSpinner.setEnabled(false);
                    naviStopPatternSpinner.setEnabled(false);
                    naviLeftPatternSpinner.setEnabled(false);
                    naviRightPatternSpinner.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        naviFwdPatternSpinner = findViewById(R.id.spnNaviFwdPattern);
        ArrayAdapter<CharSequence> adapterNaviFwdPattern = ArrayAdapter.createFromResource(this, R.array.navi_fwd_array, android.R.layout.simple_spinner_item);
        adapterNaviFwdPattern.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        naviFwdPatternSpinner.setAdapter(adapterNaviFwdPattern);
        naviFwdPatternSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempProfileData[8] = (byte) i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        naviStopPatternSpinner = findViewById(R.id.spnNaviStopPattern);
        ArrayAdapter<CharSequence> adapterNaviStopPattern = ArrayAdapter.createFromResource(this, R.array.navi_stop_array, android.R.layout.simple_spinner_item);
        adapterNaviStopPattern.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        naviStopPatternSpinner.setAdapter(adapterNaviStopPattern);
        naviStopPatternSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempProfileData[9] = (byte) i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        naviLeftPatternSpinner= findViewById(R.id.spnNaviLeftPattern);
        ArrayAdapter<CharSequence> adapterNaviLeftPattern = ArrayAdapter.createFromResource(this, R.array.navi_left_array, android.R.layout.simple_spinner_item);
        adapterNaviLeftPattern.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        naviLeftPatternSpinner.setAdapter(adapterNaviLeftPattern);
        naviLeftPatternSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempProfileData[10] = (byte) i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        naviRightPatternSpinner= findViewById(R.id.spnNaviRightPattern);
        ArrayAdapter<CharSequence> adapterNaviRightPattern = ArrayAdapter.createFromResource(this, R.array.navi_right_array, android.R.layout.simple_spinner_item);
        adapterNaviRightPattern.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        naviRightPatternSpinner.setAdapter(adapterNaviRightPattern);
        naviRightPatternSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempProfileData[11] = (byte) i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        for(int i

        if (currentCustomizeProfileNumber == 1) {
            distanceOutputTypeSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM1[0]);
            distanceOutputPatternSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM1[1]);
            distanceZone1PatternSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM1[2]);
            distanceZone2PatternSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM1[3]);
            distanceZone3PatternSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM1[4]);
            distanceZone4PatternSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM1[5]);
            naviOutputTypeSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM1[6]);
            naviOutputPatternSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM1[7]);
            naviFwdPatternSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM1[8]);
            naviStopPatternSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM1[9]);
            naviLeftPatternSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM1[10]);
            naviRightPatternSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM1[11]);
        }
        else if (currentCustomizeProfileNumber == 2) {
            distanceOutputTypeSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM2[0]);
            distanceOutputPatternSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM2[1]);
            distanceZone1PatternSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM2[2]);
            distanceZone2PatternSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM2[3]);
            distanceZone3PatternSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM2[4]);
            distanceZone4PatternSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM2[5]);
            naviOutputTypeSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM2[6]);
            naviOutputPatternSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM2[7]);
            naviFwdPatternSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM2[8]);
            naviStopPatternSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM2[9]);
            naviLeftPatternSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM2[10]);
            naviRightPatternSpinner.setSelection(common.ACTIVATE_PROFILE_CUSTOM2[11]);
        }
    }
    public void goBackPressed(View view) {
        onBackPressed();
    }
}
