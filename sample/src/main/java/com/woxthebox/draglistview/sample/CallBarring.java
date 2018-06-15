package com.woxthebox.draglistview.sample;

import android.app.Dialog;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.woxthebox.draglistview.sample.contacts.CallLogDetailsActivity;

import java.lang.reflect.Method;
import java.sql.Date;

/**
 * Created by admin on 13/06/18.
 */

public class CallBarring extends BroadcastReceiver {
    // This String will hold the incoming phone number
    private String number;
//    CustomDialog dialog;
    TelephonyManager telephonyManager;
    PhoneStateListener listener;
    Context context;

    @Override
    public void onReceive(final Context context, Intent intent) {
        // If, the received action is not a type of "Phone_State", ignore it
        Intent i = new Intent(context, CallLogDetailsActivity.class);

        if (!intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            return;
        }

            // Else, try to do some action
        else {
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            try {
                System.out.println("Receiver start");
                String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                String incomingNo = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

                if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
//                    try {
//                        Thread.sleep(3000);
                    Toast.makeText(context, "Incoming Call State", Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Ringing State Number is -" + incomingNo, Toast.LENGTH_SHORT).show();
                    i.putExtra("cno",incomingNo);
                    context.startActivity(i);

//                } catch (Exception e) {
//                    e.getLocalizedMessage();
//                }
                }
                if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))){
                    Toast.makeText(context,"Call Received State",Toast.LENGTH_SHORT).show();
                }
                if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                    String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                    Toast.makeText(context,"Call Idle State - "+number,Toast.LENGTH_SHORT).show();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context.getApplicationContext());
//                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    View dialogView = inflater.inflate(R.layout.calllog_dialog_layout, null);
//                    ImageView button = dialogView.findViewById(R.id.close_dialog);
//                    builder.setView(dialogView);
//                    final AlertDialog alert = builder.create();
//                    alert.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//                    alert.getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);
//                    alert.setCanceledOnTouchOutside(true);
//                    alert.show();
//                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                    Window window = alert.getWindow();
//                    window.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
//                    window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                    window.setGravity(Gravity.TOP);
//                    lp.copyFrom(window.getAttributes());
//                    //This makes the dialog take up the full width
//                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                    window.setAttributes(lp);
//                    button.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            //close the service and remove the from from the window
//                            alert.dismiss();
//                        }
//                    });

//                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    View v = inflater.inflate(R.layout.calllog_dialog_layout,null,false);
//                    TextView tv = v.findViewById(R.id.last_contact_no);
//                    ImageView iv = v.findViewById(R.id.close_dialog);
//                    tv.setText(incomingNumber+"");
//                    AlertDialog.Builder abd = new AlertDialog.Builder(context.getApplicationContext());
//                    abd.setTitle("Call Details: ");
//                    abd.setMessage(incomingNumber);
//                    abd.setPositiveButton("Close", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    final AlertDialog ad = abd.create();
//                    ad.show();
//                    iv.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            ad.dismiss();
//                        }
//                    });
                    i.putExtra("cno",number);
                    context.startActivity(i);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
//            this.context = context;
//            if(dialog == null){
//                dialog = new CustomDialog(context);
//                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//                dialog.show();
//            }
//            // Fetch the number of incoming call
//            number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
//            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            listener = new PhoneStateListener() {
//                @Override
//                public void onCallStateChanged(int state, String incomingNumber) {
//                    String stateString = "N/A";
//                    switch (state) {
//                        case TelephonyManager.CALL_STATE_IDLE:
//                            stateString = "Idle";
//                            dialog.dismiss();
//                            break;
//                        case TelephonyManager.CALL_STATE_OFFHOOK:
//                            stateString = "Off Hook";
//                            dialog.dismiss();
//                            break;
//                        case TelephonyManager.CALL_STATE_RINGING:
//                            stateString = "Ringing";
//                            dialog.show();
//                            break;
//                    }
//                    Toast.makeText(context, stateString,Toast.LENGTH_LONG).show();
//                }
//            };
//
//            // Register the listener with the telephony manager
//            telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

            // Check, whether this is a member of "Black listed" phone numbers
            // stored in the database
			/*if (MainActivity.blockList.contains(new Blacklist(number))) {
				// If yes, invoke the method
				disconnectPhoneItelephony(context);
				return;
			}*/
        }
    }

    // Method to disconnect phone automatically and programmatically
    // Keep this method as it is
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    private void disconnectPhoneItelephony(Context context) {
//        ITelephony telephonyService;
//        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        try {
//            Class c = Class.forName(telephony.getClass().getName());
//            Method m = c.getDeclaredMethod("getITelephony");
//            m.setAccessible(true);
//            telephonyService = (ITelephony) m.invoke(telephony);
//            telephonyService.endCall();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    class CustomDialog extends Dialog implements View.OnClickListener {
//
//        public CustomDialog(Context context) {
//            super(context);
//            // TODO Auto-generated constructor stub
//        }
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            // TODO Auto-generated method stub
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.list_item);
//            Button btnEndCall = (Button) findViewById(R.id.end_call);
//            //btnEndCall.set
//            btnEndCall.setOnClickListener(this);
//
//        }
//
//        @Override
//        public void onClick(View v) {
//            // TODO Auto-generated method stub
//            disconnectPhoneItelephony(context);
//
//        }
//    }
//
//    interface ITelephony {
//
//        boolean endCall();
//
//    }
}