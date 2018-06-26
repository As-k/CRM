package com.woxthebox.draglistview.sample.contacts;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.ServerUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {
    ImageView profileDp,searchLocImage, networkImg;
    TextView searchLocTv, infoCompany,infoNetworkName, infoNetworkDesg;
    public static List<ContactLite> contactLiteList;
    public AsyncHttpClient asyncHttpClient;
    ServerUrl serverUrl;
    ListView networkList;


    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        serverUrl = new ServerUrl(getContext());
        contactLiteList = new ArrayList<>();
        asyncHttpClient = serverUrl.getHTTPClient();
        View v = inflater.inflate(R.layout.fragment_info, container, false);

        profileDp = v.findViewById(R.id.info_image);
        searchLocImage = v.findViewById(R.id.info_iv_address);
        infoCompany = v.findViewById(R.id.info_company);
        searchLocTv = v.findViewById(R.id.info_tv_address);
        networkList = v.findViewById(R.id.network_list);

//        networkImg = v.findViewById(R.id.info_network_img);
//        infoNetwork = v.findViewById(R.id.info_network_name);

        asyncHttpClient.get(ViewDetailsActivity.dp, new FileAsyncHttpResponseHandler(getContext()) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                asyncHttpClient.get(ServerUrl.url+ "/static/images/img_avatar_card.png", new FileAsyncHttpResponseHandler(getContext()) {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, File file) {
                        Bitmap pp = BitmapFactory.decodeFile(file.getAbsolutePath());
                        profileDp.setImageBitmap(pp);
                    }
                });
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                Bitmap pp = BitmapFactory.decodeFile(file.getAbsolutePath());
                profileDp.setImageBitmap(pp);
            }
        });
        if (ViewDetailsActivity.company.equals("")) {
            searchLocImage.setVisibility(View.GONE);
            infoCompany.setVisibility(View.GONE);
            searchLocTv.setVisibility(View.GONE);
        } else {
            searchLocImage.setVisibility(View.VISIBLE);
            infoCompany.setVisibility(View.VISIBLE);
            searchLocTv.setVisibility(View.VISIBLE);
            infoCompany.setText("" + ViewDetailsActivity.company);
            String address = ViewDetailsActivity.street + " " + ViewDetailsActivity.city + " " + ViewDetailsActivity.state + " " + ViewDetailsActivity.pincode + " " + ViewDetailsActivity.country;
            searchLocTv.setText(address);
        }
        clickMethods();
        if (ViewDetailsActivity.companyPk != null) {getNetworkUser();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                NetWorkListAdapter workListAdapter = new NetWorkListAdapter();
                networkList.setAdapter(workListAdapter);
            }
        },1000);

        networkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactLite c = contactLiteList.get(position);
                String matchPk = c.getPk();
                asyncHttpClient.get(ServerUrl.url+"/api/clientRelationships/contact/"+matchPk+"/", new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Contact c  = new Contact(response);
                        Intent intent = new Intent(getContext(), ViewDetailsActivity.class);
                        intent.putExtra("pk",c.getPk());
                        intent.putExtra("image",c.getDp());
                        intent.putExtra("name", c.getName());
                        intent.putExtra("email",c.getEmail());
                        intent.putExtra("mob",c.getMobile());
                        intent.putExtra("designation",c.getDesignation());
                        intent.putExtra("companyPk",c.getCompanyPk());
                        intent.putExtra("company", c.getCompanyName());
                        intent.putExtra("gender",c.getMale());
                        intent.putExtra("cin",c.getCin());
                        intent.putExtra("tin",c.getTin());
                        intent.putExtra("companyNo", c.getCompanyMobile());
                        intent.putExtra("tel",c.getTelephone());
                        intent.putExtra("about",c.getAbout());
                        intent.putExtra("web",c.getWeb());
                        intent.putExtra("street",c.getStreet());
                        intent.putExtra("city",c.getCity());
                        intent.putExtra("state",c.getState());
                        intent.putExtra("pincode",c.getPincode());
                        intent.putExtra("country",c.getCountry());
                        startActivity(intent);
                        getActivity().finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });
            }
        });

        return v;
    }

    public void clickMethods(){

        final String loc = searchLocTv.getText().toString().trim();

        searchLocImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+loc);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        searchLocTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+loc);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }
    protected void getNetworkUser(){
        String serverURL = serverUrl.url;
        asyncHttpClient.get(serverURL+"api/clientRelationships/contactLite/?company="+ViewDetailsActivity.companyPk, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject obj = null;
                    try {
                        obj = response.getJSONObject(i);
                        String removePk = obj.getString("pk");
                        if (!removePk.equals(ViewDetailsActivity.cpk)) {
                            ContactLite contactLite = new ContactLite(obj);
                            contactLiteList.add(contactLite);
                        }
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

    public class NetWorkListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return contactLiteList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = getLayoutInflater().inflate(R.layout.company_network_layout, parent, false);
            networkImg = v.findViewById(R.id.info_network_img);
            infoNetworkName = v.findViewById(R.id.info_network_name);
            infoNetworkDesg = v.findViewById(R.id.info_network_designation);

            if (contactLiteList.size()!=0) {
                ContactLite con = contactLiteList.get(position);
                asyncHttpClient.get(con.getDp(), new FileAsyncHttpResponseHandler(getContext()) {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                        asyncHttpClient.get(ServerUrl.url + "/static/images/img_avatar_card.png", new FileAsyncHttpResponseHandler(getContext()) {
                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {

                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, File file) {
                                Bitmap pp = BitmapFactory.decodeFile(file.getAbsolutePath());
                                networkImg.setImageBitmap(pp);
                            }
                        });
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, File file) {
                        Bitmap pp = BitmapFactory.decodeFile(file.getAbsolutePath());
                        networkImg.setImageBitmap(pp);
                    }
                });
                infoNetworkName.setText(con.getName());
                infoNetworkDesg.setText(con.getDesignation());
            }
            return v;
        }
    }


}
