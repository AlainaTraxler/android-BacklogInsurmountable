package com.example.backloginsurmountable.ui;

import android.graphics.Typeface;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.backloginsurmountable.R;
import com.example.backloginsurmountable.adapters.FirebaseGameListAdapter;
import com.example.backloginsurmountable.adapters.FirebaseGameViewHolder;
import com.example.backloginsurmountable.models.GamesDBGame;
import com.example.backloginsurmountable.utils.OnStartDragListener;
import com.example.backloginsurmountable.utils.OnSwipeTouchListener;
import com.example.backloginsurmountable.utils.SimpleItemTouchHelperCallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.internal.DiskLruCache;

import static android.graphics.Typeface.createFromAsset;

public class BacklogActivity extends BaseActivity implements OnStartDragListener, View.OnClickListener {
    @Bind(R.id.textView_Completed) TextView mTextView_Completed;
    @Bind(R.id.textView_Remaining) TextView mTextView_Remaining;
    @Bind(R.id.textView_PercentCompleted) TextView mTextView_PercentCompleted;
    @Bind(R.id.textView_CompletedHeader) TextView mTextView_CompletedHeader;
    @Bind(R.id.textView_RemainingHeader) TextView mTextView_RemainingHeader;
    @Bind(R.id.listView_NESGameList) RecyclerView mListView_NESGameList;
    @Bind(R.id.searchView) SearchView mSearchView;
    @Bind(R.id.toggleButton) ToggleButton mToggleButton;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public static final String TAG = BacklogActivity.class.getSimpleName();

    float mRemaining = 0;
    float mCompleted = 0;
    float mPercentCompleted = 0;
    float mTotalGames = 0;
    String mQuery = "";

    Boolean updateNeeded = false;

    private FirebaseGameListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper = null;

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
                mQuery = query;
                Log.e(">>QueryTextSubmit", "!!!");
                setUpFirebaseAdapter(filter(mQuery, mToggleButton.isChecked()));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    mQuery = "";
                    Log.e(">>QueryTextChange", "!!!");
                    setUpFirebaseAdapter(filter(mQuery, mToggleButton.isChecked()));
                }
                return false;
            }

        });

        Typeface erbosDraco = createFromAsset(getAssets(), "fonts/erbosdraco_nova_open_nbp.ttf");
        mTextView_Completed.setTypeface(erbosDraco);
        mTextView_Remaining.setTypeface(erbosDraco);
        mTextView_PercentCompleted.setTypeface(erbosDraco);
        mTextView_CompletedHeader.setTypeface(erbosDraco);
        mTextView_RemainingHeader.setTypeface(erbosDraco);

        Log.e(">>onCreate", "!!!");
        setUpFirebaseAdapter(filter(mQuery, mToggleButton.isChecked()));

        dbCurrentUser.child("complete").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //add filter?
                mCompleted = dataSnapshot.getChildrenCount();
                dbCurrentUser.child("remaining").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mRemaining = dataSnapshot.getChildrenCount();
                        mTotalGames = mCompleted + mRemaining;
                        updateScoreboard();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mListView_NESGameList);

        mToggleButton.setOnClickListener(this);
    }

    public void onClick(View v){
        if(mToggleButton.isChecked()){
            mItemTouchHelper.attachToRecyclerView(null);
            Log.e(">>onClick is checked", "!!!");
            filter(mQuery, mToggleButton.isChecked());
        }else{
            mItemTouchHelper.attachToRecyclerView(mListView_NESGameList);
            Log.e(">>onClick not checked", "!!!");
            filter(mQuery, mToggleButton.isChecked());
        }
    }

    private DatabaseReference filter(final String query, final Boolean isChecked){
        updateNeeded = false;

        dbCurrentUser.child("search").removeValue();

        DatabaseReference dbSearch = dbCurrentUser.child("search");
        DatabaseReference dbSearchArea;

        Query queryRef;

        if(isChecked){
            queryRef = dbCurrentUser.child("complete");
        }else queryRef = dbCurrentUser.child("remaining");

        //-- CLEAN THIS UP --//

        queryRef.orderByValue().startAt(0).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                dbGames.child(dataSnapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        GamesDBGame game = dataSnapshot.getValue(GamesDBGame.class);

                        if(game.getGameTitle().toLowerCase().contains(query.toLowerCase())){
                            dbCurrentUser.child("search").child(game.getPushId()).setValue(game);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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
        return dbSearch;
    }

    private void updateScoreboard(){
        if(mCompleted == 0){
            mPercentCompleted = 0;
        }else if(mRemaining == 0){
            mPercentCompleted = 100;
        }else{
            mPercentCompleted = (mCompleted / mTotalGames) * 100;
        }

        Log.v("updateScoreboard", "triggered");

        mTextView_Completed.setText(String.format("%.0f", mCompleted));
        mTextView_Remaining.setText(String.format("%.0f", mRemaining));
        mTextView_PercentCompleted.setText(String.format("%.2f", mPercentCompleted) + "%");
    }

    private void setUpFirebaseAdapter(DatabaseReference _list) {
        Typeface PressStart2P = createFromAsset(getAssets(), "fonts/PressStart2P.ttf");
        mFirebaseAdapter = new FirebaseGameListAdapter
                (GamesDBGame.class, R.layout.game_list_item, FirebaseGameViewHolder.class,
                        _list.orderByChild("index").startAt(0),this,this, PressStart2P) {

            @Override
            protected void populateViewHolder(final FirebaseGameViewHolder viewHolder,
                                              GamesDBGame model, int position) {
                Typeface PressStart2P = createFromAsset(getAssets(), "fonts/PressStart2P.ttf");
                viewHolder.bindGame(model, PressStart2P);
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
        ////
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
//        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        dbCurrentUser.child("complete").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(updateNeeded){
                    filter(mQuery, mToggleButton.isChecked());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Do your code here
    }
}
