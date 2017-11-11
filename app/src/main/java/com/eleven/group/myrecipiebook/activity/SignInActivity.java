package com.eleven.group.myrecipiebook.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.eleven.group.myrecipiebook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class SignInActivity extends AppCompatActivity{

    /**
     * Id to identity READ_CONTACTS permission request.
     */

    /*
     * Created By: Jake Rushing
     */

    private FirebaseAuth mAuth;

    private static final int REQUEST_READ_CONTACTS = 0;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private TextView mCreateAccount;
    private ProgressBar mProgress;
    private Button mEmailSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        // Set up the login form.
        mAuth = FirebaseAuth.getInstance();
        mEmailView = (EditText) findViewById(R.id.email);
        mProgress = (ProgressBar) findViewById(R.id.pb_sign_in);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    if(isEmailValid(mEmailView.getText().toString()) && isPasswordValid(mPasswordView.getText().toString())) {
                        attemptLogin();
                    }
                    return true;
                }
                return false;
            }
        });


        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgress.setVisibility(View.VISIBLE);
                mEmailSignInButton.setVisibility(View.INVISIBLE);
                if(isEmailValid(mEmailView.getText().toString()) &&
                        isPasswordValid(mPasswordView.getText().toString())) {
                    attemptLogin();
                }
                if(!isEmailValid(mEmailView.getText().toString())
                        && isPasswordValid(mPasswordView.getText().toString())){
                    Toast.makeText(SignInActivity.this,"Your email is invalid and password is less than 6 characters",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!isEmailValid(mEmailView.getText().toString())){
                    Toast.makeText(SignInActivity.this,"Email address is invalid", Toast.LENGTH_SHORT).show();
                }
                else if(!isPasswordValid(mPasswordView.getText().toString())){
                    Toast.makeText(SignInActivity.this,"Password must be at least 6 characters",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCreateAccount = (TextView) findViewById(R.id.createAccount);
        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignInActivity.this, SignUp.class);
                startActivity(i);
            }
        });
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        mAuth.signInWithEmailAndPassword(mEmailView.getText().toString(), mPasswordView.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        mProgress.setVisibility(View.INVISIBLE);
                        mEmailSignInButton.setVisibility(View.VISIBLE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Success", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Toast.makeText(SignInActivity.this, "Sign in Successful", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Failure", "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed. " +
                                            "CHeck your credentials and try again", Toast.LENGTH_SHORT).show();
                            Toast.makeText(SignInActivity.this, "Sign up FAILED", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser user){
        if(user != null){
            mEmailView.setText(user.getEmail());
        }
    }

    public boolean isEmailValid(String email){
        return email.contains("@");
    }

    public boolean isPasswordValid(String password){
        return password.length() >= 6;
    }

}

