package com.woxthebox.draglistview.sample.contacts;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.irshulx.Editor;
import com.github.irshulx.EditorListener;
import com.github.irshulx.models.EditorTextStyle;
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
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import me.originqiu.library.EditTag;
import me.originqiu.library.MEditText;

//import me.originqiu.library.EditTag;


public class MeetingActivity extends AppCompatActivity {

    EditText meetingDate, meetingTime, meetingDuration, meetingPlace;
    Button meetingSave;
    ImageView decrease,increase;
    int c_yr, c_month, c_day, c_hr, c_min;
    String format;
    Editor editor;
    EditTag editTagViewIP, editTagViewCRM;
    AutoCompleteTextView editTagIP, editTagCRM;

    public AsyncHttpClient client;
    ServerUrl serverUrl;
    private static final String TAG = MeetingActivity.class.toString();
//    @BindView(R.id.chips_input_ip)
//    ChipsInput mChipsInputIP;
//    @BindView(R.id.chips_input_crm)
//    ChipsInput mChipsInputCRM;
//    private List<ContactChip> mContactList;
    private List<String> ipContact = new ArrayList<>();
    private List<String> crmContact = new ArrayList<>();
    private List<ContactLite> contactLites;
    private List<UserSearch> userSearchList;
    int posIp, posCrm, countIp, countCrm;
    String pk;
    DatePicker dp;
    TimePicker tp;
    JSONArray array = new JSONArray();
    JSONArray array1 = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        getSupportActionBar().hide();
        pk = getIntent().getExtras().getString("contactPk");

        serverUrl = new ServerUrl(this);
        client = serverUrl.getHTTPClient();
        contactLites = new ArrayList<>();
        userSearchList = new ArrayList<>();

        editTagViewIP = (EditTag) findViewById(R.id.edit_tag_view_ip);
        editTagViewIP.setEditable(true);
        editTagViewCRM = (EditTag) findViewById(R.id.edit_tag_view_crm);
        editTagViewCRM.setEditable(true);
//        mEditText = (MEditText) findViewById(R.id.medit_tag);
        editTagIP = (AutoCompleteTextView) findViewById(R.id.medit_tag_ip);
        editTagCRM = (AutoCompleteTextView) findViewById(R.id.medit_tag_crm);




        meetingDate = findViewById(R.id.meeting_date);
        meetingTime = findViewById(R.id.meeting_time);
        meetingDuration = findViewById(R.id.meeting_duration);
        decrease = findViewById(R.id.decrease_duration);
        increase = findViewById(R.id.increase_duration);

        meetingPlace = findViewById(R.id.meeting_place);
        meetingSave = findViewById(R.id.meeting_save);

        meetingDuration.setText("0");
        clickMethods();
        editor = (Editor) findViewById(R.id.meeting_editor);
        setUpEditor();

        ButterKnife.bind(this);
//        mContactList = new ArrayList<>();

        // get contact list
//        new RxPermissions(this).request(Manifest.permission.READ_CONTACTS).subscribe();
        getContactList();

        //Set tag add callback before set tag list
        editTagViewIP.setTagAddCallBack(new EditTag.TagAddCallback() {
            @Override
            public boolean onTagAdd(String tagValue) {
//                countIp=0;
                for (int i=0; i<ipContact.size();i++) {
                    if (ipContact.get(i).equals(tagValue)) {
                        String userPk = userSearchList.get(i).getPk();
                        array.put(Integer.parseInt(userPk));
//                        posIp = i;
//                        countIp++;
                        return true;
                    }
                }
                return false;
            }
        });
        editTagViewIP.setTagDeletedCallback(new EditTag.TagDeletedCallback() {
            @Override
            public void onTagDelete(String deletedTagValue) {
                for (int i=0; i<ipContact.size();i++) {
                    if (ipContact.get(i).equals(deletedTagValue)) {
                        String userPk = userSearchList.get(i).getPk();
                        array.put(Integer.parseInt(userPk));
//                        posIp = i;
//                        countIp++;
                    }
                }
                Toast.makeText(MeetingActivity.this, deletedTagValue, Toast.LENGTH_SHORT).show();
            }
        });

        editTagViewIP.setTagList(ipContact);

