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
import com.example.arcademania.SearchFragmentHelper.SearchGameAdapter;
import com.example.arcademania.SearchFragmentHelper.SearchGameData;
import java.util.ArrayList;
import java.util.Locale;

public class SearchFragment extends Fragment {
    private ArrayList<SearchGameData> mList;
    private SearchGameAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView); // Declare recyclerView as a local variable
        SearchView searchView = rootView.findViewById(R.id.searchView); // Declare searchView as a local variable

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>(); // Initialize mList
        addDataToList();
        adapter = new SearchGameAdapter(mList);
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
            ArrayList<SearchGameData> filteredList = new ArrayList<>();
            String lowerCaseQuery = query.toLowerCase(Locale.ROOT);
            for (SearchGameData game : mList) {
                if (game.getGameTitle().toLowerCase(Locale.ROOT).contains(lowerCaseQuery)) {
                    filteredList.add(game);
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
        mList.add(new SearchGameData("PacMan", R.drawable.pac_man_logo));
        mList.add(new SearchGameData("Space Invaders", R.drawable.space_invaders_logo));
        mList.add(new SearchGameData("Galaga", R.drawable.galaga_logo));
        mList.add(new SearchGameData("Donkey Kong", R.drawable.donkey_kong_logo));
        mList.add(new SearchGameData("Frogger", R.drawable.frogger_logo));
        mList.add(new SearchGameData("Centipede", R.drawable.centipede_logo));
        mList.add(new SearchGameData("Asteroids", R.drawable.asteroids_logo));
        mList.add(new SearchGameData("Defender", R.drawable.defender_logo));
        mList.add(new SearchGameData("Street Fighter II", R.drawable.street_fighter_ii_logo));
        mList.add(new SearchGameData("Tetris", R.drawable.tetris_logo));
        mList.add(new SearchGameData("Dig Dug", R.drawable.dig_dug));
        mList.add(new SearchGameData("Bubble Bubble", R.drawable.bubble_bobble_logo));
        mList.add(new SearchGameData("Rampage", R.drawable.rampage_logo));
        mList.add(new SearchGameData("Mortal Kombat", R.drawable.mortal_kombat_logo));
        mList.add(new SearchGameData("Q*bert", R.drawable.qbert_logo));
        mList.add(new SearchGameData("Double Dragon", R.drawable.double_dragon_logo));
    }
}