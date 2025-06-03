package com.example.edutrack;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    Toolbar topToolbar482;
    Spinner spinnerLanguage482;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switchNotifications482, switchDarkMode482;
    Button btnChangePassword482;

    SharedPreferences preferences482;
    boolean isFirstLanguageSelection = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        topToolbar482 = findViewById(R.id.topToolbar);
        spinnerLanguage482 = findViewById(R.id.spinnerLanguage);
        switchNotifications482 = findViewById(R.id.switchNotifications);
        switchDarkMode482 = findViewById(R.id.switchDarkMode);
        btnChangePassword482 = findViewById(R.id.btnChangePassword);

        setSupportActionBar(topToolbar482);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        preferences482 = getSharedPreferences("app_settings", MODE_PRIVATE);


        String[] languages = {"English", "Arabic"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage482.setAdapter(adapter);

        loadSettings();


        spinnerLanguage482.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirstLanguageSelection) {
                    isFirstLanguageSelection = false;
                    return;
                }

                String selectedLanguageCode = (position == 1) ? "ar" : "en";
                String selectedLanguageName = (position == 1) ? "Arabic" : "English";


                saveSetting("language", selectedLanguageName);
                LocaleHelper.setLocale(SettingsActivity.this, selectedLanguageCode);

                recreate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        switchNotifications482.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveSetting("notifications", isChecked ? "enabled" : "disabled");
            Toast.makeText(SettingsActivity.this, "Notifications " + (isChecked ? "enabled" : "disabled"), Toast.LENGTH_SHORT).show();
        });


        switchDarkMode482.setOnCheckedChangeListener((buttonView, isChecked) -> {
            AppCompatDelegate.setDefaultNightMode(isChecked ?
                    AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            saveSetting("dark_mode", isChecked ? "on" : "off");
        });


        btnChangePassword482.setOnClickListener(v -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            String email = auth.getCurrentUser() != null ? auth.getCurrentUser().getEmail() : null;
            if (email != null) {
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(SettingsActivity.this, "Password reset email sent to " + email, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(SettingsActivity.this, "Failed to send reset email.", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(SettingsActivity.this, "User email not found.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSettings() {
        String language = preferences482.getString("language", "English");
        String notifications = preferences482.getString("notifications", "enabled");
        String darkMode = preferences482.getString("dark_mode", "off");


        String[] languages = {"English", "Arabic"};
        for (int i = 0; i < languages.length; i++) {
            if (languages[i].equalsIgnoreCase(language)) {
                spinnerLanguage482.setSelection(i);
                break;
            }
        }

        switchNotifications482.setChecked(notifications.equals("enabled"));
        switchDarkMode482.setChecked(darkMode.equals("on"));
    }

    private void saveSetting(String key, String value) {
        SharedPreferences.Editor editor = preferences482.edit();
        editor.putString(key, value);
        editor.apply();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
