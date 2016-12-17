package com.example.backloginsurmountable.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.backloginsurmountable.Constants;
import com.example.backloginsurmountable.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignUpActivity extends BaseActivity implements View.OnClickListener  {
    @Bind(R.id.button_SignUp) Button mButton_SignUp;
    @Bind(R.id.button_SignIn) Button mButton_SignIn;
    @Bind(R.id.editText_Username) EditText mEditText_Username;
    @Bind(R.id.editText_Password) EditText mEditText_Password;
    @Bind(R.id.checkBox_Remember) CheckBox mCheckBox_Remember;

    String mUsername;
    String mPassword;

    private static final String TAG = "SignUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        mButton_SignIn.setOnClickListener(this);
        mButton_SignUp.setOnClickListener(this);
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful() && mAuth.getCurrentUser() != null) {
                            Toast.makeText(SignUpActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                            if(mCheckBox_Remember.isChecked()){
                                mEditor.putString("Remember", "true").apply();
                            }
                        }else{
                            Toast.makeText(SignUpActivity.this, "Sign in unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    private void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Account creation successful", Toast.LENGTH_SHORT).show();
                            if(mCheckBox_Remember.isChecked()){
                                mEditor.putString("Remember", "true").apply();
                            }
                            final DatabaseReference newUserRef = dbUsers.child(mAuth.getCurrentUser().getUid());
                            newUserRef.setValue(true);
                            FirebaseDatabase.getInstance().getReference(Constants.DB_GAMELISTS_NODE).addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    newUserRef.child("remaining").child(dataSnapshot.getKey()).setValue(dataSnapshot.getValue());
                                }

                                @Override
                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onChildRemoved(DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Account creation unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        String email = mEditText_Username.getText().toString();
        String password = mEditText_Password.getText().toString();

        if(!(email.equals("")) && !(password.equals(""))){

            if(v == mButton_SignIn) {
                signIn(email, password);
            }else{
                signUp(email, password);
            }



        }else{
            if(email.equals("")){
                Toast.makeText(SignUpActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            }

            if(password.equals("")){
                Toast.makeText(SignUpActivity.this, "Please enter a password longer than 6 characters", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
