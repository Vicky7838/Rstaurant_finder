package com.example.vicky.restaurantfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Review extends AppCompatActivity {
    Button button;
    EditText mul;
TextView name ,add;

    String p,p1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        button = (Button) findViewById(R.id.button5);
        name = (TextView)findViewById(R.id.nam);
        add = (TextView)findViewById(R.id.add);
        mul=(EditText)findViewById(R.id.review);
        Bundle bundle = getIntent().getExtras();

//Extract the data…
         p = bundle.getString("name");
         p1 = bundle.getString("addd");
        name.setText(p);
        add.setText(p1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BackgroundTask backgroundTask = new BackgroundTask(Review.this);
                backgroundTask.execute("review", p,mul.getText().toString());
            }
        });

    }

}
