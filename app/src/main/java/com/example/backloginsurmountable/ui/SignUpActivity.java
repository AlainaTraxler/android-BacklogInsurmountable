package com.example.backloginsurmountable.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.backloginsurmountable.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener  {
    @Bind(R.id.button_LetsGo) Button mButton_LetsGo;
    @Bind(R.id.editText_Username) EditText mEditText_Username;
    @Bind(R.id.editText_Password) EditText mEditText_Password;
    @Bind(R.id.checkBox_Returning) CheckBox mCheckBox_Returning;

    String mUsername;
    String mPassword;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final String TAG = "SignUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);


// ...
        mAuth = FirebaseAuth.getInstance();

        mButton_LetsGo.setOnClickListener(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        // ...
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            intent.putExtra("isLoggedIn", true);
                            intent.putExtra("username", mUsername);
                            startActivity(intent);
                        }

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("", "signInWithEmail:failed", task.getException());
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    @Override
    public void onClick(View v) {
        mUsername = mEditText_Username.getText().toString();
        mPassword = mEditText_Username.getText().toString();

        if(!(mUsername.equals("")) && !(mPassword.equals(""))){

            if(mCheckBox_Returning.isChecked()) {
                signIn(mUsername, mPassword);
//                mAuth.signInWithEmailAndPassword(mUsername, mPassword)
//                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                Log.d("", "signInWithEmail:onComplete:" + task.isSuccessful());
//
//                                if (task.isSuccessful()) {
//                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
//                                    intent.putExtra("isLoggedIn", true);
//                                    intent.putExtra("username", mUsername);
//                                    startActivity(intent);
//                                }
//
//                                // If sign in fails, display a message to the user. If sign in succeeds
//                                // the auth state listener will be notified and logic to handle the
//                                // signed in user can be handled in the listener.
//                                if (!task.isSuccessful()) {
//                                    Log.w("", "signInWithEmail:failed", task.getException());
//                                }
//
//                                // ...
//                            }
//                        });
            }else{
                mAuth.createUserWithEmailAndPassword(mUsername, mPassword)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("", "createUserWithEmail:onComplete:" + task.isSuccessful());

                                if (task.isSuccessful()) {
                                    Log.v("", "Yes");
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    intent.putExtra("isLoggedIn", true);
                                    intent.putExtra("username", mUsername);
                                    startActivity(intent);
                                }

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Log.v("", "No");
                                }

                                // ...
                            }
                        });
            }



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
