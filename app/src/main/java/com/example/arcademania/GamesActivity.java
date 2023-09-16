package com.example.arcademania;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arcademania.GamesActivityHelper.ActivityGameAdapter;
import com.example.arcademania.GamesActivityHelper.ActivityGameData;
import java.util.ArrayList;


public class GamesActivity extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<ActivityGameData> gameList = new ArrayList<>();
    private ActivityGameAdapter gameAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_games, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        addDataToList();
        gameAdapter = new ActivityGameAdapter(gameList);
        recyclerView.setAdapter(gameAdapter);

        // Set up a grid layout with two columns
        int spanCount = 2;
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
        recyclerView.setLayoutManager(layoutManager);

        // Calculate the number of games
        int gameCount = gameList.size();

        // Update the game count TextView
        TextView gameCountTextView = rootView.findViewById(R.id.txt_CountGames);
        gameCountTextView.setText(gameCount + " Games");
        return rootView;
    }

    private void addDataToList() {
        gameList.add(new ActivityGameData(R.drawable.pac_man_logo, "PacMan"));
        gameList.add(new ActivityGameData(R.drawable.space_invaders_logo, "Space Invaders"));
        gameList.add(new ActivityGameData(R.drawable.galaga_logo, "Galaga"));
        gameList.add(new ActivityGameData(R.drawable.donkey_kong_logo, "Donkey Kong"));
        gameList.add(new ActivityGameData(R.drawable.frogger_logo, "Frogger"));
        gameList.add(new ActivityGameData(R.drawable.centipede_logo, "Centipede"));
        gameList.add(new ActivityGameData(R.drawable.asteroids_logo, "Asteroids"));
        gameList.add(new ActivityGameData(R.drawable.defender_logo, "Defender"));
        gameList.add(new ActivityGameData(R.drawable.street_fighter_ii_logo, "Street Fighter II"));
        gameList.add(new ActivityGameData(R.drawable.tetris_logo, "Tetris"));
        gameList.add(new ActivityGameData(R.drawable.dig_dug, "Dig Dug"));
        gameList.add(new ActivityGameData(R.drawable.bubble_bobble_logo, "Bubble Bubble"));
        gameList.add(new ActivityGameData(R.drawable.rampage_logo, "Rampage"));
        gameList.add(new ActivityGameData(R.drawable.mortal_kombat_logo, "Mortal Kombat"));
        gameList.add(new ActivityGameData(R.drawable.qbert_logo, "Q*bert"));
        gameList.add(new ActivityGameData(R.drawable.double_dragon_logo, "Double Dragon"));
    }
}