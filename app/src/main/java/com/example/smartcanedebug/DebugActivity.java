package com.example.smartcanedebug;

import android.bluetooth.BluetoothGatt;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DebugActivity extends AppCompatActivity {
    private BLEController bleController;
    private Common common;

    private Button setThresholdButton;

    private EditText distance1EditText;
    private EditText distance2EditText;
    private EditText distance3EditText;
    private EditText distance4EditText;
    private EditText distance5EditText;
    private EditText distance6EditText;

    private EditText threshold1EditText;
    private EditText threshold2EditText;
    private EditText threshold3EditText;
    private EditText threshold4EditText;
    private EditText threshold5EditText;
    private EditText threshold6EditText;

    private TextView distanceTextView;
    private TextView currentTypeTextView;
    private TextView currentPatternTextView;
    private TextView batteryTextView;
    private TextView modeTextView;
    private TextView muteTextView;
    private TextView gestureTextView;
    private TextView errorsTextView;

//    static DebugActivity debugActivity;
    
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        this.bleController = BLEController.getInstance(this);
        this.common = Common.getInstance();

        initEditText();
        initTextView();
        initButtons();
//        debugActivity = this;
        bleController.setDebugCharNotification();
        common.debugReadReadyFLag = false;
        bleController.readBLEData(bleController.setThresholdsChar);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    while (common.debugReadReadyFLag == false);
                    displayThresholdValues();
            }
        });

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
        distanceTextView.setText(String.valueOf(common.distance));
        distanceTextView.setText(String.valueOf(common.distance));
        currentTypeTextView.setText(String.valueOf(common.currentType));
        currentPatternTextView.setText(String.valueOf(common.currentPattern));
        batteryTextView.setText(String.valueOf(common.batteryLevel));
        modeTextView.setText(String.valueOf(common.mode));
        muteTextView.setText(String.valueOf(common.mute));
        gestureTextView.setText(String.valueOf(common.gesture));
        errorsTextView.setText(String.valueOf(common.error));
    }
    void updateDistanceText() {
        distanceTextView.setText(String.valueOf(common.distance));
    }

    private void initButtons() {
        this.setThresholdButton = findViewById(R.id.btnSetThreshold);
        this.setThresholdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                common.thresholdInt[0] = Integer.parseInt(distance1EditText.getText().toString());
                common.thresholdInt[1] = Integer.parseInt(distance2EditText.getText().toString());
                common.thresholdInt[2] = Integer.parseInt(distance3EditText.getText().toString());
                common.thresholdInt[3] = Integer.parseInt(distance4EditText.getText().toString());
                common.thresholdInt[4] = Integer.parseInt(distance5EditText.getText().toString());
                common.thresholdInt[5] = Integer.parseInt(distance6EditText.getText().toString());

                common.thresholdInt[6] = Integer.parseInt(threshold1EditText.getText().toString());
                common.thresholdInt[7] = Integer.parseInt(threshold2EditText.getText().toString());
                common.thresholdInt[8] = Integer.parseInt(threshold3EditText.getText().toString());
                common.thresholdInt[9] = Integer.parseInt(threshold4EditText.getText().toString());
                common.thresholdInt[10] = Integer.parseInt(threshold5EditText.getText().toString());
                common.thresholdInt[11] = Integer.parseInt(threshold6EditText.getText().toString());

                int idxDst = 0;
                for (int i=0; i<12; i++){
                    common.thresholdBytes[idxDst] = (byte)(common.thresholdInt[i]);
                    idxDst++;
                    common.thresholdBytes[idxDst] = (byte)(common.thresholdInt[i] >> 8);
                    idxDst++;
                    common.thresholdBytes[idxDst] = (byte)(common.thresholdInt[i] >> 16);
                    idxDst++;
                    common.thresholdBytes[idxDst] = (byte)(common.thresholdInt[i] >> 24);
                    idxDst++;
                }
                bleController.writeBLEData(bleController.setThresholdsChar, common.thresholdBytes);
            }
        });
    }

    private void initEditText(){
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
    }

    private void initTextView(){
        distanceTextView = findViewById(R.id.txtDistanceValue);
        currentTypeTextView = findViewById(R.id.txtCurrentTypeValue);
        currentPatternTextView = findViewById(R.id.txtCurrentPatternValue);
        batteryTextView = findViewById(R.id.txtBatteryValue);
        modeTextView = findViewById(R.id.txtModeValue);
        muteTextView = findViewById(R.id.txtMuteValue);
        gestureTextView = findViewById(R.id.txtGestureValue);
        errorsTextView = findViewById(R.id.txtErrorsValue);
//        bleController.readBLEData(bleController.distanceChar);
//        bleController.readBLEData(bleController.currentOutputTypeChar);
//        bleController.readBLEData(bleController.currentOutputPatternChar);
//        bleController.readBLEData(bleController.batteryLevelChar);
//        bleController.readBLEData(bleController.modeIOChar);
//        bleController.readBLEData(bleController.muteChar);
//        bleController.readBLEData(bleController.gesturesChar);
//        bleController.readBLEData(bleController.errorsChar);
    }

    public void displayThresholdValues(){
        distance1EditText.setText(String.valueOf(common.thresholdInt[0]));
        distance2EditText.setText(String.valueOf(common.thresholdInt[1]));
        distance3EditText.setText(String.valueOf(common.thresholdInt[2]));
        distance4EditText.setText(String.valueOf(common.thresholdInt[3]));
        distance5EditText.setText(String.valueOf(common.thresholdInt[4]));
        distance6EditText.setText(String.valueOf(common.thresholdInt[5]));
        threshold1EditText.setText(String.valueOf(common.thresholdInt[6]));
        threshold2EditText.setText(String.valueOf(common.thresholdInt[7]));
        threshold3EditText.setText(String.valueOf(common.thresholdInt[8]));
        threshold4EditText.setText(String.valueOf(common.thresholdInt[9]));
        threshold5EditText.setText(String.valueOf(common.thresholdInt[10]));
        threshold6EditText.setText(String.valueOf(common.thresholdInt[11]));
    }
    public void goBackPressed(View view) {
        onBackPressed();
    }
}

