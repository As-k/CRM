package com.woxthebox.draglistview.sample.contacts;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.app.AppController;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by cioc on 29/3/18.
 */

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.MyHolder>{

    Context context;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;
    List<FeedItem> cardItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

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
//
        View v = inflater.inflate(R.layout.layout_timeline_adapter, parent, false);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        TimelineAdapter.MyHolder myHolder = new TimelineAdapter.MyHolder(v);

        return myHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull TimelineAdapter.MyHolder holder, int position) {
        FeedItem item = feedItems.get(position);
        holder.name.setVisibility(View.VISIBLE);
        holder.name.setText(item.getcontactName());
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
  /*  holder.docText.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent browserIntent= new Intent(Intent.ACTION_VIEW,Uri.parse(item.getDoc()));
            context.startActivity(browserIntent);
        }
    });*/

     if(item.getDoc().equals("null")){
         holder.docText.setVisibility(View.GONE);
         holder.pdf.setVisibility(View.GONE);
     }
     else{
         holder.docText.setVisibility(View.VISIBLE);
         holder.pdf.setVisibility(View.VISIBLE);
     }

      //  String htmlcode="<strong>Subject<strong>: Ecommerce<br> Location: Bangalore <br> Duration: 10 minutes <br>";
        holder.htmlText.setHtml(item.getData());
        if(item.getData()==null) {
            holder.htmlText.setVisibility(View.GONE);
        }
        else {
            holder.htmlText.setVisibility(View.VISIBLE);
        }

        InternalEmployeesAdapter internalEmployeesAdapter=new InternalEmployeesAdapter(context,feedItems);
        holder.internalRecycler.setLayoutManager(new LinearLayoutManager(context));
        holder.internalRecycler.setAdapter(internalEmployeesAdapter);
        ExternalUserAdapter externalUserAdapter = new ExternalUserAdapter(context,feedItems);
        holder.externalRecycler.setLayoutManager(new LinearLayoutManager(context));
        holder.externalRecycler.setAdapter(externalUserAdapter);

        if(item.getcontactsName() != null){
            holder.externalLayout.setVisibility(View.VISIBLE);
            holder.external.setVisibility(View.VISIBLE);
        } else {
            holder.externalLayout.setVisibility(View.GONE);
            holder.external.setVisibility(View.GONE);
        }
        if(item.getPk()==null) {
            holder.internalLayout.setVisibility(View.GONE);
            holder.internal.setVisibility(View.GONE);
        } else {
            holder.internal.setVisibility(View.VISIBLE);
            holder.internalLayout.setVisibility(View.VISIBLE);
        }

        }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        public  TextView name, timestamp,id,internal,external,docText,empName,empDesign,empIdInt;
        HtmlTextView htmlText;
       ImageView profilePic,pdf;
       RecyclerView externalRecycler,internalRecycler;
        ImageView imageView;
        RelativeLayout externalLayout,internalLayout;


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
