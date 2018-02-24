package com.example.alex.topstackoverflow.controllers;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alex on 2/22/2018.
 */



public class ApiConnector {

    private static String data;

    public void fetchData(final VolleyCallback callback) {
        String url = "https://api.stackexchange.com/2.2/users?pagesize=10&order=desc&sort=reputation&site=stackoverflow";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());

                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("error", "Error: " + error.getMessage());
                    }
                });

        NetworkController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public void useData() {
        fetchData(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    data = result.getString("data");
                } catch (JSONException e) {
                    Log.e("error", e.getMessage(), e);
                }
            }
        });
    }


}



//    private final Context ctx;
//    RequestQueue queue;
//
//    public static List<User> users = new ArrayList<User>();
//    private static MyResponse myResponse = new MyResponse();
//    public ApiConnector (Context ctx) {
//        this.ctx = ctx;
//    }
//    public void getResponse(int method, String url, JSONObject jsonValue, final VolleyCallback callback) {
//
//        queue = MySingleton.getInstance(ctx).getRequestQueue();
//
//        StringRequest strreq = new StringRequest(Request.Method.GET, url, new Response.Listener < String > () {
//
//            @Override
//            public void onResponse(String Response) {
//                callback.onSuccessResponse(Response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError e) {
//                e.printStackTrace();
//                Toast.makeText(ctx, e + "error", Toast.LENGTH_LONG).show();
//            }
//        })
//        {
//            // set headers
//            @Override
//            public Map < String, String > getHeaders() throws com.android.volley.AuthFailureError {
//                Map< String, String > params = new HashMap< String, String >();
//
//                return params;
//            }
//        };
//        MySingleton.getInstance(ctx).addToRequestQueue(strreq);
//    }
//
//    public List<User> myWebServiceFun() {
//
//        String url = "https://api.stackexchange.com/2.2/users?pagesize=10&order=desc&sort=reputation&site=stackoverflow";
//
//        getResponse(Request.Method.GET, url, null,
//                new VolleyCallback() {
//                    @Override
//                    public void onSuccessResponse(String result) {
//                        try {
//
//                            Gson g = new Gson();
//                            myResponse = g.fromJson(result,MyResponse.class);
//                            Log.v("tag",String.valueOf(myResponse.getItems().size()));
//                            users = myResponse.getItems();
//                            Log.v("tag",String.valueOf(users.size()));
//
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//
//        return users;
//    }
//
////    public static List<User> users = new ArrayList<User>();
////
////    private static MyResponse myResponse = new MyResponse();
////
////
////
////    public static void getUsers(Context context) {
////
////        String url = "https://api.stackexchange.com/2.2/users?pagesize=10&order=desc&sort=reputation&site=stackoverflow";
////        RequestQueue queue = Volley.newRequestQueue(context);
////        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
////                new Response.Listener<String>() {
////                    @Override
////                    public void onResponse(String response) {
////                        Gson g = new Gson();
////                        myResponse = g.fromJson(response,MyResponse.class);
////                        Log.v("tag",String.valueOf(myResponse.getItems().size()));
////                        users = myResponse.getItems();
////                        Log.v("tag",String.valueOf(users.size()));
////                        MainActivity.users = users;
////                    }
////                }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError error) {
////
////            }
////        });
////
////        queue.add(stringRequest);
////
////
////    }
////
////    public static List<User> returnUsers(List<User> users){
////       return users;
////    }
//}
//
//
//
