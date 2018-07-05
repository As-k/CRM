package com.woxthebox.draglistview.sample.opportunities;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;
import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.ServerUrl;
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
    TextView companyName, dealName, valuation, closingDate, userName, requirementName, markComplete;
    public AsyncHttpClient client;
    ServerUrl serverUrl;
    List<Deal> dealList;
    private OppViewPagerAdapter oppViewPagerAdapter;
    private ViewPager viewPager;
    public static Deal deal;

    String listName[] = {"Contacting","Demo / POC","Requirements","Proposal","Negotiation","Conclusion"};
    String dealPk;



    @Override
        protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_view);

        serverUrl = new ServerUrl(this);
        client = serverUrl.getHTTPClient();
        dealList = new ArrayList<>();
        dealPk = getIntent().getExtras().getString("dealPk");
        init();
        getDeal();


    }


    int index = 0;
    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Deal deal = dealList.get(0);
                oppViewPagerAdapter = new OppViewPagerAdapter(getSupportFragmentManager());
                viewPager.setAdapter(oppViewPagerAdapter);
                tabLayout.setupWithViewPager(viewPager);
                viewPager.setOffscreenPageLimit(3);
                tabsViewer();

                String state = deal.getState();
                for (int i=0; i<listName.length; i++){
                    if (state.equals(listName[i].toLowerCase())){
                        index = i;
                        requirementName.setText(listName[i]);
                    } else if (state.equals("demo")){
                        index  = i;
                        requirementName.setText(listName[i]);
                    }
                }


                indicator.setCurrentStep(index++);

                if (index<6) {
                    markComplete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requirementName.setText(listName[index]);
                            indicator.setCurrentStep(index++);
                        }
                    });
                } else {
                    markComplete.setClickable(false);
                }

                companyName.setText(deal.getCompanyName());
                dealName.setText(deal.getName());
                valuation.setText(deal.getValue());
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
        indicator = findViewById(R.id.stepper_indicator);
        viewPager = findViewById(R.id.opp_viewpager);
        tabLayout = findViewById(R.id.opp_deal_view);
    }


    protected void getDeal() {
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



