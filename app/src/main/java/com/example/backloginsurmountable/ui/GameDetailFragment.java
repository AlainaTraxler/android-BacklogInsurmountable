package com.example.backloginsurmountable.ui;

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
import android.widget.Toast;

import com.example.backloginsurmountable.R;
import com.example.backloginsurmountable.models.Game;
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
    @Bind(R.id.textView_Name) TextView mTextView_Name;
    @Bind(R.id.imageView_Splash) ImageView mImageView_Splash;
    @Bind(R.id.checkBox_Complete) CheckBox mCheckBox_Complete;
    @Bind(R.id.checkBox_100) CheckBox mCheckBox_100;
    @Bind(R.id.checkBox_Blind) CheckBox mCheckBox_Blind;
    @Bind(R.id.checkBox_Hardcore) CheckBox mCheckBox_Hardcore;

    public FirebaseAuth mAuth;
    private DatabaseReference dbCurrentUser;

    private Game mGame;

    public GameDetailFragment() {
        // Required empty public constructor
    }

    public static GameDetailFragment newInstance(Game game) {
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
        dbCurrentUser = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_detail, container, false);
        ButterKnife.bind(this, view);

//        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid());

        dbCurrentUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("complete/" + mGame.getpushId())){
                    mCheckBox_Complete.setChecked(true);
                }
                if(dataSnapshot.hasChild("100/" + mGame.getpushId())){
                    mCheckBox_100.setChecked(true);
                }
                if(dataSnapshot.hasChild("blind/" + mGame.getpushId())){
                    mCheckBox_Blind.setChecked(true);
                }
                if(dataSnapshot.hasChild("hardcore/" + mGame.getpushId())){
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
                    dbCurrentUser.child("complete").child(mGame.getpushId()).setValue(true);
                    dbCurrentUser.child("remaining").child(mGame.getpushId()).removeValue();
                }else{
                    dbCurrentUser.child("complete").child(mGame.getpushId()).removeValue();
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
                    dbCurrentUser.child("100").child(mGame.getpushId()).setValue(true);
                    dbCurrentUser.child("remaining").child(mGame.getpushId()).removeValue();
                    mCheckBox_Complete.setChecked(true);
                }else{
                    dbCurrentUser.child("100").child(mGame.getpushId()).removeValue();
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
                    dbCurrentUser.child("blind").child(mGame.getpushId()).setValue(true);
                    dbCurrentUser.child("remaining").child(mGame.getpushId()).removeValue();
                    mCheckBox_Complete.setChecked(true);
                }else{
                    dbCurrentUser.child("blind").child(mGame.getpushId()).removeValue();
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
                    dbCurrentUser.child("hardcore").child(mGame.getpushId()).setValue(true);
                    dbCurrentUser.child("remaining").child(mGame.getpushId()).removeValue();
                    mCheckBox_Complete.setChecked(true);
                }else{
                    dbCurrentUser.child("hardcore").child(mGame.getpushId()).removeValue();
                    checkBoxCleanup();
                }

            }
        });

        mTextView_Name.setText(mGame.getName());
        mTextView_Deck.setText(mGame.getDeck());
        Picasso.with(getActivity().getApplicationContext()).load(mGame.getImageURL()).into(mImageView_Splash);

        return view;
    }

    public Boolean checkIfOtherBoxesChecked(){
        if(mCheckBox_Complete.isChecked() || mCheckBox_100.isChecked() || mCheckBox_Blind.isChecked() || mCheckBox_Hardcore.isChecked()){
            return true;
        }else return false;
    }

    public void checkBoxCleanup(){
        if(!mCheckBox_Complete.isChecked() && !mCheckBox_100.isChecked() && !mCheckBox_Blind.isChecked() && !mCheckBox_Hardcore.isChecked()){
            dbCurrentUser.child("remaining").child(mGame.getpushId()).setValue(true);
        }
    }
}
