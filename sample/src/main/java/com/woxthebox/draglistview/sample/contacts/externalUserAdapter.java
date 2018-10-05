package com.woxthebox.draglistview.sample.contacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.woxthebox.draglistview.sample.R;

import java.util.List;

import static android.view.View.GONE;

public class externalUserAdapter extends RecyclerView.Adapter<externalUserAdapter.ViewHolder> {
    Context context;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;
    public externalUserAdapter(Context context, List<FeedItem> feedItems){
        this.context=context;
        this.feedItems=feedItems;

    }
    @NonNull
    @Override
    public externalUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.external_stakeholder, parent, false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull externalUserAdapter.ViewHolder holder, int position) {
        FeedItem item=feedItems.get(position);
        holder.empName.setText(item.getContactsName());
        holder.empID.setText(item.getInternalUsers());
        if(holder.empName.getText().equals("") && holder.empDesign.getText().equals(""))
        {
            holder.cardExternal.setVisibility(GONE);
            holder.external.setVisibility(GONE);

        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {

        CardView cardExternal;
       TextView empName,empDesign,empID,external;

        public ViewHolder(View itemView) {

            super(itemView);
            cardExternal=itemView.findViewById(R.id.card1);
            empName=itemView.findViewById(R.id.emp_name);
            empDesign=itemView.findViewById(R.id.emp_design);
            external=itemView.findViewById(R.id.txt_external);
//            empname1=itemView.findViewById(R.id.emp_name2);
//            empdesign1=itemView.findViewById(R.id.emp_design2);
            empID=itemView.findViewById(R.id.ID);
//            empID1=itemView.findViewById(R.id.ID2);
        }
    }
}
