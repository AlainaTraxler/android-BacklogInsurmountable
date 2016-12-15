package com.example.backloginsurmountable.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.backloginsurmountable.R;
import com.example.backloginsurmountable.models.GamesDBGamelet;
import com.example.backloginsurmountable.ui.ScraperDetailActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 12/14/16.
 */
public class GameletListAdapter extends RecyclerView.Adapter<GameletListAdapter.GameletViewHolder>{
    private ArrayList<GamesDBGamelet> mGamelets = new ArrayList<>();
    private Context mContext;

    public GameletListAdapter(Context context, ArrayList<GamesDBGamelet> scrapedGames) {
        mContext = context;
        mGamelets = scrapedGames;
    }

    @Override
    public GameletListAdapter.GameletViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gamelet_list_item, parent, false);
        GameletViewHolder viewHolder = new GameletViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GameletListAdapter.GameletViewHolder holder, int position) {
        holder.bindGamesDBGamelet(mGamelets.get(position));
    }

    @Override
    public int getItemCount() {
        return mGamelets.size();
    }

    public class GameletViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @Bind(R.id.textView_GameTitle) TextView mTextView_GameTitle;
        @Bind(R.id.textView_Id) TextView mTextView_Id;
        @Bind(R.id.textView_Platform) TextView mTextView_Platform;

        private Context mContext;

        public GameletViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            //add to database
        }

        public void bindGamesDBGamelet(GamesDBGamelet gameletListItem) {
            mTextView_GameTitle.setText(gameletListItem.getGameTitle());
            mTextView_Id.setText(String.valueOf(gameletListItem.getGamesDBId()));
            mTextView_Platform.setText(String.valueOf(gameletListItem.getPlatform()));
        }
    }
}
