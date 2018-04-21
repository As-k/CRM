package com.woxthebox.draglistview.sample;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by amit on 9/3/18.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

/**
 * Created by amit on 9/3/18.
 */

public class RelationshipActivity extends Activity {
    RecyclerView rv;
    public static List<Relationships> relationship;
    public AsyncHttpClient client;
    public static int pos;
    private Relationships relationships;
    ServerUrl serverUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relationships);

        serverUrl = new ServerUrl();

        client = new AsyncHttpClient();
        relationship = new ArrayList<>();
        getData();

        rv = findViewById(R.id.realtionship_recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(RelationshipActivity.this));


        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
//                        Toast.makeText(RelationshipActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                        pos = position;
                        Relationships rel = relationship.get(position);
//                        d.getPk();
//                        d.getName();
//                        d.getContactName();
                        Intent intent = new Intent(RelationshipActivity.this,ActiveDealsActivity.class);
                        intent.putExtra("company_name",rel.getCompanyName());
                        intent.putExtra("pk",rel.getPk());
                        intent.putExtra("web",rel.getWeb());
                        startActivity(intent);
                    }
                })
        );
    }

    protected void getData() {
        String serverURL = serverUrl.url;
        client.get(serverURL+"api/clientRelationships/relationships/?format=json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject Obj = null;
                    try {
                        Obj = response.getJSONObject(i);
                        Relationships relationships = new Relationships(Obj);
                        relationship.add(relationships);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                RelationshipsAdapter relationshipsAdapter = new RelationshipsAdapter(RelationshipActivity.this,relationship);
                rv.setAdapter(relationshipsAdapter);
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