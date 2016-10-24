package com.example.vicky.restaurantfinder;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Map;

public class Module extends AppCompatActivity {
Button btn,btn1,bt;
    String place;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
         btn=(Button)findViewById(R.id.button2);
        btn1=(Button)findViewById(R.id.btnLogin1);
        bt = (Button)findViewById(R.id.button3);
        Intent intent = getIntent();
        if (null != intent) {
             place= intent.getStringExtra("stuff");

                Toast.makeText(Module.this, place, Toast.LENGTH_LONG).show();

        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                   place();


            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                place2();


            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Module.this, Maps.class);
                startActivity(in);


            }
        });

        }
    public void place()
    {
        Intent in = new Intent(Module.this, Restaurant.class);

     //Create the bundle
        Bundle bundle = new Bundle();

//Add your data to bundle
        bundle.putString("stuf", place);

//Add the bundle to the intent
        in.putExtras(bundle);

//Fire that second activity
        startActivity(in);
    }
    public void place2()
    {
        Intent in = new Intent(Module.this, OrderOnline.class);

        //Create the bundle
        Bundle bundle = new Bundle();

//Add your data to bundle
        bundle.putString("stuf", place);

//Add the bundle to the intent
        in.putExtras(bundle);

//Fire that second activity
        startActivity(in);
    }


}
