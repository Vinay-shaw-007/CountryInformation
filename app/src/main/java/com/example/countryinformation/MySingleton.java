package com.example.countryinformation;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {

    private static MySingleton INSTANCE;
    private RequestQueue requestQueue;
    MySingleton(Context context){
        requestQueue = getRequestQueue(context);
    }
    public static synchronized MySingleton getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = new MySingleton(context);
        }
        return INSTANCE;
    }
    public RequestQueue getRequestQueue(Context ctx) {
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        requestQueue.add(req);
    }
}
