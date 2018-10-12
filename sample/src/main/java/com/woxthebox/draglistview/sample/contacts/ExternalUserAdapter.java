package com.woxthebox.draglistview.sample.contacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.woxthebox.draglistview.sample.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExternalUserAdapter extends RecyclerView.Adapter<ExternalUserAdapter.ViewHolder> {
    Context context;
    private LayoutInflater inflater;
    private ArrayList contactsExternal;


    public ExternalUserAdapter(Context context, ArrayList contactsExternals){
        this.context = context;
        this.contactsExternal = contactsExternals;

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
        JSONObject jsonObject=null;

            try {
                jsonObject = (JSONObject) contactsExternal.get(position);
                String contactsName = jsonObject.getString("name");
                String contactsDesignation = jsonObject.getString("designation");
                String contactsImage=jsonObject.getString("male");
                if(contactsImage.equals("true")){
                    holder.profilePic.setBackgroundResource(R.drawable.male_circlesmall);
                }
                else
                {
                   holder.profilePic.setBackgroundResource(R.drawable.femalesmall);
                }
                if (contactsName != null) {
                    holder.empName.setText(contactsName);
                    holder.empDesign.setText(contactsDesignation);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }




    @Override
    public int getItemCount() {
        return contactsExternal.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        CardView cardExternal;
        TextView empName,empDesign;
        ImageView profilePic;

        public ViewHolder(View itemView) {

            super(itemView);
            cardExternal=itemView.findViewById(R.id.card1);
            empName=itemView.findViewById(R.id.emp_name);
            empDesign=itemView.findViewById(R.id.emp_design);
            profilePic=itemView.findViewById(R.id.user_image);


        }
    }
}
