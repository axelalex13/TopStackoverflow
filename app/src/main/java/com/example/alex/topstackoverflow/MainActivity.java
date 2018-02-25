package com.example.alex.topstackoverflow;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.alex.topstackoverflow.Adapters.CustomListView;
import com.example.alex.topstackoverflow.models.MyResponse;
import com.example.alex.topstackoverflow.models.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    public static List<User> users = new ArrayList<User>();
    public MyResponse myResponse = new MyResponse();
    CustomListView customListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listView = findViewById(R.id.listViewTop);

        String url = getString(R.string.urlTop10);

        //folosesc Volley pentru lucrul cu API-ul
        RequestQueue queue = Volley.newRequestQueue(this);

        SharedPreferences sharedpreferences = getSharedPreferences("Backup", Context.MODE_PRIVATE);
        if (sharedpreferences.getLong("ExpiredDate", -1) > System.currentTimeMillis() && queue.getCache().get(url) != null) {
            String response = sharedpreferences.getString("response", "");
            parse(response, listView);
        } else {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.apply();
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            parse(response.toString(), listView);
                            //salvez datale pe disk
                            SharedPreferences sharedpreferences = getSharedPreferences("Backup", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("response", response.toString());
                            editor.putLong("ExpiredDate", System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30));
                            editor.apply();

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            String message = null;
                            if (volleyError instanceof NetworkError) {
                                message = "Cannot connect to Internet...Please check your connection!";
                            } else if (volleyError instanceof ServerError) {
                                message = "The server could not be found. Please try again after some time!!";
                            } else if (volleyError instanceof AuthFailureError) {
                                message = "Cannot connect to Internet...Please check your connection!";
                            } else if (volleyError instanceof ParseError) {
                                message = "Parsing error! Please try again after some time!!";
                            } else if (volleyError instanceof NoConnectionError) {
                                message = "Cannot connect to Internet...Please check your connection!";
                            } else if (volleyError instanceof TimeoutError) {
                                message = "Connection TimeOut! Please check your internet connection.";
                            }
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                        }
                    }) {
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    Response<JSONObject> resp = super.parseNetworkResponse(response);
                    if (!resp.isSuccess()) {
                        return resp;
                    }
                    long now = System.currentTimeMillis();
                    Cache.Entry entry = resp.cacheEntry;
                    if (entry == null) {
                        entry = new Cache.Entry();
                        entry.data = response.data;
                        entry.responseHeaders = response.headers;
                    }
                    entry.ttl = now + 60 * 10000;  //keeps cache for 10 min

                    return Response.success(resp.result, entry);
                }
            };

            queue.add(jsObjRequest);

        }

        DisplayMetrics metrics = new DisplayMetrics();
        MainActivity.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        final double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (diagonalInches >= 9) {
                            RelativeLayout layoutDetails = findViewById(R.id.layoutDetails);
                            layoutDetails.setAlpha(1);
                            TextView location = findViewById(R.id.location);
                            ImageView icon_details = findViewById(R.id.icon);
                            TextView name = findViewById(R.id.name);
                            TextView bronze = findViewById(R.id.bronze);
                            TextView silver = findViewById(R.id.silver);
                            TextView gold = findViewById(R.id.gold);
                            name.setText(MainActivity.users.get(position).getDisplay_name());
                            location.setText(MainActivity.users.get(position).getLocation());
                            bronze.setText(String.valueOf(MainActivity.users.get(position).getBadge_counts().getBronze()));
                            silver.setText(String.valueOf(MainActivity.users.get(position).getBadge_counts().getSilver()));
                            gold.setText(String.valueOf(MainActivity.users.get(position).getBadge_counts().getGold()));
                            Picasso.with(MainActivity.this).load(MainActivity.users.get(position).getProfile_image()).fit().centerCrop()
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .into(icon_details);


                        } else {
                            Intent details = new Intent(MainActivity.this, UserDetails.class);
                            details.putExtra("position", position);
                            details.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(details);
                        }
                    }
                }
        );
    }



    public void parse(String response, ListView listView) {
        Gson g = new Gson();
        myResponse = g.fromJson(response, MyResponse.class);
        users = myResponse.getItems();
        customListView = new CustomListView(MainActivity.this, users);
        listView.setAdapter(customListView);

    }


}
