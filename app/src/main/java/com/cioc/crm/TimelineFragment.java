package com.cioc.crm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cioc.crm.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    RecyclerView recyclerViewTimeline;
    TimelineAdapter timelineAdapter;

    private List<FeedItem> feedItems;
    private String URL_FEED = "https://api.androidhive.info/feed/feed.json";


    public TimelineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_timeline, container, false);

        recyclerViewTimeline = v.findViewById(R.id.timeline_rv);
        recyclerViewTimeline.setLayoutManager(new LinearLayoutManager(getActivity()));
        timelineAdapter = new TimelineAdapter(getActivity(), feedItems);
        recyclerViewTimeline.setAdapter(timelineAdapter);

        // We first check for cached request
//        Cache cache = AppController.getInstance().getRequestQueue().getCache();
//        Cache.Entry entry = cache.get(URL_FEED);
//        if (entry != null) {
//            // fetch the data from cache
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

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));

                // Image might be null sometimes
                String image = feedObj.isNull("image") ? null : feedObj
                        .getString("image");
                item.setImge(image);
                item.setStatus(feedObj.getString("status"));
                item.setProfilePic(feedObj.getString("profilePic"));
                item.setTimeStamp(feedObj.getString("timeStamp"));

                // url might be null sometimes
                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item.setUrl(feedUrl);

                feedItems.add(item);
            }

            // notify data changes to list adapater
//            timelineAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
