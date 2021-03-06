package com.example.backloginsurmountable.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guest on 12/14/16.
 */

@Parcel
public class GamesDBGame {
    public List<String> genres = new ArrayList<>();
    public String players;
    public String publisher;
    public String developer;
    public List<String> screenshots = new ArrayList<>();
    public String coop;
    public String overview;
    public String gameTitle;
    public String boxArt;
    int index;
    String pushId;

    public GamesDBGame(){}

    public GamesDBGame(String _gameTitle, String _overview, String _coop, String _developer, String _publisher, String _players, String _boxArt, int _index, List<String> _screenshots, List<String> _genres){
        screenshots = _screenshots;
        genres = _genres;
        gameTitle = _gameTitle;
        overview = _overview;
        coop = _coop;
        developer = _developer;
        publisher = _publisher;
        players = _players;
        boxArt = _boxArt;
        index = _index;
    }

    public String getGameTitle() { return gameTitle; }
    public String getOverview() { return overview; }
    public String getCoop() {return coop; }
    public String getDeveloper() { return developer; }
    public String getPublisher() { return publisher; }
    public List<String> getGenres() { return genres; }
    public List<String> getScreenshots() { return screenshots; }
    public String getPlayers() { return players; }
    public String getBoxArt() { return boxArt; }
    public int getIndex() { return index; }

    public void addGenre(String _genre) { genres.add(_genre); }
    public void addScreenshot(String _screenshot){ screenshots.add(_screenshot); }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
