package com.tutorial.project_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class HomeActivity extends AppCompatActivity  {
    SwitchCompat sc_trigger;

    private long backPressTime;
    private Toast backToast;

    private FirebaseAuth mAuth;

    private static final String MY_PREFS = "switch_prefs"; // nama shared preferences
    private static final String SWITCH_STATUS = "switch_status";

    boolean switch_status;

    SharedPreferences myPreferences;
    SharedPreferences.Editor myEditor;

    MqttAndroidClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        // save state switch button set value trigger otomatis
        sc_trigger = findViewById(R.id.sw_trigger_otomatis);

        String clientId = MqttClient.generateClientId();
        MqttConnectOptions options = new MqttConnectOptions();
        client = new MqttAndroidClient(HomeActivity.this, "tcp://broker.mqttdashboard.com:1883", clientId);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(HomeActivity.this,"Terhubung",Toast.LENGTH_LONG).show();

                    sc_trigger.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                        if (compoundButton.isChecked()){
                            myEditor.putBoolean(SWITCH_STATUS, true);
                            myEditor.apply();
                            sc_trigger.setChecked(true);

                            String topic = "riezkyf1";
                            String payload = "B";
                            byte[] encodedPayload = new byte[0];
                            try {
                                encodedPayload = payload.getBytes("UTF-8");
                                MqttMessage message = new MqttMessage(encodedPayload);
                                client.publish(topic, message);
                            } catch (UnsupportedEncodingException | MqttException e) {
                                e.printStackTrace();
                            }
                        }else {
                            myEditor.putBoolean(SWITCH_STATUS, false);
                            myEditor.apply();
                            sc_trigger.setChecked(false);

                            String topic = "riezkyf1";
                            String payload = "A";
                            byte[] encodedPayload = new byte[0];
                            try {
                                encodedPayload = payload.getBytes("UTF-8");
                                MqttMessage message = new MqttMessage(encodedPayload);
                                client.publish(topic, message);
                            } catch (UnsupportedEncodingException | MqttException e) {
                                e.printStackTrace();
                            }
                        }
                    }); // sampe sini

                    client.setCallback(new MqttCallback() {
                        @Override
                        public void connectionLost(Throwable cause) {

                        }

                        @Override
                        public void messageArrived(String topic, MqttMessage message) throws Exception {

                        }

                        @Override
                        public void deliveryComplete(IMqttDeliveryToken token) {

                        }
                    });

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(HomeActivity.this,"GAGAL Terhubung",Toast.LENGTH_LONG).show();
                }
            });
        } catch (MqttException e){
            e.printStackTrace();
        }

        myPreferences = getSharedPreferences(MY_PREFS,MODE_PRIVATE);
        myEditor = getSharedPreferences(MY_PREFS,MODE_PRIVATE).edit();

        switch_status = myPreferences.getBoolean(SWITCH_STATUS,false); // default awal memang false

        sc_trigger.setChecked(switch_status);


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

        Button btnLogout = findViewById(R.id.btn_Logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent goLogin = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(goLogin);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (backPressTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            finish();
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to Exit App", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressTime = System.currentTimeMillis();
    }
}