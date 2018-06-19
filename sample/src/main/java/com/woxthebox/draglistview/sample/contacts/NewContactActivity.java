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
import com.loopj.android.http.ResponseHandlerInterface;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.ServerUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class NewContactActivity extends Activity {

    LinearLayout showAdvanceDetails;
    EditText newFullName, newEmail, newMobNo, newEmailDuplicate, newMobNoDuplicate, newDesignation, newNotes, newLinkedin, newFb;
    AutoCompleteTextView newCompany;
    Button addNewCompany, updateCompany;
//    String items[] = {"CIOC FMCG Pvt Ltd","First Choice Yard Help","Muscle Factory","ABC Pvt Ltd","DXC Technology"};
    ArrayList<Company> companiesList;
    TextView newDp, newDpAttach;
    Button saveNewContact;
    Switch genderSwitch;
    ImageView switchProfile;
    TextView arrowUp, arrowDown;
    EditText dialogTel, dialogAbout, dialogMob, dialogStreet, dialogCity, dialogState, dialogPincode, dialogCountry, dialogCIN, dialogTIN, dialogLogo, dialogWeb;

    Button saveDialogDetails;
    TextView dialog_arrowUp, dialog_arrowDown;
    LinearLayout dialog_showAdvanceDetails;
    String name, cno;
    ArrayList companyName;
    Company c;
    int pos;
//    JSONObject jsonCompany = new JSONObject();
    Bitmap bitmap = null;
    String pathHolder;

    public AsyncHttpClient client;
    ServerUrl serverUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        serverUrl = new ServerUrl();
        client = serverUrl.getHTTPClient();
        companiesList = new ArrayList<Company>();
        companyName = new ArrayList();

        Bundle b = getIntent().getExtras();
        if (b != null){
            name = b.getString("name");
            cno = b.getString("cno");
        }

        findAllIds();

        genderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    switchProfile.setImageResource(R.drawable.male);
                }
                else switchProfile.setImageResource(R.drawable.female);
            }
        });


        addCompany();
