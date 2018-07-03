package com.woxthebox.draglistview.sample.contacts;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.ServerUrl;
import com.woxthebox.draglistview.sample.UserSearch;
import com.woxthebox.draglistview.sample.edittag.ContactChip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import me.originqiu.library.EditTag;

public class ViewDetailsActivity extends FragmentActivity {
    ImageView contactImage;
    TextView nameTv, companyTv, designationTv, cnoTv, emailTv;
    private Contact c;
    TabLayout tl;
    FloatingActionButton fabView, fabSchedule, fabTask, fabMeeting, fabNotes;
    private boolean fabExpanded = false;
    private LinearLayout layoutFabSchedule;
    private LinearLayout layoutFabTask;
    private LinearLayout layoutFabMeeting;
    private LinearLayout layoutFabNotes;
    Animation rotate_forward, rotate_Backward, fab_open, fab_close;

    int c_yr, c_month, c_day, c_hr, c_min;
    Bundle b;
    ServerUrl serverUrl;
    AsyncHttpClient client;
    EditTag editTagViewIP, editTagViewCRM;
    AutoCompleteTextView editTagIP, editTagCRM;

    ArrayList noteList;
    private List<String> ipContactSch = new ArrayList<>();
    private List<String> crmContactSch = new ArrayList<>();
    private List<String> crmContactTask = new ArrayList<>();
    private List<UserSearch> userSearchListSdl= new ArrayList<>();
    private List<ContactLite> contactLitesSdl= new ArrayList<>();
    private List<ContactLite> contactLitesTask= new ArrayList<>();

    public static String cpk,cname,street,city,state,pincode,country,eMail,mobile,designation,company,companyPk,telephone, cMobile, cin, tin, about, web,dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        b = getIntent().getExtras();

        findIds();
        serverUrl = new ServerUrl(this);
        client = serverUrl.getHTTPClient();

//        mContactList = new ArrayList<>();
        noteList = new ArrayList<>();

        tl = findViewById(R.id.tl_view);
        tl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                switch (pos){
                    case 0:
                    {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.add(R.id.view_fg, new TimelineFragment(), "TimelineFragment");
                        ft.commit();
                        break;
                    }
                    case 1:
                    {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.add(R.id.view_fg, new InfoFragment(), "ActiveFragment");
                        ft.commit();
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        final Calendar c = Calendar.getInstance();
        c_yr = c.get(Calendar.YEAR);
        c_month = c.get(Calendar.MONTH);
        c_day = c.get(Calendar.DAY_OF_MONTH);
        c_hr = c.get(Calendar.HOUR_OF_DAY);
        c_min = c.get(Calendar.MINUTE);

        fabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabExpanded == true){
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });
        closeSubMenusFab();

        fabSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText scheduleDate, scheduleTime, scheduleLocation, scheduleEventDetails;
                Button scheduleCancel, scheduleSave;
                final DatePicker[] dp = new DatePicker[1];
                final TimePicker[] tp = new TimePicker[1];
                final String[] format = new String[1];
                final JSONArray array = new JSONArray();
                final JSONArray array1 = new JSONArray();

                View v = getLayoutInflater().inflate(R.layout.layout_schedule_style, null, false);

                scheduleDate = v.findViewById(R.id.schedule_date);
                scheduleTime = v.findViewById(R.id.schedule_time);
                editTagViewIP = (EditTag) v.findViewById(R.id.edit_tag_view_ip);
                editTagViewIP.setEditable(true);
                editTagViewCRM = (EditTag) v.findViewById(R.id.edit_tag_view_crm);
                editTagViewCRM.setEditable(true);
                editTagIP = (AutoCompleteTextView) v.findViewById(R.id.medit_tag_ip);
                editTagCRM = (AutoCompleteTextView) v.findViewById(R.id.medit_tag_crm);
                scheduleLocation = v.findViewById(R.id.schedule_loction);
                scheduleEventDetails = v.findViewById(R.id.schedule_event_details);

                scheduleCancel = v.findViewById(R.id.schedule_cancel);
                scheduleSave = v.findViewById(R.id.schedule_save);

                scheduleDate.setFocusableInTouchMode(false);
                scheduleTime.setFocusableInTouchMode(false);
                scheduleDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog dpd = new DatePickerDialog(ViewDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                scheduleDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                            }
                        },c_yr,c_month,c_day);
                        dp[0] = dpd.getDatePicker();
