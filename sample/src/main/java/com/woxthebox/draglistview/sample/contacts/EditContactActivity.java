package com.woxthebox.draglistview.sample.contacts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.ServerUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class EditContactActivity extends Activity {
    ServerUrl serverUrl;
    LinearLayout showAdvanceDetails;
    EditText editFullName, editEmail, editMobNo, editEmailDuplicate, editMobNoDuplicate, editDesignation, editNotes, editLinkedin, editFb;
    AutoCompleteTextView editCompany;
    Button addNewCompany, updateCompany;
    ArrayList<Company> companiesList;
    public AsyncHttpClient client;
    TextView editDp, editDpAttach;
    Switch genderSwitch;
    ImageView switchProfile;
    Button saveEditContact;
    TextView arrowUp, arrowDown;

    EditText dialogTel, dialogAbout, dialogMob, dialogStreet, dialogCity, dialogState, dialogPincode, dialogCountry, dialogCIN, dialogTIN, dialogLogo, dialogWeb;
    Button saveDialogDetails;
    TextView dialog_arrowUp, dialog_arrowDown;
    LinearLayout dialog_showAdvanceDetails;

    public static String pk, name, street, city, state, pincode, country, email, mobile, designation, companyPk, company, telephone, cMobile, cin, tin, about, web;
    boolean gender, companyValid;
    ArrayList companyName;
    int pos;
    Bitmap bitmap = null;
    String pathHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        serverUrl = new ServerUrl(this);

//        c = getIntent().getSerializableExtra("contact");

        companiesList = new ArrayList<Company>();
        companyName = new ArrayList<String>();
        client = serverUrl.getHTTPClient();
        findAllIds();
        changeText();
        Bundle b = getIntent().getExtras();
        if (b!=null) {
            String image = b.getString("image");
            pk = b.getString("pk");
            name = b.getString("name");
            company = b.getString("company");
            companyPk = b.getString("companyPk");
            designation = b.getString("designation");
            mobile = b.getString("mob");
            email = b.getString("email");
            gender = b.getBoolean("gender");
            street = b.getString("street");
            city = b.getString("city");
            pincode = b.getString("pincode");
            state = b.getString("state");
            country = b.getString("country");
            telephone = b.getString("tel");
            cMobile = b.getString("companyNo");
            cin = b.getString("cin");
            tin = b.getString("tin");
            about = b.getString("about");
            web = b.getString("web");
        }
        genderSwitch.setChecked(gender);
        genderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    switchProfile.setImageResource(R.drawable.male);
                }
                else switchProfile.setImageResource(R.drawable.female);
            }
        });



        arrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrowUp.setVisibility(View.VISIBLE);
                arrowDown.setVisibility(View.GONE);
                showAdvanceDetails.setVisibility(View.VISIBLE);
            }
        });

        arrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrowDown.setVisibility(View.VISIBLE);
                arrowUp.setVisibility(View.GONE);
                showAdvanceDetails.setVisibility(View.GONE);
            }
        });

        editDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), 112);
            }
        });

        editFullName.setText(name);
        editCompany.setText(company);
        editDesignation.setText(designation);
        editMobNo.setText(mobile);
        editEmail.setText(email);
        for (int i=0; i<companyName.size();i++) {
            if (editCompany.getText().toString().equals(companyName.get(i))) {
                addNewCompany.setVisibility(View.GONE);
                updateCompany.setVisibility(View.VISIBLE);
            }
        }
    }

    public void findAllIds(){
        genderSwitch = findViewById(R.id.gender_sw);
        switchProfile = findViewById(R.id.switch_profile);

        editFullName = findViewById(R.id.edit_full_name);
        editEmail = findViewById(R.id.edit_email);
        editMobNo = findViewById(R.id.edit_mobile);
        editCompany = findViewById(R.id.edit_company);
        addNewCompany = findViewById(R.id.edit_add_new_company);
        updateCompany = findViewById(R.id.edit_update_company);

        arrowUp = findViewById(R.id.arrow_drop_up);
        arrowDown = findViewById(R.id.arrow_drop_down);
        showAdvanceDetails = findViewById(R.id.show_advance_ll);

        editEmailDuplicate = findViewById(R.id.edit_email_secondary);
        editMobNoDuplicate = findViewById(R.id.edit_mobile_secondary);
        editDesignation = findViewById(R.id.edit_designation);
        editNotes = findViewById(R.id.edit_notes);
        editLinkedin = findViewById(R.id.edit_linkedin);
        editFb = findViewById(R.id.edit_facebook);
        editDp = findViewById(R.id.edit_profile_photo);
        editDpAttach = findViewById(R.id.edit_dp_attached);
        saveEditContact = findViewById(R.id.edit_save_newContacts);
    }

    public void editAddNewCompany(View view){
        String companyName = editCompany.getText().toString().trim();
        RequestParams params = new RequestParams();
        params.put("name",companyName);
        params.put("user",1);
        client.post(ServerUrl.url+"/api/ERP/service/",params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                addNewCompany.setVisibility(View.GONE);
                updateCompany.setVisibility(View.VISIBLE);
                try {
                    String pk = response.getString("pk");
                    String name = response.getString("name");
                    String created = response.getString("created");
                    Company c = new Company();
                    c.save(pk,name,created);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("addedNewCompany","onFailure "+statusCode);
            }
        });
    }

    public void changeText(){

        String serverURL = serverUrl.url+"api/ERP/service/?format=json";
        client.get(serverURL, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for(int i=0; i<response.length(); i++){
                    try {
                        JSONObject json = response.getJSONObject(i);
                        Company company = new Company(json);
                        companiesList.add(company);
                        companyName.add(company.getCompanyPk());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(EditContactActivity.this, android.R.layout.simple_dropdown_item_1line, companyName);
                editCompany.setAdapter(arrayAdapter);
            }

            @Override
            public void onFinish() {
                System.out.println("finished EditContact");
                Log.e("NewContactActivity","onFinish");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("finished failed EditContact");
                Log.e("NewContactActivity","onFailure");
            }
        });

        editCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                companyName.clear();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addNewCompany.setVisibility(View.VISIBLE);
                updateCompany.setVisibility(View.GONE);
                companyValid = false;
//                client.get(ServerUrl.url+"/api/ERP/service/?name__contains="+s.toString()+"&format=json", new JsonHttpResponseHandler(){
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                        super.onSuccess(statusCode, headers, response);
//                        for(int i=0; i<response.length(); i++){
//                            try {
//                                JSONObject json = response.getJSONObject(i);
//                                String name = json.getString("name");
//                                companyName.add(name);
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        System.out.println("finished EditContact");
//                        Log.e("NewContactActivity","onFinish");
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                        super.onFailure(statusCode, headers, throwable, errorResponse);
//                        System.out.println("finished failed EditContact");
//                        Log.e("NewContactActivity","onFailure");
//                    }
//                });
                for (int i=0; i<companiesList.size(); i++){
                    Company company = (Company) companiesList.get(i);
                    if (s.toString().equals(company.getCompanyName())){
                        addNewCompany.setVisibility(View.GONE);
                        updateCompany.setVisibility(View.VISIBLE);
                        pos = i;
                        companyValid = true;
                    }
                }
                if (s.toString().equals("")){
                    addNewCompany.setVisibility(View.GONE);
                    updateCompany.setVisibility(View.GONE);
                    companyValid = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void editUpdateNewCompany(View view){

        View v = getLayoutInflater().inflate(R.layout.dialog_edit_update_company, null, false);
        dialogTel = v.findViewById(R.id.dialog_new_telephone);
        dialogAbout = v.findViewById(R.id.dialog_new_about);
        dialog_arrowUp = v.findViewById(R.id.dialog_arrow_drop_up);
        dialog_arrowDown = v.findViewById(R.id.dialog_arrow_drop_down);
        dialog_showAdvanceDetails = v.findViewById(R.id.dialog_show_advance_ll);

        dialogMob = v.findViewById(R.id.dialog_new_mob);
        dialogStreet = v.findViewById(R.id.dialog_new_street);
        dialogCity = v.findViewById(R.id.dialog_new_city);
        dialogState = v.findViewById(R.id.dialog_new_state);
        dialogPincode = v.findViewById(R.id.dialog_new_pincode);
        dialogCountry = v.findViewById(R.id.dialog_new_country);
        dialogCIN = v.findViewById(R.id.dialog_new_cin);
        dialogTIN = v.findViewById(R.id.dialog_new_tin);
        dialogLogo = v.findViewById(R.id.dialog_new_logo);
        dialogWeb = v.findViewById(R.id.dialog_new_web);
        saveDialogDetails = v.findViewById(R.id.dialog_new_save);

        final Company c = companiesList.get(pos);
        final String pk = c.getCompanyPk();
        dialogTel.setText(""+c.getTelephone());
        dialogAbout.setText(""+c.getAbout());
        dialogMob.setText(""+c.getCompanyMobile());
        dialogStreet.setText(""+c.getStreet());
        dialogCity.setText(""+c.getCity());
        dialogState.setText(""+c.getState());
        dialogPincode.setText(""+c.getPincode());
        dialogCountry.setText(""+c.getCountry());
        dialogCIN.setText(""+c.getCin());
        dialogTIN.setText(""+c.getTin());
        dialogWeb.setText(""+c.getWeb());

        dialog_arrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_arrowUp.setVisibility(View.VISIBLE);
                dialog_arrowDown.setVisibility(View.GONE);
                dialog_showAdvanceDetails.setVisibility(View.VISIBLE);
            }
        });

        dialog_arrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_arrowDown.setVisibility(View.VISIBLE);
                dialog_arrowUp.setVisibility(View.GONE);
                dialog_showAdvanceDetails.setVisibility(View.GONE);
            }
        });

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setView(v);
        adb.setCancelable(false);
        final AlertDialog ad = adb.create();
        saveDialogDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tel = dialogTel.getText().toString().trim();
                final String about = dialogAbout.getText().toString().trim();
                final String mob = dialogMob.getText().toString().trim();
                String street = dialogStreet.getText().toString().trim();
                String city = dialogCity.getText().toString().trim();
                String state = dialogState.getText().toString().trim();
                String pincode = dialogPincode.getText().toString().trim();
                String country = dialogCountry.getText().toString().trim();
                final String cin = dialogCIN.getText().toString().trim();
                final String tin = dialogTIN.getText().toString().trim();
                final String web = dialogWeb.getText().toString().trim();
                JSONObject jsonAddress = null;
                try {
                    jsonAddress = new JSONObject();
                    jsonAddress.put("street", street);
                    jsonAddress.put("city", city);
                    jsonAddress.put("pincode", pincode);
                    jsonAddress.put("country", country);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (c.getAddressPk() == null) {
                    RequestParams paramsAdd = new RequestParams();
                    paramsAdd.put("street", street);
//                paramsAdd.put("city", city);
//                paramsAdd.put("pincode", pincode);
//                paramsAdd.put("country", country);
                    client.post(ServerUrl.url + "/api/ERP/address/", paramsAdd, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                String addPk = response.getString("pk");
                                String street = response.getString("street");
                                String city = response.getString("city");
                                String state = response.getString("state");
                                String pincode = response.getString("pincode");
                                String lat = response.getString("lat");
                                String lon = response.getString("lon");
                                String country = response.getString("country");
                                Company company = companiesList.get(pos);
                                company.company_Address(addPk, street, city, state, pincode, lat, lon, country);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Log.e("updatedNewAddress", "onFailure " + statusCode);
                        }
                    });
                } else {
                    RequestParams paramsAdd = new RequestParams();
                    paramsAdd.put("street", street);
                    paramsAdd.put("pk", c.getAddressPk());
                    paramsAdd.put("city", city);
                    paramsAdd.put("state", state);
                    paramsAdd.put("lat", "");
                    paramsAdd.put("lon", "");
                    paramsAdd.put("pincode", pincode);
                    paramsAdd.put("country", country);
                    client.patch(ServerUrl.url + "/api/ERP/address/"+c.getAddressPk()+"/", paramsAdd, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Toast.makeText(EditContactActivity.this, "Saved Address", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Log.e("updatedNewCompany","onFailure "+statusCode);
                        }
                    });
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RequestParams params = new RequestParams();
                        params.put("pk", pk);
                        params.put("created", c.getCreated());
                        params.put("name", c.getCompanyName());
                        params.put("user",1);
                        params.put("telephone", tel);
                        params.put("about", about);
                        params.put("address", c.getAddressPk());
                        params.put("mobile", mob);
                        params.put("cin", cin);
                        params.put("doc", "");
                        params.put("logo", "");
                        params.put("tin", tin);
                        params.put("web", web);
                        client.patch(ServerUrl.url + "/api/ERP/service/"+pk+"/", params, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                Toast.makeText(EditContactActivity.this, "Saved Company", Toast.LENGTH_SHORT).show();
                                ad.dismiss();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                Log.e("updatedNewCompany","onFailure "+statusCode);
                            }
                        });
                    }
                },1000);

            }
        });
        ad.show();
    }


    public void editSaveDetails(View v) {
        String fullName = editFullName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String mobile = editMobNo.getText().toString().trim();
        String company = editCompany.getText().toString().trim();
        String emailDuplicate = editEmailDuplicate.getText().toString().trim();
        String mobNoDuplicate = editMobNoDuplicate.getText().toString().trim();
        String designation = editDesignation.getText().toString().trim();
        String notes = editNotes.getText().toString().trim();
        String linkedinLink = editLinkedin.getText().toString().trim();
        String fbLink = editFb.getText().toString().trim();

        if (fullName.isEmpty()) {
            editFullName.setError("Enter full name.");
            editFullName.requestFocus();
        } else {
//            if (email.isEmpty()) {
//                newEmail.setError("Enter email-id.");
//                newEmail.requestFocus();
//            } else {
//                if (mobile.isEmpty()) {
//                    newMobNo.setError("Enter mobile no.");
//                    newMobNo.requestFocus();
//                } else {
//                    if (company.isEmpty()) {
//                        newCompany.setError("Enter company name.");
//                        newCompany.requestFocus();
//                    } else {

//                        try {
//                            jsonCompany.put("name",company);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
            Company c = companiesList.get(pos);
//            JSONObject jsonAddress = null;
//            try {
//                jsonAddress = new JSONObject();
//                jsonAddress.put("street", c.getStreet());
//                jsonAddress.put("city", c.getCity());
//                jsonAddress.put("pincode", c.getPincode());
//                jsonAddress.put("country", c.getCountry());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            JSONObject jsonCompany = null;
//            try {
//                jsonCompany = new JSONObject();
//                jsonCompany.put("pk", c.getCompanyPk());
//                jsonCompany.put("created", c.getCreated());
//                jsonCompany.put("name", c.getCompanyName());
//                jsonCompany.put("telephone", c.getTelephone());
//                jsonCompany.put("about", c.getAbout());
//                jsonCompany.put("address", jsonAddress);
//                jsonCompany.put("mobile", c.getCompanyMobile());
//                jsonCompany.put("cin", c.getCin());
//                jsonCompany.put("tin", c.getTin());
//                jsonCompany.put("web", c.getWeb());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            RequestParams params = new RequestParams();
            params.put("male",genderSwitch.isChecked());
            params.put("user",1);
            params.put("name", fullName);
            params.put("email", email);
            params.put("mobile", mobile);
            if (companyValid)
                params.put("company", c.getCompanyPk());
            else
                params.put("company", 0);
            params.put("emailSecondary", emailDuplicate);
            params.put("mobileSecondary", mobNoDuplicate);
            params.put("designation", designation);
            params.put("notes", notes);
            params.put("linkedin", linkedinLink);
            params.put("facebook", fbLink);
            if (bitmap!=null){
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100, output);
                byte[] image = output.toByteArray();
                params.put("dp", new ByteArrayInputStream(image), pathHolder+".jpeg");
            } else {
                params.put("dp", "");
            }

            client.patch(serverUrl.url+"/api/clientRelationships/contact/"+pk+"/", params, new AsyncHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(EditContactActivity.this, "Saved Contact", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e("updatedContact","onFailure "+statusCode);
                }
            });

//                    }
//                }
//            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 112){
            if (resultCode == RESULT_OK){
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    pathHolder = data.getData().getPath();
                    editDpAttach.setVisibility(View.VISIBLE);
                    editDpAttach.setText("Attached");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
