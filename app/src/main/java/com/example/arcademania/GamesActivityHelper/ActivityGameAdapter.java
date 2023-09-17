package com.example.arcademania.GamesActivityHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.arcademania.R;
import java.util.List;

public class ActivityGameAdapter extends RecyclerView.Adapter<ActivityGameAdapter.GameViewHolder> {

    private final List<ActivityGameData> gameList;

    public ActivityGameAdapter(List<ActivityGameData> gameList) {
        this.gameList = gameList;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.games_item, parent, false);
        return new GameViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        ActivityGameData currentItem = gameList.get(position);
        holder.gameLogoImageView.setImageResource(currentItem.getGameLogo()); // Use your method to get the logo resource
        holder.gameTitleTextView.setText(currentItem.getGameTitle());

        // Set an OnClickListener for the item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(currentItem);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        ImageView gameLogoImageView;
        TextView gameTitleTextView;

        public GameViewHolder(View itemView) {
            super(itemView);
            gameLogoImageView = itemView.findViewById(R.id.gameLogo);
            gameTitleTextView = itemView.findViewById(R.id.gameTitle);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ActivityGameData gameData);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}