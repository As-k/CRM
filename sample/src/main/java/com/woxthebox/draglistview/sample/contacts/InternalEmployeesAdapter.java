package com.woxthebox.draglistview.sample.contacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.woxthebox.draglistview.sample.R;

import java.util.List;

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

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
