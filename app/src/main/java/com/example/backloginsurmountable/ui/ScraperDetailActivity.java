package com.example.backloginsurmountable.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.backloginsurmountable.R;
import com.example.backloginsurmountable.adapters.GamePagerAdapter;
import com.example.backloginsurmountable.adapters.ScraperPagerAdapter;
import com.example.backloginsurmountable.models.Game;
import com.example.backloginsurmountable.models.ScraperListItem;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ScraperDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    
    private ScraperPagerAdapter adapterViewPager;
    ArrayList<ScraperListItem> mScrapedGames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        ButterKnife.bind(this);

        mScrapedGames = Parcels.unwrap(getIntent().getParcelableExtra("scrapedGames"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new ScraperPagerAdapter(getSupportFragmentManager(), mScrapedGames);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
