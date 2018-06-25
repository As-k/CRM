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

import java.io.File;

import cz.msebera.android.httpclient.Header;

/**
 * Created by amit on 4/4/18.
 */

public class ExternalStakeholderAdapter extends RecyclerView.Adapter<ExternalStakeholderAdapter.MyHolder> {
 public  static String cName,cDesignation;
  private DealLite d;

    Context context;
    ServerUrl serverUrl;
    AsyncHttpClient asyncHttpClient;


//  String uName[] = {"Samuel D. Pollock ", "Samuel D. Pollock", "Samuel D. Pollock "};
//  String uDesg[] = {"Information systems manager","Information systems manager","Information systems manager"};
//    int uImage[] = {R.drawable.male,R.drawable.male,R.drawable.male};



    public ExternalStakeholderAdapter(Context context){
        this.context = context;


    }
    @NonNull
    @Override
    public ExternalStakeholderAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = layoutInflater.inflate(R.layout.layout_externalstakeholder_adapter, parent, false);
        serverUrl = new ServerUrl();
        asyncHttpClient = serverUrl.getHTTPClient();

        ExternalStakeholderAdapter.MyHolder myHolder = new ExternalStakeholderAdapter.MyHolder(v);

        return myHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ExternalStakeholderAdapter.MyHolder holder, int position) {
        if (holder instanceof MyHolder) {
            final MyHolder myHolder = (MyHolder) holder;
            DealLite d = ActiveDealsActivity.dealLites.get(ActiveDealsActivity.pos);
            String name = d.getContactName();
            String designation = d.getContactDesignation();


            myHolder.holderName.setText(name);
            myHolder.holderDesgnation.setText(designation);
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
                            ((MyHolder) myHolder).imageView.setImageBitmap(pp);
                        }
                    });
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, File file) {
                    Bitmap pp = BitmapFactory.decodeFile(file.getAbsolutePath());
                    ((MyHolder) myHolder).imageView.setImageBitmap(pp);
                }
            });
//            holder.imageView.setImageResource(uImage[position]);
        }
    }
    @Override
    public int getItemCount() {
        return ActiveDealsActivity.dealLites.size();
    }
    public class MyHolder extends RecyclerView.ViewHolder {

        TextView holderName,holderDesgnation;
        ImageView imageView;
        public MyHolder(View itemView) {
            super(itemView);

            holderName = itemView.findViewById(R.id.holder_name);
            holderDesgnation = itemView.findViewById(R.id.holder_designation);
            imageView = itemView.findViewById(R.id.stakeholder_image);

        }
    }
}
