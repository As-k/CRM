package com.woxthebox.draglistview.sample.contacts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.ServerUrl;
import com.woxthebox.draglistview.sample.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.view.View.GONE;

/**
 * Created by cioc on 29/3/18.
 */

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.MyHolder>{

    Context context;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    ArrayList jsonArray = new ArrayList();

    public InternalEmployeesAdapter internalEmployeesAdapter;
    public TimelineAdapter(Context context, List<FeedItem> feedItems){
        this.context = context;
        this.feedItems = feedItems;
    }

    @NonNull
    @Override
    public TimelineAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.layout_timeline_adapter, parent, false);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        TimelineAdapter.MyHolder myHolder = new TimelineAdapter.MyHolder(v);

        return myHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull  TimelineAdapter.MyHolder holder, int position) {
        jsonArray.clear();
        holder.internalContactsArray.clear();
        final FeedItem item = feedItems.get(position);
        JSONObject dataObject = item.getJsonObject();
        JSONArray array = item.getJsonArray();
        if (array.length() > 0) {
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject jsonObject = array.getJSONObject(i);
                    String contactsName = jsonObject.getString("name");
                    String contactsDesignation = jsonObject.getString("designation");
                    jsonArray.add(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            holder.externalLayout.setVisibility(View.VISIBLE);
            holder.external.setVisibility(View.VISIBLE);

            ExternalUserAdapter externalUserAdapter = new ExternalUserAdapter(context, jsonArray);
            holder.externalRecycler.setLayoutManager(new LinearLayoutManager(context));
            holder.externalRecycler.setAdapter(externalUserAdapter);

        } else {
            holder.externalLayout.setVisibility(GONE);
            holder.external.setVisibility(GONE);
        }

        JSONArray arrayInternal=item.getJsonArray1();
        if(arrayInternal.length()>0){
            for(int i=0;i<arrayInternal.length();i++){
                try{
                    int jsonString = arrayInternal.getInt(i);
                    holder.internalContactsArray.add(jsonString);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
            holder.internal.setVisibility(View.VISIBLE);
            holder.internalLayout.setVisibility(View.VISIBLE);
            internalEmployeesAdapter = new InternalEmployeesAdapter(context, holder.internalContactsArray);
            holder.internalRecycler.setLayoutManager(new LinearLayoutManager(context));
            holder.internalRecycler.setAdapter(internalEmployeesAdapter);

        }
        else{
            holder.internal.setVisibility(View.GONE);
            holder.internalLayout.setVisibility(View.GONE);
        }


        holder.name.setText(item.getContactName());
        Date d= new Date();
        SimpleDateFormat sdf=new SimpleDateFormat(" yyyy/MM/dd HH:mm");
        holder.timestamp.setText(sdf.format(d));
        //Converting timestamp into x ago format
        /*  CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(item.getTimeStamp()),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        holder.timestamp.setText(timeAgo);*/
        holder.docText.setText(item.getDoc().substring(item.getDoc().lastIndexOf('_')+1));
        holder.docText.setPaintFlags(holder.docText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.docText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent= new Intent(Intent.ACTION_VIEW, Uri.parse(item.getDoc()));
                context.startActivity(browserIntent);
            }

        });

        if(item.getDoc().equals("null")){
         holder.docText.setVisibility(GONE);
         holder.pdf.setVisibility(GONE);
        }
        else{
         holder.docText.setVisibility(View.VISIBLE);
         holder.pdf.setVisibility(View.VISIBLE);
        }


//            String data,duration,location,notes;
//
//               try {
//
//                   String pk = dataObject.getString("pk");
//                   String user=dataObject.getString("user");
//                   String type=dataObject.getString("typ");
//                   String created=dataObject.getString("created");
//                   if (type.equals("note")) {
//                        data = dataObject.getString("data");
//                       holder.htmlText.setHtml(data);
//                   }
//                   if (type.equals("call")) {
//                       data=dataObject.getString("data");
//                        duration = data.getString("duration");
//                       holder.htmlText.setHtml(duration);
//                   }
//                   if (type.equals("meeting")) {
//                       JSONObject dataObj = dataObject.getJSONObject("data");
//                       duration = dataObj.getString("duration");
//                       location = dataObj.getString("location");
//                       holder.htmlText.setHtml(duration+"  "+location);
//                   }
//
//                   notes = dataObject.getString("notes");
//                   if (notes.equals("null")) {
//                       notes = "";
//                   } else {
//                       holder.htmlText.setText(notes);
//                   }
//               }catch (JSONException e){
//                    e.printStackTrace();
//               }
       holder.htmlText.setText(item.getData());

        if(item.getData()==null) {
            holder.htmlText.setVisibility(GONE);
        }
        else {
            holder.htmlText.setVisibility(View.VISIBLE);
        }
        }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        public  TextView name, timestamp,id,internal,external,docText;
        HtmlTextView htmlText;
        ImageView profilePic,pdf;
        RecyclerView externalRecycler,internalRecycler;
        ImageView imageView;
        LinearLayout externalLayout,internalLayout;
        ArrayList internalContactsArray = new ArrayList();


        public MyHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            timestamp = itemView.findViewById(R.id.timestamp);
//            statusMsg = itemView.findViewById(R.id.txtStatusMsg);
//            url =  itemView.findViewById(R.id.txtUrl);
            externalRecycler=itemView.findViewById(R.id.recycler_external);
            externalLayout=itemView.findViewById(R.id.layout_external);
            internalLayout=itemView.findViewById(R.id.layout_internal);
            pdf=itemView.findViewById(R.id.pdf);
            internal=itemView.findViewById(R.id.txt_internal);
            external=itemView.findViewById(R.id.txt_external);
            docText=itemView.findViewById(R.id.docText);
            profilePic = itemView.findViewById(R.id.profilePic);
            htmlText=itemView.findViewById(R.id.html_text);
            imageView=itemView.findViewById(R.id.image);
            internalRecycler=itemView.findViewById(R.id.recycler_internal);


        }
    }


}
