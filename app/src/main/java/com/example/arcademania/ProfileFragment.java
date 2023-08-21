package com.example.arcademania;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ProfileFragment extends Fragment {

    private MainActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        //Open The Game List Fragment
        LinearLayout linearLayoutMenu = rootView.findViewById(R.id.linear_layout_games);
        linearLayoutMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace the current fragment with the SearchFragment
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new SearchFragment());
                fragmentTransaction.commit();
            }
        });

        // Find and set click listener on linear_layout_settings
        LinearLayout linearLayoutSettings = rootView.findViewById(R.id.linear_layout_settings);
        linearLayoutSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start SettingsProfileActivity
                Intent intent = new Intent(getActivity(), SettingsProfileActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    //Hide The nav Bottom Menu
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
    }

    @Override
    public void onPause() {
        super.onPause();
        if (parentActivity != null) {
            parentActivity.showBottomAppBar();
        }
    }
}