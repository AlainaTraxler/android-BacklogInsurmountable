package com.example.backloginsurmountable.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.backloginsurmountable.Constants;
import com.example.backloginsurmountable.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.graphics.Typeface.*;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.textView_Backlog) TextView mTextView_Backlog;
    @Bind(R.id.textView_Insurmountable) TextView mTextView_Insurmountable;
    @Bind(R.id.textView_Info) TextView mTextView_Info;

    @Bind(R.id.button_Backlog) Button mButton_Backlog;
    @Bind(R.id.button_Gauntlet) Button mButton_Gauntlet;
    @Bind(R.id.button_SignUp) Button mButton_SignUp;
    @Bind(R.id.button_Profile) Button mButton_Profile;

    Boolean mIsLoggedIn;
    String mUsername;

//    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

//    private DatabaseReference mSearchedLocationReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        mSearchedLocationReference = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_GAMELISTS_NODE).child(Constants.FIREBASE_NES_NODE);

//        mSearchedLocationReference.setValue("Success!!");

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
        mButton_Gauntlet.setOnClickListener(this);
        mButton_SignUp.setOnClickListener(this);
        mTextView_Info.setOnClickListener(this);

        if(mAuth.getCurrentUser() != null){
            mButton_Backlog.setVisibility(View.VISIBLE);
            mButton_Gauntlet.setVisibility(View.VISIBLE);
            mButton_SignUp.setText("Profile");
        }else {
            mButton_Backlog.setVisibility(View.INVISIBLE);
            mButton_Gauntlet.setVisibility(View.INVISIBLE);
            mButton_SignUp.setText("Sign Up / Log In");
        }
//
        setTitle("");
//        Log.v(">>>>>", mAuth.getCurrentUser().getEmail());

    }

    @Override
    public void onClick(View v) {
        if(v == mButton_Backlog){
            Intent intent = new Intent(MainActivity.this, BacklogActivity.class);
            startActivity(intent);
        }else if(v == mButton_Gauntlet){
            Intent intent = new Intent(MainActivity.this, GauntletActivity.class);
            startActivity(intent);
        }else if(v == mButton_SignUp && !(mIsLoggedIn)){
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        }else if(v == mTextView_Info){
            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.putExtra("username", mUsername);
            startActivity(intent);
        }
    }

}
