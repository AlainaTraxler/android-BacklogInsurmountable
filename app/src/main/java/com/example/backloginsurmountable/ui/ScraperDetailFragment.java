package com.example.backloginsurmountable.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.backloginsurmountable.R;
import com.example.backloginsurmountable.models.ScraperListItem;
import com.example.backloginsurmountable.services.GamesDBService;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.parceler.Parcels;

import java.io.IOException;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScraperDetailFragment extends Fragment {
    private ScraperListItem mScraperListItem;

    public static ScraperDetailFragment newInstance(ScraperListItem scraperListItem) {
        ScraperDetailFragment scraperDetailFragment = new ScraperDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("scraperListItem", Parcels.wrap(scraperListItem));
        scraperDetailFragment.setArguments(args);
        return scraperDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScraperListItem = Parcels.unwrap(getArguments().getParcelable("scraperListItem"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scraper_detail, container, false);
        ButterKnife.bind(this, view);

        GamesDBService apiService = new GamesDBService();

        apiService.findGameListByName(mScraperListItem.getName(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("GamesDB", "Failed!");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String catcher ="";
                JSONObject jsonObj = null;
                try {
                    jsonObj = XML.toJSONObject(response.body().string());
                    int arraySize = jsonObj.getJSONObject("Data").getJSONArray("Game").length();
                    for(int i = 0; i < arraySize; i++){
                        String gameTitle = jsonObj.getJSONObject("Data").getJSONArray("Game").getJSONObject(0).getString("GameTitle");
                        String overview = jsonObj.getJSONObject("Data").getJSONArray("Game").getJSONObject(0).getString("overview");
                        String coop = jsonObj.getJSONObject("Data").getJSONArray("Game").getJSONObject(0).getString("coop");
                        String developer = jsonObj.getJSONObject("Data").getJSONArray("Game").getJSONObject(0).getString("developer");
                        String publisher = jsonObj.getJSONObject("Data").getJSONArray("Game").getJSONObject(0).getString("publisher");
                    }
                    jsonObj.getJSONObject("Data").getJSONArray("Game").getJSONObject(0).getString("GameTitle");
                } catch (JSONException e) {
                    Log.e("JSON exception", e.getMessage());
                    e.printStackTrace();
                }

                Log.d("XML", response.body().string());

                Log.d("JSON", jsonObj.toString());
            }
        });

        return view;
    }

}
