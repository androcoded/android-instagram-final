package com.example.instagramfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtSignUpUsername,edtSignUpEmail,edtSignUpPassword;
    private Button btnSingUp;
    public ParseUser newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtSignUpUsername = findViewById(R.id.edtSignUpUserName);
        edtSignUpEmail = findViewById(R.id.edtSignUpEmail);
        edtSignUpPassword = findViewById(R.id.edtSignUpPassword);
        btnSingUp = findViewById(R.id.btnSignUp);
        newUser = new ParseUser();
        btnSingUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        newUser.setUsername(edtSignUpUsername.getText().toString());
        newUser.setEmail(edtSignUpEmail.getText().toString());
        newUser.setPassword(edtSignUpPassword.getText().toString());
        newUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){

                    FancyToast.makeText(getApplicationContext(),newUser.getUsername()+" successfully signed up!"
                            , Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();

                }else {

                    FancyToast.makeText(getApplicationContext(),"User not signed up!"
                            , Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                }
            }
        });
    }
}
