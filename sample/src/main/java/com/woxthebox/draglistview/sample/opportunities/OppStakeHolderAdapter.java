package com.woxthebox.draglistview.sample.opportunities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.ServerUrl;
import com.woxthebox.draglistview.sample.contacts.Contact;
import com.woxthebox.draglistview.sample.contacts.ViewDetailsActivity;
import com.woxthebox.draglistview.sample.relationships.Deal;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by amit on 30/4/18.
 */


public class OppStakeHolderAdapter extends RecyclerView.Adapter<OppStakeHolderAdapter.MyHolder> {
    private Deal d;
    Context context;
    AsyncHttpClient asyncHttpClient;
    ServerUrl serverUrl;

    public OppStakeHolderAdapter(Context context){
        this.context = context;

    }

    @NonNull
    @Override
    public OppStakeHolderAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        serverUrl = new ServerUrl(context);
        asyncHttpClient = serverUrl.getHTTPClient();
        View v = layoutInflater.inflate(R.layout.opp_stakeholder_adapter, parent, false);
        OppStakeHolderAdapter.MyHolder myHolder = new OppStakeHolderAdapter.MyHolder(v);

        return myHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull OppStakeHolderAdapter.MyHolder holder, int position) {
        final Contact contact = StepView.deal.getContactsList().get(position);
        holder.holderName.setText(contact.getName());
        holder.holderDesignation.setText(contact.getDesignation());
//        holder.imageView.setImageResource(uImage[position]);
        holder.stakeholderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncHttpClient.get(ServerUrl.url+"/api/clientRelationships/contact/"+contact.getPk()+"/", new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Contact c  = new Contact(response);
                        Intent intent = new Intent(context, ViewDetailsActivity.class);
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
                        context.startActivity(intent);
                        new StepView().finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });
            }
        });

        }

    @Override
    public int getItemCount() {
        return StepView.deal.getContactsList().size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        RelativeLayout stakeholderLayout;
        TextView holderName,holderDesignation;
        ImageView imageView;
        public MyHolder(View itemView) {
            super(itemView);
            stakeholderLayout = itemView.findViewById(R.id.opp_stakeholder_card);
            holderName = itemView.findViewById(R.id.opp_stakeholder_name);
            holderDesignation = itemView.findViewById(R.id.opp_stakeholder_designation);
            imageView = itemView.findViewById(R.id.opp_stakeholder_image);

        }
    }


}
