package com.example.arcademania.SearchFragmentHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.arcademania.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;
/*
public class GameTitleAdapter extends RecyclerView.Adapter<GameTitleAdapter.GameTitleViewHolder> {

    private List<GameTitleData> mList;
    public GameTitleAdapter(List<GameTitleData> mList) {
        this.mList = mList;
    }
    public class GameTitleViewHolder extends RecyclerView.ViewHolder {
        ImageView gameLogo;
        TextView gameTitle;
        MaterialCardView gameCardView;

        public GameTitleViewHolder(View itemView) {
            super(itemView);
            gameLogo = itemView.findViewById(R.id.gameLogo);
            gameTitle = itemView.findViewById(R.id.gameTitle);
            gameCardView = itemView.findViewById(R.id.gameCardView);
        }
    }

    public void setFilteredList(List<GameTitleData> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public GameTitleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.games_item_1, parent, false);
        return new GameTitleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GameTitleViewHolder holder, int position) {
        holder.gameLogo.setImageResource(mList.get(position).getGameLogo());
        holder.gameTitle.setText(mList.get(position).getGameTitle());

        //Animation
        holder.gameCardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_game_search));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    */
public class GameAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ONE = 1;
    private static final int VIEW_TYPE_TWO = 2;

    private List<GameData> mList;

    public GameAdapter(List<GameData> mList) {
        this.mList = mList;
    }

    public class GameTitleViewHolderTypeOne extends RecyclerView.ViewHolder {
        // Views for item type one
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

    public class GameTitleViewHolderTypeTwo extends RecyclerView.ViewHolder {
        // Views for item type two
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

    public void setFilteredList(List<GameData> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GameData item = mList.get(position);

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






