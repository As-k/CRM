package com.woxthebox.draglistview.sample.relationships;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.ServerUrl;
import com.woxthebox.draglistview.sample.contacts.BrowseAdapter;

import java.io.File;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by amit on 10/3/18.
 */


public class ActiveDealsAdapter extends RecyclerView.Adapter<ActiveDealsAdapter.MyHolder> {

    public static String  dealName,contactName, value,id,closingDate;

    Context context;
    List<DealLite> dealList;
    ServerUrl serverUrl;
    AsyncHttpClient asyncHttpClient;

    public ActiveDealsAdapter(Context context, List<DealLite> dealList) {
        this.context = context;
        this.dealList = dealList;
    }

    @NonNull
    @Override
    public ActiveDealsAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.active_deals_style, parent, false);
        serverUrl = new ServerUrl();
        asyncHttpClient = serverUrl.getHTTPClient();
        ActiveDealsAdapter.MyHolder myHolder = new ActiveDealsAdapter.MyHolder(v);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveDealsAdapter.MyHolder holder, int position) {
        if (holder instanceof MyHolder) {
            final MyHolder myHolder = (MyHolder) holder;
            final DealLite d = dealList.get(position);
//            id = (String) hm.get("pk");
//            dealName = (String) hm.get("name");
//            contactName =(String) hm.get("name_con");
//            value = (String) hm.get("value");
//            closingDate = (String) hm.get("closeDate");

            myHolder.contactname.setText(d.getContactName());
            myHolder.dealid.setText(d.getPk());
            myHolder.dealname.setText(d.getName());
            myHolder.moneyvalue.setText(d.getValue());
            asyncHttpClient.get(d.getContactDp(), new FileAsyncHttpResponseHandler(context) {
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                    asyncHttpClient.get(ServerUrl.url+ "/static/images/img_avatar_card.png", new FileAsyncHttpResponseHandler(context) {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {

                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, File file) {
                            Bitmap pp = BitmapFactory.decodeFile(file.getAbsolutePath());
                            ((MyHolder) myHolder).contactphoto.setImageBitmap(pp);
                        }
                    });
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, File file) {
                    Bitmap pp = BitmapFactory.decodeFile(file.getAbsolutePath());
                    ((MyHolder) myHolder).contactphoto.setImageBitmap(pp);
                }
            });
//            myHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Toast.makeText(context, ""+getLayoutPosition(), Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(context, ActiveDealsDetailsActivity.class);
//                    intent.putExtra("name", d.getName());
//                    intent.putExtra("value", d.getValue());
//                    intent.putExtra("closeDate",d.getCloseDate());
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return ActiveDealsActivity.dealLites.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView ownerimage, contactphoto;
        TextView ownername, contactname, dealname, dealid,moneyvalue;

        public MyHolder(View itemView) {
            super(itemView);
            ownerimage = itemView.findViewById(R.id.owner_photo);
            dealid = itemView.findViewById(R.id.id_number);
            contactphoto = itemView.findViewById(R.id.contact_photo);
            ownername = itemView.findViewById(R.id.owner_name);
            contactname = itemView.findViewById(R.id.contact_name);
            dealname = itemView.findViewById(R.id.name_first);
            moneyvalue = itemView.findViewById(R.id.money_value);
        }
    }
}

