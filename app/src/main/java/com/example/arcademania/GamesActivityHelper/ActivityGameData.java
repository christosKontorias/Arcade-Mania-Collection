package com.example.arcademania.GamesActivityHelper;

public class ActivityGameData {

    private final int gameLogo;
    private final String gameTitle;
    private String gameDescription;
    private String gameUrl;

    public ActivityGameData(int gameLogo, String gameTitle, String gameDescription, String gameUrl) {
        this.gameLogo = gameLogo;
        this.gameTitle = gameTitle;
        this.gameDescription = gameDescription;
        this.gameUrl = gameUrl;
    }

    public int getGameLogo() {
        return gameLogo;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public String getGameDescription() { return gameDescription;}
    public String getGameUrl() {
        return gameUrl;
    }
}