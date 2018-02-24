package com.example.alex.topstackoverflow.models;

/**
 * Created by alex on 2/22/2018.
 */

public class User {
    private BadgeCounts badge_counts;
    private String display_name;
    private String profile_image;
    private String location;


    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BadgeCounts getBadge_counts() {
        return badge_counts;
    }

    public void setBadge_counts(BadgeCounts badge_counts) {
        this.badge_counts = badge_counts;
    }
}
