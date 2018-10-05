package com.woxthebox.draglistview.sample;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.woxthebox.draglistview.sample.contacts.ContactsActivity;
import com.woxthebox.draglistview.sample.opportunities.MainActivityOpp;
import com.woxthebox.draglistview.sample.relationships.RelationshipActivity;

import java.io.File;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static int CHECKING_REQ_CODE = 201;
    SessionManager sessionManager;
    public static String csrfId, sessionId;
    public static File file;
    String TAG = "MainActivity";
    String[] arrStr;
    public AsyncHttpClient client;
    ServerUrl serverUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        sessionManager = new SessionManager(this);
        isPermissionGranted();

//        file = new File(Environment.getExternalStorageDirectory() + "/CIOC/libre.txt");
//        if (file.exists()) {
//            FileInputStream fis = null;
//            try {
//                fis = new FileInputStream(Environment.getExternalStorageDirectory() + "/CIOC/libre.txt");
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//
//            DataInputStream dis = new DataInputStream(fis);
//            String s;
//            try {
//                //all the content
//                while ((s = dis.readLine()) != null) {
//                    arrStr = s.split(" ");
//                }
//                csrfId = arrStr[1];
//                sessionId = arrStr[3];
//                Toast.makeText(this, csrfId+ "\n" + sessionId, Toast.LENGTH_SHORT).show();
//                sessionManager.clearAll();
//                sessionManager.setCsrfId(csrfId);
//                sessionManager.setSessionId(sessionId);
//                dis.close();
//                fis.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            Intent intent = new Intent();
//            intent.setClassName("com.cioc.libreerp", "com.cioc.libreerp.LoginActivity");
//            if (isCallable(MainActivity.this, intent)) {
//                // Attach any extras, start or start with callback
//                intent.putExtra("boolean", false);
//                sessionManager.clearAll();
//                startActivityForResult(intent, CHECKING_REQ_CODE);
//            } else {
//                // Respond to the application or activity not being available
//                Toast.makeText(MainActivity.this, "activity not being available", Toast.LENGTH_SHORT).show();
//            }
//        }
    }
    public static boolean isCallable(Activity activity, Intent intent) {
        List<ResolveInfo> list = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public void buttonContacts(View v) {
        startActivity(new Intent(this, ContactsActivity.class));
    }

    public void buttonOpportunities(View v) {
        startActivity(new Intent(this,MainActivityOpp.class));
    }

    public void buttonRelationships(View v) {
        startActivity(new Intent(this,RelationshipActivity.class));
    }

    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.WRITE_CALL_LOG) == PackageManager.PERMISSION_GRANTED
                    ) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {
                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_CALL_LOG, Manifest.permission.READ_CALL_LOG}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
                    //resume tasks needing this permission
                }
                return;
            }
            case 2: {
                if (grantResults.length > 0
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission: " + permissions[1] + "was " + grantResults[1]);
                    //resume tasks needing this permission
                }
                return;
            }
            case 3: {
                if (grantResults.length > 0
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission: " + permissions[2] + "was " + grantResults[2]);
                    //resume tasks needing this permission
                }
                return;
            }
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CHECKING_REQ_CODE && resultCode == RESULT_OK){
//            FileInputStream fis = null;
//            try {
//                fis = new FileInputStream(Environment.getExternalStorageDirectory() + "/CIOC/libre.txt");
//                /* /data/user/0/com.woxthebox.draglistview.sample/cache/temp_2143357911_handled */
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//
//            DataInputStream dis = new DataInputStream(fis);
//            String s;
//            try {
//                //all the content
//                while ((s = dis.readLine()) != null) {
//                    arrStr = s.split(" ");
//                }
//                csrfId = arrStr[1];
//             //   sessionId = arrStr[3];
//                Toast.makeText(this, csrfId+ "\n" +sessionId, Toast.LENGTH_SHORT).show();
//                sessionManager.clearAll();
//                sessionManager.setCsrfId(csrfId);
//                sessionManager.setSessionId(sessionId);
//                dis.close();
//                fis.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            sessionManager.clearAll();
//        }
//    }
}
