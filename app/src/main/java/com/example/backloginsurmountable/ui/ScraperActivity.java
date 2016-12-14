package com.example.backloginsurmountable.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.backloginsurmountable.R;
import com.example.backloginsurmountable.adapters.ScraperListAdapter;
import com.example.backloginsurmountable.models.ScraperListItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ScraperActivity extends BaseActivity {
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    private ScraperListAdapter mAdapter;
    private ArrayList<ScraperListItem> mScrapedGames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scraper);
        ButterKnife.bind(this);

        readFile("NES");
    }

    public void setAdapter(){
        Log.d("Adapter! ", "In!");
        mAdapter = new ScraperListAdapter(getApplicationContext(), mScrapedGames);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(ScraperActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

    public void readFile(String _system){
        ArrayList<ScraperListItem> catcher = new ArrayList<ScraperListItem>();

        int counter = 0;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("gamelists/" + _system + "ScrapeList.txt")));
            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                counter+=10;
                Log.v(mLine, String.valueOf(counter));
                ScraperListItem scraperListItem = new ScraperListItem(mLine, counter);
                mScrapedGames.add(scraperListItem);
            }

        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        setAdapter();
    }
}
