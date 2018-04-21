package com.woxthebox.draglistview.sample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class RelationshipsAdapter extends RecyclerView.Adapter<RelationshipsAdapter.MyHolder> {


    public static String name, street, city, state, pincode, country, logo, mobile, web, address;

//    public static int account_images[] = {R.drawable.ic_account, R.drawable.ic_account, R.drawable.ic_account, R.drawable.ic_account,
//            R.drawable.ic_account, R.drawable.ic_account};
//    public static String account_names[] = {"Account", "Account", "Account", "Account", "Account"};
//    public static String company_names[] = {"CIOC FMCG Pvt Ltd", "ABC pvt Ltd", "XYZ solution", "Apple Corp.", "Google"};

    Context context;
    List<Relationships> relationshipsList;

    public RelationshipsAdapter(Context context, List<Relationships> relationshipList) {
        this.context = context;
        this.relationshipsList = relationshipList;
    }

    @NonNull
    @Override
    public RelationshipsAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.relationship_style, parent, false);
        RelationshipsAdapter.MyHolder myHolder = new RelationshipsAdapter.MyHolder(v);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RelationshipsAdapter.MyHolder holder, int position) {
        if (holder instanceof MyHolder) {
            MyHolder myHolder =(MyHolder) holder;
            Relationships d = relationshipsList.get(position);

            myHolder.companyname.setText(d.getCompanyName());
            myHolder.accountdeal.setText("Active Deals \n"+1);

        }
    }
    @Override
    public int getItemCount() {
        return RelationshipActivity.relationship.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView accountimage, viewDetails;
        TextView accountdeal, companyname;

        public MyHolder(View itemView) {
            super(itemView);
//            accountimage = itemView.findViewById(R.id.Account_photo);
            companyname = itemView.findViewById(R.id.company_name);
            accountdeal = itemView.findViewById(R.id.active_deal);
        }

    }
}