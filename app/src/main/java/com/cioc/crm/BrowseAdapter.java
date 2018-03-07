package com.cioc.crm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ashish on 3/7/2018.
 */

public class BrowseAdapter extends RecyclerView.Adapter<BrowseAdapter.MyHolder> {
    Context context;

    public BrowseAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.browse_contacts_style, parent, false);
        BrowseAdapter.MyHolder myHolder = new BrowseAdapter.MyHolder(v);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView browse_image;
        TextView browse_name,browse_designation, browse_company, browse_phone, browse_email;

        public MyHolder(View itemView) {
            super(itemView);
            browse_image = itemView.findViewById(R.id.contacts_image_browse);
            browse_name = itemView.findViewById(R.id.contacts_name_browse);
            browse_designation = itemView.findViewById(R.id.contacts_designation_browse);
            browse_company = itemView.findViewById(R.id.contacts_company_browse);
            browse_phone = itemView.findViewById(R.id.contacts_no_browse);
            browse_email = itemView.findViewById(R.id.contacts_email_browse);
        }
    }
}
