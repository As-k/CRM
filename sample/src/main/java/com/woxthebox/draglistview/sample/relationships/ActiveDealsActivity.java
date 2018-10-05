package com.woxthebox.draglistview.sample.relationships;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.ServerUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by amit on 10/3/18.
 */

public class ActiveDealsActivity extends Activity {
    RecyclerView activeDealRV;
    TextView companyName,web;
    public static ArrayList<DealLite> dealLites;
    public static ArrayList<Deal> deals;
    ServerUrl serverUrl;
    public AsyncHttpClient client;
    public static String dealPk, requirements;
    public static int pos;
    DealLite dealLite;
    String company_pk, closeDate;
    public static ArrayList<Integer> contractspk;
    ActiveDealsAdapter activeDealsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_deals);

        companyName = findViewById(R.id.comapny_name);
        web = findViewById(R.id.web_text);
        activeDealRV = findViewById(R.id.activedearl_recyclerView);
        activeDealRV.setLayoutManager(new LinearLayoutManager(ActiveDealsActivity.this));
        serverUrl = new ServerUrl(this);
        client = serverUrl.getHTTPClient();
        dealLites = new ArrayList<DealLite>();
        deals = new ArrayList<Deal>();
        contractspk = new ArrayList<Integer>();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String company_name = bundle.getString("company_name");
            company_pk = bundle.getString("pk");
            String web1 = bundle.getString("web");
            companyName.setText(company_name);
            web.setText(web1);
        }

        getDealLites();

        activeDealRV.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        pos = position;
                        dealLite = dealLites.get(position);
                        getDeal(dealLite.getPk());
                        dealPk = dealLite.getPk();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(ActiveDealsActivity.this, ActiveDealsDetailsActivity.class);
                                intent.putExtra("name", dealLite.getContactName());
                                intent.putExtra("value", dealLite.getValue());
                                intent.putExtra("currency", dealLite.getCurrency());
                                intent.putExtra("closeDate", closeDate);
                                intent.putExtra("pk", company_pk);
                                intent.putExtra("contracts", contractspk);
                                startActivity(intent);
                            }
                        },500);
                    }
                })
        );
    }

    protected void getDealLites() {
        client.get(ServerUrl.url+"api/clientRelationships/dealLite/?result=won&format=json&company="+company_pk, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject Obj = null;
                    try {
                        Obj = response.getJSONObject(i);
                        DealLite dl = new DealLite(Obj);
                        dealLites.add(dl);
                    } catch(JSONException e) {
                        e.printStackTrace();
                    }
                }
                activeDealsAdapter = new ActiveDealsAdapter(ActiveDealsActivity.this, client, dealLites);
                activeDealRV.setAdapter(activeDealsAdapter);
            }
            @Override
            public void onFinish() {
                System.out.println("finished 001");
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                System.out.println("finished failed 001");
            }
        });
    }

    protected void getDeal(String pos) {
        client.get(ServerUrl.url+"/api/clientRelationships/deal/"+pos+"/?format=json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        Deal d = new Deal(response);
                        if (d.companyPk.equals(company_pk)) {
                            d.contactPk = company_pk;
                            contractspk = d.contracts;
                            closeDate = d.closeDate;
                            requirements = d.getRequirements();
                            deals.add(d);
                            Log.d("deal", deals.size() + "");
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            @Override
            public void onFinish() {
                System.out.println("finished 001");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                System.out.println("finished failed 001");
            }
        });
    }

}


