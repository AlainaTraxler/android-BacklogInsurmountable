package com.example.backloginsurmountable.adapters;

/**
 * Created by Guest on 12/1/16.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.backloginsurmountable.models.GamesDBGame;
import com.example.backloginsurmountable.ui.GameDetailFragment;

import java.util.ArrayList;

public class GamePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<GamesDBGame> mGames;

    public GamePagerAdapter(FragmentManager fm, ArrayList<GamesDBGame> games) {
        super(fm);
        mGames = games;
    }

    @Override
    public Fragment getItem(int position) {
        return GameDetailFragment.newInstance(mGames.get(position));
    }

    @Override
    public int getCount() {
        return mGames.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mGames.get(position).getGameTitle();
    }
}
