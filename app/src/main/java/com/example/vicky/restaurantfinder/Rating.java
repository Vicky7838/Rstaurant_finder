package com.example.vicky.restaurantfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class Rating extends AppCompatActivity {
RatingBar ratingBar;
    String n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ratingBar=(RatingBar)findViewById(R.id.ratingBar2);
Intent i = getIntent();
        n = i.getStringExtra("m");

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                String c =String.valueOf(rating);
                BackgroundTask backgroundTask = new BackgroundTask(Rating.this);
                backgroundTask.execute("rating", c.toString(),n.toString());
                Toast.makeText(Rating.this,c, Toast.LENGTH_SHORT).show();
            }
	});
  }
}
