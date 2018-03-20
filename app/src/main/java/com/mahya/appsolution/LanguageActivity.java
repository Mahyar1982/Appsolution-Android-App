package com.mahya.appsolution;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import static com.mahya.appsolution.SplashActivity.MY_PREFS_NAME;

public class LanguageActivity extends AppCompatActivity {

    private RadioGroup radioGroupLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        radioGroupLanguage = (RadioGroup) findViewById(R.id.radioGroupLanguage);

        radioGroupLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//                editor.putString("language", "en");
//                editor.apply();

                switch (checkedId) {
                    case R.id.radioButtonEnglish:
                        Toast.makeText(LanguageActivity.this, "English selected!", Toast.LENGTH_SHORT).show();
                        editor.putString("language", "en");
                        editor.apply();
                        Intent intent = new Intent(LanguageActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.radioButtonFinnish:
                        Toast.makeText(LanguageActivity.this, "Suomi on valittu!", Toast.LENGTH_SHORT).show();
                        intent = new Intent(LanguageActivity.this, MainActivity.class);
                        startActivity(intent);
                        editor.putString("language", "fi");
                        editor.apply();
                        break;
                    case R.id.radioButtonSwedish:
                        Toast.makeText(LanguageActivity.this, "Svenska utvalda!", Toast.LENGTH_SHORT).show();
                        intent = new Intent(LanguageActivity.this, MainActivity.class);
                        startActivity(intent);
                        editor.putString("language", "sw");
                        editor.apply();
                        break;
                }
            }
        });

    }
}
