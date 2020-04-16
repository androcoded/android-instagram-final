package com.example.instagramfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLoginUsername,edtLoginEmail,edtLoginPassword;
    private Button btnLogin,btnSignUpActivity;
    private Intent signUpActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtLoginUsername = findViewById(R.id.edtUserName);
        edtLoginEmail = findViewById(R.id.edtEmail);
        edtLoginPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUpActivity = findViewById(R.id.btnSignUpActivity);
        btnSignUpActivity.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        signUpActivityIntent = new Intent(getApplicationContext(),SignUpActivity.class);
        startActivity(signUpActivityIntent);
    }
}
