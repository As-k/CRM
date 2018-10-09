package com.woxthebox.draglistview.sample.contacts;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.woxthebox.draglistview.sample.MainActivity;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.ServerUrl;
import com.woxthebox.draglistview.sample.SimpleDividerItemDecoration;
import com.woxthebox.draglistview.sample.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    RecyclerView recyclerViewTimeline,externalRecycler,internalRecycler;
    TimelineAdapter timelineAdapter;
    InternalEmployeesAdapter internalEmployeesAdapter;

    ServerUrl serverUrl;
    static JSONArray feedArray;

    private List<FeedItem> feedItems;
    private List<FeedActivity> feedActivityList;
    private String URL_FEED = "http://crm.cioc.in/api/clientRelationships/activity/?contact=1&offset=0";             //https://api.androidhive.info/feed/feed.json;            //http://192.168.1.104:8000/api/clientRelationships/activity/
    ArrayList<String> companiesList;
    public AsyncHttpClient client;

    public TimelineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_timeline, container, false);


        serverUrl = new ServerUrl(getContext());
        client = serverUrl.getHTTPClient();
        feedItems = new ArrayList<>();
        feedActivityList = new ArrayList<>();
        getContentValue();
        recyclerViewTimeline = v.findViewById(R.id.timeline_rv);
        recyclerViewTimeline.setLayoutManager(new LinearLayoutManager(getActivity()));



//        // We first check for cached request
//        Cache cache = AppController.getInstance().getRequestQueue().getCache();
//        Cache.Entry entry = cache.get(URL_FEED);
//        if (entry != null) {
//            // fetch the data from cache.
//            try {
//                String data = new String(entry.data, "UTF-8");
//                try {
//                    parseJsonFeed(new JSONObject(data));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//
//        } else {
//            // making fresh volley request and getting json
//            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
//                    URL_FEED, null, new Response.Listener<JSONObject>() {
//
//                @Override
//                public void onResponse(JSONObject response) {
//                    VolleyLog.d(TAG, "Response: " + response.toString());
//                    if (response != null) {
//                        parseJsonFeed(response);
//                    }
//                }
//            }, new Response.ErrorListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    VolleyLog.d(TAG, "Error: " + error.getMessage());
//                }
//            });
//
//            // Adding request to volley request queue
//            AppController.getInstance().addToRequestQueue(jsonReq);
//        }

        return v;
    }

    protected void getContentValue() {
        client.get(ServerUrl.url + "/api/clientRelationships/activity/?format=json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject json = response.getJSONObject(i);
                        FeedItem fa = new FeedItem(json);
//                        String time = json.getString("created");
//                        String status = json.getString("data");
//                        String doc = json.getString("doc");
//                        String time = json.getString("created");
                        feedItems.add(fa);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                timelineAdapter = new TimelineAdapter(getActivity(), feedItems);
                recyclerViewTimeline.setAdapter(timelineAdapter);
            }

            @Override
            public void onFinish() {
                System.out.println("finished EditContact");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println("finished failed EditContact");
            }
        });
    }

    /**
     * Parsing json response and passing the data to feed view list adapter
     */
    private void parseJsonFeed(JSONObject response) {
        try {

           feedArray= response.getJSONArray("results");
            for(int i=0;i<response.length();i++) {
                JSONObject feedObject = (JSONObject) feedArray.get(i);
                FeedItem item = new FeedItem(feedObject);
                feedItems.add(item);
                Log.e("feedItems", "" + feedItems.size());

            }

            // notify data changes to list adapater
//            timelineAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}





