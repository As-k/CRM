package com.woxthebox.draglistview.sample.opportunities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.irshulx.Editor;
import com.github.irshulx.models.EditorTextStyle;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.ServerUrl;
import com.woxthebox.draglistview.sample.UserSearch;
import com.woxthebox.draglistview.sample.contacts.Company;
import com.woxthebox.draglistview.sample.contacts.ContactLite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import me.originqiu.library.EditTag;

public class NewOpportunityActivity extends AppCompatActivity {

    EditText oppoName, oppoCloseDate, oppoValuation;
    AutoCompleteTextView oppoCompany;
    EditTag editTagViewInternal, editTagViewCustomer;
    Spinner oppoCurrency, oppoState;
    AutoCompleteTextView editTagInternal, editTagCustomer;
    TextView minConfidence, maxConfidence;
    SeekBar confidenceSeekBar;
    String[] currencies = new String[]{"INR", "USD"};
    String[] states = {"Contacting","Demo / POC","Requirements","Proposal","Negotiation","Won / Lost"};
    String stateParams[] = {"contacted","demo","requirements","proposal","negotiation","conclusion"};
    int c_yr, c_month, c_day;

    Editor oppoRequirementEditor;
    public AsyncHttpClient client;
    ServerUrl serverUrl;
    ArrayList companyName;
    ArrayList<Company> companiesList;
    String pos;
    private List<String> ipContact = new ArrayList<>();
    private List<String> crmContact = new ArrayList<>();
    private List<ContactLite> contactLites;
    private List<UserSearch> userSearchList;
    JSONArray arrayIU = new JSONArray();
    JSONArray arrayCon = new JSONArray();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_opportunity);

        getSupportActionBar().hide();
        serverUrl = new ServerUrl(this);
        client = serverUrl.getHTTPClient();
        companiesList = new ArrayList<>();
        companyName = new ArrayList();
        contactLites = new ArrayList<>();
        userSearchList = new ArrayList<>();

        init();
        addCompany();
        getContactList();
        setUpEditor();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.layout_style_spinner, companyName);
        oppoCompany.setAdapter(arrayAdapter);

        ArrayAdapter<String> oppoCurrencyAdapter = new ArrayAdapter<String>(this, R.layout.layout_style_spinner, currencies);
        oppoCurrency.setAdapter(oppoCurrencyAdapter);
        ArrayAdapter<String> oppoStateAdapter = new ArrayAdapter<String>(this, R.layout.layout_style_spinner, states);
        oppoState.setAdapter(oppoStateAdapter);




//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {

                editTagViewInternal.setTagAddCallBack(new EditTag.TagAddCallback() {
                    @Override
                    public boolean onTagAdd(String tagValue) {
                        for (int i=0; i<ipContact.size();i++) {
                            if (ipContact.get(i).equals(tagValue)) {
                                String userPk = userSearchList.get(i).getPk();
                                arrayIU.put(Integer.parseInt(userPk));
                                return true;
                            }
                        }
                        return false;
                    }
                });

                editTagViewInternal.setTagDeletedCallback(new EditTag.TagDeletedCallback() {
                    @Override
                    public void onTagDelete(String deletedTagValue) {
                        for (int i=0; i<ipContact.size();i++) {
                            if (ipContact.get(i).equals(deletedTagValue)) {
                                String userPk = userSearchList.get(i).getPk();
                                arrayIU.remove(Integer.parseInt(userPk));
                            }
                        }
                        Toast.makeText(getApplicationContext(), deletedTagValue, Toast.LENGTH_SHORT).show();
                    }
                });

                editTagViewInternal.setTagList(ipContact);

                editTagViewCustomer.setTagAddCallBack(new EditTag.TagAddCallback() {
                    @Override
                    public boolean onTagAdd(String tagValue) {
                        for (int i=0; i<crmContact.size();i++) {
                            if (crmContact.get(i).equals(tagValue)) {
                                String contactPk = contactLites.get(i).getPk();
                                arrayCon.put(Integer.parseInt(contactPk));
                                return true;
                            }
                        }
                        return false;
                    }
                });

                editTagViewCustomer.setTagDeletedCallback(new EditTag.TagDeletedCallback() {
                    @Override
                    public void onTagDelete(String deletedTagValue) {
                        for (int i=0; i<crmContact.size();i++) {
                            if (crmContact.get(i).equals(deletedTagValue)) {
                                String contactPk = contactLites.get(i).getPk();
                                arrayCon.remove(Integer.parseInt(contactPk));
                            }
                        }
                        Toast.makeText(getApplicationContext(), deletedTagValue, Toast.LENGTH_SHORT).show();
                    }
                });

                editTagViewCustomer.setTagList(crmContact);
