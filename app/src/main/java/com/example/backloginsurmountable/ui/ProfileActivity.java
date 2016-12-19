package com.example.backloginsurmountable.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.backloginsurmountable.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.graphics.Typeface.createFromAsset;

public class ProfileActivity extends BaseActivity implements View.OnClickListener, GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{
    @Bind(R.id.textView_Username) TextView mTextView_Username;
    @Bind(R.id.textView_NES) TextView mTextView_NES;
    @Bind(R.id.textView_NESvs) TextView mTextView_NESvs;
    @Bind(R.id.button_Scraper) Button mButton_Scraper;

    private GestureDetectorCompat mDetector;

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

        mDetector = new GestureDetectorCompat(this,this);
        // Set the gesture detector as the double tap
        // listener.
        mDetector.setOnDoubleTapListener(this);
    }

    public void onClick(View v){
        Intent intent = new Intent(ProfileActivity.this, ScraperActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
//        Log.d(DEBUG_TAG,"onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
//        Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
//        Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
//        Log.d(DEBUG_TAG, "onScroll: " + e1.toString()+e2.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
//        Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
//        Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        Log.d("onDoubleTap: ", event.toString());

        if(mAuth.getCurrentUser().getEmail().equals("eloavox@gmail.com")){
            Intent intent = new Intent(ProfileActivity.this, ScraperActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        return true;
    }

}
