package com.woxthebox.draglistview.sample;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

/**
 * Created by amit on 14/3/18.
 */

public class ActiveDealsDetailsActivity extends FragmentActivity {
    public int[] tabicon = {R.drawable.ic_info,R.drawable.ic_stakeholder,R.drawable.ic_finances,R.drawable.ic_requirements};
    TabLayout tabLayout;
    TextView Dealname, Valuation, ClosingDate;
    ImageView imageView;
    Deal d;
    private String pk;
    private ActiveDealsViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;


    private static final String TAG = ActiveDealsDetailsActivity.class.toString();

    public static String name, value, closedate, web,cin,tin,about,telephone,mobile;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_deals_details);


        final Bundle b = getIntent().getExtras();
        if (b != null) {
            name = b.getString("name");
            value = b.getString("value");
            closedate = b.getString("closeDate");
            pk = b.getString("pk");

//         /*   web = b.getString("web");
//            cin = b.getString("cin");
//            tin = b.getString("tin");
//            about = b.getString("about");
//            telephone = b.getString("telephone");
//            mobile = b.getString("mobile");*/
            d = new Deal();
            d.name = name;
            d.value = value;
            d.closeDate = closedate;
        }

        Dealname = findViewById(R.id.deal_name);
        Valuation = findViewById(R.id.valuation_money);
        ClosingDate = findViewById(R.id.closing_date);

        Dealname.setText(d.getName());
        Valuation.setText(d.getValue());
        ClosingDate.setText(d.getCloseDate());

        viewPager = findViewById(R.id.deal_viewpager);

        tabLayout = findViewById(R.id.deal_view);
        viewPagerAdapter = new ActiveDealsViewPagerAdapter(getSupportFragmentManager(), pk);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);

        createTabs();

        /*tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {




            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setIcon(tabicon[tab.getPosition()]);
                int pos = tab.getPosition();


                switch (pos) {
                    case 0: {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                        Bundle bundle = new Bundle();
                        bundle.putString("pk", pk);
                        DealInfoFragment dealInfoFragment = new DealInfoFragment();
                        dealInfoFragment.setArguments(bundle);
                        ft.add(R.id.dealinfo_fragment, dealInfoFragment, "DealInfoFragment");
                        ft.commit();
                        break;
                    }
                    case 1: {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.add(R.id.dealinfo_fragment, new ExternalStakeholderFragment(), "ExternalStackHolderFragment");
                        ft.commit();
                        break;
                    }
                    case 2: {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.add(R.id.dealinfo_fragment, new FinancesFragment(), "FinancesFrangment");
                        ft.commit();
                        break;
                    }
                    case 3 : {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.add(R.id.dealinfo_fragment, new RequirementFragment(), "RequirementFrangment");
                        ft.commit();
                        break;
                    }

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/

    }


    private void createTabs() {

        LinearLayout tabOne = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.active_deal_tab, null);
        ImageView iv_tab1 = tabOne.findViewById(R.id.iv_tab);
        iv_tab1.setImageResource(R.drawable.ic_info);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        LinearLayout tabTwo = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.active_deal_tab, null);
        ImageView iv_tab2 = tabTwo.findViewById(R.id.iv_tab);
        iv_tab2.setImageResource(R.drawable.ic_stakeholder);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        LinearLayout tabThree = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.active_deal_tab, null);
        ImageView iv_tab3 = tabThree.findViewById(R.id.iv_tab);
        iv_tab3.setImageResource(R.drawable.ic_finances);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        LinearLayout tabFour = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.active_deal_tab, null);
        ImageView iv_tab4 = tabFour.findViewById(R.id.iv_tab);
        iv_tab4.setImageResource(R.drawable.ic_requirements);
        tabLayout.getTabAt(3).setCustomView(tabFour);
    }

}