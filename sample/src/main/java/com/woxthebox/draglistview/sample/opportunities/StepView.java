package com.woxthebox.draglistview.sample.opportunities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.ServerUrl;
import com.woxthebox.draglistview.sample.relationships.AddQuoteActivity;
import com.woxthebox.draglistview.sample.relationships.Contract;
import com.woxthebox.draglistview.sample.relationships.Deal;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by amit on 28/4/18.
 */

public class StepView extends FragmentActivity {
    TabLayout tabLayout;
    StepperIndicator indicator;
    TextView companyName, dealName, valuation, closingDate, userName, requirementName,
            markComplete, wonButton, lostButton, editOpportunity, quoteAdd;
    LinearLayout wonLostBtn;
    public AsyncHttpClient client;
    ServerUrl serverUrl;
    List<Deal> dealList;
    private OppViewPagerAdapter oppViewPagerAdapter;
    private ViewPager viewPager;
    public static Deal deal;

    String listName[] = {"Contacting","Demo / POC","Requirements","Proposal","Negotiation","Won / Lost"};
    String stateParams[] = {"contacted","demo","requirements","proposal","negotiation","conclusion"};
    String dealPk;
    int index = 0;


    @Override
        protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_view);

        serverUrl = new ServerUrl(this);
        client = serverUrl.getHTTPClient();
        dealList = new ArrayList<>();
        dealPk = getIntent().getExtras().getString("dealPk");
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDeal();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Deal deal = dealList.get(0);
                oppViewPagerAdapter = new OppViewPagerAdapter(getSupportFragmentManager());
                viewPager.setAdapter(oppViewPagerAdapter);
                tabLayout.setupWithViewPager(viewPager);
                viewPager.setOffscreenPageLimit(3);
                tabsViewer();
                companyName.setText(deal.getCompanyName());
                dealName.setText(deal.getName());
                valuation.setText(deal.getCurrency()+" "+deal.getValue());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                Date date1 = null;
                String dateStr = null;
                try {
                    date1 = simpleDateFormat.parse(deal.getCloseDate());
                    dateStr = dateFormat.format(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.d("ParseException",""+e);
                }
                closingDate.setText(dateStr);

                String state = deal.getState();
                for (int i=0; i<listName.length; i++){
                    if (state.equals(listName[i].toLowerCase())){
                        requirementName.setText(listName[i]);
                        index = i;
                    }
                    if ((i==0) && state.equals("contacted")){
                        requirementName.setText(listName[i]);
                        index  = i;
                    }
                    if ((i==1) && state.equals("demo")){
                        requirementName.setText(listName[i]);
                        index  = i;
                    }
                    if ((i==5) && state.equals("conclusion")){
                        requirementName.setText(listName[i]);
                        index  = i;
                        markComplete.setClickable(false);
                        markComplete.setEnabled(false);
                        markComplete.setVisibility(View.GONE);
                        wonLostBtn.setVisibility(View.VISIBLE);
                    }
                }

                indicator.setCurrentStep(index++);

                markComplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requirementName.setText(listName[index]);
                        RequestParams params = new RequestParams();
                        params.put("state", stateParams[index]);
                        client.patch(ServerUrl.url + "/api/clientRelationships/deal/" + dealPk +"/", params, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                Toast.makeText(StepView.this, "patched", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                Toast.makeText(StepView.this, "patched failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                        indicator.setCurrentStep(index++);
                        if (index == 6) {
                            markComplete.setClickable(false);
                            markComplete.setEnabled(false);
                            markComplete.setVisibility(View.GONE);
                            wonLostBtn.setVisibility(View.VISIBLE);
                        }
                    }
                });

                wonButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestParams params = new RequestParams();
                        params.put("result", "won");
                        client.patch(ServerUrl.url + "/api/clientRelationships/deal/" + dealPk +"/", params, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                Toast.makeText(StepView.this, "won patched", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                Toast.makeText(StepView.this, "patched failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                        wonButton.setEnabled(false);
                        wonButton.setClickable(false);
                        wonButton.setBackground(getDrawable(R.drawable.button_shape_green));
                        lostButton.setVisibility(View.GONE);
                    }
                });

                lostButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestParams params = new RequestParams();
                        params.put("result", "lost");
                        client.patch(ServerUrl.url + "/api/clientRelationships/deal/" + dealPk +"/", params, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                Toast.makeText(StepView.this, "lost patched", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                Toast.makeText(StepView.this, "patched failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                        lostButton.setEnabled(false);
                        lostButton.setClickable(false);
                        lostButton.setText("X Lost");
                        wonButton.setVisibility(View.GONE);
                    }
                });

                quoteAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (deal.getContracts().size() != 0){
                            for (int i = 0; i < deal.getContracts().size(); i++) {
                                client.get(ServerUrl.url + "/api/clientRelationships/contract/" + deal.getContracts().get(i)+"/", new JsonHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
                                        Contract contract = new Contract(response);
                                        Intent intent = new Intent(getApplicationContext(), AddQuoteActivity.class);
                                        intent.putExtra("data", contract.getData());
                                        intent.putExtra("value", Integer.parseInt(contract.getValue()));
                                        intent.putExtra("edit", true);
                                        intent.putExtra("download", true);
                                        intent.putExtra("pk", contract.getPk());
                                        startActivity(intent);
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
                        } else {
                            Intent intent = new Intent(getApplicationContext(), AddQuoteActivity.class);
                            intent.putExtra("download", true);
                            startActivity(intent);
                        }

                    }
                });

            }
        },500);
    }

    public void init(){
        companyName = findViewById(R.id.opp_company_name);
        dealName = findViewById(R.id.opp_deal_name);
        valuation = findViewById(R.id.opp_valuation_money);
        closingDate = findViewById(R.id.opp_closing_date);
        userName = findViewById(R.id.user_name);
        requirementName = findViewById(R.id.requirements_name);
        markComplete = findViewById(R.id.mark_complete);
        wonLostBtn = findViewById(R.id.won_lost_btn);
        wonLostBtn.setVisibility(View.GONE);
        wonButton = findViewById(R.id.won_btn);
        lostButton = findViewById(R.id.lost_btn);
        editOpportunity = findViewById(R.id.edit_oppo);
        quoteAdd = findViewById(R.id.quote_oppo);
        indicator = findViewById(R.id.stepper_indicator);
        viewPager = findViewById(R.id.opp_viewpager);
        tabLayout = findViewById(R.id.opp_deal_view);

    }


    protected void getDeal() {
        dealList.clear();
        client.get(ServerUrl.url+"/api/clientRelationships/deal/"+dealPk+"/", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                deal = new Deal(response);
                dealList.add(deal);

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

    private void tabsViewer() {
        LinearLayout tabOne = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.opp_deal_tab, null);
        ImageView iv_tab1 = tabOne.findViewById(R.id.opp_tab);
        iv_tab1.setImageResource(R.drawable.ic_stakeholder);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        LinearLayout tabTwo = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.opp_deal_tab, null);
        ImageView iv_tab2 = tabTwo.findViewById(R.id.opp_tab);
        iv_tab2.setImageResource(R.drawable.ic_timeline);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        LinearLayout tabThree = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.opp_deal_tab, null);
        ImageView iv_tab3 = tabThree.findViewById(R.id.opp_tab);
        iv_tab3.setImageResource(R.drawable.ic_event);
        tabLayout.getTabAt(2).setCustomView(tabThree);

    }

}



