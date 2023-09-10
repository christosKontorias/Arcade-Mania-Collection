package com.example.arcademania;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        TextView userTextView = rootView.findViewById(R.id.userSurname);
        boolean isProfileCreated = getProfileCreationStatus();

        if (isProfileCreated) {
            String lastName = retrieveLastName();
            userTextView.setText(lastName);
        } else {
            userTextView.setText("User");
        }

        return rootView;
    }

    private boolean getProfileCreationStatus() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        return sharedPreferences.getBoolean("accountCreated", false);
    }

    private String retrieveLastName() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        return sharedPreferences.getString("surname", "");
    }
}