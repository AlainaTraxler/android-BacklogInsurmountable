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

    public Game() {}

    public Game(String _name, String _genre, String _deck, String _imageURL){
        name = _name;
        deck = _deck;
        imageURL = _imageURL;
    }

    public String getName(){
        return name;
    }
    public String getDeck(){ return deck; }
    public String getImageURL(){ return imageURL; }
    public String getId(){ return pushId; }
}
