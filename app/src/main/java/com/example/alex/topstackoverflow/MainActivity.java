package com.example.alex.topstackoverflow;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static  List<User> users = new ArrayList<User>();
    public MyResponse myResponse = new MyResponse();
    CustomListView customListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listView = findViewById(R.id.listViewTop);


        RequestQueue queue = Volley.newRequestQueue(this);

        String url = getString(R.string.urlTop10);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Gson g = new Gson();
                        myResponse = g.fromJson(response.toString(),MyResponse.class);
                        users = myResponse.getItems();
                        customListView = new CustomListView(MainActivity.this, users);
                        listView.setAdapter(customListView);
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
                }){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Response<JSONObject> resp = super.parseNetworkResponse(response);
                if(!resp.isSuccess()) {
                    return resp;
                }
                long now = System.currentTimeMillis();
                Cache.Entry entry = resp.cacheEntry;
                if(entry == null) {
                    entry = new Cache.Entry();
                    entry.data = response.data;
                    entry.responseHeaders = response.headers;
                }
                entry.ttl = now + 60 * 10000;  //keeps cache for 10 min

                return Response.success(resp.result, entry);
            }
        };

        queue.add(jsObjRequest);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent details = new Intent(MainActivity.this, UserDetails.class);
                        details.putExtra("position", position);
                        startActivity(details);

                    }
                }
        );
    }


}
