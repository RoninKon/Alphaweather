package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Intent intent;
    private SharedPreferences setting;
    private final String APP_WEATHER = "weather";
    private final String CITY = "City";
    private final String APIKEY = "Key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);

        setting = getSharedPreferences(APP_WEATHER, MODE_PRIVATE);

        button.setOnClickListener(listener);

    }
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String choiceCity = setting.getString(CITY,"NoCity");
            String choiceKey = setting.getString(APIKEY, "NoKey");
            if (choiceCity.equals("NoCity") || choiceKey.equals("NoKey")) {
                intent = new Intent(getApplicationContext(), SettingActivity.class);
            } else {
                intent = new Intent(getApplicationContext(), MainActivity2.class);
            }
            startActivity(intent);
        }
    };
}