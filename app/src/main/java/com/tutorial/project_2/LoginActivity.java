package com.tutorial.project_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(goRegister);
                finish();
            }
        });

        Button btnToHome = findViewById(R.id.btn_input_login);
        btnToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goHome = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(goHome);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent goFirst = new Intent(LoginActivity.this, FirstActivity.class);
        startActivity(goFirst);
        finish();
    }
}