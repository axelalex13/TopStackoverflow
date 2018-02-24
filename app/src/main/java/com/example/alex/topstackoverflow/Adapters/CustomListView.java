package com.example.alex.topstackoverflow.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alex.topstackoverflow.R;
import com.example.alex.topstackoverflow.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by alex on 2/23/2018.
 */

public class CustomListView extends ArrayAdapter<User> {

    private List<User> users;
    private Activity context;

    public CustomListView(Activity context, List<User> users) {
        super(context, R.layout.listview, users);

        this.context = context;
        this.users = users;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        CustomListView.ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.listview, null, true);
            viewHolder = new CustomListView.ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (CustomListView.ViewHolder) r.getTag();
        }
        Picasso.with(context).load(users.get(position).getProfile_image()).fit().centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(viewHolder.ivw);
        viewHolder.name.setText(users.get(position).getDisplay_name());
        viewHolder.position.setText(String.valueOf(position +1 )+".");


        return r;

    }

    class ViewHolder {
        TextView name;
        TextView position;
        ImageView ivw;

        ViewHolder(View v) {
            name = (TextView) v.findViewById(R.id.name);
            ivw = (ImageView) v.findViewById(R.id.icon);
            position = (TextView) v.findViewById(R.id.position);

        }
    }

    public List<User> getUsers() {

        return users;
    }

    public void setJobs(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public Activity getContext() {
        return context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }
}
