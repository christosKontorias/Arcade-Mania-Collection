package com.example.arcademania;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_profile);

        uploadImageView = findViewById(R.id.uploadImage);
        FloatingActionButton fab = findViewById(R.id.floating_action_button);
        fab.setOnClickListener(view -> openImagePicker());

        editTextName = findViewById(R.id.editTextName);
        editTextSurname = findViewById(R.id.editTextSurname);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextBirthday = findViewById(R.id.editTextBirthday);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextCountry = findViewById(R.id.editTextCountry);
        editTextPostCode = findViewById(R.id.editTextPostCode);

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
        // Extract data from EditText fields
        String name = editTextName.getText().toString();
        String surname = editTextSurname.getText().toString();
        String email = editTextEmail.getText().toString();
        String birthday = editTextBirthday.getText().toString();
        String mobile = editTextMobile.getText().toString();
        String country = editTextCountry.getText().toString();
        String postCode = editTextPostCode.getText().toString();

        // Check if any required field is blank
        if (name.isEmpty() || surname.isEmpty() || email.isEmpty()) {
            displayMessage("Profile not created!");
            return;
        }

        // Save data to SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (selectedImageUri != null) {
            editor.putString("imageUri", selectedImageUri.toString());
        }

        editor.putBoolean("accountCreated", true); // Set the flag to true
        editor.putString("name", name);
        editor.putString("surname", surname);
        editor.putString("email", email);
        editor.putString("birthday", birthday);
        editor.putString("mobile", mobile);
        editor.putString("country", country);
        editor.putString("postCode", postCode);
        editor.apply();

        displayMessage("Profile created!");
        updateFragmentProfile();
        navigateBackToFragmentProfile();

        // Set a result indicating that the profile has been created
        Intent resultIntent = new Intent();
        resultIntent.putExtra("profileCreated", true);
        setResult(RESULT_OK, resultIntent);
        finish();
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
}