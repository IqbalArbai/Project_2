package com.tutorial.project_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MonitoringActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);
    }


    @Override
    public void onBackPressed() {
        Intent goHome = new Intent(MonitoringActivity.this, HomeActivity.class);
        startActivity(goHome);
        finish();
    }
}