//            }
//        },300);





        confidenceSeekBar.setProgress(0);
        confidenceSeekBar.setMax(100);
        confidenceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                minConfidence.setText(progress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        oppoCloseDate.setFocusableInTouchMode(false);
        oppoCloseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(NewOpportunityActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        oppoCloseDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },c_yr,c_month,c_day);
                DatePicker dp = dpd.getDatePicker();
                dpd.show();
            }
        });


    }

    public void init(){
        Calendar c = Calendar.getInstance();
        c_yr = c.get(Calendar.YEAR);
        c_month = c.get(Calendar.MONTH);
        c_day = c.get(Calendar.DAY_OF_MONTH);

        oppoName = findViewById(R.id.oppo_name);
        oppoCompany = findViewById(R.id.oppo_company);
        editTagViewInternal = findViewById(R.id.oppo_tag_view_internal);
        editTagViewCustomer = findViewById(R.id.oppo_tag_view_customer);
        oppoCloseDate = findViewById(R.id.oppo_close_date);
        oppoValuation = findViewById(R.id.oppo_valuation);
        oppoCurrency = findViewById(R.id.currency_spinner);
        oppoState = findViewById(R.id.state_spinner);
        minConfidence = findViewById(R.id.min_confidence);
        maxConfidence = findViewById(R.id.max_confidence);
        confidenceSeekBar = findViewById(R.id.confidence_seekBar);
        editTagInternal = (AutoCompleteTextView) findViewById(R.id.medit_tag_ip);
        editTagCustomer = (AutoCompleteTextView) findViewById(R.id.medit_tag_crm);

        oppoRequirementEditor = (Editor) findViewById(R.id.oppo_requirement_editor);

    }

    public void addCompany() {
        client.get(ServerUrl.url+"api/ERP/service/?format=json", new JsonHttpResponseHandler(){
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

        oppoCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                for (int i=0; i<companiesList.size(); i++) {
                    Company c = (Company) companiesList.get(i);
                    if (s.toString().equals(c.getCompanyName())){
                        pos = c.getCompanyPk();
                    }
                }
            }
        });

    }

    private void getContactList() {

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
                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, crmContact);
                editTagCustomer.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

        client.get(ServerUrl.url+"api/HR/userSearch/", new JsonHttpResponseHandler() {
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
                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, ipContact);
                editTagInternal.setAdapter(arrayAdapter);
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


    public void setUpEditor() {
        findViewById(R.id.action_h1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.updateTextStyle(EditorTextStyle.H1);
            }
        });

        findViewById(R.id.action_h2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.updateTextStyle(EditorTextStyle.H2);
            }
        });

        findViewById(R.id.action_h3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.updateTextStyle(EditorTextStyle.H3);
            }
        });

        findViewById(R.id.action_Italic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.updateTextStyle(EditorTextStyle.ITALIC);
            }
        });

        findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.updateTextStyle(EditorTextStyle.INDENT);
            }
        });

        findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.updateTextStyle(EditorTextStyle.OUTDENT);
            }
        });

        findViewById(R.id.action_bulleted).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.insertList(false);
            }
        });

        findViewById(R.id.action_unordered_numbered).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.insertList(true);
            }
        });

        findViewById(R.id.action_hr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.insertDivider();
            }
        });

        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.openImagePicker();
            }
        });

        findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.insertLink();
            }
        });

        findViewById(R.id.action_erase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.clearAllContents();
            }
        });

        oppoRequirementEditor.render();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == oppoRequirementEditor.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                oppoRequirementEditor.insertImage(bitmap);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveOpportunity(View v) {
        String name = oppoName.getText().toString().trim();
        String currency = oppoCurrency.getSelectedItem().toString();
        String probability = minConfidence.getText().toString();
        int state = oppoState.getSelectedItemPosition();
        String stateStr = stateParams[state];
        String value = oppoValuation.getText().toString();
        String req = oppoRequirementEditor.getContentAsSerialized();
        Editor editor = new Editor(getApplicationContext(),null);
        String requirements = editor.getContentAsHTML(req);



        RequestParams params = new RequestParams();
        params.put("closeDate","2018-07-09T10:19:37.931376Z");
        params.put("name",name);
        params.put("company",pos);
        params.put("probability",probability);
        params.put("currency",currency);
        params.put("internalUsers",arrayIU);
        params.put("contacts",arrayCon);
        params.put("relation","onetime");
        params.put("state",stateStr);
        params.put("value",value);
        params.put("requirements",requirements);

        client.post(ServerUrl.url+"/api/clientRelationships/deal/", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Toast.makeText(getApplicationContext(), "posted", Toast.LENGTH_SHORT).show();

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



}
