package com.example.arcademania.SearchFragmentHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.arcademania.R;
import com.google.android.material.card.MaterialCardView;
import java.util.List;

public class SearchGameAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ONE = 1;
    private static final int VIEW_TYPE_TWO = 2;
    private List<SearchGameData> mList;

    public SearchGameAdapter(List<SearchGameData> mList) {
        this.mList = mList;
    }

    public static class GameTitleViewHolderTypeOne extends RecyclerView.ViewHolder {
        ImageView gameLogo;
        TextView gameTitle;
        MaterialCardView gameCardView;

        public GameTitleViewHolderTypeOne(View itemView) {
            super(itemView);
            gameLogo = itemView.findViewById(R.id.gameLogo);
            gameTitle = itemView.findViewById(R.id.gameTitle);
            gameCardView = itemView.findViewById(R.id.gameCardView);
        }
    }

    public static class GameTitleViewHolderTypeTwo extends RecyclerView.ViewHolder {
        ImageView gameLogo;
        TextView gameTitle;
        MaterialCardView gameCardView;

        public GameTitleViewHolderTypeTwo(View itemView) {
            super(itemView);
            gameLogo = itemView.findViewById(R.id.gameLogo);
            gameTitle = itemView.findViewById(R.id.gameTitle);
            gameCardView = itemView.findViewById(R.id.gameCardView);
        }
    }

    public void setFilteredList(List<SearchGameData> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_ONE) {
            View view = inflater.inflate(R.layout.search_games_item_1, parent, false);
            return new GameTitleViewHolderTypeOne(view);
        } else if (viewType == VIEW_TYPE_TWO) {
            View view = inflater.inflate(R.layout.search_games_item_2, parent, false);
            return new GameTitleViewHolderTypeTwo(view);
        }
        throw new IllegalArgumentException("Invalid view type");
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SearchGameData item = mList.get(position);

        if (holder instanceof GameTitleViewHolderTypeOne) {
            GameTitleViewHolderTypeOne typeOneHolder = (GameTitleViewHolderTypeOne) holder;
            typeOneHolder.gameLogo.setImageResource(item.getGameLogo());
            typeOneHolder.gameTitle.setText(item.getGameTitle());
        } else if (holder instanceof GameTitleViewHolderTypeTwo) {
            GameTitleViewHolderTypeTwo typeTwoHolder = (GameTitleViewHolderTypeTwo) holder;
            typeTwoHolder.gameLogo.setImageResource(item.getGameLogo());
            typeTwoHolder.gameTitle.setText(item.getGameTitle());
        }

        // Animation
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_game_search));
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return VIEW_TYPE_ONE;
        } else {
            return VIEW_TYPE_TWO;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}






