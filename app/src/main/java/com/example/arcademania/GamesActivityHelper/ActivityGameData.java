package com.example.arcademania.GamesActivityHelper;

public class ActivityGameData {

    private int gameLogo;
    private String gameTitle;

    public ActivityGameData(int gameLogo, String gameTitle) {
        this.gameLogo = gameLogo;
        this.gameTitle = gameTitle;
    }

    public int getGameLogo() {
        return gameLogo;
    }

    public String getGameTitle() {
        return gameTitle;
    }
}