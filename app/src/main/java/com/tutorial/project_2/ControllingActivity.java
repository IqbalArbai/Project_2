 package com.tutorial.project_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

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

 public class ControllingActivity extends AppCompatActivity {
    SwitchCompat sc_heater, sc_cooler, sc_pompaAsam, sc_pompaBasa, sc_pompaAmonia ;
    // untuk button save state
    private static final String HEATER_PREFS = "heater_preferences"; // nama shared preferences
    private static final String COOLER_PREFS = "cooler_preferences";
    private static final String ASAM_PREFS = "asam_preferences";
    private static final String BASA_PREFS = "basa_preferences";
    private static final String AMONIA_PREFS = "amonia_preferences";

    private static final String HEATER_STATUS = "heater_status";
    private static final String COOLER_STATUS = "cooler_status";
    private static final String ASAM_STATUS = "asam_status";
    private static final String BASA_STATUS = "basa_status";
    private static final String AMONIA_STATUS = "amonia_status";

    boolean heater_status, cooler_status, asam_status, basa_status, amonia_status;

    SharedPreferences heaterPreferences, coolerPreferences, asamPreferences, basaPreferences, amoniaPreferences;
    SharedPreferences.Editor heaterEditor, coolerEditor, asamEditor, basaEditor, amoniaEditor;
    // ending untuk button save state


    MqttAndroidClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlling);

        sc_heater = findViewById(R.id.sw_heater);
        sc_cooler = findViewById(R.id.sw_cooler);
        sc_pompaAsam = findViewById(R.id.sw_pompa_asam);
        sc_pompaBasa = findViewById(R.id.sw_pompa_basa);
        sc_pompaAmonia = findViewById(R.id.sw_pompa_amonia);

        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(ControllingActivity.this, "tcp://broker.mqttdashboard.com:1883", clientId);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(ControllingActivity.this,"Terhubung",Toast.LENGTH_LONG).show();

                    sc_heater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                            if (compoundButton.isChecked()){
                                heaterEditor.putBoolean(HEATER_STATUS, true);
                                heaterEditor.apply();
                                sc_heater.setChecked(true); // save state

                                String topic = "riezkyf1"; // publish
                                String payload = "2"; // heater on string 2
                                byte[] encodedPayload = new byte[0];
                                try {
                                    encodedPayload = payload.getBytes("UTF-8");
                                    MqttMessage message = new MqttMessage(encodedPayload);
                                    client.publish(topic, message);
                                } catch (UnsupportedEncodingException | MqttException e) {
                                    e.printStackTrace();
                                } // publish

                            }else {
                                heaterEditor.putBoolean(HEATER_STATUS, false);
                                heaterEditor.apply();
                                sc_heater.setChecked(false);

                                String topic = "riezkyf1";
                                String payload = "3"; // heater off string 3
                                byte[] encodedPayload = new byte[0];
                                try {
                                    encodedPayload = payload.getBytes("UTF-8");
                                    MqttMessage message = new MqttMessage(encodedPayload);
                                    client.publish(topic, message);
                                } catch (UnsupportedEncodingException | MqttException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    sc_cooler.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                            if (compoundButton.isChecked()) {

                                coolerEditor.putBoolean(COOLER_STATUS, true);
                                coolerEditor.apply();
                                sc_cooler.setChecked(true);

                                String topic = "riezkyf1";
                                String payload = "1";
                                byte[] encodedPayload = new byte[0];
                                try {
                                    encodedPayload = payload.getBytes("UTF-8");
                                    MqttMessage message = new MqttMessage(encodedPayload);
                                    client.publish(topic, message);
                                } catch (UnsupportedEncodingException | MqttException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                coolerEditor.putBoolean(COOLER_STATUS, false);
                                coolerEditor.apply();
                                sc_cooler.setChecked(false);

                                String topic = "riezkyf1";
                                String payload = "0";
                                byte[] encodedPayload = new byte[0];
                                try {
                                    encodedPayload = payload.getBytes("UTF-8");
                                    MqttMessage message = new MqttMessage(encodedPayload);
                                    client.publish(topic, message);
                                } catch (UnsupportedEncodingException | MqttException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });

                    sc_pompaAmonia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                            if (compoundButton.isChecked()){

                                amoniaEditor.putBoolean(AMONIA_STATUS, true);
                                amoniaEditor.apply();
                                sc_pompaAmonia.setChecked(true);

                                String topic = "riezkyf1";
                                String payload = "4";
                                byte[] encodedPayload = new byte[0];
                                try {
                                    encodedPayload = payload.getBytes("UTF-8");
                                    MqttMessage message = new MqttMessage(encodedPayload);
                                    client.publish(topic, message);
                                } catch (UnsupportedEncodingException | MqttException e) {
                                    e.printStackTrace();
                                }

                            }else {
                                amoniaEditor.putBoolean(AMONIA_STATUS, false);
                                amoniaEditor.apply();
                                sc_pompaAmonia.setChecked(false);

                                String topic = "riezkyf1";
                                String payload = "5";
                                byte[] encodedPayload = new byte[0];
                                try {
                                    encodedPayload = payload.getBytes("UTF-8");
                                    MqttMessage message = new MqttMessage(encodedPayload);
                                    client.publish(topic, message);
                                } catch (UnsupportedEncodingException | MqttException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    sc_pompaAsam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                            if (compoundButton.isChecked()){

                                asamEditor.putBoolean(ASAM_STATUS, true);
                                asamEditor.apply();
                                sc_pompaAsam.setChecked(true);

                                String topic = "riezkyf1";
                                String payload = "8";
                                byte[] encodedPayload = new byte[0];
                                try {
                                    encodedPayload = payload.getBytes("UTF-8");
                                    MqttMessage message = new MqttMessage(encodedPayload);
                                    client.publish(topic, message);
                                } catch (UnsupportedEncodingException | MqttException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                asamEditor.putBoolean(ASAM_STATUS, false);
                                asamEditor.apply();
                                sc_pompaAsam.setChecked(false);

                                String topic = "riezkyf1";
                                String payload = "9";
                                byte[] encodedPayload = new byte[0];
                                try {
                                    encodedPayload = payload.getBytes("UTF-8");
                                    MqttMessage message = new MqttMessage(encodedPayload);
                                    client.publish(topic, message);
                                } catch (UnsupportedEncodingException | MqttException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    sc_pompaBasa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                            if (compoundButton.isChecked()){

                                basaEditor.putBoolean(BASA_STATUS, true);
                                basaEditor.apply();
                                sc_pompaBasa.setChecked(true);

                                String topic = "riezkyf1";
                                String payload = "6";
                                byte[] encodedPayload = new byte[0];
                                try {
                                    encodedPayload = payload.getBytes("UTF-8");
                                    MqttMessage message = new MqttMessage(encodedPayload);
                                    client.publish(topic, message);
                                } catch (UnsupportedEncodingException | MqttException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                basaEditor.putBoolean(BASA_STATUS, false);
                                basaEditor.apply();
                                sc_pompaBasa.setChecked(false);

                                String topic = "riezkyf1";
                                String payload = "7";
                                byte[] encodedPayload = new byte[0];
                                try {
                                    encodedPayload = payload.getBytes("UTF-8");
                                    MqttMessage message = new MqttMessage(encodedPayload);
                                    client.publish(topic, message);
                                } catch (UnsupportedEncodingException | MqttException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

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
                    Toast.makeText(ControllingActivity.this,"Gagal Terhubung",Toast.LENGTH_LONG).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }


        // save state switch value
        heaterPreferences = getSharedPreferences(HEATER_PREFS,MODE_PRIVATE);
        heaterEditor = getSharedPreferences(HEATER_PREFS,MODE_PRIVATE).edit();

        coolerPreferences = getSharedPreferences(COOLER_PREFS,MODE_PRIVATE);
        coolerEditor = getSharedPreferences(COOLER_PREFS,MODE_PRIVATE).edit();

        asamPreferences = getSharedPreferences(ASAM_PREFS,MODE_PRIVATE);
        asamEditor = getSharedPreferences(ASAM_PREFS,MODE_PRIVATE).edit();

        basaPreferences = getSharedPreferences(BASA_PREFS,MODE_PRIVATE);
        basaEditor = getSharedPreferences(BASA_PREFS,MODE_PRIVATE).edit();

        amoniaPreferences = getSharedPreferences(AMONIA_PREFS,MODE_PRIVATE);
        amoniaEditor = getSharedPreferences(AMONIA_PREFS,MODE_PRIVATE).edit();

        heater_status = heaterPreferences.getBoolean(HEATER_STATUS,false);
        cooler_status = coolerPreferences.getBoolean(COOLER_STATUS,false);
        asam_status = asamPreferences.getBoolean(ASAM_STATUS,false);
        basa_status = basaPreferences.getBoolean(BASA_STATUS,false);
        amonia_status = amoniaPreferences.getBoolean(AMONIA_STATUS,false);

        // button heater
        sc_heater.setChecked(heater_status);

        sc_cooler.setChecked(cooler_status);

        sc_pompaAsam.setChecked(asam_status);

        sc_pompaBasa.setChecked(basa_status);

        sc_pompaAmonia.setChecked(amonia_status);

    }

    @Override
    public void onBackPressed() {
        Intent goHome = new Intent(ControllingActivity.this, HomeActivity.class);
        startActivity(goHome);
        finish();
    }
}