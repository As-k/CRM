package com.woxthebox.draglistview.sample;


import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;

public class ServerUrl {
//   public String url = "http://10.0.2.2:8000/";
//   public static String url = "http://192.168.1.114:9000/";
//   public static String url = "http://192.168.1.123:8080/";
    public static  String url="http://crm.cioc.in/";
//   public String url = "http://192.168.137.26:9000/";
    public Context context;
    SessionManager sessionManager;

    //csrftoken=WVfBbmjvBMPahXhRVn3SOezVGYDpzEVnIwBx3z9mFB4UCX4w6xEAS10xi0oB6xj1; sessionid=8edsffn6c7yy899hsd42ccff027uamq1


    public ServerUrl(Context context){
        this.context = context;
    }

    public AsyncHttpClient getHTTPClient(){
        sessionManager = new SessionManager(context);
//        final String csrftoken = sessionManager.getCsrfId();
//        final String sessionid = sessionManager.getSessionId();
        final String csrftoken = "WVfBbmjvBMPahXhRVn3SOezVGYDpzEVnIwBx3z9mFB4UCX4w6xEAS10xi0oB6xj1";
        final String sessionid = "8edsffn6c7yy899hsd42ccff027uamq1";
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("X-CSRFToken" , csrftoken);
        client.addHeader("COOKIE" , String.format("csrftoken=%s; sessionid=%s" , csrftoken,  sessionid));
        return client;
    }

}
