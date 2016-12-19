package com.example.backloginsurmountable.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.backloginsurmountable.Constants;
import com.example.backloginsurmountable.R;
import com.example.backloginsurmountable.models.GamesDBGame;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameDetailFragment extends Fragment {
    @Bind(R.id.textView_Deck) TextView mTextView_Deck;
    @Bind(R.id.imageView_Splash) ImageView mImageView_Splash;
    @Bind(R.id.checkBox_Complete) CheckBox mCheckBox_Complete;
    @Bind(R.id.checkBox_100) CheckBox mCheckBox_100;
    @Bind(R.id.checkBox_Blind) CheckBox mCheckBox_Blind;
    @Bind(R.id.checkBox_Hardcore) CheckBox mCheckBox_Hardcore;
    @Bind(R.id.imageView_Screenshot) ImageView mImageView_Screenshot;
    @Bind(R.id.textView_Developer) TextView mTextView_Developer;
    @Bind(R.id.textView_Publisher) TextView mTextView_Publisher;
    @Bind(R.id.textView_Genres) TextView mTextView_Genres;
    @Bind(R.id.textView_Date) TextView mTextView_Date;
    @Bind(R.id.textView_Players) TextView mTextView_Players;
    @Bind(R.id.textView_Coop) TextView mTextView_Coop;

    public FirebaseAuth mAuth;
    private DatabaseReference dbCurrentUser;

    private GamesDBGame mGame;

    public GameDetailFragment() {
        // Required empty public constructor
    }

    public static GameDetailFragment newInstance(GamesDBGame game) {
        GameDetailFragment gameDetailFragment = new GameDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("game", Parcels.wrap(game));
        gameDetailFragment.setArguments(args);
        return gameDetailFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGame = Parcels.unwrap(getArguments().getParcelable("game"));

        mAuth = FirebaseAuth.getInstance();
        dbCurrentUser = FirebaseDatabase.getInstance().getReference(Constants.DB_USERS_NODE).child(mAuth.getCurrentUser().getUid());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_detail, container, false);
        ButterKnife.bind(this, view);

        dbCurrentUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("complete/" + mGame.getPushId())){
                    mCheckBox_Complete.setChecked(true);
                }
                if(dataSnapshot.hasChild("100/" + mGame.getPushId())){
                    mCheckBox_100.setChecked(true);
                }
                if(dataSnapshot.hasChild("blind/" + mGame.getPushId())){
                    mCheckBox_Blind.setChecked(true);
                }
                if(dataSnapshot.hasChild("hardcore/" + mGame.getPushId())){
                    mCheckBox_Hardcore.setChecked(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mCheckBox_Complete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked ) {
                    dbCurrentUser.child("complete").child(mGame.getPushId()).setValue(mGame.getIndex());
                    dbCurrentUser.child("remaining").child(mGame.getPushId()).removeValue();
                }else{
                    dbCurrentUser.child("complete").child(mGame.getPushId()).removeValue();
                    mCheckBox_Hardcore.setChecked(false);
                    mCheckBox_100.setChecked(false);
                    mCheckBox_Blind.setChecked(false);
                    checkBoxCleanup();
                }

            }
        });

        mCheckBox_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked ) {
                    dbCurrentUser.child("100").child(mGame.getPushId()).setValue(mGame.getIndex());
                    dbCurrentUser.child("remaining").child(mGame.getPushId()).removeValue();
                    mCheckBox_Complete.setChecked(true);
                }else{
                    dbCurrentUser.child("100").child(mGame.getPushId()).removeValue();
                    checkBoxCleanup();
                }
            }
        });

        mCheckBox_Blind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked ) {
                    dbCurrentUser.child("blind").child(mGame.getPushId()).setValue(mGame.getIndex());
                    dbCurrentUser.child("remaining").child(mGame.getPushId()).removeValue();
                    mCheckBox_Complete.setChecked(true);
                }else{
                    dbCurrentUser.child("blind").child(mGame.getPushId()).removeValue();
                    checkBoxCleanup();
                }

            }
        });

        mCheckBox_Hardcore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked ) {
                    dbCurrentUser.child("hardcore").child(mGame.getPushId()).setValue(mGame.getIndex());
                    dbCurrentUser.child("remaining").child(mGame.getPushId()).removeValue();
                    mCheckBox_Complete.setChecked(true);
                }else{
                    dbCurrentUser.child("hardcore").child(mGame.getPushId()).removeValue();
                    checkBoxCleanup();
                }

            }
        });

        mTextView_Deck.setText(mGame.getOverview());
        mTextView_Developer.setText(mGame.getDeveloper());
        mTextView_Publisher.setText(mGame.getPublisher());
        mTextView_Genres.setText("?");
        mTextView_Date.setText("?");
        mTextView_Players.setText(mGame.getPlayers());
        mTextView_Coop.setText(mGame.getCoop());

        if(mGame.getScreenshots().size() > 0){
            Picasso.with(getActivity().getApplicationContext()).load(mGame.getScreenshots().get(0).toString()).into(mImageView_Screenshot);
        }

        Picasso.with(getActivity().getApplicationContext()).load(mGame.getBoxArt()).into(mImageView_Splash);

        return view;
    }

    public Boolean checkIfOtherBoxesChecked(){
        if(mCheckBox_Complete.isChecked() || mCheckBox_100.isChecked() || mCheckBox_Blind.isChecked() || mCheckBox_Hardcore.isChecked()){
            return true;
        }else return false;
    }

    public void checkBoxCleanup(){
        if(!mCheckBox_Complete.isChecked() && !mCheckBox_100.isChecked() && !mCheckBox_Blind.isChecked() && !mCheckBox_Hardcore.isChecked()){
            dbCurrentUser.child("remaining").child(mGame.getPushId()).setValue(mGame.getIndex());
        }
    }

}
