package com.example.smartcanedebug;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class EmergencyCall extends AppCompatActivity {
    private static final int IGNORE_BATTERY_OPTIMIZATION_REQUEST = 1002;
    private static final int PICK_CONTACT = 1;
    private PermissionChecker PackageManager;
    TextView Called;
    ListView call;
    private ImageView  callIV;
    DbHelper db;

    List<ContactModel> list;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_activity);
        Called = findViewById(R.id.Call);
        // call = findViewById(R.id.listView2);
        DbHelper db = new DbHelper(this);

        List<ContactModel> list = db.getAllContacts();
        boolean noData = list.isEmpty();
        if(noData){


            showStartDialog();

        }

        //CustomAdapter customAdapter = new CustomAdapter(this, list);
        // customAdapter.addAll();

        //call.setAdapter(customAdapter);




          //  for (ContactModel c : list) {
                ContactModel c0 = list.get(0);
                //  i = (int) listItems.get(i);
              //  final ContactModel c = c0;
                String phone_number0 = c0.getPhoneNo();

                String Name0 = c0.getName();
                Intent phone_intent = new Intent(Intent.ACTION_CALL);

                // Set data of Intent through Uri by parsing phone number
                phone_intent.setData(Uri.parse("tel:" + phone_number0));
                Called.setText(Name0 + "\n" + phone_number0);
                // start Intent
                startActivity(phone_intent);

          //  }
        ArrayList listItems = new ArrayList();
            for (ContactModel c : list) {
            String phone_number = c.getPhoneNo();
            String Name = c.getName();

                listItems.add(new NumbersView(R.drawable.ic_phone,Name));


                NumbersViewAdapter numbersArrayAdapter = new NumbersViewAdapter(this, listItems);

                // create the instance of the ListView to set the numbersViewAdapter
                ListView numbersListView = findViewById(R.id.listView2);

                // set the numbersViewAdapter for ListView
                numbersListView.setAdapter(numbersArrayAdapter);
                numbersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override

                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        // for (ContactModel c : list) {
                        //   ArrayList listItems1 = new ArrayList();
                        ContactModel c2 = list.get(i);
                        //  i = (int) listItems.get(i);
                        final ContactModel c1 = c2;
                        String phone_number1 = c1.getPhoneNo();
                        //  String sub_List = String.valueOf(c1);
                        //  listItems1.add(sub_List);
                        // for (i = 0; i < listItems.size(); i++) {
                        System.out.println("List of first three elements: " + phone_number1);
                        // }
                         /* String phone_number = c.getPhoneNo();
                          String Name = c.getName();
                          Intent callIntent = new Intent(Intent.ACTION_CALL);
                          // on below line we are setting data to it.
                          callIntent.setData(Uri.parse("tel:" + phone_number));
                          // on below line we are checking if the calling permissions are granted not.

                          // at last we are starting activity.
                          startActivity(callIntent);


                      }*/


                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        // on below line we are setting data to it.
                        callIntent.setData(Uri.parse("tel:" + phone_number1));
                        // on below line we are checking if the calling permissions are granted not.
                        if (ActivityCompat.checkSelfPermission(EmergencyCall.this,
                                Manifest.permission.CALL_PHONE) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        // at last we are starting activity.
                        startActivity(callIntent);


                        //String phone_number = c.getPhoneNo();


                        // this method is called for making a call.
                        // on below line we are calling an intent to make a call.
                      /*  Intent callIntent = new Intent(Intent.ACTION_CALL);
                        // on below line we are setting data to it.
                        callIntent.setData(Uri.parse("tel:" + phone_number1));
                        // on below line we are checking if the calling permissions are granted not.

                        // at last we are starting activity.
                        startActivity(callIntent);*/


                        // send SMS to each contact
                    } });



                // Getting instance of Intent with action as ACTION_CALL



                   }
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation3);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.call);

        // Perform item selected listener
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

                        return true;
                }
                return false;
            }
        });

      }


        private void showStartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Essential Information!")
                .setMessage("In order to use Emergency Call, Please select contacts you want to connect to, through Emergency Settings.")
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


    }


    public void onClick20(View view) {

        Toast.makeText(getApplicationContext(), "You Clicked Emergency SMS", Toast.LENGTH_LONG).show();
        Intent intentEmergencySms = new Intent(getBaseContext(), EmergencySms.class);
//                intentNA.putExtra("Type", NAV_TYPE_LOAD_ROUTE);
        startActivity(intentEmergencySms);
    }

    public void goBackPressed (View view){
            onBackPressed();
        }
    }
