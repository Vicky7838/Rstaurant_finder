package com.example.vicky.restaurantfinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.gesture.Gesture;
import android.graphics.PointF;
import android.opengl.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by vicky on 5/3/2016.
 */
public class Image extends AppCompatActivity {
    String place;
    private   TouchImageView imageView;
    private ScaleGestureDetector scaleGestureDetector;
    private    Matrix matrix = new Matrix();
    private float scale = 1f;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        imageView = (TouchImageView)findViewById(R.id.imageView9);
        Intent intent = getIntent();
        if (null != intent) {
            place= intent.getStringExtra("stuff");

            String imageUrl = place;
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(imageUrl, imageView);

        }




    }
}
