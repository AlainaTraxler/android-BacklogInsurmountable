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
    private String mImageURL;
    private String mId;

    public Game() {}

    public Game(String _name, String _genre, String _deck, String _imageURL){
        mName = _name;
        mGenre = _genre;
        mDeck = _deck;
        mImageURL = _imageURL;
    }

    public String getName(){
        return mName;
    }

    public String getGenre(){ return mGenre; }

    public String getDeck(){ return mDeck; }

    public String getImageURL(){ return mImageURL; }

    public String getId(){ return mId; }
}
