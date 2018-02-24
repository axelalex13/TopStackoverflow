package com.example.alex.topstackoverflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class UserDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ImageView icon = (ImageView) findViewById(R.id.icon);
        TextView name = (TextView) findViewById(R.id.name);
        TextView location = (TextView) findViewById(R.id.location);
        TextView bronze = (TextView) findViewById(R.id.bronze);
        TextView silver = (TextView) findViewById(R.id.silver);
        TextView gold = (TextView) findViewById(R.id.gold);


        getSupportActionBar().setTitle("User Details");
        Intent intent = getIntent();
        final int position = intent.getIntExtra("position", -1);

        name.setText(MainActivity.users.get(position).getDisplay_name());
        location.setText(MainActivity.users.get(position).getLocation());
        bronze.setText(String.valueOf(MainActivity.users.get(position).getBadge_counts().getBronze()));
        silver.setText(String.valueOf(MainActivity.users.get(position).getBadge_counts().getSilver()));
        gold.setText(String.valueOf(MainActivity.users.get(position).getBadge_counts().getGold()));
        Picasso.with(UserDetails.this).load(MainActivity.users.get(position).getProfile_image()).fit().centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(icon);



    }
}
