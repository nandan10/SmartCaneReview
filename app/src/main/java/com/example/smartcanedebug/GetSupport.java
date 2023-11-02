package com.example.smartcanedebug;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GetSupport extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_support);


    }



    public void onClick11(View view) {


        Toast.makeText(getApplicationContext(), "You Clicked Register", Toast.LENGTH_LONG).show();
        Intent intentRegister = new Intent(getBaseContext(), RegisterActivity.class);

        startActivity(intentRegister);
    }

    public void onClick12(View view) {


        Toast.makeText(getApplicationContext(), "You Clicked Report Issue", Toast.LENGTH_LONG).show();
        Intent intentReport = new Intent(getBaseContext(), ReportIssue.class);

        startActivity(intentReport);
    }


    public void goBackPressed(View view) {
        onBackPressed();
    }
}