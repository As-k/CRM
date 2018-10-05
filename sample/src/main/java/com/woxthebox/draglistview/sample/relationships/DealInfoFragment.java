package com.woxthebox.draglistview.sample.relationships;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.ServerUrl;
import com.woxthebox.draglistview.sample.contacts.Company;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class DealInfoFragment extends Fragment {
    TextView companyName, address, web, cin, tin, mobile, telephone, about;
    ServerUrl serverUrl;
    private Company company;
    public static ArrayList info;
    public AsyncHttpClient client;
    public String pk;

    public DealInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pk = getArguments().getString("pk");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deal_info, container, false);
        companyName = view.findViewById(R.id.info_comapny_name);
        address = view.findViewById(R.id.info_address);
        web = view.findViewById(R.id.info_web);
        cin = view.findViewById(R.id.cin);
        tin = view.findViewById(R.id.tin);
        mobile = view.findViewById(R.id.info_mobile);
        telephone = view.findViewById(R.id.info_tele);
        about = view.findViewById(R.id.about);
        serverUrl = new ServerUrl(getContext());
        client = serverUrl.getHTTPClient();
        info = new ArrayList();
        getInfo();
        return view;
    }

    protected void getInfo() {
        client.get(ServerUrl.url+"api/ERP/service/"+pk+"/?format=json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                company = new Company(response);
                info.add(company);
                setData();
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

    private  void setData(){
        companyName.setText(company.getCompanyName());
        String address1 = company.getStreet()+" \n"+company.getCity()+" \n"+company.getState()+" \n"+company.getPincode()+" \n"+company.getCountry();
        address.setText(address1);
        web.setText(company.getWeb());
        cin.setText(company.getCin());
        tin.setText(company.getTin());
        mobile.setText(company.getCompanyMobile());
        telephone.setText(company.getTelephone());
        about.setText(company.getAbout());
    }

}
