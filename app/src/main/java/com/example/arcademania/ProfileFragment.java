package com.example.arcademania;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;

public class ProfileFragment extends Fragment{
    private MainActivity parentActivity;
    private LinearLayout linearLayoutDetails;
    private Button createProfileButton;
    private ImageView imgProfile;
    private TextView nameTextView, surnameTextView, emailTextView, birthdayTextView, mobileTextView, countryTextView, postCodeTextView;

    private BottomNavigationListener bottomNavigationListener;

    public interface BottomNavigationListener {
        void onNavigateToHome();
        void navigateToGamesActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        initializeViews(rootView);
        setupHomeClickListener(rootView);
        setupGameListClickListener(rootView);
        setupSettingsClickListener(rootView);
        setupCreateProfileButton();

        return rootView;
    }

    private void initializeViews(View rootView) {
        imgProfile = rootView.findViewById(R.id.imgProfile);
        nameTextView = rootView.findViewById(R.id.txt_name);
        surnameTextView = rootView.findViewById(R.id.txt_surname);
        emailTextView = rootView.findViewById(R.id.txt_email);
        birthdayTextView = rootView.findViewById(R.id.txt_Birthday);
        mobileTextView = rootView.findViewById(R.id.txt_Mobile);
        countryTextView = rootView.findViewById(R.id.txt_Country);
        postCodeTextView = rootView.findViewById(R.id.txt_PostCode);
        linearLayoutDetails = rootView.findViewById(R.id.linearLayoutDetails);
        createProfileButton = rootView.findViewById(R.id.btn_create_profile);
    }

    private void setupHomeClickListener(View rootView) {
        LinearLayout linearLayoutHome = rootView.findViewById(R.id.linear_layout_home);
        linearLayoutHome.setOnClickListener(v -> navigateToHomeFragment());
    }

    private void navigateToHomeFragment() {
        HomeFragment homeFragment = new HomeFragment();
        replaceFragment(homeFragment);
        if (bottomNavigationListener != null) {
            bottomNavigationListener.onNavigateToHome();
        }
    }

    private void setupGameListClickListener(View rootView) {
        LinearLayout linearLayoutGame = rootView.findViewById(R.id.linear_layout_games);
        linearLayoutGame.setOnClickListener(v -> navigateToGamesActivity());
    }

    private void navigateToGamesActivity() {
        GamesActivity gamesActivity = new GamesActivity();
        replaceFragment(gamesActivity);
        if (bottomNavigationListener != null) {
            bottomNavigationListener.navigateToGamesActivity();
        }
    }

    private void replaceFragment(Fragment fragment) {
        if (getActivity() != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frame_layout, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private void setupSettingsClickListener(View rootView) {
        LinearLayout linearLayoutSettings = rootView.findViewById(R.id.linear_layout_settings);
        linearLayoutSettings.setOnClickListener(v -> startSettingsProfileActivity());
    }

    private void startSettingsProfileActivity() {
        Intent intent = new Intent(getActivity(), SettingsProfileActivity.class);
        startActivity(intent);
    }

    private void setupCreateProfileButton() {
        boolean profileCreated = checkIfProfileCreated();
        createProfileButton.setText(profileCreated ? "Edit Profile" : "Create Profile");
        createProfileButton.setOnClickListener(v -> {
            if (profileCreated) {
                startCreateProfileActivity();
            } else {
                startCreateProfileActivity();
            }
        });
    }

    private void startCreateProfileActivity() {
        Intent intent = new Intent(getActivity(), CreateProfileActivity.class);
        startActivity(intent);
    }

    private boolean checkIfProfileCreated() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        String name = sharedPreferences.getString("name", "");
        String surname = sharedPreferences.getString("surname", "");
        String email = sharedPreferences.getString("email", "");
        return !name.isEmpty() && !surname.isEmpty() && !email.isEmpty();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            parentActivity = (MainActivity) context;
            bottomNavigationListener = (MainActivity) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (parentActivity != null) {
            parentActivity.hideBottomAppBar();
        }

        updateProfileData();

        boolean profileCreated = checkIfProfileCreated();
        if (profileCreated) {
            linearLayoutDetails.setVisibility(View.VISIBLE);
        } else {
            linearLayoutDetails.setVisibility(View.GONE);
        }
        setupCreateProfileButton();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (parentActivity != null) {
            parentActivity.showBottomAppBar();
        }
    }

    public void updateProfileData() {
        // Obtain the Context from the parent activity
        Context context = getActivity();

        if (context == null) {
            // Handle the case where the fragment is not attached to an activity
            return;
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sharedPreferences.getString("name", "");
        String surname = sharedPreferences.getString("surname", "");
        String email = sharedPreferences.getString("email", "");
        String birthday = sharedPreferences.getString("birthday", "");
        String mobile = sharedPreferences.getString("mobile", "");
        String country = sharedPreferences.getString("country", "");
        String postCode = sharedPreferences.getString("postCode", "");

        // Read the state of Switch 3 from SharedPreferences
        SharedPreferences switchSharedPreferences = context.getSharedPreferences("SwitchState", Context.MODE_PRIVATE);
        boolean switch3State = switchSharedPreferences.getBoolean("Switch3", false);

        if (!switch3State) {
            // Display the information normally
            nameTextView.setText(name);
            surnameTextView.setText(surname);
            emailTextView.setText(email);
            birthdayTextView.setText(birthday);
            mobileTextView.setText(mobile);
            countryTextView.setText(country);
            postCodeTextView.setText(postCode);
        } else {
            // Display the information with asterisks for sensitive fields
            nameTextView.setText(getAsteriskText(name));
            surnameTextView.setText(getAsteriskText(surname));
            emailTextView.setText(getAsteriskText(email));
            birthdayTextView.setText(getAsteriskText(birthday));
            mobileTextView.setText(getAsteriskText(mobile));
            countryTextView.setText(getAsteriskText(country));
            postCodeTextView.setText(getAsteriskText(postCode));
        }

        // Load and display the profile image using Glide
        String imageUriString = sharedPreferences.getString("imageUri", null);
        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            Glide.with(requireContext()).load(imageUri).into(imgProfile);
        }
    }

    private String getAsteriskText(String text) {
        // Replace the characters in the text with asterisks
        return text.replaceAll(".", "*");
    }
}