        //Set tag add callback before set tag list
        editTagViewCRM.setTagAddCallBack(new EditTag.TagAddCallback() {
            @Override
            public boolean onTagAdd(String tagValue) {
//                countCrm=0;
                for (int i=0; i<crmContact.size();i++) {
                    if (crmContact.get(i).equals(tagValue)) {
                        String contactPk = contactLites.get(i).getPk();
                        array1.put(Integer.parseInt(contactPk));
//                        posCrm = i;
//                        countCrm++;
                        return true;
                    }
                }
                return false;
            }
        });
        editTagViewCRM.setTagDeletedCallback(new EditTag.TagDeletedCallback() {
            @Override
            public void onTagDelete(String deletedTagValue) {
                for (int i=0; i<crmContact.size();i++) {
                    if (crmContact.get(i).equals(deletedTagValue)) {
                        String contactPk = contactLites.get(i).getPk();
                        array1.remove(Integer.parseInt(contactPk));
//                        posCrm = i;
//                        countCrm++;
                    }
                }
                Toast.makeText(MeetingActivity.this, deletedTagValue, Toast.LENGTH_SHORT).show();
            }
        });

        editTagViewCRM.setTagList(crmContact);

        // chips listener
//        mChipsInputIP.addChipsListener(new ChipsInput.ChipsListener() {
//            @Override
//            public void onChipAdded(ChipInterface chip, int newSize) {
//                Log.e(TAG, "chip added, " + newSize);
//            }
//
//            @Override
//            public void onChipRemoved(ChipInterface chip, int newSize) {
//                Log.e(TAG, "chip removed, " + newSize);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence text) {
//                Log.e(TAG, "text changed: " + text.toString());
//            }
//        });




        // chips listener
//        mChipsInputCRM.addChipsListener(new ChipsInput.ChipsListener() {
//            @Override
//            public void onChipAdded(ChipInterface chip, int newSize) {
//                Log.e(TAG, "chip added, " + newSize);
//            }
//
//            @Override
//            public void onChipRemoved(ChipInterface chip, int newSize) {
//                Log.e(TAG, "chip removed, " + newSize);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence text) {
//                Log.e(TAG, "text changed: " + text.toString());
//            }
//        });

    }

    private void clickMethods(){
        Calendar c = Calendar.getInstance();
        c_yr = c.get(Calendar.YEAR);
        c_month = c.get(Calendar.MONTH);
        c_day = c.get(Calendar.DAY_OF_MONTH);
        c_hr = c.get(Calendar.HOUR_OF_DAY);
        c_min = c.get(Calendar.MINUTE);

        meetingDate.setFocusableInTouchMode(false);
        meetingTime.setFocusableInTouchMode(false);

        meetingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(MeetingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        meetingDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },c_yr,c_month,c_day);
                dp = dpd.getDatePicker();
//                dp.setMinDate(System.currentTimeMillis()-10*24*60*60*1000);
//                dp.setMaxDate(System.currentTimeMillis());
                dpd.show();
            }
        });

        meetingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(MeetingActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                if (hourOfDay == 0) {
                                    hourOfDay += 12;
                                    format = "AM";
                                } else if (hourOfDay == 12) {
                                    format = "PM";
                                } else if (hourOfDay > 12) {
                                    hourOfDay -= 12;
                                    format = "PM";
                                } else {
                                    format = "AM";
                                }
                                if (String.valueOf(hourOfDay).length()==1 && String.valueOf(minute).length()==1)
                                    meetingTime.setText("0"+hourOfDay + ": 0"+ minute + format);
                                else  if (String.valueOf(hourOfDay).length()==1||String.valueOf(minute).length()==1)
                                    if (String.valueOf(hourOfDay).length()==1)
                                        meetingTime.setText("0"+hourOfDay + ":" + minute + format);
                                    if (String.valueOf(minute).length()==1)
                                        meetingTime.setText(hourOfDay + ": 0" + minute + format);
                                else
                                    meetingTime.setText(hourOfDay + ":" + minute + format);
                                tp = view;
                            }
                        }, c_hr, c_min, false);

                tpd.show();
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = meetingDuration.getText().toString().trim();
                int duration = Integer.parseInt(s);
                duration--;
                meetingDuration.setText(String.valueOf(duration));
            }
        });

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = meetingDuration.getText().toString().trim();
                int duration = Integer.parseInt(s);
                duration++;
                meetingDuration.setText(String.valueOf(duration));
            }
        });

    }

    public void setUpEditor() {
        findViewById(R.id.action_h1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.H1);
            }
        });

        findViewById(R.id.action_h2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.H2);
            }
        });

        findViewById(R.id.action_h3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.H3);
            }
        });

//        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.updateTextStyle(EditorTextStyle.BOLD);
//            }
//        });

        findViewById(R.id.action_Italic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.ITALIC);
            }
        });

        findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.INDENT);
            }
        });

        findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.OUTDENT);
            }
        });

        findViewById(R.id.action_bulleted).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.insertList(false);
            }
        });

        findViewById(R.id.action_unordered_numbered).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.insertList(true);
            }
        });

        findViewById(R.id.action_hr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.insertDivider();
            }
        });

        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.openImagePicker();
            }
        });

        findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.insertLink();
            }
        });

