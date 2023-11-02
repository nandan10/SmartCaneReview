package com.example.smartcanedebug;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class LandingPage extends AppCompatActivity {
    Button startButton;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        startButton = findViewById(R.id.startButton);









    }

    public void gotoMainActivity(View view) {

        startActivity(new Intent(LandingPage.this,PermissionActivity.class));
    }
}
