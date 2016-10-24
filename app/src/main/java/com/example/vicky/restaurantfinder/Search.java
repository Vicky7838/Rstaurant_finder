package com.example.vicky.restaurantfinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class Search extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            String[] areas = getResources().getStringArray(R.array.listofareas);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, areas);
            autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
            autoCompleteTextView.setAdapter(adapter);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                    Intent in = new Intent(Search.this, Module.class);

                    String getrec = autoCompleteTextView.getText().toString();

//Create the bundle
                    Bundle bundle = new Bundle();

//Add your data to bundle
                    bundle.putString("stuff", getrec);

//Add the bundle to the intent
                    in.putExtras(bundle);

//Fire that second activity
                    startActivity(in);

                }
            };

            /** Setting the itemclick event listener */
            autoCompleteTextView.setOnItemClickListener(itemClickListener);



        }


}
