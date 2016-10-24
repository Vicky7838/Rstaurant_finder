package com.example.vicky.restaurantfinder;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class EventDetails extends AppCompatActivity {
    String name, reviews, wend, wday, delivery, dcharges, menu1, cuisines, photo, fulladd, addd, rat, hours, men, phone, lat, lon;
    String y;
    TextView fullad, cuisi, weekd, weeke, deliver, nam, rate, rev, dchar, add;
    ImageView thumbnailImage;
    boolean isImageFitToScreen;
    ImageView menu, mapview;
    Button call1, bookmark,review,bu;
RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        thumbnailImage = (ImageView) findViewById(R.id.menu);
        mapview = (ImageView) findViewById(R.id.images);
        fullad = (TextView) findViewById(R.id.address);
        cuisi = (TextView) findViewById(R.id.cuisines);
        nam = (TextView) findViewById(R.id.Namess);
        weekd = (TextView) findViewById(R.id.weekd);
        weeke = (TextView) findViewById(R.id.weeke);
        rate = (TextView) findViewById(R.id.rating);
        dchar = (TextView) findViewById(R.id.delc);
        deliver = (TextView) findViewById(R.id.time);
        rev = (TextView) findViewById(R.id.rev);
        add = (TextView) findViewById(R.id.sector);
        menu = (ImageView) findViewById(R.id.menu);
        call1 = (Button) findViewById(R.id.call);
        bookmark = (Button) findViewById(R.id.bookmarks);
        review =(Button)findViewById(R.id.addareview);
        bu =(Button)findViewById(R.id.button);
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(EventDetails.this,Rating.class);
                Bundle i = new Bundle();
                i.putString("m",name);
                in.putExtras(i);
                startActivity(in);
            }
        });
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(EventDetails.this, Review.class);
                Bundle bundle = new Bundle();

                bundle.putString("name", name);
                bundle.putString("addd", addd);
//Add the bundle to the intent
                in.putExtras(bundle);
                startActivity(in);
            }
        });
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences=getSharedPreferences("myxml1", Context.MODE_PRIVATE);
                String Name=sharedpreferences.getString("mykeyname", "");

                com.example.vicky.restaurantfinder.BackgroundTask backgroundTask = new com.example.vicky.restaurantfinder.BackgroundTask(EventDetails.this);
                backgroundTask.execute("bookmark", name.toString(), Name.toString());

            }
        });
        call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneNo = phone;
                AlertDialog.Builder alert = new AlertDialog.Builder(EventDetails.this);
                alert.setMessage("Do you want to call us?" + phoneNo);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    String uri = "tel:" + phoneNo;

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(uri));
                        if (ActivityCompat.checkSelfPermission(EventDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        finish();

                    }

                });
                alert.show();
            }


        });

        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.rel);
        mapview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(EventDetails.this, MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("lat", lat);
                bundle.putString("lon", lon);
                bundle.putString("name", name);
                bundle.putString("addd", addd);
//Add the bundle to the intent
                in.putExtras(bundle);
                startActivity(in);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDetails.this, Image.class);
                Bundle bundle = new Bundle();

//Add your data to bundle
                bundle.putString("stuff", men);

//Add the bundle to the intent
                intent.putExtras(bundle);

//Fire that second activity
                startActivity(intent);


            }

        });

//Get the bundle
        Bundle bundle = getIntent().getExtras();

//Extract the data…
        String p = bundle.getString("pid");
        y = bundle.getString("pid");

        String b= y.replaceAll("\\s+","%20");

        System.out.println("\nOutput String is  :\n"+b);

            BackgroundTask backgroundTask = new BackgroundTask(EventDetails.this);
            backgroundTask.execute("http://restaurantfind.esy.es/chowringhee.php?name="+b);
System.out.println(y);
    }

    class BackgroundTask extends AsyncTask<String, String, String> {
        Context ctx;
        Activity activity;
        String url1;
        AlertDialog.Builder builder;
        ProgressDialog progressDialog;

        public BackgroundTask(Context ctx) {
            this.ctx = ctx;
            activity = (Activity) ctx;
        }

        @Override
        protected void onPreExecute() {
            builder = new AlertDialog.Builder(activity);
            progressDialog = new ProgressDialog(ctx);
            progressDialog.setTitle("Please wait..");
            progressDialog.setMessage("Connecting to server");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "/n");
                }

                String finaljson = stringBuffer.toString();
                JSONObject parentobject = new JSONObject(finaljson);
                JSONArray parentarray = parentobject.getJSONArray("result");
                StringBuffer finalbuffereddata = new StringBuffer();


                JSONObject finalobject = parentarray.getJSONObject(0);

                dcharges = finalobject.getString("dcharges");
                name = finalobject.getString("name");
                reviews = finalobject.getString("reviews");
                photo = finalobject.getString("photo");
                menu1 = finalobject.getString("menu");
                wend = finalobject.getString("w-end");
                hours = finalobject.getString("hours");
                cuisines = finalobject.getString("cuisines");
                fulladd = finalobject.getString("fulladd");
                delivery = finalobject.getString("delivery");
                addd = finalobject.getString("address");
                rat = finalobject.getString("rating");
                men = finalobject.getString("menu");
                lat = finalobject.getString("lat");
                lon = finalobject.getString("long");
                phone = finalobject.getString("phoneno");


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            dchar.setText(dcharges);
            add.setText(addd);
            nam.setText(name);
            fullad.setText(fulladd);
            cuisi.setText(cuisines);
            rev.setText(reviews);
            weeke.setText(wend);
            weekd.setText(hours);
            dchar.setText(dcharges);
            rate.setText(rat);
            deliver.setText("Time :" + delivery);
            String imageUrl = men;
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(imageUrl, thumbnailImage);
        }
    }

}
