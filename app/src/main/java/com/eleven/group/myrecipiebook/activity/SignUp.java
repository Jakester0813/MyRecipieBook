package com.eleven.group.myrecipiebook.activity;

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

import static com.eleven.group.myrecipiebook.R.id.macros;
import static com.eleven.group.myrecipiebook.R.id.radioGroup;

public class SignUp extends AppCompatActivity {

    EditText fullName;
    EditText email;
    EditText password;
    Button signUp;
    private FirebaseAuth mAuth;


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
                if(!email.getText().toString().contains("@") && password.length() < 6){
                    Toast.makeText(SignUp.this,"Your email is invalid and password is less than 6 characters", Toast.LENGTH_SHORT).show();
                }
                else if(!email.getText().toString().contains("@")){
                    Toast.makeText(SignUp.this,"Email address is invalid", Toast.LENGTH_SHORT).show();
                }
                else if(password.length() < 6){
                    Toast.makeText(SignUp.this,"Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isEmailValid(String email){
        return email.contains("@");
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
                            Toast.makeText(SignUp.this, "Sign up Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUp.this, "Sign up FAILED", Toast.LENGTH_SHORT).show();
                            Log.d("Fail", task.getException().toString());

                        }

                        // ...
                    }
                });

    }
}
