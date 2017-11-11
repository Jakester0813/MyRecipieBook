package com.eleven.group.myrecipiebook.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.eleven.group.myrecipiebook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.eleven.group.myrecipiebook.R.id.radioGroup;

public class SignUp extends AppCompatActivity {

    EditText fullName;
    EditText email;
    EditText password;
    Button signUp;
    private FirebaseAuth mAuth;

    private static String PASSWORD_EMAIL_INVALID = "Your email is invalid and password is less than 6 characters";
    private static String EMAIL_INVALID = "Email address is invalid";
    private static String PASSWORD_INVALID = "Password must be at least 6 characters";
    private static String SIGN_UP_SUCCESS = "Sign up Successful";
    private static String SIGN_UP_FAILED = "Sign up FAILED";
    private static String EMAIL_CHARACTER = "@";

    /*
     * Created By: Jake Rushing
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        setupView();
    }

    public void setupView(){
        fullName = (EditText) findViewById(R.id.etFullName);
        email = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etPassword);
        signUp = (Button) findViewById(R.id.button5);

        fullName.setText(fullName.getText().toString());
        email.setText(email.getText().toString());
        password.setText(password.getText().toString());
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmailValid(email.getText().toString()) && isPasswordValid(password.getText().toString())) {
                    attemptSignUp();
                }
                if(isEmailValid(email.getText().toString()) && isPasswordValid(password.getText().toString())){
                    Toast.makeText(SignUp.this,PASSWORD_EMAIL_INVALID,
                            Toast.LENGTH_SHORT).show();
                }
                else if(!isEmailValid(email.getText().toString())){
                    Toast.makeText(SignUp.this,EMAIL_INVALID, Toast.LENGTH_SHORT).show();
                }
                else if(isPasswordValid(password.getText().toString())){
                    Toast.makeText(SignUp.this,PASSWORD_INVALID, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isEmailValid(String email){
        return email.contains(EMAIL_CHARACTER);
    }

    public boolean isPasswordValid(String password){
        return password.length() >= 6;
    }

    public void attemptSignUp(){
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUp.this, SIGN_UP_SUCCESS, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignUp.this, SignInActivity.class);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUp.this, SIGN_UP_FAILED, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
