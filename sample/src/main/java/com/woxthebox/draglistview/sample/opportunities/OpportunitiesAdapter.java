package com.woxthebox.draglistview.sample.opportunities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.woxthebox.draglistview.sample.R;

import java.util.List;

/**
 * Created by admin on 04/07/18.
 */

public class OpportunitiesAdapter extends RecyclerView.Adapter<OpportunitiesAdapter.MyHolder> {

    Context context;
    List<Opportunities> opportunitiesList;

    public OpportunitiesAdapter(Context context, List<Opportunities> opportunitiesList){
        this.context = context;
        this.opportunitiesList = opportunitiesList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.column_item, parent, false);
        MyHolder myHolder = new MyHolder(v);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        final Opportunities opp = opportunitiesList.get(position);
        holder.contactName.setText(opp.getContactName());
        holder.dealName.setText(opp.getName());
        holder.companyName.setText(opp.getCompanyName());
        if (opp.getState().equals("contacted")){
            holder.oppCard.setCardBackgroundColor(Color.rgb(22,160,133));
        } else
        if (opp.getState().equals("demo")){
            holder.oppCard.setCardBackgroundColor(Color.rgb(195,118,18));
        } else
        if (opp.getState().equals("requirements")){
            holder.oppCard.setCardBackgroundColor(Color.rgb(255,180,36));
        } else
        if (opp.getState().equals("proposal")){
            holder.oppCard.setCardBackgroundColor(Color.rgb(41,128,185));
        } else
        if (opp.getState().equals("negotiation")){
            holder.oppCard.setCardBackgroundColor(Color.rgb(39,174,96));
        } else
        if (opp.getState().equals("conclusion")){
            holder.oppCard.setCardBackgroundColor(Color.rgb(121,95,153));
        }

        holder.oppCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,StepView.class);
                intent.putExtra("dealPk", opp.getPk());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return opportunitiesList.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder{
        CardView oppCard;
        TextView contactName, companyName,dealName;

        public MyHolder(View itemView) {
            super(itemView);
            oppCard = itemView.findViewById(R.id.opp_card);
            contactName = itemView.findViewById(R.id.opp_name);
            companyName = itemView.findViewById(R.id.opp_card_company);
            dealName = itemView.findViewById(R.id.opp_card_deal);
        }

    }
}
