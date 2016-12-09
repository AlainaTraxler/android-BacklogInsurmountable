package com.example.backloginsurmountable.models;

import org.parceler.Parcel;

/**
 * Created by Guest on 11/30/16.
 */

@Parcel
public class Game {
    private String name;
    private String deck;
    private String imageURL;
    private String pushId;
    private String giantBombId;
    private String system = "NES";

    public Game() {}

    public Game(String _name, String _genre, String _deck, String _imageURL, String _giantBombId){
        name = _name;
        deck = _deck;
        imageURL = _imageURL;
        giantBombId = _giantBombId;
    }

    public String getName(){
        return name;
    }
    public String getDeck(){ return deck; }
    public String getImageURL(){ return imageURL; }
    public String getpushId(){ return pushId; }
    public String getGiantBombId(){ return giantBombId; }

    public void setPushId(String _pushId){ pushId = _pushId; }
    public void setName(String _name){ name = _name; }
}
