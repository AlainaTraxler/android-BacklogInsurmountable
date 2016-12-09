package com.example.backloginsurmountable.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.backloginsurmountable.R;
import com.example.backloginsurmountable.models.Game;
import com.example.backloginsurmountable.ui.GameDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by Guest on 12/9/16.
 */
public class FirebaseGameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    View mView;
    Context mContext;

    public FirebaseGameViewHolder(View itemView) {
        super(itemView);

        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindGame(Game game) {
        TextView TextView_Name = (TextView) mView.findViewById(R.id.textView_Name);
        TextView_Name.setText(game.getName());
    }
    @Override
    public void onClick(View view) {
        final ArrayList<Game> games = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("games");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    games.add(snapshot.getValue(Game.class));
                }

                int itemPosition = getLayoutPosition();
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
}
