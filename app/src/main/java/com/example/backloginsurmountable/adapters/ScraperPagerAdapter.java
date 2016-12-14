package com.example.backloginsurmountable.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.backloginsurmountable.models.ScraperListItem;
import com.example.backloginsurmountable.ui.ScraperDetailFragment;

import java.util.ArrayList;

/**
 * Created by Guest on 12/14/16.
 */
public class ScraperPagerAdapter extends FragmentPagerAdapter{
    private ArrayList<ScraperListItem> mScrapedItems;
    
    public ScraperPagerAdapter(FragmentManager fm, ArrayList<ScraperListItem> restaurants) {
        super(fm);
        mScrapedItems = restaurants;
    }

    @Override
    public Fragment getItem(int position) {
        return ScraperDetailFragment.newInstance(mScrapedItems.get(position));
    }

    @Override
    public int getCount() {
        return mScrapedItems.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mScrapedItems.get(position).getName();
    }
}
