package com.tutorial.project_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MonitoringActivity extends AppCompatActivity {

    // inisialisasi TextView
    private TextView nilaiSuhu, nilaiPH, nilaiAmonia;

    // untuk mqtt

    MqttAndroidClient client, client2, client3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);

        // baca komponen nilai Suhu (textView)
        nilaiSuhu = (TextView) findViewById(R.id.tv_nilai_suhu);
        nilaiPH = (TextView) findViewById(R.id.tv_nilai_ph);
        nilaiAmonia= (TextView) findViewById(R.id.tv_nilai_amonia);

        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(MonitoringActivity.this, "tcp://broker.mqttdashboard.com:1883", clientId);

        String clientId2 = MqttClient.generateClientId();
        client2 = new MqttAndroidClient(MonitoringActivity.this, "tcp://broker.mqttdashboard.com:1883", clientId2);

        String clientId3 = MqttClient.generateClientId();
        client3 = new MqttAndroidClient(MonitoringActivity.this, "tcp://broker.mqttdashboard.com:1883", clientId3);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(MonitoringActivity.this,"Terhubung",Toast.LENGTH_SHORT).show();
                    setSubsSuhu();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(MonitoringActivity.this,"Gagal Terhubung",Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        try {
            IMqttToken token = client2.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(MonitoringActivity.this,"Terhubung",Toast.LENGTH_SHORT).show();
                    setSubsPH();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(MonitoringActivity.this,"Gagal Terhubung",Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        try {
            IMqttToken token = client3.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(MonitoringActivity.this,"Terhubung",Toast.LENGTH_SHORT).show();
                    setSubsAmonia();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(MonitoringActivity.this,"Gagal Terhubung",Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                nilaiSuhu.setText(new String(message.getPayload()));

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        client2.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                nilaiPH.setText(new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        client3.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                nilaiAmonia.setText(new String(message.getPayload()));

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

    }

    private void setSubsAmonia(){
        String topic = "riezkyf2";
        int qos = 0;
        try {
            IMqttToken subToken = client3.subscribe(topic, qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // The message was published
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // The subscription could not be performed, maybe the user was not
                    // authorized to subscribe on the specified topic e.g. using wildcards

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void setSubsSuhu(){
        String topic = "riezkyf3";
        int qos = 0;
        try {
            IMqttToken subToken = client.subscribe(topic, qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // The message was published
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // The subscription could not be performed, maybe the user was not
                    // authorized to subscribe on the specified topic e.g. using wildcards

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void setSubsPH(){
        String topic = "riezkyf4";
        int qos = 0;
        try {
            IMqttToken subToken = client2.subscribe(topic, qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // The message was published
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // The subscription could not be performed, maybe the user was not
                    // authorized to subscribe on the specified topic e.g. using wildcards

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        Intent goHome = new Intent(MonitoringActivity.this, HomeActivity.class);
        startActivity(goHome);
        finish();
    }


}