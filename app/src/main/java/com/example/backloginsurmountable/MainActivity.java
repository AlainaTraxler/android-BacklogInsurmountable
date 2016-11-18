package com.example.backloginsurmountable;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.graphics.Typeface.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.textView_Backlog) TextView mTextView_Backlog;
    @Bind(R.id.textView_Insurmountable) TextView mTextView_Insurmountable;

    @Bind(R.id.button_Backlog) Button mButton_Backlog;
    @Bind(R.id.button_Gauntlet) Button mButton_Gauntlet;
    @Bind(R.id.button_Profile) Button mButton_Profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Typeface nintender = createFromAsset(getAssets(), "fonts/Nintender.ttf");
        mTextView_Backlog.setTypeface(nintender);
        mTextView_Insurmountable.setTypeface(nintender);

        mButton_Backlog.setOnClickListener(this);
        mButton_Gauntlet.setOnClickListener(this);
        mButton_Profile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mButton_Backlog){
            Intent intent = new Intent(MainActivity.this, BacklogActivity.class);
            startActivity(intent);
        }
    }

}
