package com.cioc.crm;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by amit on 29/3/18.
 */

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    String name,cName,street,city,state,pincode,country,email,mobile,designation,company;

    public ContactAdapter (Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_browse_adapter,parent,false);

        ContactAdapter.MyHolder myHolder = new ContactAdapter.MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BrowseAdapter.MyHolder myHolder = (BrowseAdapter.MyHolder) holder;
        HashMap hm = (HashMap) ContactsActivity.contactList.get(position);
        name  = (String) hm.get("name");
        company = (String) hm.get("company");
//         street  = (String) hm.get("street");
//         city = (String) hm.get("city");
//         state = (String) hm.get("state");
//         pincode = (String) hm.get("pincode");
//         country = (String) hm.get("country");
//         telephone = (String) hm.get("telephone");
        email = (String) hm.get("email");
        mobile = (String) hm.get("mobile");
        designation = (String) hm.get("designation");

//        holder.browseImage.setImageResource(contact_images[position]);
        myHolder.browseName.setText(name);
        myHolder.browseDesignation.setText(designation);
        myHolder.browseCompany.setText(company);
        myHolder.browseMob.setText(mobile);
        myHolder.browseEmail.setText(email);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView browseImage, editProfile, viewDetails;
        TextView browseName,browseDesignation, browseCompany, browseMob, browseEmail;
//        ProgressBar  progressBar  = (ProgressBar) itemView.findViewById(R.id.progressBar1);

        public MyHolder(View itemView) {
            super(itemView);
            browseImage = itemView.findViewById(R.id.contacts_image_browse);
            browseName = itemView.findViewById(R.id.contacts_name_browse);
            browseDesignation = itemView.findViewById(R.id.contacts_designation_browse);
            browseCompany = itemView.findViewById(R.id.contacts_company_browse);
            browseMob = itemView.findViewById(R.id.contacts_no_browse);
            editProfile = itemView.findViewById(R.id.edit_profile);
            browseEmail = itemView.findViewById(R.id.contacts_email_browse);
            viewDetails = itemView.findViewById(R.id.view_details);

            editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditContactActivity.class);
//                    intent.putExtra("image", contact_images[getLayoutPosition()]);
                    intent.putExtra("name", name);
                    intent.putExtra("designation", designation);
                    intent.putExtra("company", company);
                    intent.putExtra("cno", mobile);
                    intent.putExtra("email", email);
                    context.startActivity(intent);
                }
            });

            viewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, ""+getLayoutPosition(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ViewDetailsActivity.class);
//                    intent.putExtra("image", contact_images[getLayoutPosition()]);
                    intent.putExtra("name", name);
                    intent.putExtra("designation", designation);
                    intent.putExtra("company", company);
                    intent.putExtra("cno", mobile);
                    intent.putExtra("email", email);
                    context.startActivity(intent);
                }
            });
        }
    }
}
