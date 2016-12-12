package com.example.backloginsurmountable.adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.backloginsurmountable.R;
import com.example.backloginsurmountable.models.Game;
import com.example.backloginsurmountable.ui.GameDetailActivity;
import com.example.backloginsurmountable.utils.ItemTouchHelperViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

import static android.graphics.Typeface.createFromAsset;

/**
 * Created by Guest on 12/9/16.
 */
public class FirebaseGameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ItemTouchHelperViewHolder {
    View mView;
    Context mContext;
    Game gameHolder;

    public FirebaseGameViewHolder(View itemView) {
        super(itemView);

        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindGame(Game game, Typeface PressStart2P) {
        TextView TextView_Name = (TextView) mView.findViewById(R.id.textView_Name);
        TextView_Name.setText(game.getName());
        TextView_Name.setTypeface(PressStart2P);
        gameHolder = game;
    }
    @Override
    public void onClick(View view) {
        final ArrayList<Game> games = new ArrayList<>();
        final ArrayList<String> indexArray = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("games");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    games.add(snapshot.getValue(Game.class));
                    indexArray.add(snapshot.getKey());
                }

                int itemPosition = indexArray.indexOf(gameHolder.getpushId());
                Intent intent = new Intent(mContext, GameDetailActivity.class);

                intent.putExtra("position", itemPosition);
                intent.putExtra("games", Parcels.wrap(games));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public Game getGameHolder(){
        return gameHolder;
    }

    @Override
    public void onItemSelected() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,
                R.animator.drag_scale_on);
        set.setTarget(itemView);
        set.start();
    }

    @Override
    public void onItemClear() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,
                R.animator.drag_scale_off);
        set.setTarget(itemView);
        set.start();
    }

}
