package com.eleven.group.myrecipiebook.activity;

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

import static com.eleven.group.myrecipiebook.R.id.macros;
import static com.eleven.group.myrecipiebook.R.id.radioGroup;

public class SignUp extends AppCompatActivity {

    EditText fullName;
    EditText email;
    EditText password;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setupView();
    }

    public void setupView(){
        fullName = (EditText) findViewById(R.id.etFullName);
        email = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etPassword);

        fullName.setText(fullName.getText().toString());
        email.setText(email.getText().toString());
        password.setText(password.getText().toString());

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUp.this, "Sign up Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
