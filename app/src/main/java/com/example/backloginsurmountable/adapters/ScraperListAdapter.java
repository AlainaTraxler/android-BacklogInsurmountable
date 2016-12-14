package com.example.backloginsurmountable.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.backloginsurmountable.R;
import com.example.backloginsurmountable.models.ScraperListItem;
import com.example.backloginsurmountable.ui.ScraperDetailActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 12/14/16.
 */
public class ScraperListAdapter extends RecyclerView.Adapter<ScraperListAdapter.ScraperViewHolder> {
    private ArrayList<ScraperListItem> mScrapedGames = new ArrayList<>();
    private Context mContext;

    public ScraperListAdapter(Context context, ArrayList<ScraperListItem> scrapedGames) {
        mContext = context;
        mScrapedGames = scrapedGames;
    }

    @Override
    public ScraperListAdapter.ScraperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scraper_list_item, parent, false);
        ScraperViewHolder viewHolder = new ScraperViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ScraperListAdapter.ScraperViewHolder holder, int position) {
        holder.bindScraperListItem(mScrapedGames.get(position));
    }

    @Override
    public int getItemCount() {
        return mScrapedGames.size();
    }

    public class ScraperViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @Bind(R.id.textView_Name) TextView mTextView_Name;
        @Bind(R.id.textView_Index) TextView mTextView_Index;

        private Context mContext;

        public ScraperViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, ScraperDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("scrapedGames", Parcels.wrap(mScrapedGames));
            mContext.startActivity(intent);
        }

        public void bindScraperListItem(ScraperListItem scraperListItem) {
            mTextView_Name.setText(scraperListItem.getName());
            mTextView_Index.setText(String.valueOf(scraperListItem.getIndex()));
        }
    }
}
