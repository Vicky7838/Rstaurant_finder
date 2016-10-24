package com.example.vicky.restaurantfinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Bookmark extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView restaurant;
    String pid;
    RestaurantModel restaurantModel;
    String c,name,reviews,wend,wday,delivery,dcharges,menu1,cuisines,photo,fulladd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        restaurantModel = new RestaurantModel();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedpreferences=getSharedPreferences("myxml1",Context.MODE_PRIVATE);
        String Name=sharedpreferences.getString("mykeyname", "");
        setTitle("Hello " + Name);
        com.example.vicky.restaurantfinder.BackgroundTask backgroundTask = new com.example.vicky.restaurantfinder.BackgroundTask(Bookmark.this);
        backgroundTask.execute("bookmark", Name.toString());

        Intent i = getIntent();

        pid = i.getStringExtra("stuf");

        restaurant = (ListView) findViewById(R.id.listView);


        /*if(pid.equals("Sector - 7")) {
            url = "http://restaurantfind.esy.es/restaurant.php";
            new BackgroundTask(Restaurant.this).execute(url);
        }
        if(pid.equals("Sector - 33")) {
            url = "http://restaurantfind.esy.es/res33.php";
            new BackgroundTask(Restaurant.this).execute(url);
        }
        if(pid.equals("Sector - 45")) {
            url = "http://restaurantfind.esy.es/res45.php";
            new BackgroundTask(Restaurant.this).execute(url);
        }
        if(pid.equals("Sector - 23")) {
            url = "http://restaurantfind.esy.es/res23.php";
            new BackgroundTask(Restaurant.this).execute(url);
        }
*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.restaurant, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        final SharedPreferences spobj = getSharedPreferences("myxml1",
                MODE_PRIVATE);

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            SharedPreferences.Editor editObj = spobj.edit();
            editObj.putBoolean("mykey", false);
            editObj.commit();
            Intent act1 = new Intent(Bookmark.this, Login.class);
            startActivity(act1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
