package com.example.arcademania;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arcademania.databinding.ActivityMainBinding;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FloatingActionButton fab;
    private BottomAppBar bottomAppBar;
    private FloatingActionButton floatingActionButton;

    private static final int REQUEST_CREATE_PROFILE = 1;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        bottomAppBar = findViewById(R.id.bottomAppBar);
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

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_nav_settings);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        SwitchCompat switch1 = dialog.findViewById(R.id.firstOption);
        SwitchCompat switch2 = dialog.findViewById(R.id.secondOption);
        SwitchCompat switch3 = dialog.findViewById(R.id.thirdOption);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showToast("Switch 1 is ON");
                } else {
                    showToast("Switch 1 is OFF");
                }
            }
        });

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showToast("Switch 2 is ON");
                } else {
                    showToast("Switch 2 is OFF");
                }
            }
        });

        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showToast("Switch 3 is ON");
                } else {
                    showToast("Switch 3 is OFF");
                }
            }
        });


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
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
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

    public void navigateToCreateProfileActivity(View view) {
        Intent intent = new Intent(this, CreateProfileActivity.class);
        startActivity(intent);
    }

    public void hideBottomAppBar() {
        bottomAppBar.setVisibility(View.GONE);
        floatingActionButton.setVisibility(View.GONE);
    }

    public void showBottomAppBar() {
        bottomAppBar.setVisibility(View.VISIBLE);
        floatingActionButton.setVisibility(View.VISIBLE);
    }
}