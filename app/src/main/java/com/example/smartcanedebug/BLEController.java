package com.example.smartcanedebug;

import static android.bluetooth.BluetoothProfile.GATT;


import android.app.Dialog;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.bluetooth.BluetoothDevice;
import android.util.Log;
import android.content.IntentFilter;

public class BLEController {
    private final static String TAG = BLEController.class.getSimpleName();


    private int mConnectionState = STATE_DISCONNECTED;

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    public final static String ACTION_GATT_CONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE =
            "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA =
            "com.example.bluetooth.le.EXTRA_DATA";





    private static BLEController instance;
    //public  BluetoothGattCallback bleConnectCallback;

    private final Handler mHandler = new Handler();

    private BluetoothLeScanner scanner;
    private BluetoothDevice device;
    private BluetoothGatt bluetoothGatt;
    private BluetoothManager bluetoothManager;
    // private BLEController bleController;
    // Context activity;
//    BLEService debugService(debugServiceUUID);
//    BLEUnsignedCharCharacteristic setThresholdsChar(setThresholdsCharUUID, BLERead | BLEWrite);
//    BLEUnsignedCharCharacteristic distanceChar(distanceCharUUID, BLERead | BLENotify);
//    BLEFloatCharacteristic distanceOutputTypeChar(distanceOutputTypeCharUUID, BLERead | BLENotify);
//    BLEUnsignedIntCharacteristic distanceOutputPatternChar(distanceOutputPatternCharUUID, BLERead | BLENotify);
//    BLEUnsignedCharCharacteristic batteryLevelChar(batteryLevelCharUUID, BLERead | BLENotify);
//    BLEUnsignedCharCharacteristic errorsChar(errorsCharUUID, BLERead | BLENotify);
//
//    BLEService trainingService(trainingServiceUUID);
//    BLEUnsignedCharCharacteristic trainingCommandsChar(trainingCommandsCharUUID, BLEWrite);
//    BLEUnsignedCharCharacteristic trainingStatusChar(trainingStatusCharUUID, BLERead | BLENotify);
//
//    BLEService customizeService(customizeServiceUUID);
//    BLEUnsignedCharCharacteristic outputTypeChar(outputTypeCharUUID, BLEWrite);
//    BLEUnsignedCharCharacteristic outputPatternChar(outputPatternCharUUID, BLEWrite);
//
//    BLEService userActivityService(userActivityServiceUUID);
//    BLEUnsignedCharCharacteristic gesturesChar(gesturesCharUUID, BLERead | BLENotify);
//    BLEUnsignedCharCharacteristic modeIOChar(modeIOCharUUID, BLERead | BLENotify);
//    BLEUnsignedCharCharacteristic muteChar(muteCharUUID, BLERead | BLENotify);
//
//    BLEService appCommandsService(appCommandsServiceUUID);
//    BLEUnsignedCharCharacteristic navigationInstructionsChar(navigationInstructionsCharUUID, BLEWrite);
//    BLEUnsignedCharCharacteristic otherInstructionsChar(otherInstructionsCharUUID, BLEWrite);

  /*  private final String debugServiceUUID = "19b10001-e8f2-537e-4f6c-d104768a1210";
    private final String setThresholdsCharUUID = "19b10001-e8f2-537e-4f6c-d104768a1211";
    private final String distanceCharUUID = "19b10001-e8f2-537e-4f6c-d104768a1212";
    private final String distanceOutputTypeCharUUID = "19b10001-e8f2-537e-4f6c-d104768a1213";
    private final String distanceZoneCharUUID = "19b10001-e8f2-537e-4f6c-d104768a1214";
    private final String distanceOutputPatternCharUUID = "19b10001-e8f2-537e-4f6c-d104768a1215";
    private final String navigationOutputTypeCharUUID = "19b10001-e8f2-537e-4f6c-d104768a1216";
    private final String navigationCharUUID = "19b10001-e8f2-537e-4f6c-d104768a1217";
    private final String navigationOutputPatternCharUUID = "19b10001-e8f2-537e-4f6c-d104768a1218";
    private final String batteryLevelCharUUID = "19b10001-e8f2-537e-4f6c-d104768a1219";
    private final String errorsCharUUID = "19b10001-e8f2-537e-4f6c-d104768a121a";

    private final String trainingServiceUUID = "19b10002-e8f2-537e-4f6c-d104768a1210";
    private final String trainingCommandsCharUUID = "19b10002-e8f2-537e-4f6c-d104768a1211";
    private final String trainingStatusCharUUID = "19b10002-e8f2-537e-4f6c-d104768a1212";

    private final String customizeServiceUUID = "19b10003-e8f2-537e-4f6c-d104768a1210";
    //    private final String outputTypeCharUUID = "19b10003-e8f2-537e-4f6c-d104768a1211";
//    private final String outputPatternCharUUID = "19b10003-e8f2-537e-4f6c-d104768a1212";
    private final String profileCharUUID = "19b10003-e8f2-537e-4f6c-d104768a1211";
    private final String vibrationCharUUID = "19b10003-e8f2-537e-4f6c-d104768a1212";
    private final String buzzerCharUUID = "19b10003-e8f2-537e-4f6c-d104768a1213";

    private final String userActivityServiceUUID = "19b10004-e8f2-537e-4f6c-d104768a1210";
    private final String gesturesCharUUID = "19b10004-e8f2-537e-4f6c-d104768a1211";
    private final String modeIOCharUUID = "19b10004-e8f2-537e-4f6c-d104768a1212";
    private final String muteCharUUID = "19b10004-e8f2-537e-4f6c-d104768a1213";

    private final String appCommandsServiceUUID = "19b10005-e8f2-537e-4f6c-d104768a1210";
    private final String navigationInstructionsCharUUID = "19b10005-e8f2-537e-4f6c-d104768a1211";
    private final String otherInstructionsCharUUID = "19b10005-e8f2-537e-4f6c-d104768a1212";

    BluetoothGattCharacteristic setThresholdsChar = null;
    BluetoothGattCharacteristic distanceChar = null;
    BluetoothGattCharacteristic distanceOutputTypeChar = null;
    BluetoothGattCharacteristic distanceOutputPatternChar = null;
    BluetoothGattCharacteristic batteryLevelChar = null;
    BluetoothGattCharacteristic errorsChar = null;

    BluetoothGattCharacteristic trainingCommandsChar = null;
    BluetoothGattCharacteristic trainingStatusChar = null;

    //    BluetoothGattCharacteristic outputTypeChar = null;
//    BluetoothGattCharacteristic outputPatternChar = null;
    BluetoothGattCharacteristic profileChar = null;
    BluetoothGattCharacteristic vibrationChar = null;
    BluetoothGattCharacteristic buzzerChar = null;

    BluetoothGattCharacteristic gesturesChar = null;
    BluetoothGattCharacteristic modeIOChar = null;
    BluetoothGattCharacteristic muteChar = null;

    BluetoothGattCharacteristic navigationInstructionsChar = null;
    BluetoothGattCharacteristic otherInstructionsChar = null;*/

