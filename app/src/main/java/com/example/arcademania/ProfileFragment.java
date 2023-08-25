package com.example.arcademania;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProfileFragment extends Fragment{
    private MainActivity parentActivity;
    private LinearLayout section2Layout;
    private Button createProfileButton;
    private TextView nameTextView, surnameTextView, emailTextView, mobileTextView, countryTextView, postCodeTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        initializeViews(rootView);
        setupGameListClickListener(rootView);
        setupSettingsClickListener(rootView);
        setupCreateProfileButton();

        return rootView;
    }

    private void initializeViews(View rootView) {
        nameTextView = rootView.findViewById(R.id.txt_name);
        surnameTextView = rootView.findViewById(R.id.txt_surname);
        emailTextView = rootView.findViewById(R.id.txt_email);
        mobileTextView = rootView.findViewById(R.id.txt_Mobile);
        countryTextView = rootView.findViewById(R.id.txt_Country);
        postCodeTextView = rootView.findViewById(R.id.txt_PostCode);
        section2Layout = rootView.findViewById(R.id.section2);
        createProfileButton = rootView.findViewById(R.id.btn_create_profile);
    }

    private void setupGameListClickListener(View rootView) {
        LinearLayout linearLayoutMenu = rootView.findViewById(R.id.linear_layout_games);
        linearLayoutMenu.setOnClickListener(v -> replaceWithSearchFragment());
    }

    private void replaceWithSearchFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, new SearchFragment());
        fragmentTransaction.commit();
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
        });    }

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
            section2Layout.setVisibility(View.VISIBLE);
        } else {
            section2Layout.setVisibility(View.GONE);
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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String name = sharedPreferences.getString("name", "");
        String surname = sharedPreferences.getString("surname", "");
        String email = sharedPreferences.getString("email", "");
        String mobile = sharedPreferences.getString("mobile", "");
        String country = sharedPreferences.getString("country", "");
        String postCode = sharedPreferences.getString("postCode", "");

        nameTextView.setText(name);
        surnameTextView.setText(surname);
        emailTextView.setText(email);
        mobileTextView.setText(mobile);
        countryTextView.setText(country);
        postCodeTextView.setText(postCode);
    }
}