package com.example.smartcanedebug;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;




public class EmergencySms extends AppCompatActivity {

    private static final int IGNORE_BATTERY_OPTIMIZATION_REQUEST = 1002;
    private static final int PICK_CONTACT = 1;

    // create instances of various classes to be used
    Button button1;
    ListView listView;
    DbHelper db;
    List<ContactModel> list;
    CustomAdapter customAdapter;
    private String phoneNo;
    ListView sms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_activity);
        sms = findViewById(R.id.listView1);
        DbHelper db = new DbHelper(this);
        List<ContactModel> list = db.getAllContacts();
        CustomAdapter customAdapter = new CustomAdapter(this, list);
        customAdapter.addAll();

        sms.setAdapter(customAdapter);

        onsos();

        // check for runtime permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS}, 100);
            }
        }

        // this is a special permission required only by devices using
        // Android Q and above. The Access Background Permission is responsible
        // for populating the dialog with "ALLOW ALL THE TIME" option
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 100);
        }

        // check for BatteryOptimization,
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (pm != null && !pm.isIgnoringBatteryOptimizations(getPackageName())) {
                askIgnoreOptimization();
            }
        }



        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation3);


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

    // this method prompts the user to remove any
    // battery optimisation constraints from the App
    private void askIgnoreOptimization() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("BatteryLife") Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, IGNORE_BATTERY_OPTIMIZATION_REQUEST);
        }

    }
    public void goBackPressed(View view) {
        onBackPressed();
    }
    public void onsos() {

        // create FusedLocationProviderClient to get the user location
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());


        if (ActivityCompat.checkSelfPermission(EmergencySms.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EmergencySms.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, new CancellationToken() {
            @Override
            public boolean isCancellationRequested() {
                return false;
            }

            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<android.location.Location>() {
            @Override
            public void onSuccess(Location location) {
                // check if location is null
                // for both the cases we will
                // create different messages
                if (location != null) {

                    // get the SMSManager
                    SmsManager smsManager = SmsManager.getDefault();

                    // get the list of all the contacts in Database
                    DbHelper db = new DbHelper(EmergencySms.this);
                    List<ContactModel> list = db.getAllContacts();

                    // send SMS to each contact
                    for (ContactModel c : list) {
                        String message = "Hey, " + c.getName() + " I am in DANGER, need help. Please urgently reach me out. Here are my coordinates.\n " + "http://maps.google.com/?q=" + location.getLatitude() + "," + location.getLongitude();
                        smsManager.sendTextMessage(c.getPhoneNo(), null, message, null, null);



                    }
                } else {
                   /* String message = " I am in DANGER, need help. Please urgently reach me out.\n" + "GPS was turned off.Couldn't find location. Call your nearest Police Station.";
                    SmsManager smsManager = SmsManager.getDefault();
                    DbHelper db = new DbHelper(SensorService.this);
                    List<ContactModel> list = db.getAllContacts();
                    for (ContactModel c : list) {
                        smsManager.sendTextMessage(c.getPhoneNo(), null, message, null, null);

                    }*/
                }
            }
        });
    /*.addOnFailureListener(new OnFailureListener() {
           // @Override
           /* public void onFailure(@NonNull Exception e) {
                Log.d("Check: ", "OnFailure");
                String message = "I am in DANGER, need help. Please urgently reach me out.\n" + "GPS was turned off.Couldn't find location. Call your nearest Police Station.";
                SmsManager smsManager = SmsManager.getDefault();
                DbHelper db = new DbHelper(SensorService.this);
                List<ContactModel> list = db.getAllContacts();
                for (ContactModel c : list) {
                    smsManager.sendTextMessage(c.getPhoneNo(), null, message, null, null);
                }
            }
        });*/

        // this method is called for making a call.

    }


}
