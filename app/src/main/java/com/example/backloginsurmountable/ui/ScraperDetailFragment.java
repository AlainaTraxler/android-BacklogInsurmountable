package com.example.backloginsurmountable.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.backloginsurmountable.Constants;
import com.example.backloginsurmountable.R;
import com.example.backloginsurmountable.adapters.GameletListAdapter;
import com.example.backloginsurmountable.adapters.ScraperListAdapter;
import com.example.backloginsurmountable.models.GamesDBGame;
import com.example.backloginsurmountable.models.GamesDBGamelet;
import com.example.backloginsurmountable.models.ScraperListItem;
import com.example.backloginsurmountable.services.GamesDBService;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScraperDetailFragment extends Fragment implements View.OnClickListener{
    @Bind(R.id.editText_Search) EditText mEditText_Search;
    @Bind(R.id.button_Search) Button mButton_Search;

    private ScraperListItem mScraperListItem;
    private GameletListAdapter mAdapter;
    private ArrayList<GamesDBGamelet> mGameletArray = new ArrayList<GamesDBGamelet>();

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

    public void setAdapter(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_scraper_detail, container, false);
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
                        String gameTitle = jsonObj.getJSONObject("Data").getJSONArray("Game").getJSONObject(i).getString("GameTitle");
                        String releaseDate = jsonObj.getJSONObject("Data").getJSONArray("Game").getJSONObject(i).getString("ReleaseDate");
                        String platform = jsonObj.getJSONObject("Data").getJSONArray("Game").getJSONObject(i).getString("Platform");
                        String id = jsonObj.getJSONObject("Data").getJSONArray("Game").getJSONObject(i).getString("id");

                        GamesDBGamelet gamelet = new GamesDBGamelet(gameTitle, releaseDate, platform, id, mScraperListItem.getIndex());
                        mGameletArray.add(gamelet);
//                        Log.v("????", mGameletArray.size() + "");
                    }
                  getActivity().runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
                          mAdapter = new GameletListAdapter(getActivity(), mGameletArray);
                          mRecyclerView.setAdapter(mAdapter);
                          RecyclerView.LayoutManager layoutManager =
                                  new LinearLayoutManager(getActivity());
                          mRecyclerView.setLayoutManager(layoutManager);
                          mRecyclerView.setHasFixedSize(true);
                      }
                  });
                    Log.v("!!!!", mGameletArray.size() + "");
                } catch (JSONException e) {
                    Log.e("JSON exception", e.getMessage());
                    e.printStackTrace();
                }

                Log.d("XML", response.body().string());

                Log.d("JSON", jsonObj.toString());
            }
        });

        mButton_Search.setOnClickListener(this);

        Log.v("!!!!!!!!", mGameletArray.size() + "");

        return view;
    }

    public void onClick(View v){
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        final GamesDBService apiServcice = new GamesDBService();
        apiServcice.findGameById(mEditText_Search.getText().toString(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("GamesDBService: ", "Failed!");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                GamesDBGame game = apiServcice.processResultById(response, mScraperListItem.getIndex());
                DatabaseReference pushRef =  dbRef.child(Constants.DB_GAMES_NODE).push();
                game.setPushId(pushRef.getKey());
                pushRef.setValue(game);

                dbRef.child(Constants.DB_GAMELISTS_NODE).child(pushRef.getKey()).setValue(game.getIndex());
            }
        });
    }

}
