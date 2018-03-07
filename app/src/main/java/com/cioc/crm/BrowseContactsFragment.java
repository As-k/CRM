package com.cioc.crm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseContactsFragment extends Fragment {

    RecyclerView browse_rv;

    public BrowseContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_browse_contacts, container, false);

        browse_rv = v.findViewById(R.id.browse_recyclerView);
        browse_rv.setLayoutManager(new LinearLayoutManager(getContext()));

        BrowseAdapter browseAdapter = new BrowseAdapter(getContext());
        browse_rv.setAdapter(browseAdapter);

        return v;
    }

}
