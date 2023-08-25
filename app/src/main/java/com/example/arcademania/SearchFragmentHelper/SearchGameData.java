package com.example.arcademania.SearchFragmentHelper;


public class SearchGameData {
    private String gameTitle;
    private int gameLogo;

    public SearchGameData(String gameTitle, int gameLogo) {
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