    UUID batteryServiceUuid = UUID.fromString("19b10001-e8f2-537e-4f6c-d104768a1210");
    UUID batteryLevelCharUuid = UUID.fromString("19b10001-e8f2-537e-4f6c-d104768a1211");
    UUID operatingModeStatuseUuid = UUID.fromString("19b10001-e8f2-537e-4f6c-d104768a1212");
    UUID settingsUuid =  UUID.fromString("19b10002-e8f2-537e-4f6c-d104768a1210");
    UUID VibrationUuid = UUID.fromString("19b10002-e8f2-537e-4f6c-d104768a1212");
    UUID buzzerUuid = UUID.fromString("19b10002-e8f2-537e-4f6c-d104768a1213");
    UUID operatingModeUuid = UUID.fromString("19b10002-e8f2-537e-4f6c-d104768a1211");
    UUID CommandUuid = UUID.fromString("19b10003-e8f2-537e-4f6c-d104768a1210");
    UUID MiscUuid = UUID.fromString( "19b10003-e8f2-537e-4f6c-d104768a1211");
    UUID MagicUuid = UUID.fromString( "19b10003-e8f2-537e-4f6c-d104768a1212");




    BluetoothGattCharacteristic misc = null;
    BluetoothGattCharacteristic magic = null;
    BluetoothGattCharacteristic batteryLevelChar = null;
    BluetoothGattCharacteristic operatingMode = null;
    BluetoothGattCharacteristic buzzer = null;
    BluetoothGattCharacteristic vibration = null;
    BluetoothGattCharacteristic operatingModeStatus = null;

  /*  BluetoothGattCharacteristic batterylevel = null;
    BluetoothGattCharacteristic operatingModeStatus = null;
    BluetoothGattCharacteristic muteStatus = null;
    BluetoothGattCharacteristic errors = null;
    BluetoothGattCharacteristic operatingMode = null;
    BluetoothGattCharacteristic vibrationLevel = null;

    BluetoothGattCharacteristic buzzerSound = null;

    BluetoothGattCharacteristic misc = null;*/


    List<BluetoothGattCharacteristic> bgcNotificationArray = new ArrayList<>();

    private boolean resetChar = true;
    private boolean bleDescriptorWritten = true;

    private final HashMap<String, BluetoothDevice> devices = new HashMap<>();
    private final Common common;

    //  Context context = getApplicationContext();

    //    EditText distance1EditText = findViewById(R.id.editTextDistance1);
//    EditText distance2EditText = findViewById(R.id.editTextDistance2);
//    EditText distance3EditText = findViewById(R.id.editTextDistance3);
//    EditText distance4EditText = findViewById(R.id.editTextDistance4);
//    EditText distance5EditText = findViewById(R.id.editTextDistance5);
//    EditText distance6EditText = findViewById(R.id.editTextDistance6);
//    EditText threshold1EditText = findViewById(R.id.editTextThreshold1);
//    EditText threshold2EditText = findViewById(R.id.editTextThreshold2);
//    EditText threshold3EditText = findViewById(R.id.editTextThreshold3);
//    EditText threshold4EditText = findViewById(R.id.editTextThreshold4);
//    EditText threshold5EditText = findViewById(R.id.editTextThreshold5);
//    EditText threshold6EditText = findViewById(R.id.editTextThreshold6);
//
//    TextView distanceValueTxt = findViewById(R.id.txtDistanceValue);
//    TextView currentTypeValueTxt = findViewById(R.id.txtCurrentTypeValue);
//    TextView currentPatternValueTxt = findViewById(R.id.txtCurrentPatternValue);
//    TextView batteryValueTxt = findViewById(R.id.txtBatteryValue);
//    TextView modeValueTxt = findViewById(R.id.txtModeValue);
//    TextView muteValueTxt = findViewById(R.id.txtMuteValue);
//    TextView errorsValueTxt = findViewById(R.id.txtErrorsValue);
    EditText distance1EditText;
    EditText distance2EditText;
    EditText distance3EditText;
    EditText distance4EditText;
    EditText distance5EditText;
    EditText distance6EditText;
    EditText threshold1EditText;
    EditText threshold2EditText;
    EditText threshold3EditText;
    EditText threshold4EditText;
    EditText threshold5EditText;
    EditText threshold6EditText;

