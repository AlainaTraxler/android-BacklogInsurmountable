package com.example.backloginsurmountable.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.backloginsurmountable.R;
import com.example.backloginsurmountable.models.Game;
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

        mTextView_Name.setText(mGame.getName());
        mTextView_Deck.setText(mGame.getDeck());
        Picasso.with(getActivity().getApplicationContext()).load(mGame.getImageURL()).into(mImageView_Splash);

        return view;
    }
}
