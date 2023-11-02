package com.example.smartcanedebug;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.PermissionChecker;

import java.util.List;




public class EmergencyCall extends AppCompatActivity {
    private static final int IGNORE_BATTERY_OPTIMIZATION_REQUEST = 1002;
    private static final int PICK_CONTACT = 1;
    private PermissionChecker PackageManager;
    TextView Called;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_activity);
        Called = findViewById(R.id.Call);
        DbHelper db = new DbHelper(this);
        List<ContactModel> list = db.getAllContacts();


        // send SMS to each contact
        for (ContactModel c : list) {
            String phone_number = c.getPhoneNo();
            String Name =  c.getName();

            // Getting instance of Intent with action as ACTION_CALL
            Intent phone_intent = new Intent(Intent.ACTION_CALL);

            // Set data of Intent through Uri by parsing phone number
            phone_intent.setData(Uri.parse("tel:" + phone_number));
            Called.setText(Name+"\n"+phone_number);
            // start Intent
            startActivity(phone_intent);
        } }
    public void goBackPressed(View view) {
        onBackPressed();
    }}
