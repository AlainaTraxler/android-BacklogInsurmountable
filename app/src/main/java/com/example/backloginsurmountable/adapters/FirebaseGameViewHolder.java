package com.example.backloginsurmountable.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.backloginsurmountable.Constants;
import com.example.backloginsurmountable.R;
import com.example.backloginsurmountable.models.GamesDBGame;
import com.example.backloginsurmountable.ui.GameDetailActivity;
import com.example.backloginsurmountable.utils.ItemTouchHelperViewHolder;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by Guest on 12/9/16.
 */
public class FirebaseGameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ItemTouchHelperViewHolder {
    View mView;
    Context mContext;
    GamesDBGame gameHolder;

    public FirebaseGameViewHolder(View itemView) {
        super(itemView);

        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindGame(GamesDBGame game, Typeface PressStart2P) {
        TextView TextView_Name = (TextView) mView.findViewById(R.id.textView_Name);
        TextView_Name.setText(game.getGameTitle());
        TextView_Name.setTypeface(PressStart2P);
        gameHolder = game;
    }
    @Override
    public void onClick(View view) {
        final ArrayList<GamesDBGame> games = new ArrayList<>();
        final ArrayList<String> indexArray = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.DB_GAMES_NODE);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                GamesDBGame game = dataSnapshot.getValue(GamesDBGame.class);
                games.add(game);
                indexArray.add(dataSnapshot.getKey());
                int itemPosition = indexArray.indexOf(gameHolder.getPushId());

                Intent intent = new Intent(mContext, GameDetailActivity.class);
                intent.putExtra("position", itemPosition);
                intent.putExtra("games", Parcels.wrap(games));



                mContext.startActivity(intent);
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
    }

    public GamesDBGame getGameHolder(){
        return gameHolder;
    }

    @Override
    public void onItemSelected() {
    }

    @Override
    public void onItemClear() {

    }

}
