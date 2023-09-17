package com.example.arcademania;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class CreateProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private TextInputEditText editTextName, editTextSurname, editTextEmail, editTextBirthday, editTextMobile, editTextCountry, editTextPostCode;
    private ImageView uploadImageView;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_create_profile);

        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        uploadImageView = findViewById(R.id.uploadImage);
        FloatingActionButton fab = findViewById(R.id.floating_action_button);
        editTextName = findViewById(R.id.editTextName);
        editTextSurname = findViewById(R.id.editTextSurname);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextBirthday = findViewById(R.id.editTextBirthday);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextCountry = findViewById(R.id.editTextCountry);
        editTextPostCode = findViewById(R.id.editTextPostCode);
        Button submitButton = findViewById(R.id.submitButton);
    }

    private void setupListeners() {
        FloatingActionButton fab = findViewById(R.id.floating_action_button);
        fab.setOnClickListener(view -> openImagePicker());

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(v -> submitForm());
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            uploadImageView.setImageURI(selectedImageUri);
        }
    }

    private void submitForm() {
        String name = editTextName.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String birthday = editTextBirthday.getText().toString().trim();
        String mobile = editTextMobile.getText().toString().trim();
        String country = editTextCountry.getText().toString().trim();
        String postCode = editTextPostCode.getText().toString().trim();

        if (!isValidForm(name, surname, email)) {
            displayMessage("Please fill in all required fields.");
            return;
        }

        if (!isValidEmail(email)) {
            displayMessage("Please enter a valid email address.");
            return;
        }

        if (!isValidDate(birthday)) {
            displayMessage("Please enter a valid date (DD/MM/YYYY).");
            return;
        }

        if (!isValidMobile(mobile)) {
            displayMessage("Please enter a valid mobile number (+30-0123456789).");
            return;
        }

        if (!isValidPostalCode(postCode)) {
            displayMessage("Please enter a valid post code (5-digit).");
            return;
        }

        saveProfileData(name, surname, email, birthday, mobile, country, postCode);
        displayMessage("Profile created!");
        updateFragmentProfile();
        navigateBackToFragmentProfile();
    }

    private boolean isValidForm(String name, String surname, String email) {
        return !name.isEmpty() && !surname.isEmpty() && !email.isEmpty();
    }

    private void saveProfileData(String name, String surname, String email, String birthday, String mobile, String country, String postCode) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (selectedImageUri != null) {
            editor.putString("imageUri", selectedImageUri.toString());
        }

        editor.putBoolean("accountCreated", true);
        editor.putString("name", name);
        editor.putString("surname", surname);
        editor.putString("email", email);
        editor.putString("birthday", birthday);
        editor.putString("mobile", mobile);
        editor.putString("country", country);
        editor.putString("postCode", postCode);
        editor.apply();
    }

    private void updateFragmentProfile() {
        ProfileFragment fragmentProfile = (ProfileFragment) getSupportFragmentManager().findFragmentByTag("ProfileFragment");
        if (fragmentProfile != null) {
            fragmentProfile.updateProfileData();
        }
    }

    private void displayMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void navigateBackToFragmentProfile() {
        super.onBackPressed();
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidDate(String date) {
        String regexPattern = "\\d{2}/\\d{2}/\\d{4}";
        return date.matches(regexPattern);
    }

    private boolean isValidMobile(String mobile) {
        String regexPattern1 = "^\\+[0-9]{1,3}-[0-9]{10}$";
        return mobile.matches(regexPattern1);
    }

    private boolean isValidPostalCode(String postalCode) {
        String regexPattern = "^[0-9]{5}$";
        return postalCode.matches(regexPattern);
    }
}