//                dp.setMinDate(System.currentTimeMillis()-10*24*60*60*1000);
//                dp.setMaxDate(System.currentTimeMillis());
                        dpd.show();
                    }
                });

                scheduleTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TimePickerDialog timepickerdialog = new TimePickerDialog(ViewDetailsActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {
                                        if (hourOfDay == 0) {
                                            hourOfDay += 12;
                                            format[0] = "AM";
                                        } else if (hourOfDay == 12) {
                                            format[0] = "PM";
                                        } else if (hourOfDay > 12) {
                                            hourOfDay -= 12;
                                            format[0] = "PM";
                                        } else {
                                            format[0] = "AM";
                                        }
                                        if (String.valueOf(hourOfDay).length()==1 && String.valueOf(minute).length()==1)
                                            scheduleTime.setText("0"+hourOfDay + ": 0"+ minute + format[0]);
                                        else  if (String.valueOf(hourOfDay).length()==1||String.valueOf(minute).length()==1)
                                            if (String.valueOf(hourOfDay).length()==1)
                                                scheduleTime.setText("0"+hourOfDay + ":" + minute + format[0]);
                                        if (String.valueOf(minute).length()==1)
                                            scheduleTime.setText(hourOfDay + ": 0" + minute + format[0]);
                                        else
                                            scheduleTime.setText(hourOfDay + ":" + minute + format[0]);
                                        tp[0] = view;
                                    }
                                }, c_hr, c_min, false);
                        timepickerdialog.show();
                    }
                });

                // get contact list
//                new RxPermissions(ViewDetailsActivity.this).request(Manifest.permission.READ_CONTACTS).subscribe();
                getContactList();

                //Set tag add callback before set tag list
//                countIpScl=0;
                editTagViewIP.setTagAddCallBack(new EditTag.TagAddCallback() {
                    @Override
                    public boolean onTagAdd(String tagValue) {
                        for (int i=0; i<ipContactSch.size(); i++) {
                            if (ipContactSch.get(i).equals(tagValue)) {
                                String userPk = userSearchListSdl.get(i).getPk();
                                array.put(Integer.parseInt(userPk));
//                                posIpSdl = i;
//                                countIpScl++;
                                return true;
                            }
                        }
                        return false;
                    }
                });
                editTagViewIP.setTagDeletedCallback(new EditTag.TagDeletedCallback() {
                    @Override
                    public void onTagDelete(String deletedTagValue) {
                        Toast.makeText(ViewDetailsActivity.this, deletedTagValue, Toast.LENGTH_SHORT).show();
                    }
                });

//                editTagViewIP.setTagList(ipContactSch);

                //Set tag add callback before set tag list
//                countCrmScl=0;
                editTagViewCRM.setTagAddCallBack(new EditTag.TagAddCallback() {
                    @Override
                    public boolean onTagAdd(String tagValue) {
                        for (int i=0; i<crmContactSch.size(); i++) {
                            if (crmContactSch.get(i).equals(tagValue)) {
                                String contactPk = contactLitesSdl.get(i).getPk();
                                array1.put(Integer.parseInt(contactPk));
//                                posCrmScl = i;
//                                countCrmScl++;
                                return true;
                            }
                        }
                        return false;
                    }
                });
                editTagViewCRM.setTagDeletedCallback(new EditTag.TagDeletedCallback() {
                    @Override
                    public void onTagDelete(String deletedTagValue) {
                        Toast.makeText(ViewDetailsActivity.this, deletedTagValue, Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog.Builder adb = new AlertDialog.Builder(ViewDetailsActivity.this);
                adb.setView(v);
                adb.setCancelable(false);
                final AlertDialog ad = adb.create();
                scheduleCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ad.dismiss();
                    }
                });

                scheduleSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date = scheduleDate.getText().toString();
                        String time = scheduleTime.getText().toString();
                        String place = scheduleLocation.getText().toString();
                        String eventDetails = scheduleEventDetails.getText().toString();
//        if (date.isEmpty()||time.isEmpty()){
//            return;
//        }
                        String inputRaw = dp[0].getYear()+"-"+ dp[0].getMonth()+"-"+ dp[0].getDayOfMonth()+"T"+ tp[0].getHour()+":"+ tp[0].getMinute()+":00.000Z";//date+" "+time
//        String input = inputRaw.replace( "/", "-" ).replace( " ", "T" );

//        SimpleDateFormat sdf_datetime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//        String date_time = sdf_datetime.format(new Date(Long.parseLong(date+" "+time)));

//                        JSONArray array = new JSONArray();
//                        try {
//                            for (int j=1; j<=countIpScl; j++) {
//                                String userPk = userSearchListSdl.get(posIpSdl).getPk();
//                                array.put(Integer.parseInt(userPk));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//
//                        JSONArray array1 = new JSONArray();
//                        try {
//                            for (int j=1; j<=countCrmScl; j++) {
//                                String contactPk = contactLitesSdl.get(posCrmScl).getPk();
//                                array1.put(Integer.parseInt(contactPk));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }

                        RequestParams params = new RequestParams();
                        params.put("venue",place);
                        params.put("text",eventDetails);
                        params.put("followers",array);
                        params.put("clients",array1);
                        params.put("eventType","Meeting");
                        params.put("originator","CRM");
                        params.put("when",inputRaw);//2018-06-19T10:19:37.931376Z

                        client.post(ServerUrl.url+"api/PIM/calendar/", params, new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                                Toast.makeText(ViewDetailsActivity.this, "posted", Toast.LENGTH_SHORT).show();

                                ad.dismiss();
                            }

                            @Override
                            public void onFinish() {
                                super.onFinish();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                super.onFailure(statusCode, headers, throwable, errorResponse);
                            }
                        });

                    }
                });
                ad.show();
            }
        });

        fabTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText taskDate, taskDetails;
                Button taskCancel, taskSave;
                final DatePicker[] dp = new DatePicker[1];
                final JSONArray array1 = new JSONArray();
                View v = getLayoutInflater().inflate(R.layout.layout_task_style, null, false);
                taskDate = v.findViewById(R.id.task_date);
