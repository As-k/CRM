package com.woxthebox.draglistview.sample.opportunities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.woxthebox.draglistview.sample.MainActivity;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.ServerUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.woxthebox.draglistview.sample.relationships.ActiveDealsActivity.dealPk;

/**Created by Utkarsh on 13/09/18*/

public class MainActivityOpp extends AppCompatActivity {
    private SimpleBoardAdapter boardAdapter;
  public static ArrayList<Item> list;
  public  ArrayList<Item> list2;
    public  ArrayList<Item> list3 ;
    public  ArrayList<Item> list4 ;
    public  ArrayList<Item> list5;
    public  ArrayList<Item> list6 ;




    public static ArrayList<SimpleBoardAdapter.SimpleColumn> data;
    public String pos="def";
   // JSONObject op;
   public static Item opp1;

    public static AsyncHttpClient client;
   static  ServerUrl serverUrl;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainopp);
        serverUrl = new ServerUrl(this);
        client = serverUrl.getHTTPClient();
        list = new ArrayList<>();
        list2=new ArrayList<>();
        list3=new ArrayList<>();
        list4=new ArrayList<>();
        list5=new ArrayList<>();
        list6=new ArrayList<>();


        getSupportActionBar().hide();
        final BoardView boardView = findViewById(R.id.boardView);
        boardView.SetColumnSnap(false);
        boardView.SetColumnSnap(true);

        data = new ArrayList<>();
//         list.add(new Item("Item 1"));
       getOpp();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                data.add(new SimpleBoardAdapter.SimpleColumn("Contacting", (ArrayList) list));
                data.add(new SimpleBoardAdapter.SimpleColumn("Demo/POC", (ArrayList)list2));
                data.add(new SimpleBoardAdapter.SimpleColumn("Requirements", (ArrayList)list3));

                data.add(new SimpleBoardAdapter.SimpleColumn("Proposal", (ArrayList) list4));

                data.add(new SimpleBoardAdapter.SimpleColumn("Negotiation", (ArrayList) list5));

                data.add(new SimpleBoardAdapter.SimpleColumn("Conclusion", (ArrayList) list6));
                boardAdapter = new SimpleBoardAdapter(MainActivityOpp.this, data, list);
                boardView.setAdapter(boardAdapter);
                boardView.setOnDoneListener(new BoardView.DoneListener() {
                    @Override
                    public void onDone() {
                        Log.e("scroll", "done");
                    }
                });
            }
        }, 1000);


        FloatingActionButton newCreateOppo = findViewById(R.id.create_new_oppo);
        newCreateOppo.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                startActivity(new Intent(getApplicationContext(), NewOpportunityActivity.class));
            }
        });

    }

   static void getOpp() {

        client.get(ServerUrl.url + "api/clientRelationships/deal/?&created=false&board&format=json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                   JSONObject Obj = null;

                    try {

                      Obj = response.getJSONObject(i);
                        opp1 = new Item(Obj);
                        list.add(opp1);

                        } catch (JSONException e) {
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


