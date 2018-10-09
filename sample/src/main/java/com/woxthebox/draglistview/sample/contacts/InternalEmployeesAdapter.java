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

public class InternalEmployeesAdapter extends RecyclerView.Adapter<InternalEmployeesAdapter.ViewHolder> {
    Context context;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;
    public InternalEmployeesAdapter(Context context,List<FeedItem> feedItems){
        this.context=context;
        this.feedItems=feedItems;
    }
    @NonNull
    @Override
    public InternalEmployeesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.internal_employees, parent, false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InternalEmployeesAdapter.ViewHolder holder, int position) {
        FeedItem item=feedItems.get(position);
        holder.empIdInt.setText(item.getPk());


    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView internalCardView;
        TextView empNameInt,empIdInt;
        public ViewHolder(View itemView) {
            super(itemView);
            internalCardView=itemView.findViewById(R.id.card2);
            empNameInt=itemView.findViewById(R.id.emp_name2);
            empIdInt=itemView.findViewById(R.id.ID2);


        }
    }
}
