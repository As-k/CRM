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

public class ExternalUserAdapter extends RecyclerView.Adapter<ExternalUserAdapter.ViewHolder> {
    Context context;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;

    public ExternalUserAdapter(Context context, List<FeedItem> feedItems){
        this.context=context;
        this.feedItems=feedItems;

    }
    @NonNull
    @Override
    public ExternalUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.external_stakeholder, parent, false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExternalUserAdapter.ViewHolder holder, int position) {
        FeedItem item=feedItems.get(position);
        if(item.getcontactsName()!=null) {
            holder.empName.setText(item.getcontactsName());
            holder.empDesign.setText(item.getDesignationContacts());
        }

    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        CardView cardExternal;
        TextView empName,empDesign;
        public ViewHolder(View itemView) {

            super(itemView);
            cardExternal=itemView.findViewById(R.id.card1);
            empName=itemView.findViewById(R.id.emp_name);
            empDesign=itemView.findViewById(R.id.emp_design);


        }
    }
}
