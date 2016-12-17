package com.example.backloginsurmountable.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.backloginsurmountable.R;
import com.example.backloginsurmountable.models.GamesDBGame;

import org.parceler.Parcels;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameDetailFragmentLand extends Fragment {


    public GameDetailFragmentLand() {
        // Required empty public constructor
    }

    public static GameDetailFragmentLand newInstance(GamesDBGame game) {
        GameDetailFragmentLand gameDetailFragmentLand = new GameDetailFragmentLand();
        Bundle args = new Bundle();
        args.putParcelable("game", Parcels.wrap(game));
        gameDetailFragmentLand.setArguments(args);
        return gameDetailFragmentLand;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_detail_land, container, false);
    }

}
