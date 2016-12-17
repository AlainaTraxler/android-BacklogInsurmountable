package com.example.backloginsurmountable.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.backloginsurmountable.R;
import com.example.backloginsurmountable.models.GamesDBGame;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.graphics.Typeface.createFromAsset;

public class ProfileActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.textView_Username) TextView mTextView_Username;
    @Bind(R.id.textView_NES) TextView mTextView_NES;
    @Bind(R.id.textView_NESvs) TextView mTextView_NESvs;
    @Bind(R.id.button_Scraper) Button mButton_Scraper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        final Typeface PressStart2P = createFromAsset(getAssets(), "fonts/PressStart2P.ttf");
        final Typeface nintender = createFromAsset(getAssets(), "fonts/Nintender.ttf");

        dbCurrentUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float completed = dataSnapshot.child("complete").getChildrenCount();
                final float totalNESGames = dataSnapshot.child("remaining").getChildrenCount() + completed;
//                float percentCompleted = (completed / totalNESGames) * 100;
                mTextView_NESvs.setTypeface(PressStart2P);
                mTextView_NESvs.setText(String.format("%.0f", completed) + "/" + String.format("%.0f", totalNESGames));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mTextView_NES.setTypeface(nintender);

        mTextView_Username.setTypeface(PressStart2P);
        mTextView_Username.setText(mAuth.getCurrentUser().getEmail());

        mButton_Scraper.setVisibility(View.INVISIBLE);
        mButton_Scraper.setOnClickListener(this);
    }

    public void onClick(View v){
        Intent intent = new Intent(ProfileActivity.this, ScraperActivity.class);
        startActivity(intent);
    }
}
