package com.example.backloginsurmountable.models;

import org.parceler.Parcel;

/**
 * Created by Guest on 11/30/16.
 */

@Parcel
public class Game {
    private String mName;
    private String mGenre;
    private String mDeck;

    public Game() {}

    public Game(String _name, String _genre, String _deck){
        mName = _name;
        mGenre = _genre;
        mDeck = _deck;
    }

    public String getName(){
        return mName;
    }

    public String getGenre(){ return mGenre; }

    public String getDeck(){ return mDeck; }
}
