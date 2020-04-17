package com.example.instagramfinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtSignUpUsername, edtSignUpEmail, edtSignUpPassword;
    private Button btnSingUp;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtSignUpUsername = findViewById(R.id.edtSignUpUserName);
        edtSignUpEmail = findViewById(R.id.edtSignUpEmail);
        edtSignUpPassword = findViewById(R.id.edtSignUpPassword);
        btnSingUp = findViewById(R.id.btnSignUp);
        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtSignUpUsername.getText().toString().equals("") || edtSignUpEmail.getText().toString().equals("") || edtSignUpPassword.getText().toString().equals("")) {
                    FancyToast.makeText(getApplicationContext(), "All fields are required!"
                            , Toast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                } else {

                    setSignUpUser();
                }
            }
        });
    }


    //Sign Up the new user in parse  using three editTxt
    private void setSignUpUser() {

        try {
            final ParseUser newUser = new ParseUser();
            newUser.setUsername(edtSignUpUsername.getText().toString());
            newUser.setEmail(edtSignUpEmail.getText().toString());
            newUser.setPassword(edtSignUpPassword.getText().toString());

            newUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        alertDisplayer("Sucessful Sign Up!","Welcome" + edtSignUpUsername.getText().toString() + "!");
                        FancyToast.makeText(getApplicationContext(), newUser.getUsername() + " successfully signed up!"
                                , Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    } else {
                        ParseUser.logOut();
                        FancyToast.makeText(getApplicationContext(), e.getMessage()
                                , Toast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Displaying alert dialogue after successfull signup of an user
    private void alertDisplayer(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this
        )
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // don't forget to change the line below with the names of your Activities
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}
