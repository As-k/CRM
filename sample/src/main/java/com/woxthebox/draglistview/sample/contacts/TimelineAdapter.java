package com.woxthebox.draglistview.sample.contacts;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.ServerUrl;
import com.woxthebox.draglistview.sample.app.AppController;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static android.view.View.GONE;

/**
 * Created by cioc on 29/3/18.
 */

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.MyHolder>{

    Context context;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;
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
//        holder.textView.setText(note[position]);
//        holder.imageView.setImageResource(img[position]);
      final FeedItem item = feedItems.get(position);

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
    holder.docText.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent browserIntent= new Intent(Intent.ACTION_VIEW,Uri.parse(item.getDoc()));
            context.startActivity(browserIntent);
        }
    });


     if(item.getDoc().equals("null")){
         holder.docText.setVisibility(GONE);
         holder.pdf.setVisibility(GONE);
     }
     if(item.getNotes().equals("Null"))
     {
         holder.htmlText.setVisibility(GONE);
     }


        String htmlcode="<strong>Subject<strong>: Ecommerce<br> Location: Bangalore <br> Duration: 10 minutes <br>";
        holder.htmlText.setHtml(item.getData());

   //     holder.empname1.setText(item.getContactsName());

   //       holder.empID1.setText(item.getInternalUsers());



        // user profile pic



          }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        public  TextView name, timestamp,id,internal,external,docText;
        HtmlTextView htmlText;
       ImageView profilePic,pdf;
        ImageView imageView;

        public MyHolder(View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.name);
            timestamp = itemView.findViewById(R.id.timestamp);
//            statusMsg = itemView.findViewById(R.id.txtStatusMsg);
//            url =  itemView.findViewById(R.id.txtUrl);
            pdf=itemView.findViewById(R.id.pdf);
            internal=itemView.findViewById(R.id.txt_internal);

            docText=itemView.findViewById(R.id.docText);
            profilePic = itemView.findViewById(R.id.profilePic);
            htmlText=itemView.findViewById(R.id.html_text);

            imageView=itemView.findViewById(R.id.image);

        }
    }

}
