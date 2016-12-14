package com.example.backloginsurmountable.models;

/**
 * Created by Guest on 12/14/16.
 */
public class ScraperListItem {
    private String name;
    private int index;

    public ScraperListItem(String _name, int _index){
        name = _name;
        index = _index;
    }

    public String getName(){ return name; }
    public int getIndex(){ return index; }
}
