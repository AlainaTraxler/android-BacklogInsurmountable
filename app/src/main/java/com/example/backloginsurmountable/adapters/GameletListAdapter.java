package com.example.backloginsurmountable.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.backloginsurmountable.R;
import com.example.backloginsurmountable.models.GamesDBGame;
import com.example.backloginsurmountable.models.GamesDBGamelet;
import com.example.backloginsurmountable.services.GamesDBService;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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

        private String mId;
        private int mCounter;

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

            final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

            final GamesDBService apiServcice = new GamesDBService();
            apiServcice.findGameById(mId, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("GamesDBService: ", "Failed!");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    GamesDBGame game = apiServcice.processResultById(response, mCounter);
                    DatabaseReference pushRef =  dbRef.child("ngames").push();
                    game.setPushId(pushRef.getKey());
                    pushRef.setValue(game);

                }
            });

        }

        public void bindGamesDBGamelet(GamesDBGamelet gameletListItem) {
            mId = gameletListItem.getGamesDBId();
            mCounter = gameletListItem.getCounter();

            mTextView_GameTitle.setText(gameletListItem.getGameTitle());
            mTextView_Id.setText(String.valueOf(gameletListItem.getGamesDBId()));
            mTextView_Platform.setText(String.valueOf(gameletListItem.getPlatform()));
        }
    }
}
