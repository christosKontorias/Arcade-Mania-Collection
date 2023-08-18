package com.example.arcademania;


public class GameTitleData {
    private String gameTitle;
    private int gameLogo;

    public GameTitleData(String gameTitle, int gameLogo) {
        this.gameTitle = gameTitle;
        this.gameLogo = gameLogo;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public int getGameLogo() {
        return gameLogo;
    }
}