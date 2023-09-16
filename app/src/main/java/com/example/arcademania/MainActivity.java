package com.example.arcademania;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.arcademania.databinding.ActivityMainBinding;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity{
    ActivityMainBinding binding;
    private BottomAppBar bottomAppBar;
    private FloatingActionButton floatingActionButton;
    private Dialog dialog;
    private MediaPlayer backgroundMusicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        bottomAppBar = findViewById(R.id.bottomAppBar);
        backgroundMusicPlayer = MediaPlayer.create(this, R.raw.background_music);
        floatingActionButton = findViewById(R.id.fab);

        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.games) {
                replaceFragment(new GamesActivity());
            } else if (itemId == R.id.search) {
                replaceFragment(new SearchFragment());
            } else if (itemId == R.id.profile) {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });


        binding.fab.setOnClickListener(view -> showBottomDialog());
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }
    private void showBottomDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_nav_settings);

        TextView settingsTextView = dialog.findViewById(R.id.settingsTextView);
        settingsTextView.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SettingsProfileActivity.class);
            startActivity(intent);
            dialog.dismiss();
        });

        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(view -> dialog.dismiss());

        setupSwitchListeners(dialog);

        boolean userHasCreatedAccount = checkIfProfileCreated();
        TextView profileStatus = dialog.findViewById(R.id.profileStatus);
        profileStatus.setText(userHasCreatedAccount ? "Edit Account" : "Sign Up");

        ImageView imageViewAccount = dialog.findViewById(R.id.imageViewAccount);
        imageViewAccount.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CreateProfileActivity.class);
            startActivity(intent);
            dialog.dismiss();
        });

        ImageView deleteAccountButton = dialog.findViewById(R.id.deleteProfileImgView);
        deleteAccountButton.setOnClickListener(view -> showDeleteConfirmationDialog());

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void setupSwitchListeners(Dialog dialog) {
        SwitchCompat switch1 = dialog.findViewById(R.id.firstOption);
        SwitchCompat switch2 = dialog.findViewById(R.id.secondOption);
        SwitchCompat switch3 = dialog.findViewById(R.id.thirdOption);

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch1.setChecked(currentNightMode == Configuration.UI_MODE_NIGHT_YES);

        switch2.setChecked(getSharedPreferences("SwitchState", Context.MODE_PRIVATE).getBoolean("Switch2", false));
        switch3.setChecked(getSharedPreferences("SwitchState", Context.MODE_PRIVATE).getBoolean("Switch3", false));

        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showToast(isChecked ? "Dark Menu is enabled" : "Light Menu is enabled");
            AppCompatDelegate.setDefaultNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            saveSwitchState("Switch1", isChecked);
        });

        switch2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showToast(isChecked ? "Background music is playing" : "Background music is stopped");
            if (isChecked) startBackgroundMusic();
            else stopBackgroundMusic();
            saveSwitchState("Switch2", isChecked);
        });

        switch3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showToast(isChecked ? "Profile is now private" : "Profile is now public");
            saveSwitchState("Switch3", isChecked);
            notifyProfileFragment(isChecked);
        });
    }

    private void notifyProfileFragment(boolean isChecked) {
        SharedPreferences.Editor editor = getSharedPreferences("SwitchState", Context.MODE_PRIVATE).edit();
        editor.putBoolean("Switch3", isChecked);
        editor.apply();
    }

    private void saveSwitchState(String switchKey, boolean isChecked) {
        SharedPreferences.Editor editor = getSharedPreferences("SwitchState", Context.MODE_PRIVATE).edit();
        editor.putBoolean(switchKey, isChecked);
        editor.apply();
    }

    private void showDeleteConfirmationDialog() {
        boolean isAccountCreated = checkIfProfileCreated();

        if (!isAccountCreated) {
            showToast("There is no account to delete.");
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete Account Confirmation");
            builder.setMessage("Are you sure you want to delete your account?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                boolean isAccountDeleted = deleteAccount();
                if (isAccountDeleted) {
                    TextView profileStatus = dialog.findViewById(R.id.profileStatus);
                    profileStatus.setText(R.string.signUp);
                    dialog.dismiss();
                    showToast("Account deleted successfully!");
                } else {
                    showToast("Failed to delete the account. Please try again.");
                }
            });
            builder.setNegativeButton("No", (dialogInterface, i) -> {
                // User clicked "No," do nothing
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private boolean deleteAccount() {
        clearSharedPreferences();
        navigateToInitialScreen();
        return true;
    }

    private void clearSharedPreferences() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.clear();
        editor.apply();
    }

    private void navigateToInitialScreen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new ProfileFragment())
                .addToBackStack(null)
                .commit();
    }

    private boolean checkIfProfileCreated() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean accountCreated = sharedPreferences.getBoolean("accountCreated", false);
        String name = sharedPreferences.getString("name", "");
        String surname = sharedPreferences.getString("surname", "");
        String email = sharedPreferences.getString("email", "");
        return accountCreated && !name.isEmpty() && !surname.isEmpty() && !email.isEmpty();
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    public void hideBottomAppBar() {
        bottomAppBar.setVisibility(View.GONE);
        floatingActionButton.setVisibility(View.GONE);
    }

    public void showBottomAppBar() {
        bottomAppBar.setVisibility(View.VISIBLE);
        floatingActionButton.setVisibility(View.VISIBLE);
    }

    private void startBackgroundMusic() {
        if (backgroundMusicPlayer != null && !backgroundMusicPlayer.isPlaying()) {
            backgroundMusicPlayer.start();
        }
    }

    private void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null && backgroundMusicPlayer.isPlaying()) {
            backgroundMusicPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.release();
            backgroundMusicPlayer = null;
        }
    }
}