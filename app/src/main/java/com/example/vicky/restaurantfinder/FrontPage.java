package com.example.vicky.restaurantfinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class FrontPage extends AppCompatActivity {

    private ViewFlipper mViewFlipper,mViewFlipper1;
    private GestureDetector mGestureDetector;
    private TextView textView1,textView2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontpage);


                // Get the ViewFlipper
                mViewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
                mViewFlipper1 = (ViewFlipper) findViewById(R.id.viewFlipper1);
        textView1 = (TextView) findViewById(R.id.textView);
        textView1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent login = new Intent(FrontPage.this,Login.class);
                startActivity(login);
            }
        });

        textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent sign = new Intent(FrontPage.this,Sign.class);
                startActivity(sign);
            }
        });

                 //Add all the images to the ViewFlipper
// Set in/out flipping animations
                mViewFlipper.setInAnimation(this, android.R.anim.fade_in);
        mViewFlipper.setOutAnimation(this, android.R.anim.fade_out);

        mViewFlipper1.setInAnimation(this, android.R.anim.fade_in);
        mViewFlipper1.setOutAnimation(this, android.R.anim.fade_out);

               CustomGestureDetector customGestureDetector = new CustomGestureDetector();
                mGestureDetector = new GestureDetector(this, customGestureDetector);
               mViewFlipper.setAutoStart(true);
               mViewFlipper.setFlipInterval(2000);
        mViewFlipper1.setAutoStart(true);
        mViewFlipper1.setFlipInterval(2000);// flip every 2 seconds (2000ms)
            }

            class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                    // Swipe left (next)
                    if (e1.getX() > e2.getX()) {
                        mViewFlipper.showNext();
                    }

                    // Swipe right (previous)
                    if (e1.getX() < e2.getX()) {
                        mViewFlipper.showPrevious();
                    }

                    return super.onFling(e1, e2, velocityX, velocityY);


        }
  }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
