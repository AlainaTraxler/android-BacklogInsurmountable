package com.example.backloginsurmountable.models;

/**
 * Created by Guest on 12/14/16.
 */
public class GamesDBGamelet {
    public String releaseDate;
    public String platform;
    public String gamesDBId;
    public String gameTitle;

    public GamesDBGamelet(String gameTitle, String releaseDate, String platform, String gamesDBId) {
        this.gameTitle = gameTitle;
        this.releaseDate = releaseDate;
        this.platform = platform;
        this.gamesDBId = gamesDBId;
    }

    public String getGamesDBId() {
        return gamesDBId;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPlatform() {
        return platform;
    }
}
