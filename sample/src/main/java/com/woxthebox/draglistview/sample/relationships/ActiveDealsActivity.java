package com.woxthebox.draglistview.sample.relationships;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    RecyclerView rv1;
    TextView companyname,web;
    public static ArrayList<DealLite> dealLites;
    public static ArrayList<Deal> deals;
    public AsyncHttpClient client;
    public static String c_pk,company,street,city,astate,pincode,country,pkc,requirements,namec,designation;
//    public static JSONObject contracts;
    public static int pos;
    private Deal d;
    String company_pk;
    ArrayList<Integer> contractspk;


    ServerUrl serverUrl;
    ActiveDealsAdapter activeDealsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_deals);

        companyname = findViewById(R.id.comapny_name);
        web = findViewById(R.id.web_text);
        rv1 = findViewById(R.id.activedearl_recyclerView);
        rv1.setLayoutManager(new LinearLayoutManager(ActiveDealsActivity.this));
        serverUrl = new ServerUrl();
        client = serverUrl.getHTTPClient();
        dealLites = new ArrayList<DealLite>();
        deals = new ArrayList<Deal>();
        contractspk = new ArrayList<Integer>();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String company_name = bundle.getString("company_name");
            company_pk = bundle.getString("pk");
            String web1 = bundle.getString("web");
            companyname.setText(company_name);
            web.setText(web1);
        }
        getDeal();
        getDealLites();

        rv1.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(ActiveDealsActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                        pos=position;
                        DealLite d = dealLites.get(position);
                        Deal deal = deals.get(position);
                        Intent intent = new Intent(ActiveDealsActivity.this, ActiveDealsDetailsActivity.class);
                        intent.putExtra("name", d.getContactName());
                        intent.putExtra("value", d.getValue());
                        intent.putExtra("closeDate", deal.getCloseDate());
                        intent.putExtra("pk", company_pk);
                        intent.putExtra("contracts", contractspk);
                        startActivity(intent);
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

                activeDealsAdapter = new ActiveDealsAdapter(ActiveDealsActivity.this, dealLites);
                rv1.setAdapter(activeDealsAdapter);
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
    protected void getDeal() {
        String serverURL = serverUrl.url;
        client.get(serverURL+"api/clientRelationships/deal/?format=json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject Obj = null;
                    try {
                        Obj = response.getJSONObject(i);
                        Deal d = new Deal(Obj);
                        if (d.companyPk.equals(company_pk)) {
                            d.contactPk = company_pk;
                            contractspk = d.contracts;

                            deals.add(d);
                            Log.d("deal", deals.size() + "");
                        }
                    } catch(JSONException e) {
                        e.printStackTrace();
                    }
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


