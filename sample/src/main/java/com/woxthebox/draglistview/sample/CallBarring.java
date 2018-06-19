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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.woxthebox.draglistview.sample.contacts.CallLogDetailsActivity;

import java.lang.reflect.Method;
import java.sql.Date;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

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
    private WindowManager wm;
    private static LinearLayout ly1;
    private WindowManager.LayoutParams params1;
    View v1;

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

                if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)||state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                    Toast.makeText(context, "Incoming Call State", Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Ringing State Number is -" + incomingNo, Toast.LENGTH_SHORT).show();
                    i.putExtra("cno", incomingNo);
//                    i.putExtras(intent);
//                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                    wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//                    params1 = new WindowManager.LayoutParams(
//                            ViewGroup.LayoutParams.MATCH_PARENT,
//                            ViewGroup.LayoutParams.MATCH_PARENT,
//                            WindowManager.LayoutParams.TYPE_SYSTEM_ALERT |
//                            WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
//                            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
//                                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//                            PixelFormat.TRANSPARENT);
//
//                    params1.height = 75;
//                    params1.width = 512;
//                    params1.x = 265;
//                    params1.y = 400;
//                    params1.format = PixelFormat.TRANSLUCENT;
//
//                    ly1 = new LinearLayout(context);
//                    ly1.setOrientation(LinearLayout.HORIZONTAL);
//
//                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
//
//                    View hiddenInfo = inflater.inflate(R.layout.calllog_dialog_layout, ly1, false);
//                    ly1.addView(hiddenInfo);
//
//                    wm.addView(ly1, params1);
                    context.startActivity(i);

                }
                if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                    Toast.makeText(context, "Call Received State", Toast.LENGTH_SHORT).show();
//                    wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//                    params1 = new WindowManager.LayoutParams(
//                            WindowManager.LayoutParams.MATCH_PARENT,
//                            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_SYSTEM_ALERT |
//                            WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
//                            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
//                                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//                            PixelFormat.TRANSPARENT);
//
//                    params1.height = 75;
//                    params1.width = 512;
//                    params1.x = 265;
//                    params1.y = 400;
//                    params1.format = PixelFormat.TRANSLUCENT;
//
//                    ly1 = new LinearLayout(context);
//                    ly1.setBackgroundColor(Color.GREEN);
//                    ly1.setOrientation(LinearLayout.VERTICAL);
//
//                    wm.addView(ly1, params1);
//                }
//
//                // To remove the view once the dialer app is closed.
//                if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
//                    String state1 = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
//                    if (state1.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
//                        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//                        if (ly1 != null) {
//                            wm.removeView(ly1);
//                            ly1 = null;
//                        }
//                    }
                }
                if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                    String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                    Toast.makeText(context,"Call Idle State - "+number,Toast.LENGTH_SHORT).show();
//                    LayoutInflater layoutInflater =
//                            (LayoutInflater) context
//                                    .getSystemService(LAYOUT_INFLATER_SERVICE);
//                    View popupView = layoutInflater.inflate(R.layout.calllog_dialog_layout, null);
//                    final PopupWindow popupWindow = new PopupWindow(
//                            popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//                    Button btnDismiss = (Button)popupView.findViewById(R.id.call_log_no);
//
//                    btnDismiss.setOnClickListener(new Button.OnClickListener(){
//
//                        @Override
//                        public void onClick(View v) {
//                            popupWindow.dismiss();
//                        }});
//
//                    popupWindow.showAsDropDown(, 50, -30);
//
//                    popupView.setOnTouchListener(new View.OnTouchListener() {
//                        int orgX, orgY;
//                        int offsetX, offsetY;
//
//                        @Override
//                        public boolean onTouch(View v, MotionEvent event) {
//                            switch (event.getAction()) {
//                                case MotionEvent.ACTION_DOWN:
//                                    orgX = (int) event.getX();
//                                    orgY = (int) event.getY();
//                                    break;
//                                case MotionEvent.ACTION_MOVE:
//                                    offsetX = (int)event.getRawX() - orgX;
//                                    offsetY = (int)event.getRawY() - orgY;
//                                    popupWindow.update(offsetX, offsetY, -1, -1, true);
//                                    break;
//                            }
//                            return true;
//                        }});
//                    i.putExtra("cno",number);
//                    context.startActivity(i);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

//        }
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