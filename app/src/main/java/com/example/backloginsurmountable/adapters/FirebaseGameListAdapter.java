package com.example.backloginsurmountable.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import com.example.backloginsurmountable.models.Game;
import com.example.backloginsurmountable.models.GamesDBGame;
import com.example.backloginsurmountable.utils.ItemTouchHelperAdapter;
import com.example.backloginsurmountable.utils.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static android.graphics.Typeface.createFromAsset;

/**
 * Created by Guest on 12/12/16.
 */
public class FirebaseGameListAdapter extends FirebaseRecyclerAdapter<GamesDBGame, FirebaseGameViewHolder> implements ItemTouchHelperAdapter{
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    private Typeface PressStart2P;

    public FirebaseGameListAdapter(Class<GamesDBGame> modelClass, int modelLayout,
                                         Class<FirebaseGameViewHolder> viewHolderClass,
                                         Query ref, OnStartDragListener onStartDragListener, Context context, Typeface _PressStart2P) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
        PressStart2P = _PressStart2P;
    }
    @Override
    protected void populateViewHolder(FirebaseGameViewHolder viewHolder, GamesDBGame model, int position) {
        viewHolder.bindGame(model, PressStart2P);
    }

    @Override
    public void onItemDismiss(int position) {

    }
}
