package com.example.backloginsurmountable.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.example.backloginsurmountable.R;
import com.example.backloginsurmountable.adapters.GamePagerAdapter;
import com.example.backloginsurmountable.models.GamesDBGame;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class GameDetailActivity extends BaseActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;

    private GamePagerAdapter adapterViewPager;
    ArrayList<GamesDBGame> mGames = new ArrayList<>();

    private Boolean restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(new Bundle());
        setContentView(R.layout.activity_game_detail);
        ButterKnife.bind(this);


        mGames = Parcels.unwrap(getIntent().getParcelableExtra("games"));
        final int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new GamePagerAdapter(getSupportFragmentManager(), mGames, mContext, "landscape");
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

}