//                taskOtherStake = v.findViewById(R.id.chips_input_os_task);
                editTagViewCRM = (EditTag) v.findViewById(R.id.edit_tag_view_crm);
                editTagViewCRM.setEditable(true);
                editTagCRM = (AutoCompleteTextView) v.findViewById(R.id.medit_tag_crm);
                taskDetails = v.findViewById(R.id.task_details);

                taskCancel= v.findViewById(R.id.task_cancel);
                taskSave = v.findViewById(R.id.task_save);

                taskDate.setFocusableInTouchMode(false);
                taskDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog dpd = new DatePickerDialog(ViewDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                taskDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                            }
                        },c_yr,c_month,c_day);
                        dp[0] = dpd.getDatePicker();
//                dp.setMinDate(System.currentTimeMillis()-10*24*60*60*1000);
//                dp.setMaxDate(System.currentTimeMillis());
                        dpd.show();
                    }
                });

                // get contact list
//                new RxPermissions(ViewDetailsActivity.this).request(Manifest.permission.READ_CONTACTS).subscribe();
                getContactListTask();

                //Set tag add callback before set tag list
                editTagViewCRM.setTagAddCallBack(new EditTag.TagAddCallback() {
                    @Override
                    public boolean onTagAdd(String tagValue) {
                        for (int i=0; i<crmContactTask.size(); i++) {
                            if (crmContactTask.get(i).equals(tagValue)) {
                                String contactPk = contactLitesTask.get(i).getPk();
                                array1.put(Integer.parseInt(contactPk));
//                                posCrmTask=i;
//                                countCrmTask++;
                                return true;
                            }
                        }
                        return false;
                    }
                });
                editTagViewCRM.setTagDeletedCallback(new EditTag.TagDeletedCallback() {
                    @Override
                    public void onTagDelete(String deletedTagValue) {
                        Toast.makeText(ViewDetailsActivity.this, deletedTagValue, Toast.LENGTH_SHORT).show();
                    }
                });

//                editTagViewCRM.setTagList(crmContactTask);

                // chips listener
//                taskOtherStake.addChipsListener(new ChipsInput.ChipsListener() {
//                    @Override
//                    public void onChipAdded(ChipInterface chip, int newSize) {
//                        Log.e(TAG, "chip added, " + newSize);
//                    }
//
//                    @Override
//                    public void onChipRemoved(ChipInterface chip, int newSize) {
//                        Log.e(TAG, "chip removed, " + newSize);
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence text) {
//                        Log.e(TAG, "text changed: " + text.toString());
//                    }
//                });
                AlertDialog.Builder adb = new AlertDialog.Builder(ViewDetailsActivity.this);
                adb.setView(v);
                adb.setCancelable(false);
                final AlertDialog ad = adb.create();
                taskCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ad.dismiss();
                    }
                });
                taskSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date = taskDate.getText().toString();
                        String eventDetails = taskDetails.getText().toString();
//        if (date.isEmpty()||time.isEmpty()){
//            return;
//        }
                        String inputRaw = dp[0].getYear()+"-"+ dp[0].getMonth()+"-"+ dp[0].getDayOfMonth()+"T"+ c.get(Calendar.HOUR_OF_DAY)+":"+ c.get(Calendar.MINUTE)+":00.000Z";//date+" "+time
//        String input = inputRaw.replace( "/", "-" ).replace( " ", "T" );

