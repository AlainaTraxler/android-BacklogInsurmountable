package com.example.backloginsurmountable;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.graphics.Typeface.*;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.textView_Backlog) TextView mTextView_Backlog;
    @Bind(R.id.textView_Insurmountable) TextView mTextView_Insurmountable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Typeface nintender = createFromAsset(getAssets(), "fonts/Nintender.ttf");
        mTextView_Backlog.setTypeface(nintender);
        mTextView_Insurmountable.setTypeface(nintender);
    }
}
