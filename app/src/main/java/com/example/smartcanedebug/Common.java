/*
 * (c) Matey Nenov (https://www.thinker-talk.com)
 *
 * Licensed under Creative Commons: By Attribution 3.0
 * http://creativecommons.org/licenses/by/3.0/
 *
 */

package com.example.smartcanedebug;

import android.app.Application;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Common extends Application {
//    public SQLiteDatabase myDb;

//    public void BLEControllerConnected();
//    public void BLEControllerDisconnected();
//    public void BLEDeviceFound(String name, String address);
    private static Common instance;
    String deviceAddress;
    int[] thresholdInt = new int[12];
    byte[] thresholdBytes = new byte[48];
    public boolean debugReadReadyFLag = false;
//    public boolean debugNotifyDistanceFLag = false;
    public int distance;
    public byte currentType;
    public byte currentPattern;
    public byte batteryLevel;
    public byte mode;
    public byte mute;
    public byte gesture;
    public byte error;
    public byte vibration ;
    public byte buzzer ;

    final byte NoneType = 0;
    final byte HapticType = 1;
    final byte BuzzerType = 2;
    final byte BothType = 3;

    final byte NonePattern = 0;
    final byte BeginnerPattern = 1;
    final byte RegularPattern = 2;
    final byte CustomPattern = 3;

    final byte CustomDistancePattern1a = 0;
    final byte CustomDistancePattern1b = 1;
    final byte CustomDistancePattern1c = 2;
    final byte CustomDistancePattern1d = 3;
    final byte CustomDistancePattern2a = 0;
    final byte CustomDistancePattern2b = 1;
    final byte CustomDistancePattern2c = 2;
    final byte CustomDistancePattern2d = 3;
    final byte CustomDistancePattern3a = 0;
    final byte CustomDistancePattern3b = 1;
    final byte CustomDistancePattern3c = 2;
    final byte CustomDistancePattern3d = 3;
    final byte CustomDistancePattern4a = 0;
    final byte CustomDistancePattern4b = 1;
    final byte CustomDistancePattern4c = 2;
    final byte CustomDistancePattern4d = 3;

    final byte CustomNaviPattern1a = 0;
    final byte CustomNaviPattern1b = 1;
    final byte CustomNaviPattern1c = 2;
    final byte CustomNaviPattern1d = 3;
    final byte CustomNaviPattern2a = 0;
    final byte CustomNaviPattern2b = 1;
    final byte CustomNaviPattern2c = 2;
    final byte CustomNaviPattern2d = 3;
    final byte CustomNaviPattern3a = 0;
    final byte CustomNaviPattern3b = 1;
    final byte CustomNaviPattern3c = 2;
    final byte CustomNaviPattern3d = 3;
    final byte CustomNaviPattern4a = 0;
    final byte CustomNaviPattern4b = 1;
    final byte CustomNaviPattern4c = 2;
    final byte CustomNaviPattern4d = 3;

    final byte[] OTHER_CMD_IDENTIFY_SMARTCANE = new byte[]{1};

    final byte[] ACTIVATE_PROFILE_BEGINNER = new byte[]{HapticType, BeginnerPattern, NoneType, NonePattern, 0, 0, 0, 0, 0, 0, 0, 0};
    final byte[] ACTIVATE_PROFILE_BEGINNER_NAVI = new byte[]{HapticType, BeginnerPattern, HapticType, BeginnerPattern, 0, 0, 0, 0, 0, 0, 0, 0};
    final byte[] ACTIVATE_PROFILE_REGULAR = new byte[]{HapticType, RegularPattern, NoneType, NonePattern, 0, 0, 0, 0, 0, 0, 0, 0};
    final byte[] ACTIVATE_PROFILE_REGULAR_NAVI = new byte[]{HapticType, RegularPattern, HapticType, RegularPattern, 0, 0, 0, 0, 0, 0, 0, 0};
    byte[] ACTIVATE_PROFILE_CUSTOM1 = new byte[]{BuzzerType, CustomPattern, BuzzerType, CustomPattern, CustomDistancePattern1a, CustomDistancePattern2a, CustomDistancePattern3a, CustomDistancePattern4a, CustomNaviPattern1a, CustomNaviPattern2a, CustomNaviPattern3a, CustomNaviPattern4a};
    byte[] ACTIVATE_PROFILE_CUSTOM2 = new byte[]{BothType, CustomPattern, BothType, CustomPattern, CustomDistancePattern1b, CustomDistancePattern2b, CustomDistancePattern3b, CustomDistancePattern4b, CustomNaviPattern1b, CustomNaviPattern2b, CustomNaviPattern3b, CustomNaviPattern4b};

   // final byte[] ACTIVATE_INTENSITY_VIBRATION = new byte[]{1,2,3,4,5,6,7,8,9,10};
   // final byte[] ACTIVATE_INTENSITY_BUZZER = new byte[]{1,2,3,4,5,6,7,8,9,10};
    @Override
    public void onCreate() {
        super.onCreate();
//        myDb = openOrCreateDatabase("NavMobDB", Context.MODE_PRIVATE, null);
    }

//    private Common(Context ctx) {
//        this.bluetoothManager = (BluetoothManager) ctx.getSystemService(Context.BLUETOOTH_SERVICE);
//    }

    public static Common getInstance() {
        if (instance == null) {
            instance = new Common();
        }
        return instance;
    }

//    public static byte[] intToByteArray(int a)
//    {
//        byte[] ret = new byte[4];
//        ret[3] = (byte) (a & 0xFF);
//        ret[2] = (byte) ((a >> 8) & 0xFF);
//        ret[1] = (byte) ((a >> 16) & 0xFF);
//        ret[0] = (byte) ((a >> 24) & 0xFF);
//        return ret;
//    }
}
