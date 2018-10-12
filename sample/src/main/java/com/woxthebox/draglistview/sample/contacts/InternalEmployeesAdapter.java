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
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.ServerUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.media.CamcorderProfile.get;
import static android.view.View.GONE;

public class InternalEmployeesAdapter extends RecyclerView.Adapter<InternalEmployeesAdapter.ViewHolder> {
    Context context;
    private LayoutInflater inflater;
    private ArrayList contactsInternal;

    public InternalEmployeesAdapter(Context context, ArrayList feedItems) {
        this.context = context;
        this.contactsInternal = feedItems;
    }

    @NonNull
    @Override
    public InternalEmployeesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.internal_employees, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final InternalEmployeesAdapter.ViewHolder holder, int position) {
        int internalPk = (int) contactsInternal.get(position);
//        getInternalEmpsValue(internalPk, holder);
        ServerUrl url = new ServerUrl(context);
        AsyncHttpClient client = url.getHTTPClient();
        client.get(ServerUrl.url + "api/HR/userSearch/" + internalPk + "/", new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                super.onSuccess(statusCode, headers, json);
                String firstName = null;
                try {
                    firstName = json.getString("first_name");
                    String lastName = json.getString("last_name");
                    String pk = json.getString("pk");
                    holder.firstName.setText(firstName + " " + lastName);
                    holder.idValue.setText(pk);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(context, "Failed to get user", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return contactsInternal.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView internalCardView;
        TextView firstName, lastName, empIdInt, idValue;
       ImageView profilePic;

        public ViewHolder(View itemView) {
            super(itemView);
            internalCardView = itemView.findViewById(R.id.card2);
            firstName = itemView.findViewById(R.id.firstName);
            //   empIdInt = itemView.findViewById(R.id.ID2);
            idValue = itemView.findViewById(R.id.id_value);
            //  lastName=itemView.findViewById(R.id.lastName);
            profilePic = itemView.findViewById(R.id.user_image_female);

        }
    }

}