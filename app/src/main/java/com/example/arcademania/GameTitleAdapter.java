package com.example.arcademania;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.games_item, parent, false);
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
}






