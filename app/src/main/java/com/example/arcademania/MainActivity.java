package com.example.arcademania;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.arcademania.databinding.ActivityMainBinding;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;


public class MainActivity extends AppCompatActivity{
    ActivityMainBinding binding;
    FloatingActionButton fab;
    private BottomAppBar bottomAppBar;
    private FloatingActionButton floatingActionButton;

    private static final int REQUEST_CREATE_PROFILE = 1;
    private Dialog dialog;
    private MediaPlayer backgroundMusicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        bottomAppBar = findViewById(R.id.bottomAppBar);

        //backgroundMusicPlayer = MediaPlayer.create(this, R.raw.background_music);

        floatingActionButton = findViewById(R.id.fab);

        fab = findViewById(R.id.fab);
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.games:
                    replaceFragment(new GamesActivity());
                    break;
                case R.id.search:
                    replaceFragment(new SearchFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
            }
            return true;
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
            }
        });
    }

    private  void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
    private void showBottomDialog() {

        //final Dialog dialog = new Dialog(this);

        dialog = new Dialog(this); // Change dialog to a class-level variable
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_nav_settings);

        TextView settingsTextView = dialog.findViewById(R.id.settingsTextView);
        settingsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsProfileActivity.class);
                startActivity(intent);

                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        setupSwitchListeners(dialog);

        //Check If the user create the account
        boolean userHasCreatedAccount  = checkIfProfileCreated();
        TextView profileStatus = dialog.findViewById(R.id.profileStatus);

        profileStatus.setText(userHasCreatedAccount ? "Edit Account" : "Sign Up");

        ImageView imageViewAccount = dialog.findViewById(R.id.imageViewAccount);
        imageViewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateProfileActivity.class);
                startActivity(intent);

                dialog.dismiss();
            }
        });

        ImageView deleteAccountButton = dialog.findViewById(R.id.deleteProfileImgView);
        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the delete confirmation dialog
                showDeleteConfirmationDialog(dialog);
            }
        });

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

        // Load the switch states from SharedPreferences and set them
        SharedPreferences sharedPreferences = getSharedPreferences("SwitchState", Context.MODE_PRIVATE);
        //switch1.setChecked(sharedPreferences.getBoolean("Switch1", false));
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch1.setChecked(currentNightMode == Configuration.UI_MODE_NIGHT_YES);

        switch2.setChecked(sharedPreferences.getBoolean("Switch2", false));
        boolean switch3State = sharedPreferences.getBoolean("Switch3", false);
        switch3.setChecked(switch3State);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Switch 1 is ON, enable dark mode
                    showToast("Dark Menu is enabled");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    // Switch 1 is OFF, enable light mode
                    showToast("Light Menu is enabled");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                // Save the state of Switch 1
                saveSwitchState("Switch1", isChecked);
            }
        });


        //No .mp3 file Git
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showToast("Background music is playing");
                    startBackgroundMusic();
                } else {
                    showToast("Background music is stopped");
                    stopBackgroundMusic();
                }
                // Save the state of Switch 2
                saveSwitchState("Switch2", isChecked);
            }
        });
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showToast("Profile is now private");
                } else {
                    showToast("Profile is now public");
                }
                // Save the state of Switch 3
                saveSwitchState("Switch3", isChecked);

                // Notify the ProfileFragment about the change
                notifyProfileFragment(isChecked);
            }
        });
    }
    private void notifyProfileFragment(boolean isChecked) {
        // Save the state of Switch 3 in SharedPreferences
        SharedPreferences switchSharedPreferences = getSharedPreferences("SwitchState", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = switchSharedPreferences.edit();
        editor.putBoolean("Switch3", isChecked);
        editor.apply();
    }
    private void saveSwitchState(String switchKey, boolean isChecked) {
        SharedPreferences sharedPreferences = getSharedPreferences("SwitchState", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(switchKey, isChecked);
        editor.apply();
    }
    private void showDeleteConfirmationDialog(final Dialog parentDialog) {
        boolean isAccountCreated = checkIfProfileCreated();

        if (!isAccountCreated) {
            showToast("There is no account to delete.");
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete Account Confirmation");
            builder.setMessage("Are you sure you want to delete your account?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    boolean isAccountDeleted = deleteAccount();
                    if (isAccountDeleted) {
                        TextView profileStatus = parentDialog.findViewById(R.id.profileStatus);
                        profileStatus.setText("Sign Up");
                        parentDialog.dismiss();
                        showToast("Account deleted successfully!");
                    } else {
                        showToast("Failed to delete the account. Please try again.");
                    }
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // User clicked "No," do nothing
                }
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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    private void navigateToInitialScreen() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, new ProfileFragment());
        transaction.addToBackStack(null);
        transaction.commit();
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

        // Dismiss the dialog if it's showing to prevent window leak
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        // Release the MediaPlayer resources when the activity is destroyed
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.release();
            backgroundMusicPlayer = null;
        }
    }

}