package com.example.arcademania.SearchFragmentHelper;


public class GameData {
    private String gameTitle;
    private int gameLogo;

    public GameData(String gameTitle, int gameLogo) {
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