package com.example.smartcanedebug;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.content.DialogInterface;
import android.content.SharedPreferences;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 11;
    public static BluetoothAdapter BA;
    private BLEController bleController;
    private Common common;

    private Button connectButton;
    private Button disconnectButton;
    private Button locateButton;
    private Button trainingButton;
    private Button navigationButton;
    private Button profilesButton;
    private Button debugButton;
    private Button button;
    ToggleButton toggleButton;
    TextView textview1;

    private BluetoothLeScanner mBluetoothLeScanner;
    CardView cd1, cd2, cd3, cd4, cd5, cd6, cd7, cd8, cd9, cd10,cd11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggleButton = findViewById(R.id.toggleButton);
        cd1 = (CardView) findViewById(R.id.cd1);

        cd4 = (CardView) findViewById(R.id.cd4);


    /*    BluetoothManager bluetoothManager = getSystemService(BluetoothManager.class);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
        }


        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_CONNECT_BT);
        }*/


        this.bleController = BLEController.getInstance(this);
        this.common = Common.getInstance();
        // getPermissionFromUser();

        // initButtons();

        //   checkBLESupport();
        // checkPermissions();
        //   if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        //   this.bleController.init();
        // } else {
        //    Log.d("ble init", "onCreate: ble controller not executed due to ACCESS_FINE_LOCATION permission");
        //}
        // getPermissionFromUser();
           /* cd1.setOnClickListener(new View.OnCLickListener () {
            @Override
                public void onClick(View v){
                    Toast.makeText(getApplicationContext(),"You Clicked Card ", Toast.LENGTH_LONG).show();
                }
            });
            cd2.setOnClickListener(new View.OnCLickListener () {
                @Override
                public void onClick(View v){
                    Toast.makeText(getApplicationContext(),"You Clicked Card 2", Toast.LENGTH_LONG).show();
                }
            });
            cd3.setOnClickListener(new View.OnCLickListener () {
                @Override
                public void onClick(View v){
                    Toast.makeText(getApplicationContext(),"You Clicked Card 3", Toast.LENGTH_LONG).show();
                }
            });
        }*/


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation3);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home1);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.settings3:
                        // startActivity(new Intent(getApplicationContext(),SearchPOI.class));
                        overridePendingTransition(0,0);
                        Toast.makeText(getApplicationContext(), "You Clicked Settings", Toast.LENGTH_LONG).show();
                        Intent intentProfiles = new Intent(getBaseContext(), SettingsActivity.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
                        startActivity(intentProfiles);
                        return true;
                    case R.id.home1:
                        return true;
                    case R.id.getsupport:
                        overridePendingTransition(0,0);
                        Toast.makeText(getApplicationContext(), "You Clicked Get Support", Toast.LENGTH_LONG).show();
                        Intent intentRegister = new Intent(getBaseContext(), GetSupport.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
                        startActivity(intentRegister);
                        return true;
                    case R.id.findmysmartcane:
                        // startActivity(new Intent(getApplicationContext(),ShowRoutes.class));
                        overridePendingTransition(0,0);
                        Toast.makeText(getApplicationContext(), "You Clicked Find My SmartCane", Toast.LENGTH_LONG).show();

                        // bleController.writeBLEData(bleController.otherInstructionsChar, common.OTHER_CMD_IDENTIFY_SMARTCANE);
                        return true;

                    case R.id.emergencysettings:

                        overridePendingTransition(0,0);
                        Toast.makeText(getApplicationContext(), "You Clicked Emergency Settings", Toast.LENGTH_LONG).show();
                        Intent intentEmergency = new Intent(getBaseContext(), EmergencyMainActivity.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
                        startActivity(intentEmergency);
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
                .setMessage("In order to use Emergency features,Please select contacts you want to connect to,during any sort of Emergency through Emergency Settings.")
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

    public void onClick1(View view) {

        if (toggleButton.isChecked()) {
            // textview1.setText("CONNECT");
            Log.d("initButtons", "onClick: Connecting...");
            Toast.makeText(getApplicationContext(), "You Clicked Connect", Toast.LENGTH_LONG).show();
            // bleController.connectToDevice(common.deviceAddress);
        } else {
            // textview1.setText("DISCONNECT");
            Log.d("initButtons", "onClick: Disconnecting...");
            Toast.makeText(getApplicationContext(), "You Clicked Disconnect", Toast.LENGTH_LONG).show();
            //bleController.disconnect();
            ;
        }
    }

    public void onClick2(View view) {
        Toast.makeText(getApplicationContext(), "You Clicked Find My SmartCane", Toast.LENGTH_LONG).show();

        // bleController.writeBLEData(bleController.otherInstructionsChar, common.OTHER_CMD_IDENTIFY_SMARTCANE);

    }

    public void onClick3(View view) {
        Toast.makeText(getApplicationContext(), "You Clicked Emergency Settings", Toast.LENGTH_LONG).show();
        Intent intentEmergency = new Intent(getBaseContext(), EmergencyMainActivity.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
        startActivity(intentEmergency);
    }

    public void onClick7(View view) {
        Toast.makeText(getApplicationContext(), "You Clicked Debug", Toast.LENGTH_LONG).show();
        Intent intentDebug = new Intent(getBaseContext(), DebugActivity.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
        startActivity(intentDebug);
    }


    public void onClick5(View view) {

        Toast.makeText(getApplicationContext(), "You Clicked Emergency Call", Toast.LENGTH_LONG).show();
        Intent intentEmergencyCall = new Intent(getBaseContext(), EmergencyCall.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
        startActivity(intentEmergencyCall);
    }


    public void onClick6(View view) {
        Toast.makeText(getApplicationContext(), "You Clicked Settings", Toast.LENGTH_LONG).show();
        Intent intentProfiles = new Intent(getBaseContext(), SettingsActivity.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
        startActivity(intentProfiles);
    }

    public void onClick4(View view) {

        Toast.makeText(getApplicationContext(), "You Clicked Emergency SMS", Toast.LENGTH_LONG).show();
        Intent intentEmergencySms = new Intent(getBaseContext(), EmergencySms.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
        startActivity(intentEmergencySms);
    }

    public void onClick8(View view) {

        Toast.makeText(getApplicationContext(), "You Clicked Location", Toast.LENGTH_LONG).show();
        Intent intentLocation = new Intent(getBaseContext(), Location.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
        startActivity(intentLocation);
    }

    public void onClick9(View view) {
        Toast.makeText(getApplicationContext(), "You Clicked Battery Status", Toast.LENGTH_LONG).show();
        Intent intentBattery = new Intent(getBaseContext(), BatteryActivity.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
        startActivity(intentBattery);
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




  /*  public void onClick11(View view) {

        // Stay at the current activity.
        Toast.makeText(getApplicationContext(), "You Clicked CardARTI8", Toast.LENGTH_LONG).show();
        Intent intentReport = new Intent(getBaseContext(), ReportIssue.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
        startActivity(intentReport);
    }*/








//    @Override
//    public void onBackPressed() {
//        Log.i("back", "onBackPressed:___________________________________ back");
////        MainActivity.this.finish();
//        System.exit(0);
////        finish();
//    }

    final String[] PERMISSIONS = new String[]{Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_ADVERTISE};

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
                    } });

             /*   builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        requestPermissions(new String[]{PERMISSIONS[finalI]}, 3);
                    }
                });*/
                builder.show();
            }

            i++;
        }}




  /*  public void onToggleClick(View view)
    {
        if(togglebutton.isChecked()) {
            textview1.setText("CONNECT");
               // Log.d("initButtons", "onClick: Connecting...");
                //bleController.connectToDevice(common.deviceAddress);
            }

        else {
            textview1.setText("DISCONNECT");
       // Log.d("initButtons", "onClick: Disconnecting...");
       // bleController.disconnect();;
        }
    }*/

    /*  private void initButtons() {
          this.connectButton = findViewById(R.id.btnConnect);
          this.connectButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Log.d("initButtons", "onClick: Connecting...");
                  bleController.connectToDevice(common.deviceAddress);
              }
          });

          this.disconnectButton = findViewById(R.id.btnDisconnect);
          this.disconnectButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Log.d("initButtons", "onClick: Disconnecting...");
                  bleController.disconnect();
              }
          });

          //this.locateButton = findViewById(R.id.btnLocate);
         // this.locateButton.setOnClickListener(new View.OnClickListener() {
            //  @Override
            //  public void onClick(View v) {
                  bleController.writeBLEData(bleController.otherInstructionsChar, common.OTHER_CMD_IDENTIFY_SMARTCANE);
              }
          });

          this.trainingButton = findViewById(R.id.btnTraining);
          this.trainingButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intentNavigation = new Intent(getBaseContext(), NavigationActivity.class);
  //                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
                  startActivity(intentNavigation);
              }
          });

          this.navigationButton = findViewById(R.id.btnNavi);
          this.navigationButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intentTraining = new Intent(getBaseContext(), TrainingActivity.class);
  //                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
                  startActivity(intentTraining);
              }
          });

          this.profilesButton = findViewById(R.id.btnProfiles);
          this.profilesButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intentProfiles = new Intent(getBaseContext(), ProfilesActivity.class);
  //                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
                  startActivity(intentProfiles);
              }
          });

          this.debugButton = findViewById(R.id.btnDebug);
          this.debugButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intentDebug = new Intent(getBaseContext(), DebugActivity.class);
  //                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
                  startActivity(intentDebug);
              }
          });

  //        connectButton.setEnabled(true);
  //        disconnectButton.setEnabled(false);
  //        locateButton.setEnabled(false);
  //        trainingButton.setEnabled(true);
  //        navigationButton.setEnabled(true);
  //        profilesButton.setEnabled(true);
  //        debugButton.setEnabled(false);
      }
  */
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
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
