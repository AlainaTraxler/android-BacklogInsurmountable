package com.example.backloginsurmountable.ui;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.backloginsurmountable.Constants;
import com.example.backloginsurmountable.GiantBombService.GiantBombService;
import com.example.backloginsurmountable.R;
import com.example.backloginsurmountable.models.Game;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
//    @Bind(R.id.textView_Name) TextView mTextView_Name;
//    @Bind(R.id.textView_Genre) TextView mTextView_Genre;
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


        mReserveName = mGame.getName();
        mGame = getGame(mGame.getName());

    }

    private Game getGame(String name) {
        final GiantBombService giantBombService = new GiantBombService();
        giantBombService.findGameByName(name, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                new getGameTask().execute(response);
            }

        });

        return mGame;
    }

    private class getGameTask extends AsyncTask<Response, Void, Game> {
        /** The system calls this to perform work in a worker thread and
         * delivers it the parameters given to AsyncTask.execute() */
        protected Game doInBackground(Response... response) {
            final GiantBombService giantBombService = new GiantBombService(); //pass in activity?
            return giantBombService.processResultByName(response[0]);
        }


        /** The system calls this to perform work in the UI thread and delivers
         * the result from doInBackground() */
        protected void onPostExecute(Game game) {
            mTextView_Deck.setText(game.getDeck());
            Picasso.with(getActivity().getApplicationContext()).load(game.getImageURL()).into(mImageView_Splash);

            DatabaseReference gameNode;

            if(game.getName().equals("Not Found")){
                Random rand = new Random();
                int  rnd = rand.nextInt(100000);

                gameNode = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_GAMELISTS_NODE).child(Constants.FIREBASE_NES_NODE).child(game.getId() + String.valueOf(rnd)).child("name");
                gameNode.setValue(mReserveName);
            }else{
                gameNode = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_GAMELISTS_NODE).child(Constants.FIREBASE_NES_NODE).child(game.getId()).child("name");
                gameNode.setValue(game.getName());
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_detail, container, false);
        ButterKnife.bind(this, view);

        mTextView_Deck.setText(mGame.getDeck());

        Log.v("onCreateView ImageURL:", mGame.getImageURL());

        return view;
    }

}
