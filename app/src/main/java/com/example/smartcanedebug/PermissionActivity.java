package com.example.smartcanedebug;
import static com.example.smartcanedebug.MainActivity.BA;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import java.util.List;

public class PermissionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission_activity);

        // initializing our button and adding on click listener to it.
        Button requestPermissionsBtn = findViewById(R.id.RequestPermission);
        requestPermissionsBtn.setOnClickListener(v -> {

            // inside on click listener calling method to request permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                requestPermissions();

            }
            // mainWork();

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    private void requestPermissions() {
        // below line is use to request permission in the current activity.
        // this method is use to handle error in runtime permissions
        Dexter.withActivity(this)
                // below line is use to request the number of permissions which are required in our app.
                .withPermissions(//Manifest.permission.CAMERA,
                        // below is the list of permissions
               Manifest.permission.BLUETOOTH,Manifest.permission.BLUETOOTH_ADMIN,


   Manifest.permission.BLUETOOTH_CONNECT,
   Manifest.permission.BLUETOOTH_SCAN,
                              Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION,

        Manifest.permission.READ_CONTACTS,
 //  Manifest.permission.INTERNET,

  Manifest.permission.WRITE_EXTERNAL_STORAGE,
  // Manifest.permission.ACCESS_NETWORK_STATE,
   Manifest.permission.WRITE_CONTACTS,
   Manifest.permission.CALL_PHONE,
   Manifest.permission.SEND_SMS,

   Manifest.permission.FOREGROUND_SERVICE)
                // after adding permissions we are calling an with listener method.
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        // this method is called when all permissions are granted
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            // do you work now
                            Toast.makeText(PermissionActivity.this, "All the permissions are granted..", Toast.LENGTH_SHORT).show();

                    mainWork();

                        }
                        // check for permanent denial of any permission
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permanently, we will show user a dialog message.
                            showSettingsDialog();
                        }
                        //mainWork();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        // this method is called when user grants some permission and denies some of them.
                        permissionToken.continuePermissionRequest();
                    }
                }).withErrorListener(error -> {
                    // we are displaying a toast message for error message.
                    Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                })
                // below line is use to run the permissions on same thread and to check the permissions
                .onSameThread().check();
       // mainWork();

    }

    // below is the shoe setting dialog method which is use to display a dialogue message.
    private void showSettingsDialog() {
        // we are displaying an alert dialog for permissions
        AlertDialog.Builder builder = new AlertDialog.Builder(PermissionActivity.this);

        // below line is the title for our alert dialog.
        builder.setTitle("Need Permissions");

        // below line is our message for our dialog
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            // this method is called on click on positive button and on clicking shit button
            // we are redirecting our user from our app to the settings page of our app.
            dialog.cancel();
            // below is the intent from which we are redirecting our user.
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, 101);

        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            // this method is called when user click on negative button.
            dialog.cancel();

        });
        // below line is used to display our dialog
        builder.show();

    }



    private void mainWork() {
        Intent intent = new Intent(PermissionActivity.this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}
