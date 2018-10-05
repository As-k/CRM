package com.woxthebox.draglistview.sample.relationships;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.ServerUrl;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class FinancesFragment extends Fragment {
    RecyclerView financesRecyclerView;
    FinancesAdapter financesAdapter;
    public static ArrayList<Contract> finance;
    public AsyncHttpClient client;
    ServerUrl serverUrl;

    public FinancesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_finances, container, false);

        serverUrl = new ServerUrl(getContext());
        client = serverUrl.getHTTPClient();
        finance = new ArrayList<>();

        financesRecyclerView = v.findViewById(R.id.finances_rv);
        financesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FloatingActionButton addQuote = v.findViewById(R.id.add_quote_fab);
        addQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddQuoteActivity.class));
            }
        });

        getFinances();
        return v;
    }

    protected void getFinances() {
        for (int i = 0; i < ActiveDealsActivity.contractspk.size(); i++) {
            client.get(ServerUrl.url + "/api/clientRelationships/contract/" + ActiveDealsActivity.contractspk.get(i), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
                        Contract contract = new Contract(response);
                        finance.add(contract);
                }

                @Override
                public void onFinish() {
                    System.out.println("finished 001");
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                    // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                    System.out.println("finished failed 001");
                    Log.d("finance error", "statusCode: " + statusCode);
                }
            });
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                financesAdapter = new FinancesAdapter(getContext(), finance);
                financesRecyclerView.setAdapter(financesAdapter);
                financesAdapter.notifyDataSetChanged();
            }
        },500);

    }
}
