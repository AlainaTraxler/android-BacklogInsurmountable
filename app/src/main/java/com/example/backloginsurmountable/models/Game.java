package com.example.backloginsurmountable.models;

import org.parceler.Parcel;

/**
 * Created by Guest on 11/30/16.
 */

@Parcel
public class Game {
    private String mName;
    private String mGenre;

    public Game() {}

    public Game(String _name, String _genre){
        mName = _name;
        mGenre = _genre;
    }

    public String getName(){
        return mName;
    }

    public String getGenre(){
        return mGenre;
    }
}
