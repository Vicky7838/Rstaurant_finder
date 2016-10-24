package com.example.vicky.restaurantfinder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sign extends AppCompatActivity {
    private EditText editTextMobilenumber;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextEmail;
    AlertDialog.Builder builder;
    private Button buttonRegister, buttonRegister1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextUsername = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextMobilenumber = (EditText) findViewById(R.id.mobilenumber);

        buttonRegister = (Button) findViewById(R.id.btnRegister);
        buttonRegister1 = (Button) findViewById(R.id.btnLinkToLoginScreen);
        buttonRegister1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Sign.this, Login.class);
                startActivity(in);
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextEmail.getText().toString().equals("") || editTextUsername.getText().toString().equals("") || editTextPassword.getText().toString().equals("") || editTextMobilenumber.getText().toString().equals("")) {
                    builder = new AlertDialog.Builder(Sign.this);
                    builder.setTitle("something wrong");
                    builder.setMessage("fill all the fields");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    ;
                } else {

                    final String email = editTextEmail.getText().toString();
                    final String pass = editTextPassword.getText().toString();
                    final String mob = editTextMobilenumber.getText().toString();
                    if (!isValidEmail(email)) {
                        editTextEmail.setError("Invalid Email");
                    } else {

                        if (!isValidPassword(pass)) {
                            editTextPassword.setError("Invalid Password");
                        } else {
                            if (!isValidMobile(mob)) {
                                editTextMobilenumber.setError("Invalid Number");
                            } else {


                                BackgroundTask backgroundTask = new BackgroundTask(Sign.this);
                                backgroundTask.execute("register", editTextEmail.getText().toString(), editTextUsername.getText().toString(), editTextPassword.getText().toString(), editTextMobilenumber.getText().toString());

                            }

                        }
                    }
                }
            }

        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }

    private boolean isValidMobile(String mob) {
        if (mob != null && mob.length() == 10) {
            return true;
        }
        return false;
    }
}
