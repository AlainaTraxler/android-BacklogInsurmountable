package com.example.backloginsurmountable.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.backloginsurmountable.R;
import com.google.firebase.auth.FirebaseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.graphics.Typeface.createFromAsset;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.textView_Backlog) TextView mTextView_Backlog;
    @Bind(R.id.textView_Insurmountable) TextView mTextView_Insurmountable;

    @Bind(R.id.button_Backlog) Button mButton_Backlog;
    @Bind(R.id.button_SignUp) Button mButton_SignUp;
    @Bind(R.id.button_Profile) Button mButton_Profile;

    FirebaseUser mUser = null;

    Boolean mIsLoggedIn;
    String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mIsLoggedIn = intent.getBooleanExtra("isLoggedIn", false);
        mUsername = intent.getStringExtra("username");

        Typeface nintender = createFromAsset(getAssets(), "fonts/Nintender.ttf");
        mTextView_Backlog.setTypeface(nintender);
        mTextView_Insurmountable.setTypeface(nintender);

        mButton_Backlog.setOnClickListener(this);
        mButton_SignUp.setOnClickListener(this);

        mUser = mAuth.getCurrentUser();

        if(mUser != null){
            mButton_Backlog.setVisibility(View.VISIBLE);
            mButton_SignUp.setText("Profile");
        }else {
            mButton_Backlog.setVisibility(View.INVISIBLE);
            mButton_SignUp.setText("Sign Up / Log In");
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#d9d9d9"));
        myToolbar.setBackgroundDrawable(colorDrawable);

        setTitle("");
    }

    @Override
    public void onClick(View v) {
        if(v == mButton_Backlog){
            Intent intent = new Intent(MainActivity.this, BacklogActivity.class);
            startActivity(intent);
        }else if(v == mButton_SignUp && mUser == null){
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
    }

}