    TextView distanceValueTxt;
    TextView currentTypeValueTxt;
    TextView currentPatternValueTxt;
    TextView batteryValueTxt;
    TextView modeValueTxt;
    TextView muteValueTxt;
    TextView errorsValueTxt;
    // Handler mHandler;
    Dialog dialog;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            final String action = intent.getAction();

            if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED))
            {
                final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);

                switch(state){
                    case BluetoothDevice.BOND_BONDING:
                        // Bonding...
                        break;

                    case BluetoothDevice.BOND_BONDED:
                        // Bonded...
                        MainActivity mActivity = new MainActivity();
                        mActivity.unregisterReceiver(mReceiver);
                        break;

                    case BluetoothDevice.BOND_NONE:
                        // Not bonded...
                        break;
                }
            }
        }
    };




    private BLEController(Context ctx) {
        try {
            this.bluetoothManager = (BluetoothManager) ctx.getSystemService(Context.BLUETOOTH_SERVICE);

        } catch (Exception e) {
            Log.d("BLE Service", "BLEController: " + e);
        }
//        this.context = ctx;
        common = Common.getInstance();
    }

    public static BLEController getInstance(Context ctx) {
        if (instance == null) {
            instance = new BLEController((ctx));
        }
        return instance;
    }

    public boolean init() {
//        initOtherActivityItems();
        this.devices.clear();
        this.scanner = this.bluetoothManager.getAdapter().getBluetoothLeScanner();
        this.scanner.startScan(bleCallback);
        return false;
    }

    /*  private void initOtherActivityItems() {
          distance1EditText = findViewById(R.id.editTextDistance1);
          distance2EditText = findViewById(R.id.editTextDistance2);
          distance3EditText = findViewById(R.id.editTextDistance3);
          distance4EditText = findViewById(R.id.editTextDistance4);
          distance5EditText = findViewById(R.id.editTextDistance5);
          distance6EditText = findViewById(R.id.editTextDistance6);
          threshold1EditText = findViewById(R.id.editTextThreshold1);
          threshold2EditText = findViewById(R.id.editTextThreshold2);
          threshold3EditText = findViewById(R.id.editTextThreshold3);
          threshold4EditText = findViewById(R.id.editTextThreshold4);
          threshold5EditText = findViewById(R.id.editTextThreshold5);
          threshold6EditText = findViewById(R.id.editTextThreshold6);

          distanceValueTxt = findViewById(R.id.txtDistanceValue);
          currentTypeValueTxt = findViewById(R.id.txtCurrentTypeValue);
          currentPatternValueTxt = findViewById(R.id.txtCurrentPatternValue);
          batteryValueTxt = findViewById(R.id.txtBatteryValue);
          modeValueTxt = findViewById(R.id.txtModeValue);
          muteValueTxt = findViewById(R.id.txtMuteValue);
          errorsValueTxt = findViewById(R.id.txtErrorsValue);
      }
  */
    private final ScanCallback bleCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            if (!devices.containsKey(device.getAddress()) && isThisTheDevice(device)) {
                devices.put(device.getAddress(), device);
                common.deviceAddress = device.getAddress();
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            for (ScanResult sr : results) {
                BluetoothDevice device = sr.getDevice();
                if (!devices.containsKey(device.getAddress()) && isThisTheDevice(device)) {
                    devices.put(device.getAddress(), device);
                    common.deviceAddress = device.getAddress();
                }
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            Log.i("[BLE]", "scan failed with errorcode: " + errorCode);
        }
    };

    private boolean isThisTheDevice(BluetoothDevice device) {
        return (device.getName() != null) && device.getName().startsWith("IITD");
    }






    public void connectToDevice(String address) {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            Log.d("[BLE]", "init: BLE scan not permitted");
//            return;
//        }
        this.device = devices.get(address);
        this.scanner.stopScan(bleCallback);
        Log.i("[BLE]", "connect to device " + device.getAddress());
        this.bluetoothGatt = device.connectGatt(null, false, this.bleConnectCallback);
    }

    public  BluetoothGattCallback bleConnectCallback = new BluetoothGattCallback() {


        // private BluetoothGattCharacteristic bgc;

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                //intentAction = ACTION_GATT_CONNECTED;
                mConnectionState = STATE_CONNECTED;
                //broadcastUpdate(intentAction);
                Log.i("[BLE]", "start service discovery " + bluetoothGatt.discoverServices());
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(context, "Smart Cane connected", Toast.LENGTH_LONG).show();
//                    }
//                });
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                initChars();
                //intentAction = ACTION_GATT_DISCONNECTED;
                // mConnectionState = STATE_DISCONNECTED;
                // broadcastUpdate(intentAction);
                Log.w("[BLE]", "DISCONNECTED with status " + status);
//                Toast.makeText(context, "Smart Cane disconnected", Toast.LENGTH_LONG).show();
            } else {
                Log.i("[BLE]", "unknown state " + newState + " and status " + status);
                //broadcastUpdate(intentAction);
            }
        }

      /*  @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            Log.i("[BLE]", "service discovered");
            Log.d("BLE", "onServicesDiscovered: reset char " + resetChar + "______________________________________________________________________________");
            if (resetChar == true) {
                Log.d("BLE", "onServicesDiscovered: reset char______________________________________________________________________________");
                for (BluetoothGattService service : gatt.getServices()) {
                    List<BluetoothGattCharacteristic> gattCharacteristics = service.getCharacteristics();
                    Log.d("BLE Service discovered", "onServicesDiscovered: " + service.getUuid().toString());
                    if (service.getUuid().toString().equalsIgnoreCase(debugServiceUUID)) {
                        for (BluetoothGattCharacteristic bgc : gattCharacteristics) {
                            Log.d("BLE Char discovered", "onServicesDiscovered: " + bgc.getUuid().toString());
                            if (bgc.getUuid().toString().equalsIgnoreCase(setThresholdsCharUUID)) {
                                setThresholdsChar = bgc;
                            } else if (bgc.getUuid().toString().equalsIgnoreCase(distanceCharUUID)) {
                                distanceChar = bgc;
                                setCharNotification(gatt, bgc);
                            } else if (bgc.getUuid().toString().equalsIgnoreCase(distanceOutputTypeCharUUID)) {
                                distanceOutputTypeChar = bgc;
                                setCharNotification(gatt, bgc);
                            } else if (bgc.getUuid().toString().equalsIgnoreCase(distanceOutputPatternCharUUID)) {
                                distanceOutputPatternChar = bgc;
                                setCharNotification(gatt, bgc);
                            } else if (bgc.getUuid().toString().equalsIgnoreCase(batteryLevelCharUUID)) {
                                batteryLevelChar = bgc;
                                setCharNotification(gatt, bgc);
                            } else if (bgc.getUuid().toString().equalsIgnoreCase(errorsCharUUID)) {
                                errorsChar = bgc;
                                setCharNotification(gatt, bgc);
                            }
                        }
                    } else if (service.getUuid().toString().equalsIgnoreCase(trainingServiceUUID)) {
                        for (BluetoothGattCharacteristic bgc : gattCharacteristics) {
                            Log.d("BLE Char discovered", "onServicesDiscovered: " + bgc.getUuid().toString());
                            if (bgc.getUuid().toString().equalsIgnoreCase(trainingCommandsCharUUID)) {
                                trainingCommandsChar = bgc;
                            } else if (bgc.getUuid().toString().equalsIgnoreCase(trainingStatusCharUUID)) {
                                trainingStatusChar = bgc;
                                setCharNotification(gatt, bgc);
                            }
                        }
                    } else if (service.getUuid().toString().equalsIgnoreCase(customizeServiceUUID)) {
                        for (BluetoothGattCharacteristic bgc : gattCharacteristics) {
                            Log.d("BLE Char discovered", "onServicesDiscovered: " + bgc.getUuid().toString());
//                            if (bgc.getUuid().toString().equalsIgnoreCase(outputTypeCharUUID)) {
//                                outputTypeChar = bgc;
//                            } else if (bgc.getUuid().toString().equalsIgnoreCase(outputPatternCharUUID)) {
//                                outputPatternChar = bgc;
//                            }
                            if (bgc.getUuid().toString().equalsIgnoreCase(profileCharUUID)) {
                                profileChar = bgc;
                            }
                            if (bgc.getUuid().toString().equalsIgnoreCase(vibrationCharUUID)) {
                                vibrationChar = bgc;
                                setCharNotification(gatt, bgc);
                            }
                            if (bgc.getUuid().toString().equalsIgnoreCase(buzzerCharUUID)) {
                                buzzerChar = bgc;
                                setCharNotification(gatt, bgc);
                            }
                        }
                    } else if (service.getUuid().toString().equalsIgnoreCase(userActivityServiceUUID)) {
                        for (BluetoothGattCharacteristic bgc : gattCharacteristics) {
                            Log.d("BLE Char discovered", "onServicesDiscovered: " + bgc.getUuid().toString());
                            if (bgc.getUuid().toString().equalsIgnoreCase(gesturesCharUUID)) {
                                gesturesChar = bgc;
                            } else if (bgc.getUuid().toString().equalsIgnoreCase(modeIOCharUUID)) {
                                modeIOChar = bgc;
                            } else if (bgc.getUuid().toString().equalsIgnoreCase(muteCharUUID)) {
                                muteChar = bgc;
                            }
                            setCharNotification(gatt, bgc);
                        }
                    } else if (service.getUuid().toString().equalsIgnoreCase(appCommandsServiceUUID)) {
                        for (BluetoothGattCharacteristic bgc : gattCharacteristics) {
                            Log.d("BLE Char discovered", "onServicesDiscovered: " + bgc.getUuid().toString());
                            if (bgc.getUuid().toString().equalsIgnoreCase(navigationInstructionsCharUUID)) {
                                navigationInstructionsChar = bgc;
                            } else if (bgc.getUuid().toString().equalsIgnoreCase(otherInstructionsCharUUID)) {
                                otherInstructionsChar = bgc;
                            }
                        }
                    }
                }*/

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            Log.i("[BLE]", "service discovered");
            Log.d("BLE", "onServicesDiscovered: reset char " + resetChar + "______________________________________________________________________________");
            if (resetChar == true) {
                Log.d("BLE", "onServicesDiscovered: reset char______________________________________________________________________________");
                for (BluetoothGattService service : gatt.getServices()) {

                    // broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
                    List<BluetoothGattCharacteristic> gattCharacteristics = service.getCharacteristics();
                    for (BluetoothGattCharacteristic bgc : gattCharacteristics) {
                        Log.d("BLE Service discovered", "onServicesDiscovered: " + service.getUuid().toString());
                        if (service.getUuid().toString().equalsIgnoreCase(String.valueOf(batteryServiceUuid))) {
                            // for (BluetoothGattCharacteristic bgc : gattCharacteristics) {
                            Log.d("BLE Char discovered", "onServicesDiscovered: " + bgc.getUuid().toString());
                            if (bgc.getUuid().toString().equalsIgnoreCase(String.valueOf(batteryLevelCharUuid))) {
                                batteryLevelChar = bgc;
                                setCharNotification(gatt, bgc);
                            } else if (bgc.getUuid().toString().equalsIgnoreCase(String.valueOf(operatingModeStatuseUuid))) {
                                operatingModeStatus = bgc;
                                setCharNotification(gatt, bgc);}}
                        // }
                        if (service.getUuid().toString().equalsIgnoreCase(String.valueOf(settingsUuid))) {
                            // for (BluetoothGattCharacteristic bgc : gattCharacteristics) {
                            Log.d("BLE Char discovered", "onServicesDiscovered: " + bgc.getUuid().toString());
                            if (bgc.getUuid().toString().equalsIgnoreCase(String.valueOf(VibrationUuid))) {
                                vibration = bgc;}
                            else if (bgc.getUuid().toString().equalsIgnoreCase(String.valueOf(buzzerUuid))) {
                                buzzer = bgc;}
                            // setCharNotification(gatt, bgc);
                            else if (bgc.getUuid().toString().equalsIgnoreCase(String.valueOf(operatingModeUuid))) {
                                operatingMode = bgc;}}
                        if (service.getUuid().toString().equalsIgnoreCase(String.valueOf(CommandUuid))) {
                            // for (BluetoothGattCharacteristic bgc : gattCharacteristics) {
                            Log.d("BLE Char discovered", "onServicesDiscovered: " + bgc.getUuid().toString());
                            if (bgc.getUuid().toString().equalsIgnoreCase(String.valueOf(MiscUuid))) {
                                misc = bgc;}
                            else if (bgc.getUuid().toString().equalsIgnoreCase(String.valueOf(MagicUuid))) {
                                magic = bgc;
                                setCharNotification(gatt, bgc);}}





                        //  if (service.getUuid().toString().equalsIgnoreCase(debugServiceUUID)) {
                       /* for (BluetoothGattCharacteristic bgc : gattCharacteristics) {
                            Log.d("BLE Char discovered", "onServicesDiscovered: " + bgc.getUuid().toString());
                            if (bgc.getUuid().toString().equalsIgnoreCase(setThresholdsCharUUID)) {
                                setThresholdsChar = bgc;
                            } else if (bgc.getUuid().toString().equalsIgnoreCase(distanceCharUUID)) {
                                distanceChar = bgc;
                                setCharNotification(gatt, bgc);
                            } else if (bgc.getUuid().toString().equalsIgnoreCase(distanceOutputTypeCharUUID)) {
                                distanceOutputTypeChar = bgc;
                                setCharNotification(gatt, bgc);
                            } else if (bgc.getUuid().toString().equalsIgnoreCase(distanceOutputPatternCharUUID)) {
                                distanceOutputPatternChar = bgc;
                                setCharNotification(gatt, bgc);
                            } else if (bgc.getUuid().toString().equalsIgnoreCase(batteryLevelCharUUID)) {
                                batteryLevelChar = bgc;
                                setCharNotification(gatt, bgc);
                            } else if (bgc.getUuid().toString().equalsIgnoreCase(errorsCharUUID)) {
                                errorsChar = bgc;
                                setCharNotification(gatt, bgc);
                            }
                        }
                    }*/ /* if (service.getUuid().toString().equalsIgnoreCase(deviceStatusServiceUUID)) {
                        for (BluetoothGattCharacteristic bgc : gattCharacteristics) {
                            Log.d("BLE Char discovered", "onServicesDiscovered: " + bgc.getUuid().toString());
                            if (bgc.getUuid().toString().equalsIgnoreCase(batterylevelUUID)) {
                               batterylevel = bgc;
                            } else if (bgc.getUuid().toString().equalsIgnoreCase(operatingModeStatusUUID)) {
                               operatingModeStatus = bgc;
                                setCharNotification(gatt, bgc);
                            }
                            else if (bgc.getUuid().toString().equalsIgnoreCase(muteStatusUUID)) {
                                muteStatus = bgc;
                                setCharNotification(gatt, bgc);
                            }else if (bgc.getUuid().toString().equalsIgnoreCase(errorsUUID)) {
                                errors = bgc;
                                setCharNotification(gatt, bgc);
                            }
                        }
                    } else if (service.getUuid().toString().equalsIgnoreCase(settingsStatusUUID)) {
                        for (BluetoothGattCharacteristic bgc : gattCharacteristics) {
                            Log.d("BLE Char discovered", "onServicesDiscovered: " + bgc.getUuid().toString());
//                            if (bgc.getUuid().toString().equalsIgnoreCase(outputTypeCharUUID)) {
//                                outputTypeChar = bgc;
//                            } else if (bgc.getUuid().toString().equalsIgnoreCase(outputPatternCharUUID)) {
//                                outputPatternChar = bgc;
//                            }
                            if (bgc.getUuid().toString().equalsIgnoreCase(operatingModeUUID)) {
                               operatingMode = bgc;
                            }
                            if (bgc.getUuid().toString().equalsIgnoreCase(vibrationLevelUUID)) {
                               vibrationLevel = bgc;
                                setCharNotification(gatt, bgc);
                            }
                            if (bgc.getUuid().toString().equalsIgnoreCase(buzzerSoundUUID)) {
                               buzzerSound = bgc;
                                setCharNotification(gatt, bgc);
                            }
                        }
                    } else if (service.getUuid().toString().equalsIgnoreCase(commandsUUID)) {
                        for (BluetoothGattCharacteristic bgc : gattCharacteristics) {
                            //Log.d("BLE Char discovered", "onServicesDiscovered: " + bgc.getUuid().toString());
                          //  if (bgc.getUuid().toString().equalsIgnoreCase(miscUUID)) {
                              // misc = bgc;
                           // }
                         if (bgc.getUuid().toString().equalsIgnoreCase(magicButtonUUID)) {
                               magicButton = bgc;
                                setCharNotification(gatt, bgc);
                            }

                        }
                    }
                }*/
                    }}
                class enableCharNotificationDescriptor implements Runnable {
                    //        runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for (BluetoothGattCharacteristic bgcTemp : bgcNotificationArray) {
                                while (bleDescriptorWritten == false) ;
                                BluetoothGattDescriptor descriptor = bgcTemp.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
                                descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                                gatt.writeDescriptor(descriptor);
                                bleDescriptorWritten = false;


                            }
                        } catch (Exception e) {
                            Log.i("enableCharNotificationDescriptor exception", "run: " + e);
                        }
                    }
                }
                ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
                exec.execute(new enableCharNotificationDescriptor());
                resetChar = false;
            }

        }
        public List<BluetoothGattService> getSupportedGattServices() {
            if (bluetoothGatt == null) return null;

            return bluetoothGatt.getServices();
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.i("[BLE]", "onCharacteristicRead status: " + status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.i("[BLERead]", characteristic.getUuid().toString());
                Log.i("[BLERead]", String.valueOf(characteristic.getValue()));
                // common.thresholdBytes = characteristic.getValue();
                if (characteristic == batteryLevelChar) {
                    common.batterylevel = characteristic.getValue()[0];
                    Log.d("Batterylevel", "onCharacteristicChanged, batterylevel: " + common.batterylevel);
                } else if (characteristic == operatingModeStatus) {
                    common.operatingModeStatus = characteristic.getValue()[0];
                    Log.d("operatingModeStatus", "onCharacteristicChanged, operatingModeStatus: " + common.operatingModeStatus);
                }


                /*else if (characteristic == muteStatus) {
                    common.muteStatus = characteristic.getValue()[0];
                } else if (characteristic == errors) {
                    common.errors = characteristic.getValue()[0];
                }*/ else if (characteristic == operatingMode) {
                    common.operatingMode = characteristic.getValue()[0];
                    Log.d("operatingMode", "onCharacteristicChanged, operatingMode: " + common.operatingMode);
                } else if (characteristic == vibration) {
                    common.vibrationLevel = characteristic.getValue()[0];
                    Log.d("vibration", "onCharacteristicChanged, vibration: " + common.vibrationLevel);
                    // Log.i("vib", String.valueOf(common.vibrationLevel));
                    // batteryTextView.setText(common.vibrationLevel));
                } else if (characteristic == buzzer) {
                    common.buzzerSound = characteristic.getValue()[0];
                    Log.d("buzzer", "onCharacteristicChanged, buzzer: " + common.buzzerSound);
                }
                // common.debugReadReadyFLag = true;
//                    DebugActivity.getInstance().displayThresholdValues();
            }


            else if(BluetoothGatt.GATT_INSUFFICIENT_AUTHENTICATION == status ||
                    BluetoothGatt.GATT_INSUFFICIENT_ENCRYPTION == status)
            {
                /*
                 * failed to complete the operation because of encryption issues,
                 * this means we need to bond with the device
                 */

                /*
                 * registering Bluetooth BroadcastReceiver to be notified
                 * for any bonding messages
                 */
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
                MainActivity mActivity = new MainActivity();
                mActivity.registerReceiver(mReceiver, filter);
            }
            else
            {
                // operation failed for some other reason
            }
        }


        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.i("[BLEWrite]", "------------- onCharacteristicWrite status: " + status);
