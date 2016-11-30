package com.example.backloginsurmountable.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.backloginsurmountable.models.Game;

import java.util.ArrayList;

/**
 * Created by Guest on 11/30/16.
 */
public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.GameViewHolder> {
    private ArrayList<Game> mGames = new ArrayList<>();
    private Context mContext;

    public GameListAdapter(Context context, ArrayList<Game> games) {
        mContext = context;
        mGames = games;
    }
}