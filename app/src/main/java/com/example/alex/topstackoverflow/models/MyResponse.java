package com.example.alex.topstackoverflow.models;

import java.util.List;

/**
 * Created by alex on 2/23/2018.
 */

public class MyResponse {

    private List<User> items;
    private boolean has_more;
    private int quota_more;
    private int quota_remaining;


    public List<User> getItems() {
        return items;
    }

    public void setItems(List<User> items) {
        this.items = items;
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public int getQuota_more() {
        return quota_more;
    }

    public void setQuota_more(int quota_more) {
        this.quota_more = quota_more;
    }

    public int getQuota_remaining() {
        return quota_remaining;
    }

    public void setQuota_remaining(int quota_remaining) {
        this.quota_remaining = quota_remaining;
    }
}