//        SimpleDateFormat sdf_datetime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//        String date_time = sdf_datetime.format(new Date(Long.parseLong(date+" "+time)));
//                        JSONArray array1 = new JSONArray();
//                        try {
//                            for (int j=1; j<=countCrmTask; j++) {
//                                String contactPk = contactLitesTask.get(posCrmTask).getPk();
//                                array1.put(Integer.parseInt(contactPk));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }

                        RequestParams params = new RequestParams();
                        params.put("text",eventDetails);
                        params.put("eventType","Reminder");
                        params.put("clients",array1);
                        params.put("originator","CRM");
                        params.put("when",inputRaw);//2018-06-19T10:19:37.931376Z

                        client.post(ServerUrl.url+"api/PIM/calendar/", params, new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                                Toast.makeText(ViewDetailsActivity.this, "posted", Toast.LENGTH_SHORT).show();

                                ad.dismiss();
                            }

                            @Override
                            public void onFinish() {
                                super.onFinish();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                super.onFailure(statusCode, headers, throwable, errorResponse);
                            }
                        });

                    }
                });
                ad.show();
            }
        });

        fabMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewDetailsActivity.this, MeetingActivity.class).putExtra("contactPk", cpk));
            }
        });

        fabNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImageView attachFile;
                final EditText noteDetails;
                Button noteCancel, noteSend;
                View v = getLayoutInflater().inflate(R.layout.layout_note_style, null, false);
                attachFile = v.findViewById(R.id.note_attach_file);
                noteDetails = v.findViewById(R.id.note_details);
                noteCancel = v.findViewById(R.id.note_cancel);
                noteSend = v.findViewById(R.id.note_send);

                attachFile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("*/*");
                        startActivityForResult(intent, 101);
                    }
                });

                AlertDialog.Builder adb = new AlertDialog.Builder(ViewDetailsActivity.this);
                adb.setView(v);
                adb.setCancelable(false);
                final AlertDialog ad = adb.create();
                noteCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ad.dismiss();
                    }
                });

                noteSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String details = noteDetails.getText().toString().trim();

                        if (details.isEmpty()){
                            return;
                        }
                        RequestParams params = new RequestParams();
                        params.put("contact",Integer.parseInt(cpk));
                        params.put("data",details);
                        params.put("typ","note");
