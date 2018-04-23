package com.woxthebox.draglistview.sample;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExternalStakeholderFragment extends Fragment {
    RecyclerView recyclerView;
    Context context;
    ServerUrl serverUrl;
    private Deal d;
    public static ArrayList externalHolder;
    public AsyncHttpClient client;
    public String contact_Pk;


    public ExternalStakeholderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_external_stakeholder, container, false);

        recyclerView = view.findViewById(R.id.stakeholder_rv);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ExternalStakeholderAdapter externalStakeholderAdapter = new ExternalStakeholderAdapter(getContext());
        recyclerView.setAdapter(externalStakeholderAdapter);

        return view;
    }

}




