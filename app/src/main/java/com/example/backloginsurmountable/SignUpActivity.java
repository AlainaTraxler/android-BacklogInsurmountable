package com.example.backloginsurmountable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener  {
    @Bind(R.id.button_LetsGo) Button mButton_LetsGo;
    @Bind(R.id.editText_Username) EditText mEditText_Username;
    @Bind(R.id.editText_Password) EditText mEditText_Password;

    String mUsername;
    String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        mButton_LetsGo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mUsername = mEditText_Username.getText().toString();
        mPassword = mEditText_Username.getText().toString();

        if(!(mUsername.equals("")) && !(mPassword.equals(""))){
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            intent.putExtra("isLoggedIn", true);
            intent.putExtra("username", mUsername);
            startActivity(intent);
        }else{
            if(mUsername.equals("")){
                Toast.makeText(SignUpActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
            }

            if(mPassword.equals("")){
                Toast.makeText(SignUpActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
