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
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class me extends AppCompatActivity
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

        SharedPreferences sharedpreferences=getSharedPreferences("myxml1", Context.MODE_PRIVATE);
        String Name=sharedpreferences.getString("mykeyname", "");
        setTitle("Hello " + Name);

        Intent i = getIntent();

        pid = i.getStringExtra("pid");
        String url;
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(config);
        restaurant = (ListView) findViewById(R.id.listView);
        restaurant.setOnItemClickListener(listPairedClickItem);

        if(pid.equals("DCK Dana Choga")) {
            url = "http://restaurantfind.esy.es/restaurant.php";
            new BackgroundTask(me.this).execute(url);
        }
        if(pid.equals("Cake Innovation")) {
            url = "http://restaurantfind.esy.es/res33.php";
            new BackgroundTask(me.this).execute(url);
        }
        if(pid.equals("Chowringhee")) {
            url = "http://restaurantfind.esy.es/res45.php";
            new BackgroundTask(me.this).execute(url);
        }
        if(pid.equals("Dial a Chicken")) {
            url = "http://restaurantfind.esy.es/res23.php";
            new BackgroundTask(me.this).execute(url);
        }


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
            Intent act1 = new Intent(me.this, Login.class);
            startActivity(act1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private AdapterView.OnItemClickListener listPairedClickItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            String ID = ((TextView) arg1.findViewById(R.id.nam))
                    .getText().toString();
            Toast.makeText(me.this,ID,Toast.LENGTH_LONG).show();
            Bundle bundle = new Bundle();
            bundle.putString("pid", ID);
            bundle.putString("de",c);
            Intent intent = new Intent(arg1.getContext(),
                    me.class);
            intent.putExtras(bundle);
            arg1.getContext().startActivity(intent);
        }
    };

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
                    restaurantModel.setDeliverytime(finalobject.getString("delivery"));
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
            RestaurantAdapter restaurantAdapter = new RestaurantAdapter(getApplicationContext(),R.layout.orderrow, result);
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
                convertView = inflater.inflate(R.layout.orderrow, null);
                viewHolder.name = (TextView) convertView.findViewById(R.id.nam);
                viewHolder.dcharge = (TextView) convertView.findViewById(R.id.mind);
                viewHolder.dtime = (TextView) convertView.findViewById(R.id.dtime);
                viewHolder.rating = (TextView) convertView.findViewById(R.id.textView12);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView6);

                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder)convertView.getTag();
            }


            final ProgressBar progressBar=(ProgressBar)convertView.findViewById(R.id.progressBar);
            ImageLoader.getInstance().displayImage(restaurantModelList.get(position).getImage(), viewHolder.imageView, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    progressBar.setVisibility(View.GONE);
                }
            });

            viewHolder.name.setText(restaurantModelList.get(position).getName());
            viewHolder.dcharge.setText(restaurantModelList.get(position).getDelivery());
            viewHolder.dtime.setText(restaurantModelList.get(position).getDeliverytime());
            viewHolder.rating.setText(restaurantModelList.get(position).getRating());


            return convertView;
        }
    }
    public class ViewHolder{
        private TextView name;
        private TextView dcharge;
        private  TextView dtime;
        private  TextView rating;
        private  ImageView imageView;


    }
}

