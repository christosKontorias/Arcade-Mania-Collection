package com.example.arcademania;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Locale;


public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private SearchView searchView;
    private ArrayList<GameTitleData> mList = new ArrayList<>();
    private GameTitleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        searchView = rootView.findViewById(R.id.searchView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addDataToList();
        adapter = new GameTitleAdapter(mList);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        return rootView;
    }

    private void filterList(String query) {
        if (query != null) {
            ArrayList<GameTitleData> filteredList = new ArrayList<>();
            for (GameTitleData i : mList) {
                if (i.getGameTitle().toLowerCase(Locale.ROOT).contains(query)) {
                    filteredList.add(i);
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(getContext(), "No Game found", Toast.LENGTH_SHORT).show();
            } else {
                adapter.setFilteredList(filteredList);
            }
        }
    }

    private void addDataToList() {
        mList.add(new GameTitleData("PacMan", R.drawable.pac_man_logo));
        mList.add(new GameTitleData("Space Invaders", R.drawable.space_invaders_logo));
        mList.add(new GameTitleData("Galaga", R.drawable.galaga_logo));
        mList.add(new GameTitleData("Donkey Kong", R.drawable.donkey_kong_logo));
        mList.add(new GameTitleData("Frogger", R.drawable.frogger_logo));
        mList.add(new GameTitleData("Centipede", R.drawable.centipede_logo));
        mList.add(new GameTitleData("Asteroids", R.drawable.asteroids_logo));
        mList.add(new GameTitleData("Defender", R.drawable.defender_logo));
        mList.add(new GameTitleData("Street Fighter II", R.drawable.street_fighter_ii_logo));
        mList.add(new GameTitleData("Tetris", R.drawable.tetris_logo));
        mList.add(new GameTitleData("Dig Dug", R.drawable.dig_dug));
        mList.add(new GameTitleData("Bubble Bubble", R.drawable.bubble_bobble_logo));
        mList.add(new GameTitleData("Rampage", R.drawable.rampage_logo));
        mList.add(new GameTitleData("Mortal Kombat", R.drawable.mortal_kombat_logo));
        mList.add(new GameTitleData("Q*bert", R.drawable.qbert_logo));
        mList.add(new GameTitleData("Double Dragon", R.drawable.double_dragon_logo));

    }
}


