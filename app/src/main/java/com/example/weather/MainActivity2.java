package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity2 extends AppCompatActivity implements Runnable {
    private TextView infoCity;
    private TextView infoTemperature;
    private TextView Wind;
    private TextView Aktirovka;
    private Button button;
    private SharedPreferences setting;
    private final String APP_WEATHER = "weather";
    private final String CITY = "City";
   private final String APIKEY = "Key";

    private final String URL_SERVER = "https://api.openweathermap.org/data/2.5/weather?q=";
    private final String OPtionKey = "&APPID="; //  ключ доступа к сервисам сервера (получается при регистрации на https://openweathermap.org)
    private final String EXTRA_OPTIONS = "&units=metric&lang=ru";
    private String choiceCity;
   private String choiceKey;
    private String request;
    private String response;
    private HttpsURLConnection connection;
    private Handler handler;
    private JSONObject jsonObject;
    private Intent intent;
    private double temp;
    private double speed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        button = findViewById(R.id.buttonSetting);
        infoCity = findViewById(R.id.infoCity);
        infoTemperature = findViewById(R.id.infoTemperature);
        Wind = findViewById(R.id.Wind);
        Aktirovka = findViewById(R.id.Aktirovka);

        setting = getSharedPreferences(APP_WEATHER, MODE_PRIVATE);

        choiceCity = setting.getString(CITY, "NoCity");
        choiceKey = setting.getString(APIKEY, "NoKey");



        infoCity.setText("В населенном пенкте " + choiceCity);

        infoTemperature.setText("Данные обновляются ...");
        Wind.setText("Данные обновляются ...");
        Aktirovka.setText("Данные обновляются ...");

        handler = new Handler();
        new Thread(this).start();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void run() {
        request = URL_SERVER + choiceCity + OPtionKey + choiceKey + EXTRA_OPTIONS;       //request = URL_SERVER + choiceCity + OPtionKey + EXTRA_OPTIONS;//request = URL_SERVER + choiceCity + OPtionKey + choiceKey + EXTRA_OPTIONS; request = URL_SERVER + choiceCity + Key + EXTRA_OPTIONS
    try {
        URL url = new URL(request);
        //HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection = (HttpsURLConnection) url.openConnection();
        connection.connect();
        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        StringBuffer buffer = new StringBuffer();
        String line = "";

        while ((line = reader.readLine()) != null)
            buffer.append(line).append("\n");
        response = buffer.toString();

        jsonObject = new JSONObject(response);


        handler.post(new Runnable() {
            @Override
            public void run() {

                try {


                    temp = jsonObject.getJSONObject("main").getDouble("temp");
                     speed = jsonObject.getJSONObject("wind").getDouble("speed");
                    infoTemperature.setText(temp + " Градусов");
                    Wind.setText(speed + " Метров в секунду");
                   // infoTemperature.setText(jsonObject.getJSONObject("main").getDouble("temp") + " градусов");
                   // Wind.setText(jsonObject.getJSONObject("wind").getDouble("speed") + " Метров в секунду");
                    if (temp == -24 && speed > 10 ){
                        Aktirovka.setText("Актировка с 1-4 класс");
                    } else if (temp == -25 && speed > 5  && speed <= 10){
                        Aktirovka.setText("Актировка с 1-4 класс");
                    } else if (temp >= -27 && temp < -25  && speed >= 1  && speed <= 5){
                        Aktirovka.setText("Актировка с 1-4 класс");
                    } else if (temp == -30   && speed < 1  && speed >= 0){
                        Aktirovka.setText("Актировка с 1-4 класс");
                    } else if (temp <= -27 && temp > -29   && speed > 10) {
                        Aktirovka.setText("Актировка с 1-6 класс");
                    }else if (temp <= -29 && temp > -31  && speed >= 5  && speed <= 10) {
                        Aktirovka.setText("Актировка с 1-6 класс");
                    }else if (temp <= -31 && temp > -32  && speed >= 1  && speed < 5) {
                        Aktirovka.setText("Актировка с 1-6 класс");
                    } else if (temp == -32  && speed >= 0  && speed < 1) {
                        Aktirovka.setText("Актировка с 1-6 класс");
                    }else if (temp == -30  && speed > 10) {
                        Aktirovka.setText("Актировка с 1-8 класс");
                    }else if (temp == -31  && speed > 5 && speed <= 10) {
                        Aktirovka.setText("Актировка с 1-8 класс");
                    }else if (temp <= -32  && speed > -34 && speed >= 1 && speed <= 5) {
                        Aktirovka.setText("Актировка с 1-8 класс");
                    }else if (temp == -34   && speed >= 0 && speed <= 1) {
                        Aktirovka.setText("Актировка с 1-8 класс");
                    }else if (temp <= -32 && temp > -34  && speed > 10) {
                        Aktirovka.setText("Актировка с 1-11 класс");
                    } else if (temp <= -34 && temp > -36  && speed > 5 && speed <= 10) {
                        Aktirovka.setText("Актировка с 1-11 класс");
                    } else if (temp == -36  && speed >= 1 && speed <= 5) {
                        Aktirovka.setText("Актировка с 1-11 класс");
                    } else if (temp >= -37  && speed >= 0 && speed <= 1) {
                        Aktirovka.setText("Актировка с 1-11 класс");
                    } else {
                        Aktirovka.setText("НЕт актировки");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();

    } catch (JSONException e) {
        e.printStackTrace();
    }
    }
}