package com.example.backloginsurmountable.ui;


import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.backloginsurmountable.Constants;
import com.example.backloginsurmountable.GiantBombService.GiantBombService;
import com.example.backloginsurmountable.R;
import com.example.backloginsurmountable.models.Game;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameDetailFragment extends Fragment {
    @Bind(R.id.textView_Deck) TextView mTextView_Deck;
    @Bind(R.id.imageView_Splash) ImageView mImageView_Splash;

//    private TextView mTextView_Deck;

    private Game mGame;
    private String mReserveName;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_detail, container, false);
        ButterKnife.bind(this, view);

        mTextView_Deck.setText(mGame.getDeck());
        Picasso.with(getActivity().getApplicationContext()).load(mGame.getImageURL()).into(mImageView_Splash);

        Log.v("onCreateView ImageURL:", mGame.getImageURL());

        return view;
    }
}
