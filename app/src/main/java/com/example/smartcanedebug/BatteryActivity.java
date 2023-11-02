package com.example.smartcanedebug;

import android.app.Notification;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BatteryActivity extends AppCompatActivity {
    private TextView batteryTextView;
    private BLEController bleController;
    private Common common;

  /*  TextView text;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battery_activity);
        text = findViewById(R.id.text);
        BatteryManager bm = (BatteryManager)getSystemService(BATTERY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            int percentage = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            text.setText("Battery Percentage is "+percentage+" %");
        }
    }*/
  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.battery_activity);

      this.bleController = BLEController.getInstance(this);
      this.common = Common.getInstance();

    //  initEditText();
      initTextView();

//        debugActivity = this;
      bleController.setDebugCharNotification();
      common.debugReadReadyFLag = false;
      bleController.readBLEData(bleController.setThresholdsChar);


      class notifyUpdateTask implements Runnable {
          //        runOnUiThread(new Runnable() {
          @Override
          public void run() {
              try {
//                if (common.debugReadReadyFLag == true) {
//                    displayThresholdValues();
//                    common.debugReadReadyFLag = false;
//                }
                  runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          updateNotifyTexts();
                      }
                  });
//                    Log.i("notifyUpdateTask", "run: notifyUpdateTask");
              }
              catch (Exception e){
                  Log.i("notifyUpdateTask exception", "run: " + e);
              }
          }
      }
      ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
      exec.scheduleAtFixedRate(new notifyUpdateTask(), 0, 1000, TimeUnit.MILLISECONDS);
      // return null;
  }

    private void startForeground(int i, Notification notification) {
    }

//    @Override
//    public void onBackPressed() {
//        Intent intentMain = new Intent(this, MainActivity.class);
//        startActivity(intentMain);
//        finish();
//    }

//    public static DebugActivity getInstance(){
//        return debugActivity;
//    }

    void updateNotifyTexts(){

        batteryTextView.setText(String.valueOf(common.batteryLevel));

    }





    private void initTextView(){

        batteryTextView = findViewById(R.id.txtBatteryValue1);

//        bleController.readBLEData(bleController.distanceChar);
//        bleController.readBLEData(bleController.currentOutputTypeChar);
//        bleController.readBLEData(bleController.currentOutputPatternChar);
//        bleController.readBLEData(bleController.batteryLevelChar);
//        bleController.readBLEData(bleController.modeIOChar);
//        bleController.readBLEData(bleController.muteChar);
//        bleController.readBLEData(bleController.gesturesChar);
//        bleController.readBLEData(bleController.errorsChar);
    }
    public void goBackPressed(View view) {
        onBackPressed();
    }
}



