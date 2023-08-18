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
        mList.add(new GameTitleData("Game 1", R.drawable.baseline_home_24));
        mList.add(new GameTitleData("Game 2", R.drawable.baseline_search_24));
        mList.add(new GameTitleData("Game 3", R.drawable.baseline_profile_24));
        mList.add(new GameTitleData("Game 4", R.drawable.baseline_home_24));
        mList.add(new GameTitleData("Game 5", R.drawable.baseline_search_24));
        mList.add(new GameTitleData("Game 6", R.drawable.baseline_profile_24));
        mList.add(new GameTitleData("Game 7", R.drawable.baseline_home_24));
        mList.add(new GameTitleData("Game 8", R.drawable.baseline_search_24));
        mList.add(new GameTitleData("Game 9", R.drawable.baseline_profile_24));
        mList.add(new GameTitleData("Game 10", R.drawable.baseline_home_24));
        mList.add(new GameTitleData("Game 11", R.drawable.baseline_search_24));
        mList.add(new GameTitleData("Game 12", R.drawable.baseline_profile_24));
        mList.add(new GameTitleData("Game 13", R.drawable.baseline_home_24));
        mList.add(new GameTitleData("Game 14", R.drawable.baseline_search_24));

    }
}


