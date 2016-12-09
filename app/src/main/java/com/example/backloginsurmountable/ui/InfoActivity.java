package com.example.backloginsurmountable.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.backloginsurmountable.R;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.graphics.Typeface.createFromAsset;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.textView1) TextView mTextView1;
    @Bind(R.id.textView2) TextView mTextView2;
    @Bind(R.id.textView3) TextView mTextView3;
    @Bind(R.id.textView4) TextView mTextView4;
    @Bind(R.id.textView5) TextView mTextView5;
    @Bind(R.id.textView6) TextView mTextView6;
    @Bind(R.id.textView7) TextView mTextView7;
    @Bind(R.id.textView8) TextView mTextView8;
    @Bind(R.id.textView9) TextView mTextView9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);

        Typeface PressStart2P = createFromAsset(getAssets(), "fonts/PressStart2P.ttf");
        mTextView1.setTypeface(PressStart2P);
        mTextView2.setTypeface(PressStart2P);
        mTextView3.setTypeface(PressStart2P);
        mTextView4.setTypeface(PressStart2P);
        mTextView5.setTypeface(PressStart2P);
        mTextView6.setTypeface(PressStart2P);
        mTextView7.setTypeface(PressStart2P);
        mTextView8.setTypeface(PressStart2P);
        mTextView9.setTypeface(PressStart2P);

        mTextView5.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == mTextView5) {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:eloavox@gmail.com"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "From App:");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "");
            startActivity(emailIntent);
        }
    }
}
