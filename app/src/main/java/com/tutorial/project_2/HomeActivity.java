package com.tutorial.project_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CardView CvMonitoring  = findViewById(R.id.btn_monitoring);
        CvMonitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goMonitoring = new Intent(HomeActivity.this, MonitoringActivity.class);
                startActivity(goMonitoring);
                finish();
            }
        });

        CardView CvControlling  = findViewById(R.id.btn_controlling);
        CvControlling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goControlling = new Intent(HomeActivity.this, ControllingActivity.class);
                startActivity(goControlling);
                finish();
            }
        });

    }


}