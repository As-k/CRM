package com.woxthebox.draglistview.sample.relationships;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.ServerUrl;
import com.woxthebox.draglistview.sample.contacts.MeetingActivity;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * Created by amit on 4/4/18.
 */

public class FinancesAdapter extends RecyclerView.Adapter<FinancesAdapter.MyHolder> {
    Context context;
    List<Contract> financeList;
    int c_yr, c_month, c_day, c_hr, c_min;
    ServerUrl serverUrl;
    public AsyncHttpClient client;
    String duedatePrams;

    public FinancesAdapter(Context context,List<Contract> financeList) {
        this.context = context;
        this.financeList = financeList;
    }

    @NonNull
    @Override
    public FinancesAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.layout_finances_adapter, parent, false);
        FinancesAdapter.MyHolder myHolder = new FinancesAdapter.MyHolder(v);
        Calendar c = Calendar.getInstance();
        c_yr = c.get(Calendar.YEAR);
        c_month = c.get(Calendar.MONTH);
        c_day = c.get(Calendar.DAY_OF_MONTH);
        c_hr = c.get(Calendar.HOUR_OF_DAY);
        c_min = c.get(Calendar.MINUTE);
        serverUrl = new ServerUrl(context);
        client = serverUrl.getHTTPClient();

        return myHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final FinancesAdapter.MyHolder holder, int position) {
        if (holder instanceof MyHolder) {
            final MyHolder myHolder = (MyHolder) holder;
            final Contract r = financeList.get(position);

            String dtc = r.getCreated();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.ENGLISH);
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMMM",Locale.ENGLISH);
            Date date = null;
            try{
                date = sdf1.parse(dtc);
                String newDate = sdf2.format(date);
                System.out.println(newDate);
                Log.d("created Date",newDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar thatDay = Calendar.getInstance();
            thatDay.setTime(date);
            long today = System.currentTimeMillis();

            long diff = today - thatDay.getTimeInMillis();
            long days = diff/(24*60*60*1000);

            String dt = r.getUpdated();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.ENGLISH);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM",Locale.ENGLISH);
            Date date1 = null;
            try{
                date1 = simpleDateFormat.parse(dt);
                String newDate1 = sdf.format(date1);
                System.out.println(newDate1);
                Log.d("updates Date",newDate1);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar thatDay1 = Calendar.getInstance();
            thatDay1.setTime(date1);
            long today1 = System.currentTimeMillis();

            long diff1 = today1 - thatDay1.getTimeInMillis();
            long days1 = diff1/(24*60*60*1000);

            myHolder.idDeal.setText(r.getPk());
            holder.items.setText(r.getSize().size()+"");
            myHolder.value.setText(r.getValue());
            myHolder.created.setText(days+ " Days");
            myHolder.update.setText(days1+ " Days");
            if (r.getStatus().equals("received")) {
                holder.notification.setVisibility(View.VISIBLE);
                holder.download.setVisibility(View.VISIBLE);
                holder.edit.setVisibility(View.VISIBLE);
                holder.duaDate.setVisibility(View.GONE);
                holder.duaDatetxt.setVisibility(View.GONE);
                myHolder.status.setText("Received");
            }
            else if (r.getStatus().equals("approved")) {
                holder.notification.setVisibility(View.GONE);
                holder.download.setVisibility(View.VISIBLE);
                holder.edit.setVisibility(View.VISIBLE);
                holder.duaDate.setVisibility(View.GONE);
                holder.duaDatetxt.setVisibility(View.GONE);
                myHolder.status.setText("Approved");
            }
            else if (r.getStatus().equals("billed")) {
                holder.notification.setVisibility(View.VISIBLE);
                holder.download.setVisibility(View.VISIBLE);
                holder.edit.setVisibility(View.VISIBLE);
                holder.duaDate.setVisibility(View.VISIBLE);
                holder.duaDatetxt.setVisibility(View.VISIBLE);
                SimpleDateFormat dueDatefrmtIn = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                SimpleDateFormat dueDatefrmtOut = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                Date dueDate = null;
                String dueStr = null;
                try{
                    dueDate = dueDatefrmtIn.parse(r.getDueDate());
                    dueStr = dueDatefrmtOut.format(dueDate);
                    Log.e("billed Date",dueStr);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                holder.duaDate.setText(dueStr);
                myHolder.status.setText("Billed");
            }
            else if (r.getStatus().equals("cancelled")) {
                holder.duaDate.setVisibility(View.GONE);
                holder.duaDatetxt.setVisibility(View.GONE);
                holder.notification.setVisibility(View.GONE);
                holder.download.setVisibility(View.GONE);
                holder.edit.setVisibility(View.GONE);
                myHolder.status.setText("Cancelled");
            }
            else if (r.getStatus().equals("quoted")) {
                holder.notification.setVisibility(View.GONE);
                holder.download.setVisibility(View.VISIBLE);
                holder.edit.setVisibility(View.VISIBLE);
                holder.duaDate.setVisibility(View.GONE);
                holder.duaDatetxt.setVisibility(View.GONE);
                myHolder.status.setText("Quoted");
            }
            else {
                holder.duaDate.setVisibility(View.GONE);
                holder.duaDatetxt.setVisibility(View.GONE);
                holder.notification.setVisibility(View.VISIBLE);
                holder.download.setVisibility(View.VISIBLE);
                holder.edit.setVisibility(View.VISIBLE);
                myHolder.status.setText("Due Elapsed");
            }


            myHolder.download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    client.get(ServerUrl.url + "/api/clientRelationships/downloadInvoice/?contract=" + r.getPk(), new FileAsyncHttpResponseHandler(context) {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {

                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, File file) {
                            Toast.makeText(context, "Download", Toast.LENGTH_SHORT).show();
//                            Bitmap pp = BitmapFactory.decodeFile(file.getAbsolutePath());
                            File file1 = new File(file.getAbsolutePath()+".pdf");
//                            try {
                            try {
                                FileInputStream fis = new FileInputStream(Environment.getExternalStorageDirectory() + "/CIOC/Download");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            int file_size = Integer.parseInt(String.valueOf(file1.length()/1024));
                            Intent target = new Intent(Intent.ACTION_VIEW);
                            target.setDataAndType(Uri.fromFile(file1),"application/pdf");
                            target.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            Intent intent = Intent.createChooser(target, "Open File");
                            try {
                                context.startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                // Instruct the user to install a PDF reader here, or something
                            }
                        }
                    });
                }
            });

            myHolder.notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Notification", Toast.LENGTH_SHORT).show();
                }
            });

            myHolder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddQuoteActivity.class);
                    intent.putExtra("data", r.getData());
                    intent.putExtra("value", Integer.parseInt(r.getValue()));
                    intent.putExtra("edit", true);
                    intent.putExtra("pk", r.getPk());
                    context.startActivity(intent);
                    Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();

                }
            });


            holder.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PopupMenu popupMenu = new PopupMenu(context, v);
                    popupMenu.inflate(R.menu.card_manu);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.quoted: {
                                    holder.notification.setVisibility(View.GONE);
                                    holder.download.setVisibility(View.VISIBLE);
                                    holder.edit.setVisibility(View.VISIBLE);
                                    holder.duaDate.setVisibility(View.GONE);
                                    holder.duaDatetxt.setVisibility(View.GONE);
                                    holder.status.setText("Quoted");
                                    statusPatch(r.getPk(), "quoted");
                                    break;
                                }
                                case R.id.approved: {
                                    holder.notification.setVisibility(View.GONE);
                                    holder.download.setVisibility(View.VISIBLE);
                                    holder.edit.setVisibility(View.VISIBLE);
                                    holder.duaDate.setVisibility(View.GONE);
                                    holder.duaDatetxt.setVisibility(View.GONE);
                                    holder.status.setText("Approved");
                                    statusPatch(r.getPk(), "approved");
                                    break;
                                }
                                case R.id.billed: {
                                    holder.notification.setVisibility(View.VISIBLE);
                                    holder.download.setVisibility(View.VISIBLE);
                                    holder.edit.setVisibility(View.VISIBLE);
                                    holder.duaDate.setVisibility(View.VISIBLE);
                                    holder.duaDatetxt.setVisibility(View.VISIBLE);
                                    DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            String dateStr = dayOfMonth + "/" + (month + 1) + "/" + year;
                                            duedatePrams = year  + "-" + (month + 1) + "-" + dayOfMonth;
                                            statusPatch(r.getPk(), "billed");
                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                                            Date date1 = null;
                                            String date = null;
                                            try{
                                                date1 = simpleDateFormat.parse(dateStr);
                                                date = sdf.format(date1);
                                                Log.e("billed Date",date);

                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            holder.duaDate.setText(date);
                                        }
                                        }, c_yr, c_month, c_day);

                                    dpd.show();
                                    holder.status.setText("Billed");
                                    break;
                                }
                                case R.id.received: {
                                    holder.notification.setVisibility(View.VISIBLE);
                                    holder.download.setVisibility(View.VISIBLE);
                                    holder.edit.setVisibility(View.VISIBLE);
                                    holder.duaDate.setVisibility(View.GONE);
                                    holder.duaDatetxt.setVisibility(View.GONE);
                                    holder.status.setText("Received");
                                    statusPatch(r.getPk(), "received");
                                    break;
                                }
                                case R.id.cancelled: {
                                    holder.duaDate.setVisibility(View.GONE);
                                    holder.duaDatetxt.setVisibility(View.GONE);
                                    holder.notification.setVisibility(View.GONE);
                                    holder.download.setVisibility(View.GONE);
                                    holder.edit.setVisibility(View.GONE);
                                    holder.status.setText("Cancelled");
                                    statusPatch(r.getPk(), "cancelled");
                                    break;
                                }
                                case R.id.due_Elapsed: {
                                    holder.duaDate.setVisibility(View.GONE);
                                    holder.duaDatetxt.setVisibility(View.GONE);
                                    holder.notification.setVisibility(View.VISIBLE);
                                    holder.download.setVisibility(View.VISIBLE);
                                    holder.edit.setVisibility(View.VISIBLE);
                                    holder.status.setText("Due Elapsed");
                                    break;
                                }
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return financeList.size();
    }
    public class MyHolder extends RecyclerView.ViewHolder {

        TextView idDeal,items,value,created,update,status, duaDate, duaDatetxt;
        ImageButton menu, download, edit, notification;
        public MyHolder(View itemView) {
            super(itemView);
            idDeal = itemView.findViewById(R.id.id_deals);
            items = itemView.findViewById(R.id.items_id);
            value = itemView.findViewById(R.id.value_id);
            created = itemView.findViewById(R.id.created);
            update = itemView.findViewById(R.id.updated);
            status =itemView.findViewById(R.id.status);
            duaDate =itemView.findViewById(R.id.dua_date);
            duaDatetxt =itemView.findViewById(R.id.dua_date_txt);
            menu =itemView.findViewById(R.id.popup_menu);
            download =itemView.findViewById(R.id.download);
            edit =itemView.findViewById(R.id.edit_contract);
            notification =itemView.findViewById(R.id.notification);
        }
    }

    public void statusPatch(String pk, String status){
        RequestParams params = new RequestParams();
        if (status.equals("billed")){
            params.put("status", status);
            params.put("dueDate", duedatePrams);
        } else params.put("status", status);
        client.patch(ServerUrl.url + "/api/clientRelationships/contract/" + pk+"/", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(context, "patched", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });

    }


}
