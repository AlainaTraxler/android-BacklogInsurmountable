package com.example.backloginsurmountable.ui;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        ButterKnife.bind(this);

        mGames = Parcels.unwrap(getIntent().getParcelableExtra("games"));
        final int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new GamePagerAdapter(getSupportFragmentManager(), mGames, mContext);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);

        SensorEventListener sensor = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                adapterViewPager = new GamePagerAdapter(getSupportFragmentManager(), mGames, mContext);
                mViewPager.setAdapter(adapterViewPager);
                mViewPager.setCurrentItem(startingPosition);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };
    }
}
