package com.example.backloginsurmountable.adapters;

import android.content.Context;

import com.example.backloginsurmountable.models.Game;
import com.example.backloginsurmountable.utils.ItemTouchHelperAdapter;
import com.example.backloginsurmountable.utils.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Guest on 12/12/16.
 */
public class FirebaseGameListAdapter extends FirebaseRecyclerAdapter<Game, FirebaseGameViewHolder> implements ItemTouchHelperAdapter{
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    public FirebaseGameListAdapter(Class<Game> modelClass, int modelLayout,
                                         Class<FirebaseGameViewHolder> viewHolderClass,
                                         Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
    }
    @Override
    protected void populateViewHolder(FirebaseGameViewHolder viewHolder, Game model, int position) {
        viewHolder.bindGame(model);
    }

//    @Override
//    public boolean onItemMove(int fromPosition, int toPosition) {
//        return false;
//    }

    @Override
    public void onItemDismiss(int position) {

    }
}
