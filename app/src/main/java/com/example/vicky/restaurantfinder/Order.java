package com.example.vicky.restaurantfinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Order extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    int co = 0;
    ListView restaurant;
    String pid;
    Button butt;
    RestaurantModel restaurantModel;
    AlertDialog.Builder builder;
    RestaurantAdapter restaurantAdapter;
    String c;
    int totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        restaurantModel = new RestaurantModel();
        butt = (Button) findViewById(R.id.submit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent i = getIntent();

        pid = i.getStringExtra("pid");
        String url;

        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = "Selected Product are :";

                for (RestaurantModel p : restaurantAdapter.getBox()) {
                    if (p.box) {
                        result += "\n" + p.itemname +" "+"="+" "+"Rs "+ p.itemcost;

                        int r = Integer.parseInt(p.itemcost);
                        totalAmount += r;
                    }
                }

                builder = new AlertDialog.Builder(Order.this);
                builder.setTitle("Order");
                builder.setMessage(result);
                builder.setPositiveButton("Payment = Rs "+totalAmount, new DialogInterface.OnClickListener() {

                    String cot = Integer.toString(totalAmount);
                    public void onClick(DialogInterface dialog, int which) {
                        Bundle bundle = new Bundle();
                        bundle.putString("pid",cot);

                        Intent i = new Intent(Order.this,Payment.class);
                        i.putExtras(bundle);

                        startActivity(i);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                /*Toast.makeText(Order.this, result + "\n" + "Total Amount:=" + totalAmount, Toast.LENGTH_LONG).show();*/
            }
       /* String[] outputStrArr = new String[selectedItems.size()];

        for (int i = 0; i < selectedItems.size(); i++) {
            outputStrArr[i] = selectedItems.get(i);
        }

        Intent intent = new Intent(getApplicationContext(),
                ResultActivity.class);

        // Create a bundle object
        Bundle b = new Bundle();
        b.putStringArray("selectedItems", outputStrArr);

        // Add the bundle to the intent.
        intent.putExtras(b);

        // start the ResultActivity
        startActivity(intent);*/

        });
        restaurant = (ListView) findViewById(R.id.listView);
        restaurant.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        restaurant.setOnItemClickListener(listPairedClickItem);

        if (pid.equals("DCK Dana Choga")) {
            BackgroundTask backgroundTask = new BackgroundTask(Order.this);
            backgroundTask.execute("http://restaurantfind.esy.es/menu1.php");
        }
        if (pid.equals("Cake Innovation")) {
            BackgroundTask backgroundTask = new BackgroundTask(Order.this);
            backgroundTask.execute("http://restaurantfind.esy.es/menu.php");
        }
        if (pid.equals("Chowringhee")) {
            BackgroundTask backgroundTask = new BackgroundTask(Order.this);
            backgroundTask.execute("http://restaurantfind.esy.es/menu3.php");
        }
        if (pid.equals("Dial a Chicken")) {
            BackgroundTask backgroundTask = new BackgroundTask(Order.this);
            backgroundTask.execute("http://restaurantfind.esy.es/menu4.php");
        }
        if (pid.equals("Biryani Paradise")) {
            BackgroundTask backgroundTask = new BackgroundTask(Order.this);
            backgroundTask.execute("http://restaurantfind.esy.es/mn5.php");
        }
        if (pid.equals("OmSweets&Paradise")) {
            BackgroundTask backgroundTask = new BackgroundTask(Order.this);
            backgroundTask.execute("http://restaurantfind.esy.es/mn6.php");
        }
        if (pid.equals("Eato")) {
            BackgroundTask backgroundTask = new BackgroundTask(Order.this);
            backgroundTask.execute("http://restaurantfind.esy.es/mn7.php");
        }
        if (pid.equals("Chicken Factory")) {
            BackgroundTask backgroundTask = new BackgroundTask(Order.this);
            backgroundTask.execute("http://restaurantfind.esy.es/mn8.php");
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



    private AdapterView.OnItemClickListener listPairedClickItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            TextView mTextView = (TextView) arg1.findViewById(R.id.textView19);
            TextView TextView = (TextView) arg1.findViewById(R.id.textView20);
            String value = mTextView.getText().toString();
            String valu = TextView.getText().toString();
            Toast.makeText(Order.this, value + " added " + valu, Toast.LENGTH_SHORT).show();

        }
    };


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
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
                    restaurantModel.setItemname(finalobject.getString("itemname"));
                    restaurantModel.setItemcost(finalobject.getString("itemprice"));

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
            restaurantAdapter = new RestaurantAdapter(getApplicationContext(), R.layout.mi, result);
            restaurant.setAdapter(restaurantAdapter);


        }
    }

    public class RestaurantAdapter extends ArrayAdapter {
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
                convertView = inflater.inflate(R.layout.mi, null);
                viewHolder.name = (TextView) convertView.findViewById(R.id.textView19);
                viewHolder.price = (TextView) convertView.findViewById(R.id.textView20);
                viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.check);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            viewHolder.name.setText(restaurantModelList.get(position).getItemname());
            viewHolder.price.setText("Rs." + restaurantModelList.get(position).getItemcost());

            RestaurantModel p = getProduct(position);
            CheckBox cbBuy = (CheckBox) convertView.findViewById(R.id.check);
            cbBuy.setOnCheckedChangeListener(myCheckChangList);
            cbBuy.setTag(position);
            cbBuy.setChecked(p.box);

            return convertView;
        }


        ArrayList<RestaurantModel> getBox() {
            ArrayList<RestaurantModel> box = new ArrayList<RestaurantModel>();
            for (RestaurantModel p : restaurantModelList) {
                if (p.box)
                    box.add(p);
            }
            return box;
        }

        RestaurantModel getProduct(int position) {
            return ((RestaurantModel) getItem(position));
        }

        CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                getProduct((Integer) buttonView.getTag()).box = isChecked;
            }
        };
    }

    public class ViewHolder {
        private TextView name;
        private TextView price;
        private CheckBox checkBox;
    }
}