//            BluetoothGatt.GATT_WRITE_NOT_PERMITTED
            // handler callback of write characteristic.
            // do somethings here.
            // gatt.writeCharacteristic(characteristic);
            if (characteristic == operatingMode) {
                common.operatingMode = characteristic.getValue()[0];
                Log.d("operatingMode", "onCharacteristicChanged, operatingMode: " + common.operatingMode);

            }
            if (characteristic == vibration) {
                common.vibrationLevel = characteristic.getValue()[0];
                Log.d("vibration", "onCharacteristicChanged, vibration: " + common.vibrationLevel);

            } else if (characteristic == buzzer) {
                common.buzzerSound = characteristic.getValue()[0];
                Log.d("vibration", "onCharacteristicChanged, vibration: " + common.buzzerSound);

            }
            else if (characteristic == misc) {
                //common.misc = characteristic.getValue()[0];
            }

            else if(BluetoothGatt.GATT_INSUFFICIENT_AUTHENTICATION == status ||
                    BluetoothGatt.GATT_INSUFFICIENT_ENCRYPTION == status)
            {
                /*
                 * failed to complete the operation because of encryption issues,
                 * this means we need to bond with the device
                 */

                /*
                 * registering Bluetooth BroadcastReceiver to be notified
                 * for any bonding messages
                 */
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
                MainActivity mActivity = new MainActivity();
                mActivity.registerReceiver(mReceiver, filter);
            }
            else
            {
                // operation failed for some other reason
            }


        }
      /*  @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.i("[BLE]", "onCharacteristicRead status: " + status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.i("[BLE]", characteristic.getUuid().toString());
                Log.i("[BLE]", String.valueOf(characteristic.getValue()));
                common.thresholdBytes = characteristic.getValue();
               /* if (characteristic == setThresholdsChar) {
                    int idxOrg = 0, v;
                    for (int i = 0; i < 12; i++) {
                        common.thresholdInt[i] = 0;
                        v = 0x000000FF & common.thresholdBytes[idxOrg];
                        common.thresholdInt[i] = common.thresholdInt[i] | v;
                        idxOrg++;
                        v = 0x000000FF & common.thresholdBytes[idxOrg];
                        common.thresholdInt[i] = common.thresholdInt[i] | (v << 8);
                        idxOrg++;
                        v = 0x000000FF & common.thresholdBytes[idxOrg];
                        common.thresholdInt[i] = common.thresholdInt[i] | (v << 16);
                        idxOrg++;
                        v = 0x000000FF & common.thresholdBytes[idxOrg];
                        common.thresholdInt[i] = common.thresholdInt[i] | (v << 24);
                        idxOrg++;
                        Log.d("debug distance/threshold", "onCharacteristicRead: " + common.thresholdInt[i]);
                    }
                    common.debugReadReadyFLag = true;
//                    DebugActivity.getInstance().displayThresholdValues();
                }
            }
        }*/

      /*  @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.i("[BLE]", "------------- onCharacteristicWrite status: " + status);
//            BluetoothGatt.GATT_WRITE_NOT_PERMITTED
            // handler callback of write characteristic.
            // do somethings here.
        }*/


        //enable notifcations by setting the respective config flag

        /* public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic,
                                                   boolean enabled) {

             BluetoothGatt mBluetoothGatt = null;
             BluetoothAdapter BA = null;
             if (BA == null || mBluetoothGatt == null) {

                 Log.d("TAG", "BluetoothAdapter no inicializado");
                 return;
             }

             mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);

         }*/
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            displayCharacteristic(characteristic);
            //  broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);

            if (characteristic == batteryLevelChar) {
                common.batterylevel  = characteristic.getValue()[0];

                Log.d("BatteryLevel", "onCharacteristicChanged, batteryLevel: " + common.batterylevel);
            } else if (characteristic == operatingModeStatus) {
                common.operatingModeStatus = characteristic.getValue()[0];
                Log.d("operatingModeStatus", "onCharacteristicChanged, operatingModeStatus: " +  common.operatingModeStatus);
            }/* else if (characteristic == muteStatus) {
                common.muteStatus = characteristic.getValue()[0];
            } else if (characteristic == errors) {
                common.errors = characteristic.getValue()[0];
            }*/ else if (characteristic == magic) {
                common.magicbtn = characteristic.getValue()[0];
                Log.d("magicbtn", "onCharacteristicChanged, magicbtn: " + common.magicbtn);



                // });
                // if (common.magicbtn == 1){
                //  Log.d("OMS", "click");
                //  setDebugCharNotification();
                // Toast.makeText( BLEController.this, "You Clicked Settings", Toast.LENGTH_LONG).show();
                       /* Intent intentEmergency = new Intent(getBaseContext(), EmergencyMainActivity.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
                        startActivity(intentEmergency);*/
                // }*/

                // broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);

            }

        }


    /*  private void broadcastUpdate(final String action) {
          final Intent intent = new Intent(action);
          sendBroadcast(intent);
      }*/
    /*  private void broadcastUpdate(final String action,
                                   final BluetoothGattCharacteristic characteristic) {
          final Intent intent = new Intent(action);

          // This is special handling for the Heart Rate Measurement profile.  Data parsing is
          // carried out as per profile specifications:
          // http://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.heart_rate_measurement.xml
          if (characteristic == magic) {
              common.magicbtn = characteristic.getValue()[0];
             // common.magicbtn = characteristic.getValue()[0];
              Log.d("magicbtn", "onCharacteristicChanged, magicbtn: " + common.magicbtn);
              intent.putExtra(EXTRA_DATA, String.valueOf(common.magicbtn));
              }

       sendBroadcast(intent);
      }*/


        // if (toggleButton.isChecked()) {
        // textview1.setText("CONNECT");
                    /*    Log.d("initButtons", "onClick: Connecting...");
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast toast = Toast.makeText(getApplicationContext(), "Toast", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                       // bleController.connectToDevice(common.deviceAddress);
      /*  } else {
            // textview1.setText("DISCONNECT");
            Log.d("initButtons", "onClick: Disconnecting...");
            Toast.makeText(getApplicationContext(), "You Clicked Disconnect", Toast.LENGTH_LONG).show();
            //bleController.disconnect();
            ;
        }*/



        //}*/


      /*  @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            displayCharacteristic(characteristic);
            if (characteristic == distanceChar) {
                byte dis[] = new byte[4];
                dis = characteristic.getValue();
                int idxOrg = 0, v = 0;
                common.distance = 0;
                v = 0x000000FF & dis[idxOrg];
                common.distance = common.distance | v;
                idxOrg++;
                v = 0x000000FF & dis[idxOrg];
                common.distance = common.distance | (v << 8);
                idxOrg++;
                v = 0x000000FF & dis[idxOrg];
                common.distance = common.distance | (v << 16);
                idxOrg++;
                v = 0x000000FF & dis[idxOrg];
                common.distance = common.distance | (v << 24);
                idxOrg++;
                Log.d("Distance", "onCharacteristicChanged, distance: " + common.distance);
            } else if (characteristic == distanceOutputTypeChar) {
                common.currentType = characteristic.getValue()[0];
                Log.d("Distance", "onCharacteristicChanged, distanceOutputType: " + common.currentType);
            } else if (characteristic == distanceOutputPatternChar) {
                common.currentPattern = characteristic.getValue()[0];
            } else if (characteristic == batteryLevelChar) {
                common.batteryLevel = characteristic.getValue()[0];
            } else if (characteristic == vibrationChar) {
                common.vibration = characteristic.getValue()[0];
            } else if (characteristic == buzzerChar) {
                common.buzzer = characteristic.getValue()[0];
            } else if (characteristic == modeIOChar) {
                common.mode = characteristic.getValue()[0];
            } else if (characteristic == muteChar) {
                common.mute = characteristic.getValue()[0];
            } else if (characteristic == gesturesChar) {
                common.gesture = characteristic.getValue()[0];
            } else if (characteristic == errorsChar) {
                common.error = characteristic.getValue()[0];
            }
        }
//        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, byte[] value) {
//            Log.i("[BLE]", "onCharacteristicChanged: " + value);
//            displayCharacteristic(characteristic);
//        }

        //        @Override
//        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
//            // Log.d("onReadRemoteRssi rssi = " + rssi + "; status = " +
//            // status);
//            if (mBLEServiceCb != null) {
//                mBLEServiceCb.displayRssi(rssi);
//            }
//        }
       */



        // @Override
     /* public LocalBinder onBind(Intent intent) {
          return mBinder;
      }

     // @Override
      public boolean onUnbind(Intent intent) {
          // After using a given device, you should make sure that BluetoothGatt.close() is called
          // such that resources are cleaned up properly.  In this particular example, close() is
          // invoked when the UI is disconnecclass BluetoothLeService extends Service {
          //
          //    private Binder binder = new LocalBinder();
          //
          //    @Nullable
          //    @Override
          //    public IBinder onBind(Intent intent) {
          //        return binder;
          //    }
          //
          //    class LocalBinder extends Binder {
          //        public BluetoothLeService getService() {
          //            return BluetoothLeService.this;
          //        }
          //    }
          //}ted from the Service.
         // close();
         // return super.onUnbind(intent);
          return false;
      }

      private final LocalBinder mBinder =  new LocalBinder();*/

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            Log.i("[BLE]", descriptor.getUuid().toString() + " " + Arrays.toString(descriptor.getValue()));
            Log.i("[BLE]", "status:" + status);

            bleDescriptorWritten = true;

        }
    };

    public void setCharNotification(@NonNull BluetoothGatt gatt, BluetoothGattCharacteristic bgc) {
        gatt.setCharacteristicNotification(bgc, true);
        bgcNotificationArray.add(bgc);


    }





    private void initChars() {
       /* setThresholdsChar = null;
        distanceChar = null;
        distanceOutputTypeChar = null;
        distanceOutputPatternChar = null;
        batteryLevelChar = null;
        errorsChar = null;

        trainingCommandsChar = null;
        trainingStatusChar = null;

//        outputTypeChar = null;
//        outputPatternChar = null;
        profileChar = null;
        vibrationChar = null;
        buzzerChar = null;
        gesturesChar = null;
        modeIOChar = null;
        muteChar = null;

        navigationInstructionsChar = null;
        otherInstructionsChar = null;*/

        resetChar = true;
    }

    public void setDebugCharNotification() {
        this.bluetoothGatt.setCharacteristicNotification(batteryLevelChar, true);
        //Looper.prepare(); // to be able to make toast
        // Toast.makeText(common, "battery", Toast.LENGTH_LONG).show();
        // Looper.loop();
        this.bluetoothGatt.setCharacteristicNotification(magic, true);
//        this.bluetoothGatt.setCharacteristicNotification(distanceOutputPatternChar, true);
//        this.bluetoothGatt.setCharacteristicNotification(batteryLevelChar, true);
//        this.bluetoothGatt.setCharacteristicNotification(modeIOChar, true);
//        this.bluetoothGatt.setCharacteristicNotification(muteChar, true);
//        this.bluetoothGatt.setCharacteristicNotification(gesturesChar, true);
//        this.bluetoothGatt.setCharacteristicNotification(errorsChar, true);
    }

    public static void displayCharacteristic(final BluetoothGattCharacteristic characteristic) {
        Log.i("[BLE]", characteristic.getUuid().toString() + " " + characteristic.getValue());
       /* if (common.magicbtn == 1){
            Log.d("OMS", "click");
            // Toast.makeText(getApplicationContext(), "You Clicked Settings", Toast.LENGTH_LONG).show();
                       /* Intent intentEmergency = new Intent(getBaseContext(), EmergencyMainActivity.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
                        startActivity(intentEmergency);*/

        // overridePendingTransition(0, 0);
        //Toast.makeText(getApplicationContext(), "You Clicked Settings", Toast.LENGTH_LONG).show();


         /*   Looper.prepare(); // to be able to make toast
            //Toast.makeText(activity, "not connected", Toast.LENGTH_LONG).show();
            // Looper.loop();

            Toast.makeText(bleController, "Magic Btn ", Toast.LENGTH_LONG).show();
            Looper.loop();*/

    }



    public void writeBLEData(BluetoothGattCharacteristic btGattChar, byte[] data) {
        btGattChar.setValue(data);
        bluetoothGatt.writeCharacteristic(btGattChar);
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        bluetoothGatt.writeCharacteristic(btGattChar, data, BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
    }

    public void readBLEData(BluetoothGattCharacteristic btGattChar) {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
        Log.i("[BLE]", String.valueOf(bluetoothGatt.readCharacteristic(btGattChar)));
    }

    public boolean checkConnectedState() {
        return this.bluetoothManager.getConnectionState(this.device, GATT) == 2;
    }

    public void disconnect() {
        this.bluetoothGatt.disconnect();
//        this.bluetoothGatt.close();
    }

  /*  public List<BluetoothGattService> getSupportedGattServices() {
        if (bluetoothGatt == null) return null;

        return bluetoothGatt.getServices();
    }*/

  /* @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public class LocalBinder {

            BLEController getService() {
                return BLEController.this;
            }
        }
*/
}


