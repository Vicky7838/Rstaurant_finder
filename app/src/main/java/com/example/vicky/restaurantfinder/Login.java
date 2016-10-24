package com.example.vicky.restaurantfinder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    private EditText editTextMobilenumber;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextEmail;
    AlertDialog.Builder builder;
    private Button buttonLogin,buttonRLogin1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editTextEmail = (EditText) findViewById(R.id.email);

        editTextPassword = (EditText) findViewById(R.id.password);


        buttonLogin = (Button) findViewById(R.id.btnLogin);
        buttonRLogin1 = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        final SharedPreferences spobj1 = getSharedPreferences("myxml1",
                MODE_PRIVATE);
        boolean key1 = spobj1.getBoolean("mykey", false);

        if(key1)
        {

            Intent act1 = new Intent(Login.this,Restaurant.class);
            startActivity(act1);
            finish();

        }
        buttonRLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Login.this,Sign.class);
                startActivity(in);
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editObj = spobj1.edit();
                String n= editTextEmail.getText().toString();
                String c= editTextPassword.getText().toString();
                editObj.putBoolean("mykey", true);
                editObj.putString("mykeyname", n);
                editObj.putString("mykeyclass", c);
                editObj.commit();


                if (editTextEmail.getText().toString().equals("") || editTextPassword.getText().toString().equals("")) {
                    builder = new AlertDialog.Builder(Login.this);
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

                } else {
                    BackgroundTask backgroundTask = new BackgroundTask(Login.this);
                    backgroundTask.execute("login", editTextEmail.getText().toString(), editTextPassword.getText().toString());

                }
            }
        });





        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
