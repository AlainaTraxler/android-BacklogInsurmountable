package com.example.backloginsurmountable.ui;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.backloginsurmountable.GiantBombService.GiantBombService;
import com.example.backloginsurmountable.R;
import com.example.backloginsurmountable.adapters.FirebaseGameViewHolder;
import com.example.backloginsurmountable.adapters.GameListAdapter;
import com.example.backloginsurmountable.models.Game;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.graphics.Typeface.createFromAsset;

public class BacklogActivity extends AppCompatActivity {
    @Bind(R.id.textView_Completed) TextView mTextView_Completed;
    @Bind(R.id.textView_Remaining) TextView mTextView_Remaining;
    @Bind(R.id.textView_PercentCompleted) TextView mTextView_PercentCompleted;
    @Bind(R.id.textView_CompletedHeader) TextView mTextView_CompletedHeader;
    @Bind(R.id.textView_RemainingHeader) TextView mTextView_RemainingHeader;
    @Bind(R.id.listView_NESGameList) RecyclerView mListView_NESGameList;
    @Bind(R.id.searchView) SearchView mSearchView;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public static final String TAG = BacklogActivity.class.getSimpleName();

    ArrayList<Game> mNESGameList = new ArrayList<Game>();
    int mNumberOfGames;
    int mRemaining;
    int mCompleted;
    String mPercentCompleted;

    private DatabaseReference mGameListReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backlog);
        ButterKnife.bind(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String query) {
                Toast.makeText(BacklogActivity.this, "Submitted", Toast.LENGTH_SHORT).show();

                final DatabaseReference mGames = FirebaseDatabase.getInstance()
                        .getReference("games");

                final DatabaseReference mSearch = FirebaseDatabase.getInstance()
                        .getReference("search");

                mSearch.removeValue();

                mGames.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Game game = dataSnapshot.getValue(Game.class);
                        if(game.getName().toLowerCase().contains(query.toLowerCase())){
                            mSearch.child(game.getpushId()).setValue(game);
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                setUpFirebaseAdapter(mSearch);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    mGameListReference = FirebaseDatabase.getInstance()
                            .getReference("games");
                    setUpFirebaseAdapter(mGameListReference);
                }
                return false;
            }

        });


        mGameListReference = FirebaseDatabase.getInstance()
                .getReference("games");
        setUpFirebaseAdapter(mGameListReference);

        Typeface erbosDraco = createFromAsset(getAssets(), "fonts/erbosdraco_nova_open_nbp.ttf");
        mTextView_Completed.setTypeface(erbosDraco);
        mTextView_Remaining.setTypeface(erbosDraco);
        mTextView_PercentCompleted.setTypeface(erbosDraco);
        mTextView_CompletedHeader.setTypeface(erbosDraco);
        mTextView_RemainingHeader.setTypeface(erbosDraco);


        mNumberOfGames = mNESGameList.size();
        mRemaining = mNumberOfGames;
        mCompleted = 0;
        mPercentCompleted = "0%";

        mTextView_Completed.setText(String.valueOf(mCompleted));
        mTextView_Remaining.setText(String.valueOf(mRemaining));
        mTextView_PercentCompleted.setText(String.valueOf(mPercentCompleted));
    }

    private void setUpFirebaseAdapter(DatabaseReference _list) {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Game, FirebaseGameViewHolder>
                (Game.class, R.layout.game_list_item, FirebaseGameViewHolder.class,
                        _list) {

            @Override
            protected void populateViewHolder(final FirebaseGameViewHolder viewHolder,
                                              Game model, int position) {
                String key = this.getRef(position).getKey();
                Query queryRef = FirebaseDatabase.getInstance().getReference("games").orderByKey().equalTo(key);
                //--------------
                queryRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Game game = dataSnapshot.getValue(Game.class);
                        viewHolder.bindGame(game);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {}

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }
        };

        mListView_NESGameList.setHasFixedSize(true);
        mListView_NESGameList.setLayoutManager(new LinearLayoutManager(this));
        mListView_NESGameList.setAdapter(mFirebaseAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Backlog Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.backloginsurmountable/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Backlog Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.backloginsurmountable/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
