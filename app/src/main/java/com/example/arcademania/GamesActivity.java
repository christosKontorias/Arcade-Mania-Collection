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
/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_games, container, false);

        TextView gameCountTextView = rootView.findViewById(R.id.txt_games);
        // Get references to your CardViews
        CardView cardView1 = rootView.findViewById(R.id.pacManCV);
        CardView cardView2 = rootView.findViewById(R.id.spaceInvadersCV);
        CardView cardView3 = rootView.findViewById(R.id.galagaCV);
        CardView cardView4 = rootView.findViewById(R.id.donkeyKongCV);
        CardView cardView5 = rootView.findViewById(R.id.froggerCV);
        CardView cardView6 = rootView.findViewById(R.id.asteroidsCV);
        CardView cardView7 = rootView.findViewById(R.id.defenderCV);
        CardView cardView8 = rootView.findViewById(R.id.streetFighterIICV);
        CardView cardView9 = rootView.findViewById(R.id.tetrisCV);
        CardView cardView10 = rootView.findViewById(R.id.digDugCV);
        CardView cardView11 = rootView.findViewById(R.id.bubbleBubbleCV);
        CardView cardView12 = rootView.findViewById(R.id.rampageCV);
        CardView cardView13 = rootView.findViewById(R.id.mortalKombatCV);
        CardView cardView14 = rootView.findViewById(R.id.qbertCV);
        CardView cardView15 = rootView.findViewById(R.id.doubleDragonCV);

        // Define an array of CardView resource IDs
        int[] cardViewIds = {
                R.id.pacManCV, R.id.spaceInvadersCV, R.id.galagaCV, R.id.donkeyKongCV, R.id.froggerCV,
                R.id.asteroidsCV, R.id.defenderCV, R.id.streetFighterIICV, R.id.tetrisCV, R.id.digDugCV,
                R.id.bubbleBubbleCV, R.id.rampageCV, R.id.mortalKombatCV, R.id.qbertCV, R.id.doubleDragonCV
        };

        // Count the number of non-null CardViews
        int gameCount = 0;
        for (int cardViewId : cardViewIds) {
            CardView cardView = rootView.findViewById(cardViewId);
            if (cardView != null) {
                gameCount++;
            }
        }

        gameCountTextView.setText(gameCount + " Games");

        // Load the fade-in animation
        Animation fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.anim_game_search);

        // Apply the animation to each CardView
        cardView1.startAnimation(fadeInAnimation);
        cardView2.startAnimation(fadeInAnimation);
        cardView3.startAnimation(fadeInAnimation);
        cardView4.startAnimation(fadeInAnimation);
        cardView5.startAnimation(fadeInAnimation);
        cardView6.startAnimation(fadeInAnimation);
        cardView7.startAnimation(fadeInAnimation);
        cardView8.startAnimation(fadeInAnimation);
        cardView9.startAnimation(fadeInAnimation);
        cardView10.startAnimation(fadeInAnimation);
        cardView11.startAnimation(fadeInAnimation);
        cardView12.startAnimation(fadeInAnimation);
        cardView13.startAnimation(fadeInAnimation);
        cardView14.startAnimation(fadeInAnimation);
        cardView15.startAnimation(fadeInAnimation);
        // ...

        return rootView;
    }*/

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

        //Calculate Games
        int gameCount = gameList.size();

        // Update the game count TextView
        TextView gameCountTextView = rootView.findViewById(R.id.txt_CountGames);
        gameCountTextView.setText(gameCount + " Games");

        return rootView;
    }



    private void addDataToList() {
        gameList.add(new ActivityGameData(R.drawable.pac_man_logo,"PacMan"));
        gameList.add(new ActivityGameData(R.drawable.space_invaders_logo,"Space \nInvaders"));
        gameList.add(new ActivityGameData(R.drawable.galaga_logo,"Galaga"));
        gameList.add(new ActivityGameData(R.drawable.donkey_kong_logo,"Donkey \nKong"));
        gameList.add(new ActivityGameData(R.drawable.frogger_logo,"Frogger"));
        gameList.add(new ActivityGameData(R.drawable.centipede_logo,"Centipede"));
        gameList.add(new ActivityGameData(R.drawable.asteroids_logo,"Asteroids"));
        gameList.add(new ActivityGameData(R.drawable.defender_logo,"Defender"));
        gameList.add(new ActivityGameData(R.drawable.street_fighter_ii_logo,"Street \nFighter II"));
        gameList.add(new ActivityGameData(R.drawable.tetris_logo,"Tetris"));
        gameList.add(new ActivityGameData(R.drawable.dig_dug,"Dig Dug"));
        gameList.add(new ActivityGameData(R.drawable.bubble_bobble_logo, "Bubble \nBubble"));
        gameList.add(new ActivityGameData(R.drawable.rampage_logo,"Rampage"));
        gameList.add(new ActivityGameData(R.drawable.mortal_kombat_logo,"Mortal \nKombat"));
        gameList.add(new ActivityGameData(R.drawable.qbert_logo,"Q*bert"));
        gameList.add(new ActivityGameData(R.drawable.double_dragon_logo, "Double \nDragon"));
    }
}