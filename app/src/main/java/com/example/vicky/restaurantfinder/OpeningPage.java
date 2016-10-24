package com.example.vicky.restaurantfinder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class OpeningPage extends AppCompatActivity {
    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.openingpage);
        cd = new ConnectionDetector(getApplicationContext());

        final SharedPreferences spobj = getSharedPreferences("myxml1",
                MODE_PRIVATE);
        boolean key = spobj.getBoolean("mykey", false);

        if(key)
        {
            isInternetPresent = cd.isConnectingToInternet();

            // check for Internet status
            if (isInternetPresent) {
                // Internet Connection is Present
                // make HTTP requests
                showAlertDialog(OpeningPage.this, "Internet Connection",
                        "You have internet connection", true);
                Intent in = new Intent(getApplicationContext(),Search.class);
                startActivity(in);
            } else {
                // Internet connection is not present
                // Ask user to connect to Internet
                showAlertDialog(OpeningPage.this, "No Internet Connection",
                        "You don't have internet connection.", false);
                Thread timer = new Thread() {
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            finish();
                        }
                    }
                };

                timer.start();
            }




        }
        else {
            Thread timer = new Thread() {
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Intent start = new Intent(OpeningPage.this.getApplicationContext(), FrontPage.class);
                        startActivity(start);
                    }
                }
            };

            timer.start();
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();

    }
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);



        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

}
