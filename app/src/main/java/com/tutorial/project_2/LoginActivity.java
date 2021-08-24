package com.tutorial.project_2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText et_username_login, et_password_login;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username_login= findViewById(R.id.et_username_login);
        et_password_login= findViewById(R.id.et_password_login);


        mAuth = FirebaseAuth.getInstance();

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
                loginUser();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent goFirst = new Intent(LoginActivity.this, FirstActivity.class);
        startActivity(goFirst);
        finish();
    }


    public void loginUser(){
        String email = et_username_login.getText().toString();
        String password= et_password_login.getText().toString();

        if (TextUtils.isEmpty(email)){
            et_username_login.setError("Email tidak boleh kosong");
            et_username_login.requestFocus(); // tampilan display nya
        } else if (TextUtils.isEmpty(password)){
            et_password_login.setError("Password tidak boleh kosong");
            et_password_login.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Gagal" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }
}