package com.example.backloginsurmountable.ui;

import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.backloginsurmountable.R;
import com.example.backloginsurmountable.adapters.FirebaseGameListAdapter;
import com.example.backloginsurmountable.adapters.FirebaseGameViewHolder;
import com.example.backloginsurmountable.models.Game;
import com.example.backloginsurmountable.utils.OnStartDragListener;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    ArrayList<Game> mNESGameList = new ArrayList<Game>();
    int mNumberOfGames;
    float mRemaining;
    float mCompleted;
    float mPercentCompleted;
    String mQuery = "";

    private FirebaseGameListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;

    private DatabaseReference mUserRef;
    private DatabaseReference mGamesRef;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backlog);
        ButterKnife.bind(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        mUserRef = FirebaseDatabase.getInstance().getReference("users/" + mAuth.getCurrentUser().getUid());
        mGamesRef = FirebaseDatabase.getInstance().getReference("gamelists/NES");
        dbRef = FirebaseDatabase.getInstance().getReference();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String query) {
                mQuery = query;
                setUpFirebaseAdapter(filter(mQuery, mToggleButton.isChecked()));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    mQuery = "";
                    setUpFirebaseAdapter(filter(mQuery, mToggleButton.isChecked()));
                }
                return false;
            }

        });

        setUpFirebaseAdapter(filter(mQuery, mToggleButton.isChecked()));

        Typeface erbosDraco = createFromAsset(getAssets(), "fonts/erbosdraco_nova_open_nbp.ttf");
        mTextView_Completed.setTypeface(erbosDraco);
        mTextView_Remaining.setTypeface(erbosDraco);
        mTextView_PercentCompleted.setTypeface(erbosDraco);
        mTextView_CompletedHeader.setTypeface(erbosDraco);
        mTextView_RemainingHeader.setTypeface(erbosDraco);

        updateScoreboard();

        mToggleButton.setOnClickListener(this);
    }

    public void onClick(View v){
        filter(mQuery, mToggleButton.isChecked());
    }

    private DatabaseReference filter(final String query, final Boolean isChecked){
        final DatabaseReference mGames = FirebaseDatabase.getInstance()
                .getReference("games");

        final DatabaseReference mUserRef = FirebaseDatabase.getInstance()
                .getReference("users").child(mAuth.getCurrentUser().getUid());

        mUserRef.child("search").removeValue();

        mGames.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final Game game = dataSnapshot.getValue(Game.class);
                if(game.getName().toLowerCase().contains(query.toLowerCase())){
//                    final
                    mUserRef.child("complete").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Boolean isThere = dataSnapshot.hasChild(game.getpushId());

                            if(isChecked && isThere) {
                                mUserRef.child("search").child(game.getpushId()).setValue(game);
                            }else if(!isChecked && !isThere){
                                mUserRef.child("search").child(game.getpushId()).setValue(game);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
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
        return mUserRef.child("search");
    }

    private void updateScoreboard(){
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mCompleted = dataSnapshot.child("users").child(mAuth.getCurrentUser().getUid()).child("complete").getChildrenCount();
                float totalGames = dataSnapshot.child("gamelists/NES").getChildrenCount();
                mRemaining = totalGames - mCompleted;
                mPercentCompleted = (mCompleted / totalGames) * 100;

                mTextView_Completed.setText(String.valueOf(mCompleted));
                mTextView_Remaining.setText(String.valueOf(mRemaining));
                mTextView_PercentCompleted.setText(String.format("%.2f", mPercentCompleted) + "%");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setUpFirebaseAdapter(DatabaseReference _list) {
        Typeface PressStart2P = createFromAsset(getAssets(), "fonts/PressStart2P.ttf");
        mFirebaseAdapter = new FirebaseGameListAdapter
                (Game.class, R.layout.game_list_item, FirebaseGameViewHolder.class,
                        _list,this,this, PressStart2P) {

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
                        Typeface PressStart2P = createFromAsset(getAssets(), "fonts/PressStart2P.ttf");
                        viewHolder.bindGame(game, PressStart2P);
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

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mListView_NESGameList);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
//        mItemTouchHelper.startDrag(viewHolder);
    }
}
