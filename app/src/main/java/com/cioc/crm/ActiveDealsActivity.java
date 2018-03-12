package com.cioc.crm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

/**
 * Created by amit on 10/3/18.
 */

public class ActiveDealsActivity extends AppCompatActivity {
    RecyclerView rv1;
    TextView companyname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_deals);
        companyname = findViewById(R.id.comapny_name);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String company = bundle.getString("company_name");
            companyname.setText(company);
        }


        rv1 = findViewById(R.id.activedearl_recyclerView);
        rv1.setLayoutManager(new LinearLayoutManager(this));

        ActiveDealsAdapter activeDealsAdapter= new ActiveDealsAdapter(this);
        rv1.setAdapter(activeDealsAdapter);

    }
}