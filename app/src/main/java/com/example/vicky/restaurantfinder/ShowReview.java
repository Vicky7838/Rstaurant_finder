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

public class ShowReview extends AppCompatActivity
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

        Intent i = getIntent();

        pid = i.getStringExtra("stuf");
        String url;

        restaurant = (ListView) findViewById(R.id.listView);



            url = "http://restaurantfind.esy.es/review.php";
            new BackgroundTask(ShowReview.this).execute(url);



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
            Intent act1 = new Intent(ShowReview.this, Review.class);
            startActivity(act1);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            SharedPreferences.Editor editObj = spobj.edit();
            editObj.putBoolean("mykey", false);
            editObj.commit();
            Intent act1 = new Intent(ShowReview.this, Login.class);
            startActivity(act1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    class BackgroundTask extends AsyncTask<String, String, List<RestaurantModel>> {
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
        protected List<RestaurantModel> doInBackground(String... params) {
            try {

                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "/n");
                }
                String finaljson = stringBuffer.toString();
                JSONObject parentobject = new JSONObject(finaljson);
                JSONArray parentarray = parentobject.getJSONArray("result");
                StringBuffer finalbuffereddata = new StringBuffer();
                List<RestaurantModel> restaurantModelList = new ArrayList<>();

                for (int i = 0; i < parentarray.length(); i++) {
                    JSONObject finalobject = parentarray.getJSONObject(i);
                    RestaurantModel restaurantModel = new RestaurantModel();
                    restaurantModel.setName(finalobject.getString("name"));
                    restaurantModel.setAddress(finalobject.getString("address"));
                    restaurantModel.setHours(finalobject.getString("hours"));
                    restaurantModel.setCost(finalobject.getString("cost"));
                    restaurantModel.setRating(finalobject.getString("rating"));
                    restaurantModel.setImage(finalobject.getString("photo"));
                    restaurantModel.setDelivery(finalobject.getString("dcharges"));
                    restaurantModel.setReview(finalobject.getString("reviews"));
                    dcharges= finalobject.getString("dcharges");
                    name=finalobject.getString("name");
                    reviews=finalobject.getString("reviews");
                    photo=finalobject.getString("photo");
                    menu1=finalobject.getString("menu");
                    wend=finalobject.getString("w-end");
                    wday=finalobject.getString("w-day");
                    cuisines=finalobject.getString("cuisines");
                    fulladd=finalobject.getString("fulladd");
                    delivery=finalobject.getString("delivery");





                    restaurantModelList.add(restaurantModel);
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                Thread.sleep(5000);
                return restaurantModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<RestaurantModel> result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            RestaurantAdapter restaurantAdapter = new RestaurantAdapter(getApplicationContext(),R.layout.rev, result);
            restaurant.setAdapter(restaurantAdapter);
        }
    }

    public  class RestaurantAdapter extends ArrayAdapter {
        private List<RestaurantModel> restaurantModelList;
        private int resource;
        private LayoutInflater inflater;

        public RestaurantAdapter(Context context, int resource, List<RestaurantModel> object) {
            super(context, resource, object);
            restaurantModelList = object;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;

            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.rev, null);
                viewHolder.n = (TextView) convertView.findViewById(R.id.rest);
                viewHolder.ad = (TextView) convertView.findViewById(R.id.ad);
                viewHolder.re = (TextView) convertView.findViewById(R.id.rev);


                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder)convertView.getTag();
            }



            viewHolder.n.setText(restaurantModelList.get(position).getName());
            viewHolder.ad.setText(restaurantModelList.get(position).getAddress());
            viewHolder.re.setText(restaurantModelList.get(position).getReview());


            return convertView;
        }
    }
    public class ViewHolder{
        private TextView n;
        private TextView ad;
        private  TextView re;



    }
}
