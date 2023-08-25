package com.example.arcademania;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;

public class CreateProfileActivity extends AppCompatActivity {

    private TextInputEditText editTextName, editTextSurname, editTextEmail, editTextMobile, editTextCountry, editTextPostCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        // Get references to EditText fields
        editTextName = findViewById(R.id.editTextName);
        editTextSurname = findViewById(R.id.editTextSurname);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextCountry = findViewById(R.id.editTextCountry);
        editTextPostCode = findViewById(R.id.editTextPostCode);

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
    }
    private void submitForm() {

        // Extract data from EditText fields
        String name = editTextName.getText().toString();
        String surname = editTextSurname.getText().toString();
        String email = editTextEmail.getText().toString();
        String mobile = editTextMobile.getText().toString();
        String country = editTextCountry.getText().toString();
        String postCode = editTextPostCode.getText().toString();

        // Check if any required field is blank
        if (name.isEmpty() || surname.isEmpty() || email.isEmpty()) {
            displayMessage("Profile not created! Please fill in all required fields.");
            return;
        }

        // Save data to SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("surname", surname);
        editor.putString("email", email);
        editor.putString("mobile", mobile);
        editor.putString("country", country);
        editor.putString("postCode", postCode);
        editor.apply();

        // Display success message
        displayMessage("Profile created!");
        updateFragmentProfile();
        navigateBackToFragmentProfile();
    }

    private void updateFragmentProfile() {
        // Find the ProfileFragment using its tag
        ProfileFragment fragmentProfile = (ProfileFragment) getSupportFragmentManager().findFragmentByTag("ProfileFragment");
        if (fragmentProfile != null) {
            fragmentProfile.updateProfileData(); // Update profile data
        }
    }

    private void displayMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void navigateBackToFragmentProfile() {
        super.onBackPressed();
    }
}

