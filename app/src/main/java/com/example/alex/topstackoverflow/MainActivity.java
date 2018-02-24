package com.example.alex.topstackoverflow;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alex.topstackoverflow.Adapters.CustomListView;
import com.example.alex.topstackoverflow.models.MyResponse;
import com.example.alex.topstackoverflow.models.User;
import com.google.gson.Gson;

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
        final ListView listView = (ListView) findViewById(R.id.listViewTop);


        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.stackexchange.com/2.2/users?pagesize=10&order=desc&sort=reputation&site=stackoverflow";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson g = new Gson();
                        myResponse = g.fromJson(response,MyResponse.class);
                        users = myResponse.getItems();
                        customListView = new CustomListView(MainActivity.this, users);
                        listView.setAdapter(customListView);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }


}