//        findViewById(R.id.action_map).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.insertMap();
//            }
//        });

        findViewById(R.id.action_erase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clearAllContents();
            }
        });
        //editor.dividerBackground=R.drawable.divider_background_dark;
        //editor.setFontFace(R.string.fontFamily__serif);
//        Map<Integer, String> headingTypeface = getHeadingTypeface();
//        Map<Integer, String> contentTypeface = getContentface();
//        editor.setHeadingTypeface(headingTypeface);
//        editor.setContentTypeface(contentTypeface);
//        editor.setDividerLayout(R.layout.tmpl_divider_layout);
//        editor.setEditorImageLayout(R.layout.tmpl_image_view);
//        editor.setListItemLayout(R.layout.tmpl_list_item);
//        //editor.StartEditor();
//        editor.setEditorListener(new EditorListener() {
//            @Override
//            public void onTextChanged(EditText editText, Editable text) {
//                // Toast.makeText(EditorTestActivity.this, text, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onUpload(Bitmap image, String uuid) {
//                Toast.makeText(MeetingActivity.this, uuid, Toast.LENGTH_LONG).show();
//                editor.onImageUploadComplete("http://www.videogamesblogger.com/wp-content/uploads/2015/08/metal-gear-solid-5-the-phantom-pain-cheats-640x325.jpg", uuid);
//                // editor.onImageUploadFailed(uuid);
//            }
//        });
        editor.render();  // this method must be called to start the editor
//        findViewById(R.id.btnRender).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /*
//                Retrieve the content as serialized, you could also say getContentAsHTML();
//                */
//                String text = editor.getContentAsSerialized();
//                Intent intent = new Intent(getApplicationContext(), RenderTestActivity.class);
//                intent.putExtra("content", text);
//                startActivity(intent);
//            }
//        });
    }

    public void saveMeeting(View v){
        String date = meetingDate.getText().toString();
        String time = meetingTime.getText().toString();
        String duration = meetingDuration.getText().toString();
        String place = meetingPlace.getText().toString();
        String editorText = editor.getContentAsSerialized();
//        if (date.isEmpty()||time.isEmpty()){
//            return;
//        }
        String inputRaw = dp.getYear()+"-"+dp.getMonth()+"-"+dp.getDayOfMonth()+"T"+ tp.getHour()+":"+tp.getMinute()+":00.000Z";//date+" "+time
//        String input = inputRaw.replace( "/", "-" ).replace( " ", "T" );

//        SimpleDateFormat sdf_datetime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//        String date_time = sdf_datetime.format(new Date(Long.parseLong(date+" "+time)));
        JSONObject object = new JSONObject();
        try {
            object.put("duration", duration);
            object.put("location", place);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        JSONArray array = new JSONArray();
//        try {
//            for (int j=1; j<=countIp; j++) {
//                String userPk = userSearchList.get(posIp).getPk();
//                array.put(Integer.parseInt(userPk));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        JSONArray array1 = new JSONArray();
//        try {
//            for (int j=1; j<=countCrm; j++) {
//                String contactPk = contactLites.get(posCrm).getPk();
//                array1.put(Integer.parseInt(contactPk));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        RequestParams params = new RequestParams();
        params.put("contact",Integer.parseInt(pk));
        params.put("data",object);
        params.put("internalUsers",array);
        params.put("contacts",array1);
        params.put("typ","meeting");
        params.put("when",inputRaw);//2018-06-19T10:19:37.931376Z

        client.post(ServerUrl.url+"api/clientRelationships/activity/", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Toast.makeText(MeetingActivity.this, "posted", Toast.LENGTH_SHORT).show();

                finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == editor.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                editor.insertImage(bitmap);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            //Write your code if there's no result
            Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            // editor.RestoreState();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit Meeting?")
                .setMessage("Are you sure you want to exit the meeting?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
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

        client.get(ServerUrl.url+"/api/HR/userSearch/", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i=0; i<response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String fname = object.getString("first_name");
                        String lname = object.getString("last_name");
                        UserSearch userSearch = new UserSearch(object);
                        userSearchList.add(userSearch);
                        String name = fname+" "+lname;
                        ipContact.add(name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(MeetingActivity.this, android.R.layout.simple_dropdown_item_1line, ipContact);
                editTagIP.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


        client.get(ServerUrl.url+"/api/clientRelationships/contactLite/", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i=0; i<response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        ContactLite c = new ContactLite(object);
                        contactLites.add(c);
                        String name = object.getString("name");
                        crmContact.add(name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(MeetingActivity.this, android.R.layout.simple_dropdown_item_1line, crmContact);
                editTagCRM.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });




        // pass contact list to chips input
//        mChipsInputIP.setFilterableList(mContactList);
//        mChipsInputCRM.setFilterableList(mContactList);
    }

}