//                        params.put("when",inputRaw);//2018-06-19T10:19:37.931376Z

                        client.post(ServerUrl.url+"api/clientRelationships/activity/", params, new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                                Toast.makeText(ViewDetailsActivity.this, "posted", Toast.LENGTH_SHORT).show();

                                ad.dismiss();
                            }

                            @Override
                            public void onFinish() {
                                super.onFinish();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                super.onFailure(statusCode, headers, throwable, errorResponse);
                            }
                        });
                    }
                });


                ad.show();
            }
        });
    }

    void findIds(){
        if (b!=null) {
            dp = b.getString("image");
            cname = b.getString("name");
            cpk = b.getString("pk");
            String company1 = b.getString("company");
            if (company1 == null) {
                company = "";
            } else {
                company = company1;
            }
            companyPk = b.getString("companyPk");
            designation = b.getString("designation");
            mobile = b.getString("mob");
            eMail = b.getString("email");
            final boolean gender = b.getBoolean("gender");
            String street1 = b.getString("street");
            if (street1 == null) {
                street = "";
            } else {
                street = street1;
            }
            String city1 = b.getString("city");
            if (city1==null) {
                city = "";
            } else {
                city = street1;
            }
            String pincode1 = b.getString("pincode");
            if (pincode1==null) {
                pincode = "";
            } else {
                pincode = pincode1;
            }
            String state1 = b.getString("state");
            if (state1==null) {
                state = "";
            } else {
                state = state1;
            }
            String country1 = b.getString("country");
            if (country1==null) {
                country = "";
            } else {
                country = country1;
            }
            telephone = b.getString("tel");
            cMobile = b.getString("companyNo");
            cin = b.getString("cin");
            tin = b.getString("tin");
            about = b.getString("about");
            web = b.getString("web");
        }


        nameTv = findViewById(R.id.view_d_name);
        companyTv = findViewById(R.id.view_d_comapany);
        designationTv = findViewById(R.id.view_d_designation);
        cnoTv = findViewById(R.id.view_mob_no);
        emailTv = findViewById(R.id.view_email);


        nameTv.setText(cname);
        companyTv.setText(company);
        designationTv.setText(designation);
        cnoTv.setText(mobile);
        emailTv.setText(eMail);

        fabView = findViewById(R.id.fab_view);
        fabSchedule = findViewById(R.id.fab_schedule);
        fabTask = findViewById(R.id.fab_task);
        fabMeeting = findViewById(R.id.fab_meeting);
        fabNotes = findViewById(R.id.fab_notes);

        layoutFabSchedule = (LinearLayout) this.findViewById(R.id.layoutFabSchedule);
        layoutFabTask = (LinearLayout) this.findViewById(R.id.layoutFabTask);
        layoutFabMeeting = (LinearLayout) this.findViewById(R.id.layoutFabMeeting);
        layoutFabNotes = (LinearLayout) this.findViewById(R.id.layoutFabNotes);

        rotate_forward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotate_Backward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);
        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close);
    }

    //closes FAB submenus
    private void closeSubMenusFab(){
        layoutFabSchedule.setVisibility(View.GONE);
        layoutFabTask.setVisibility(View.GONE);
        layoutFabMeeting.setVisibility(View.GONE);
        layoutFabNotes.setVisibility(View.GONE);
        fabView.setImageResource(R.drawable.ic_add);
        fabSchedule.startAnimation(fab_close);
        fabTask.setAnimation(fab_close);
        fabMeeting.setAnimation(fab_close);
        fabNotes.setAnimation(fab_close);
        fabView.startAnimation(rotate_Backward);
//        fabView.startAnimation(fab_open);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        layoutFabSchedule.setVisibility(View.VISIBLE);
        layoutFabTask.setVisibility(View.VISIBLE);
        layoutFabMeeting.setVisibility(View.VISIBLE);
        layoutFabNotes.setVisibility(View.VISIBLE);
        //Change settings icon to 'X' icon
//        fab.setImageResource(R.drawable.ic_close);
        fabSchedule.startAnimation(fab_open);
        fabTask.setAnimation(fab_open);
        fabMeeting.setAnimation(fab_open);
        fabNotes.setAnimation(fab_open);
        fabView.startAnimation(rotate_forward);
        fabExpanded = true;
    }

    private void getContactList() {
//        Cursor phones = this.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null,null,null, null);
//
//        // loop over all contacts
//        if(phones != null) {
//            while (phones.moveToNext()) {
//                // get contact info
//                String phoneNumber = null;
//                String id = phones.getString(phones.getColumnIndex(ContactsContract.Contacts._ID));
//                String name = phones.getString(phones.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                String avatarUriString = phones.getString(phones.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
//                Uri avatarUri = null;
//                if(avatarUriString != null)
//                    avatarUri = Uri.parse(avatarUriString);
//
//                // get phone number
//                if (Integer.parseInt(phones.getString(phones.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
//                    Cursor pCur = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                            null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { id }, null);
//
//                    while (pCur != null && pCur.moveToNext()) {
//                        phoneNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                    }
//
//                    pCur.close();
//
//                }
//
//                ContactChip contactChip = new ContactChip(id, avatarUri, name, phoneNumber);
//                // add contact to the list
//                mContactList.add(contactChip);
//            }
//            phones.close();
//        }
//
//        // pass contact list to chips input
//
//
//        if (fabSchedule.isClickable()) {
//            scheduleOS.setFilterableList(mContactList);
//            scheduleInternalPeople.setFilterableList(mContactList);
//        } else if (fabTask.isClickable()){
//            taskOtherStake.setFilterableList(mContactList);
//        }


        client.get(ServerUrl.url+"/api/clientRelationships/contact/", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i=0; i<response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        ContactLite c = new ContactLite(object);
                        contactLitesSdl.add(c);
                        String name = object.getString("name");
                        crmContactSch.add(name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(ViewDetailsActivity.this, android.R.layout.simple_dropdown_item_1line, crmContactSch);
                editTagCRM.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

        client.get(ServerUrl.url+"/api/HR/userSearch/", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i=0; i<response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        UserSearch userSearch = new UserSearch(object);
                        userSearchListSdl.add(userSearch);
                        String fname = object.getString("first_name");
                        String lname = object.getString("last_name");
                        String name = fname+" "+lname;
                        ipContactSch.add(name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(ViewDetailsActivity.this, android.R.layout.simple_dropdown_item_1line, ipContactSch);
                editTagIP.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void getContactListTask() {
        client.get(ServerUrl.url+"/api/clientRelationships/contact/", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i=0; i<response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        ContactLite c = new ContactLite(object);
                        contactLitesTask.add(c);
                        String name = object.getString("name");
                        crmContactTask.add(name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(ViewDetailsActivity.this, android.R.layout.simple_dropdown_item_1line, crmContactTask);
                editTagCRM.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101){
            if (resultCode == RESULT_OK){
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

