package com.example.backloginsurmountable.models;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Guest on 12/14/16.
 */
public class GamesDBGame {
    public List<String> genres;
    public String players;
    public String publisher;
    public String developer;
    public List<String> screenshots;
    public String coop;
    public String overview;
    public String gameTitle;

    public GamesDBGame(String _gameTitle, String _overview, String _coop, String _developer, String _publisher, String _players){
        screenshots = new ArrayList<String>();
        genres = new ArrayList<String>();
        gameTitle = _gameTitle;
        overview = _overview;
        coop = _coop;
        developer = _developer;
        publisher = _publisher;
        players = _players;
    }

    public String getGameTitle() { return gameTitle; }
    public String getOverview() { return overview; }
    public String getCoop() {return coop; }

    public String getDeveloper() {
        return developer;
    }

    public String getPublisher() {
        return publisher;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<String> getScreenshots() {
        return screenshots;
    }

    public void addGenre(String _genre) {
        genres.add(_genre);
    }

    public void addScreenshot(String _screenshot){
        screenshots.add(_screenshot);
    }
}
