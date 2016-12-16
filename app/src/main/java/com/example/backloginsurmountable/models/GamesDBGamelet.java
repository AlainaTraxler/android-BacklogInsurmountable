package com.example.backloginsurmountable.models;

/**
 * Created by Guest on 12/14/16.
 */
public class GamesDBGamelet {
    public String releaseDate;
    public String platform;
    public String gamesDBId;
    public String gameTitle;
    public int counter;

    public GamesDBGamelet(String gameTitle, String releaseDate, String platform, String gamesDBId, int _counter) {
        this.gameTitle = gameTitle;
        this.releaseDate = releaseDate;
        this.platform = platform;
        this.gamesDBId = gamesDBId;
        counter = _counter;
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

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