//        editUpdateCompony();

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



        newDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), 111);
            }
        });

    }

    public void findAllIds(){
        genderSwitch = findViewById(R.id.gender_sw);
        switchProfile = findViewById(R.id.switch_profile);

        newFullName = findViewById(R.id.contacts_full_name);
        newEmail = findViewById(R.id.contacts_email);
        newMobNo = findViewById(R.id.contacts_mobile);
        newCompany = findViewById(R.id.contacts_company);
        addNewCompany = findViewById(R.id.add_new_company);
        updateCompany = findViewById(R.id.update_company);

        arrowUp = findViewById(R.id.arrow_drop_up);
        arrowDown = findViewById(R.id.arrow_drop_down);
        showAdvanceDetails = findViewById(R.id.show_advance_ll);

        newEmailDuplicate = findViewById(R.id.contacts_email_secondary);
        newMobNoDuplicate = findViewById(R.id.contacts_mobile_secondary);
        newDesignation = findViewById(R.id.contacts_designation);
        newNotes = findViewById(R.id.contacts_notes);
        newLinkedin = findViewById(R.id.contacts_linkedin);
        newFb = findViewById(R.id.contacts_facebook);
        newDp = findViewById(R.id.contact_profile_photo);
        newDpAttach = findViewById(R.id.dp_attached);
        saveNewContact = findViewById(R.id.save_newContacts);

        newFullName.setText(name);
        newMobNo.setText(cno);
    }

    public void addCompany(){
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
                        companyName.add(company.getCompanyName());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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


        ArrayAdapter arrayAdapter = new ArrayAdapter(NewContactActivity.this, android.R.layout.simple_dropdown_item_1line, companyName);
        newCompany.setAdapter(arrayAdapter);

        newCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                addNewCompany.setVisibility(View.VISIBLE);
                updateCompany.setVisibility(View.GONE);
                for (int i=0; i<companiesList.size(); i++){
                    Company c = (Company) companiesList.get(i);
                    if (s.toString().equals(c.getCompanyName())){
                        pos = i;
                        addNewCompany.setVisibility(View.GONE);
                        updateCompany.setVisibility(View.VISIBLE);
//
                    }
                }
                if (s.toString().equals("")){
                    addNewCompany.setVisibility(View.GONE);
                    updateCompany.setVisibility(View.GONE);
                }
            }
        });
    }

    public void addedNewCompany(View view){
        String companyName = newCompany.getText().toString().trim();
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

    public void updateNewCompany(View view){
        final String companyName = newCompany.getText().toString().trim();

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
                if (c.getAddressPk()==null){
                RequestParams paramsAdd = new RequestParams();
                paramsAdd.put("street", street);
//                paramsAdd.put("city", city);
//                paramsAdd.put("pincode", pincode);
//                paramsAdd.put("country", country);
                client.post(ServerUrl.url+"/api/ERP/address/", paramsAdd, new JsonHttpResponseHandler(){
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
                            Company company = new Company();
                            company.company_Address(addPk,street,city,state,pincode,lat,lon,country);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.e("updatedNewAddress","onFailure "+statusCode);
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
                        Toast.makeText(NewContactActivity.this, "Saved Address", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(NewContactActivity.this, "Saved Company", Toast.LENGTH_SHORT).show();
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

    public void saveDetails(View v) {
        String fullName = newFullName.getText().toString().trim();
        String email = newEmail.getText().toString().trim();
        String mobile = newMobNo.getText().toString().trim();
        String company = newCompany.getText().toString().trim();
        String emailDuplicate = newEmailDuplicate.getText().toString().trim();
        String mobNoDuplicate = newMobNoDuplicate.getText().toString().trim();
        String designation = newDesignation.getText().toString().trim();
        String notes = newNotes.getText().toString().trim();
        String linkedinLink = newLinkedin.getText().toString().trim();
        String fbLink = newFb.getText().toString().trim();

        if (fullName.isEmpty()) {
            newFullName.setError("Enter full name.");
            newFullName.requestFocus();
        } else {
            Company c = companiesList.get(pos);
            JSONObject jsonAddress = null;
            try {
                jsonAddress = new JSONObject();
                jsonAddress.put("street", c.getStreet());
                jsonAddress.put("city", c.getCity());
                jsonAddress.put("pincode", c.getPincode());
                jsonAddress.put("country", c.getCountry());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject jsonCompany = null;
            try {
                jsonCompany = new JSONObject();
                jsonCompany.put("pk", c.getCompanyPk());
                jsonCompany.put("created", c.getCreated());
                jsonCompany.put("name", c.getCompanyName());
                jsonCompany.put("telephone", c.getTelephone());
                jsonCompany.put("about", c.getAbout());
                jsonCompany.put("address", jsonAddress);
                jsonCompany.put("mobile", c.getCompanyMobile());
                jsonCompany.put("cin", c.getCin());
                jsonCompany.put("tin", c.getTin());
                jsonCompany.put("web", c.getWeb());
            } catch (JSONException e) {
                e.printStackTrace();
            }
                        RequestParams params = new RequestParams();
                        params.put("male",genderSwitch.isChecked());
                        params.put("user",1);
                        params.put("name", fullName);
                        params.put("email", email);
                        params.put("mobile", mobile);
                        params.put("company", c.getCompanyPk());
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
                        client.post(serverUrl.url+"/api/clientRelationships/contact/", params, new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                                Toast.makeText(NewContactActivity.this, "saved", Toast.LENGTH_SHORT).show();
                                new Contact(response);
                                finish();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                super.onFailure(statusCode, headers, throwable, errorResponse);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                super.onFailure(statusCode, headers, responseString, throwable);
                            }
                        });
            }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111){
            if (resultCode == RESULT_OK){
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    pathHolder = data.getData().getPath();
                    newDpAttach.setVisibility(View.VISIBLE);
                    newDpAttach.setText("Attached");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
