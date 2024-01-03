package com.example.smartcanedebug;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.concurrent.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.content.SharedPreferences;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import android.widget.ExpandableListView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 11;
    public static BluetoothAdapter BA;
    List<BluetoothGattCharacteristic> bgcNotificationArray = new ArrayList<>();
    private BLEController bleController;
    private Common common;
    Handler appHandler = new Handler();
    int delay = 100, delayCheck = 100 ; //milliseconds

    ToggleButton toggleButton;
   // TextView textview1;

    private BluetoothLeScanner mBluetoothLeScanner;
    public final static String EXTRA_DATA =
            "com.example.bluetooth.le.EXTRA_DATA";
    CardView cd1, cd2, cd3, cd4, cd5, cd6, cd7, cd8, cd9, cd10, cd11;
    private Handler handler;
    private BluetoothDevice device;
   // private BluetoothGatt bluetoothGatt;
    private final static String TAG = MainActivity.class.getSimpleName();

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    private final int mConnectionState = STATE_DISCONNECTED;
    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTED = 2;
  //  private TextView mConnectionState;
    private TextView mDataField;
    private String mDeviceName;
    private String mDeviceAddress;
    private ExpandableListView mGattServicesList;
    //private BluetoothLeService mBluetoothLeService;
    private final ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private final boolean mConnected = false;
    private BluetoothGattCharacteristic mNotifyCharacteristic;
    private boolean shutdown = false;
    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";
    private TextView batteryTextView;
    private TextView magicTextView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  toggleButton = findViewById(R.id.toggleButton);
        cd1 = findViewById(R.id.cd1);

        cd4 = findViewById(R.id.cd4);
        batteryTextView = findViewById(R.id.txtBatteryValue);
        magicTextView = findViewById(R.id.txtMagicValue);
        this.bleController = BLEController.getInstance(this);
        this.common = Common.getInstance();

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (mConnectionState == STATE_CONNECTED) {
//
                    bleController.setDebugCharNotification();

                }

            }
        });
        class MyRunnable implements Runnable {
            @Override
            public void run() {

                try {



                    runOnUiThread(new Runnable() {


            public void run() {

                updateNotifyTexts1();

            }

                    });


                } catch (Exception e) {
                    Log.i("notifyUpdateTask exception", "run: " + e);
                }


        }}ScheduledThreadPoolExecutor exec1 = new ScheduledThreadPoolExecutor(1);

        exec1.scheduleAtFixedRate(new MyRunnable(), 0, 1000, MILLISECONDS);
      


        class notifyUpdateTask implements Runnable {


            @Override
            public void run() {

                try {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {




                            updateNotifyTexts();
                        }




                    });

                } catch (Exception e) {
                    Log.i("notifyUpdateTask exception", "run: " + e);
                }


            }


        }
        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
        exec.scheduleAtFixedRate(new notifyUpdateTask(), 0, 1000, MILLISECONDS);



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation3);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home1);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.settings3:
                        // startActivity(new Intent(getApplicationContext(),SearchPOI.class));
                        overridePendingTransition(0, 0);
                        Toast.makeText(getApplicationContext(), "You Clicked Settings", Toast.LENGTH_LONG).show();
                        Intent intentProfiles = new Intent(getBaseContext(), SettingsActivity.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
                        startActivity(intentProfiles);
                        return true;
                    case R.id.home1:

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

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        if (firstStart) {
            showStartDialog();

        }


    }

    private void showStartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Essential Information!")
                .setMessage("In order to use Emergency features,Please select contacts you want to connect to, during any sort of Emergency through Emergency Settings.")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intentEmergency = new Intent(getBaseContext(), EmergencyMainActivity.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
                        startActivity(intentEmergency);

                    }
                })
                .create().show();


        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();

    }

    void updateNotifyTexts() {


        batteryTextView.setText(String.valueOf(common.batterylevel));

    }
  // @Override


    public void updateNotifyTexts1() {
      
        magicTextView.setText(String.valueOf(common.magicbtn));
        if (common.magicbtn == 0){}

        if (common.magicbtn == 1){

            Toast.makeText( getApplicationContext(), "Your Device is connected to the App!", Toast.LENGTH_SHORT).show();


        }


        if (common.magicbtn == 2){
            Toast.makeText(getApplicationContext(), " Where Am I?", Toast.LENGTH_SHORT).show();
            Intent intentLocation = new Intent(getBaseContext(), Location.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
            startActivity(intentLocation);
        }if (common.magicbtn == 3){
            Log.d("initButtons", "onClick: Disconnecting...");
            Toast.makeText(getApplicationContext(), "Your Device is Disconnected", Toast.LENGTH_SHORT).show();
            bleController.disconnect();
        }if (common.magicbtn == 10){
            Toast.makeText(getApplicationContext(), "You Clicked Emergency Call", Toast.LENGTH_SHORT).show();
            Intent intentEmergencyCall = new Intent(getBaseContext(), EmergencyCall.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
            startActivity(intentEmergencyCall);
        }if (common.magicbtn == 11){
            Toast.makeText(getApplicationContext(), "You Clicked Mute", Toast.LENGTH_SHORT).show();
            // Intent intentEmergencyCall = new Intent(getBaseContext(), EmergencyCall.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
            // startActivity(intentEmergencyCall);
        }if (common.magicbtn == 12){
            Toast.makeText(getApplicationContext(), "You Clicked Emergency SMS", Toast.LENGTH_SHORT).show();
            Intent intentEmergencySms = new Intent(getBaseContext(), EmergencySms.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
            startActivity(intentEmergencySms);}
        stop();






    }

    private void stop() {
       common.magicbtn = 0;

    }




    public void onClick1(View view) {

        // if (toggleButton.isChecked()) {
        // textview1.setText("CONNECT");
        Log.d("initButtons", "onClick: Connecting...");
        Toast.makeText(getApplicationContext(), "You Clicked Connect", Toast.LENGTH_LONG).show();
        bleController.connectToDevice(common.deviceAddress);


      /*  } else {
            // textview1.setText("DISCONNECT");
            Log.d("initButtons", "onClick: Disconnecting...");
            Toast.makeText(getApplicationContext(), "You Clicked Disconnect", Toast.LENGTH_LONG).show();
            //bleController.disconnect();
            ;
        }*/
    }

    public void onClick2(View view) {
        Toast.makeText(getApplicationContext(), "You Clicked Find My SmartCane", Toast.LENGTH_LONG).show();
        // bleController.readBatteryLevel();
        bleController.writeBLEData(bleController.misc, common.OTHER_CMD_IDENTIFY_SMARTCANE);

    }



    public void onClick4(View view) {

        Toast.makeText(getApplicationContext(), "You Clicked Emergency SMS", Toast.LENGTH_LONG).show();
        Intent intentEmergencySms = new Intent(getBaseContext(), EmergencySms.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
        startActivity(intentEmergencySms);
        // bleController.readBLEData(bleController.batteryLevelChar);
        // mDataField.setText(common.batterylevel);
    }

    public void onClick8(View view) {

        Toast.makeText(getApplicationContext(), "You Clicked Where Am I?", Toast.LENGTH_LONG).show();
        Intent intentLocation = new Intent(getBaseContext(), Location.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
        startActivity(intentLocation);
    }



    public void onClick10(View view) {


        Toast.makeText(getApplicationContext(), "You Clicked Get Support", Toast.LENGTH_LONG).show();
        Intent intentRegister = new Intent(getBaseContext(), GetSupport.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
        startActivity(intentRegister);
    }

    public void onClick15(View view) {

        // call Login Activity
        Toast.makeText(getApplicationContext(), "You Clicked Training", Toast.LENGTH_LONG).show();
          Intent intentTraining = new Intent(getBaseContext(), Training.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
        startActivity(intentTraining);
    }









  /*  final String[] PERMISSIONS = new String[]{Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_ADVERTISE};

    public void getPermissionFromUser() {
        if (BA == null) {
            BA = BluetoothAdapter.getDefaultAdapter();
        }


        int i = 0;
        for (String permission : PERMISSIONS) {
            String title = "BLUETOOTH PERMISSION";
            String msg = "";
            if (i == 1) {
                msg = " Please give the App permission to use Bluetooth";
            }
            if (ActivityCompat.checkSelfPermission(MainActivity.this, PERMISSIONS[i]) == PackageManager.PERMISSION_DENIED) {
                int finalI = i;
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(title);
                builder.setMessage(msg);
                // builder.setNegativeButton("DENY", null);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        BluetoothManager bluetoothManager = getSystemService(BluetoothManager.class);
                        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
                        if (bluetoothAdapter == null) {
                            // Device doesn't support Bluetooth
                        }


                        if (!bluetoothAdapter.isEnabled()) {
                            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                        }
                    }
                });

             /*   builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        requestPermissions(new String[]{PERMISSIONS[finalI]}, 3);
                    }
                });*/
             /*   builder.show();
            }

            i++;
        }
    }*/



    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("BLE", "checkPermissions: \"Access Fine Location\" permission not granted yet!");
            Log.d("BLE", "checkPermissions: Without this permission Blutooth devices cannot be searched!");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 42);
        }
    }

    private void checkBLESupport() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE not supported!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivityForResult(enableBTIntent, 1);


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        common.deviceAddress = null;
        this.bleController = BLEController.getInstance(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d("BLE", "onResume: Searching for SmartCane...");
            this.bleController.init();
          //  registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

}

