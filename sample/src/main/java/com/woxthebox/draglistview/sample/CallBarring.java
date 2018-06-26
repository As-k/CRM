package com.woxthebox.draglistview.sample;

import android.Manifest;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.woxthebox.draglistview.sample.contacts.CallLogDetailsActivity;
import com.woxthebox.draglistview.sample.contacts.ContactLite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.SimpleDateFormat;

import cz.msebera.android.httpclient.Header;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by admin on 13/06/18.
 */

public class CallBarring extends BroadcastReceiver {
    Context cxt;
    String phNumber, callDuration, dateString, timeString, dir, date;
    int tot_seconds;
    public AsyncHttpClient asyncHttpClient;
    ServerUrl serverUrl;
    Intent intent1;

    @Override
    public void onReceive(final Context context, Intent intent) {
        cxt = context;
        serverUrl = new ServerUrl(context);
        asyncHttpClient = serverUrl.getHTTPClient();
        // If, the received action is not a type of "Phone_State", ignore it
        intent1 = new Intent(context, CallLogDetailsActivity.class);

        if (!intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            return;
        } else {
            try {
                System.out.println("Receiver start");
                String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                String incomingNo = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)||state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                    Toast.makeText(context, "Incoming Call State", Toast.LENGTH_SHORT).show();
                    intent.putExtra("cno", incomingNo);
//                    getCalldetailsNow();
                    if (incomingNo != null) {
                        String mobNo = incomingNo.replace("+91", "");
                        asyncHttpClient.get(ServerUrl.url + "api/clientRelationships/contactLite/?format=json&mobile=" + mobNo, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, final JSONArray response) {
                                super.onSuccess(statusCode, headers, response);
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject obj = null;
                                    try {
                                        obj = response.getJSONObject(i);
                                        final String removePk = obj.getString("pk");
                                        ContactLite lite = new ContactLite(obj);
                                        context.startActivity(intent1);
//                                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
//                                        View v = inflater.inflate(R.layout.calllog_dialog_layout, null, false);
//                                        final ImageView dp = v.findViewById(R.id.call_log_profile);
//                                        TextView name = v.findViewById(R.id.call_log_name);
//                                        TextView cno = v.findViewById(R.id.call_log_cno);
//                                        TextView duration = v.findViewById(R.id.call_log_duration);
//                                        final EditText comment = v.findViewById(R.id.call_log_comment);
//                                        Button yesBtn = v.findViewById(R.id.call_log_yes);
//                                        Button noBtn = v.findViewById(R.id.call_log_no);
//                                        cno.setText(phNumber);
//                                        name.setText(lite.name);
//                                        duration.setText(callDuration);
//
//                                        asyncHttpClient.get(lite.getDp(), new FileAsyncHttpResponseHandler(context) {
//                                            @Override
//                                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
//                                                asyncHttpClient.get(ServerUrl.url + "/static/images/img_avatar_card.png", new FileAsyncHttpResponseHandler(context) {
//                                                    @Override
//                                                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
//
//                                                    }
//
//                                                    @Override
//                                                    public void onSuccess(int statusCode, Header[] headers, File file) {
//                                                        Bitmap pp = BitmapFactory.decodeFile(file.getAbsolutePath());
//                                                        dp.setImageBitmap(pp);
//                                                    }
//                                                });
//                                            }
//
//                                            @Override
//                                            public void onSuccess(int statusCode, Header[] headers, File file) {
//                                                Bitmap pp = BitmapFactory.decodeFile(file.getAbsolutePath());
//                                                dp.setImageBitmap(pp);
//                                            }
//                                        });
//
//
//                                        final AlertDialog.Builder abd = new AlertDialog.Builder(context);
//                                        abd.setView(v);
//                                        final AlertDialog ad = abd.create();
//                                        ad.show();
//                                        noBtn.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                ad.dismiss();
//                                            }
//                                        });
//                                        yesBtn.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                comment.getText().toString();
//                                                SimpleDateFormat sdf_datetime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//                                                String date_time = sdf_datetime.format(new Date(Long.parseLong(date)));
//                                                JSONObject object = new JSONObject();
//                                                try {
//                                                    object.put("duration",tot_seconds);
//                                                } catch (JSONException e) {
//                                                    e.printStackTrace();
//                                                }
//                                                RequestParams params = new RequestParams();
//                                                params.put("contact",removePk);
//                                                params.put("data",object);
//                                                params.put("typ","call");
//                                                params.put("when",date_time);
//
//                                                asyncHttpClient.post(ServerUrl.url+"api/clientRelationships/activity/", params, new JsonHttpResponseHandler(){
//                                                    @Override
//                                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                                                        super.onSuccess(statusCode, headers, response);
//                                                        Toast.makeText(context, "posted", Toast.LENGTH_SHORT).show();
//
//
//                                                        ad.dismiss();
//                                                    }
//
//                                                    @Override
//                                                    public void onFinish() {
//                                                        super.onFinish();
//                                                    }
//
//                                                    @Override
//                                                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                                                        super.onFailure(statusCode, headers, throwable, errorResponse);
//                                                    }
//                                                });
//
//                                            }
//                                        });

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFinish() {
                                System.out.println("finished 001");

                            }
                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                                System.out.println("finished failed 001");
                            }
                        });
                    }
                }
                if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                    Toast.makeText(context, "Call Received State", Toast.LENGTH_SHORT).show();
                }
                if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                    String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                    Toast.makeText(context,"Call Idle State - "+number,Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private void getCalldetailsNow() {
        // TODO Auto-generated method stub
        if (ActivityCompat.checkSelfPermission(cxt, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Cursor managedCursor = cxt.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, android.provider.CallLog.Calls.DATE + " ASC");

        int number = managedCursor.getColumnIndex( CallLog.Calls.NUMBER );
        int duration1 = managedCursor.getColumnIndex( CallLog.Calls.DURATION);
        int type1 = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date1 = managedCursor.getColumnIndex(CallLog.Calls.DATE);

        if(managedCursor.moveToLast() == true) {
            phNumber = managedCursor.getString(number);
            String callDuration1 = managedCursor.getString(duration1);

            String type = managedCursor.getString(type1);
            date = managedCursor.getString(date1);
            int dircode = Integer.parseInt(type);
            dir=null;
            switch (dircode)
            {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
                default:
                    dir = "MISSED";
                    break;
            }

            SimpleDateFormat sdf_date = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdf_time = new SimpleDateFormat("h:mm a");
//            SimpleDateFormat sdf_dur = new SimpleDateFormat("KK:mm:ss");

            tot_seconds = Integer.parseInt(callDuration1);
            int hours = tot_seconds / 3600;
            int minutes = (tot_seconds % 3600) / 60;
            int seconds = tot_seconds % 60;

            callDuration = String.format("%02d : %02d : %02d ", hours, minutes, seconds);

            dateString = sdf_date.format(new Date(Long.parseLong(date)));
            timeString = sdf_time.format(new Date(Long.parseLong(date)));
            //  String duration_new=sdf_dur.format(new Date(Long.parseLong(callDuration)));
        }
        managedCursor.close();
    }
}