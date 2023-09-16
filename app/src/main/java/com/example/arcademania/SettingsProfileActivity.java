package com.example.arcademania;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class SettingsProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if getSupportActionBar() is not null before hiding
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_settings_profile);

        // Find the back button and set its click listener using a lambda expression
        Button backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(view -> finish());
    }
}