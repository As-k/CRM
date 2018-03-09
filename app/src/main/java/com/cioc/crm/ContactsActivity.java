package com.cioc.crm;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ContactsActivity extends FragmentActivity {

    FloatingActionButton newFAB;
    RecyclerView browse_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);


        browse_rv = findViewById(R.id.browse_recyclerView);
        browse_rv.setLayoutManager(new LinearLayoutManager(this));

        BrowseAdapter browseAdapter = new BrowseAdapter(this);
        browse_rv.setAdapter(browseAdapter);
    }
}
