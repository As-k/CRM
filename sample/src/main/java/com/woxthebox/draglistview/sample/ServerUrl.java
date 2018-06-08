package com.woxthebox.draglistview.sample;


import com.loopj.android.http.AsyncHttpClient;

public class ServerUrl {
//    public String url = "http://10.0.2.2:8000/";
//   public static String url = "http://192.168.1.114:9000/";
   public static String url = "http://192.168.1.103:8000/";
//   public String url = "http://192.168.137.26:9000/";

    public ServerUrl(){

    }

    public AsyncHttpClient getHTTPClient(){
        final String csrftoken = MainActivity.csrfId;
        final String sessionid = MainActivity.sessionId;
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("X-CSRFToken" , csrftoken);
        client.addHeader("COOKIE" , String.format("csrftoken=%s; sessionid=%s" ,csrftoken,  sessionid));
        return client;
    }
}
