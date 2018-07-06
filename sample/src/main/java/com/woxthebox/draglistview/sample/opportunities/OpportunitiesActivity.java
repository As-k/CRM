/**
 * Copyright 2014 Magnus Woxblom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.woxthebox.draglistview.sample.opportunities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.ServerUrl;
import com.woxthebox.draglistview.sample.opportunities.BoardFragment;
import com.woxthebox.draglistview.sample.relationships.ActiveDealsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class OpportunitiesActivity extends AppCompatActivity {
    public AsyncHttpClient client;
    ServerUrl serverUrl;
    List<Opportunities> opportunities;
    RecyclerView opportunitiesRecyclerView;
    OpportunitiesAdapter opportunitiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opportunities);
        getSupportActionBar().hide();
        serverUrl = new ServerUrl(this);
        client = serverUrl.getHTTPClient();


        FloatingActionButton newCreateOppo = findViewById(R.id.create_new_oppo);
        newCreateOppo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewOpportunityActivity.class));
            }
        });
        opportunitiesRecyclerView = findViewById(R.id.opportunities_recycleview);
        opportunitiesRecyclerView.setLayoutManager(new LinearLayoutManager(OpportunitiesActivity.this));
//        if (savedInstanceState == null) {
//            showFragment(BoardFragment.newInstance());
//        }

    }

    protected void getOpp() {
        opportunities.clear();
        client.get(ServerUrl.url+"api/clientRelationships/deal/?&created=false&board&format=json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject Obj = null;
                    try {
                        Obj = response.getJSONObject(i);
                        Opportunities opp = new Opportunities(Obj);
                        opportunities.add(opp);
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

    @Override
    protected void onResume() {
        super.onResume();
        opportunities = new ArrayList<>();

        getOpp();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                opportunitiesAdapter = new OpportunitiesAdapter(getApplicationContext(), opportunities);
                opportunitiesAdapter.notifyDataSetChanged();
                opportunitiesRecyclerView.setAdapter(opportunitiesAdapter);
            }
        },500);
    }

    //    private void showFragment(Fragment fragment) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.container, fragment, "fragment").commit();
//    }
}

