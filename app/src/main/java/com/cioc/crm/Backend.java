package com.cioc.crm;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by admin on 30/04/18.
 */

class Backend {
//    static String serverUrl = "http://192.168.1.113:8000/";
//    static String serverUrl = "http://192.168.1.114:8000/";
    static String serverUrl = "http://192.168.1.103:8000/";
//    static String serverUrl = "http://192.168.137.26:8000/";
//    static String serverUrl = "https://vamso.cioc.in/";
    public Context context;


    public Backend(Context context){
        this.context = context;
    }

//    public AsyncHttpClient getHTTPClient() {
//
//        final String csrftoken = sessionManager.getCsrfId();
//        final String sessionid = sessionManager.getSessionId();
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.addHeader("X-CSRFToken" , csrftoken);
//        client.addHeader("COOKIE" , String.format("csrftoken=%s; sessionid=%s" ,csrftoken,  sessionid));
//        return client;
//    }

}
