package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    private Button settingButton;
    private EditText editCity;
    private EditText editKey;
    private SharedPreferences setting;

    private SharedPreferences.Editor editor;
    private Intent intent;
    private final String APP_WEATHER = "weather";

    private final String APIKEY = "Key";
    private final String CITY = "City";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        settingButton = findViewById(R.id.buttonSetting);

        editCity = findViewById(R.id.editCity);
        editKey= findViewById(R.id.editKey);

        setting = getSharedPreferences(APP_WEATHER, MODE_PRIVATE);

        settingButton.setOnClickListener(listener);
    }
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String textKey = editKey.getText().toString();
            String nameCity = editCity.getText().toString();
            if (nameCity.equals("")) {
                Toast toast = Toast.makeText(getApplicationContext(), "Вы не ввели название населённого пункта", Toast.LENGTH_SHORT);
                toast.show();
            } else if (textKey.equals("")) {
                Toast toast = Toast.makeText(getApplicationContext(), "Вы не ввели API ключ", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                editor = setting.edit();
                editor.putString(CITY, nameCity);
                editor.putString(APIKEY, textKey);
                editor.apply();
                intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
            }

        }
    };
}