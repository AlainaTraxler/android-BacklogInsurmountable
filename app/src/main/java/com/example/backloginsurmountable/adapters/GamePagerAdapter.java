package com.example.backloginsurmountable.adapters;

/**
 * Created by Guest on 12/1/16.
 */

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.backloginsurmountable.models.GamesDBGame;
import com.example.backloginsurmountable.ui.GameDetailFragment;
import com.example.backloginsurmountable.ui.GameDetailFragmentLand;

import java.util.ArrayList;

public class GamePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<GamesDBGame> mGames;
    private Context mContext;

    public GamePagerAdapter(FragmentManager fm, ArrayList<GamesDBGame> games, Context context) {
        super(fm);
        mGames = games;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        final int orientation;
        orientation = mContext.getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            Log.v("!!!", "Landscape!");
            return GameDetailFragmentLand.newInstance(mGames.get(position));
        }else{
            return GameDetailFragment.newInstance(mGames.get(position));
        }

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
