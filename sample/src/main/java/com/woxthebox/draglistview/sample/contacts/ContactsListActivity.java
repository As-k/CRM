package com.woxthebox.draglistview.sample.contacts;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.ServerUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class ContactsListActivity extends AppCompatActivity {
    ListView listView;
//    EditText searchContact;
    String keys[] = {"k1", "k2"};
    int ids[] = {R.id.contact_name, R.id.contact_number};
    ArrayList storeContacts;
    SimpleAdapter simpleAdapter;
    Cursor cursor;
    String name, phonenumber, companyName, email;
    public static final int RequestPermissionCode = 1;
//    ContentResolver mContentResolver = getContentResolver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

//        searchContact = findViewById(R.id.search_contact);
        listView = findViewById(R.id.import_contact_list_view);

        storeContacts = new ArrayList<String>();

        enableRuntimePermission();

        allContacts();

//        textChange();

    }

    public void allContacts(){
        GetContactsIntoArrayList();

        simpleAdapter = new SimpleAdapter(ContactsListActivity.this, storeContacts, R.layout.contact_items_listview, keys, ids);

        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap hm = (HashMap) storeContacts.get(position);
                String name = (String) hm.get(keys[0]);
                final String cno = (String) hm.get(keys[1]);
                Intent i = new Intent(ContactsListActivity.this, NewContactActivity.class);
                i.putExtra("name", name);
                i.putExtra("cno", cno);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);

        search.setVisible(true);
//        contactList.clear();
//        browseAdapter.clearData();
        return true;
    }

    private void search(final SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    simpleAdapter.getFilter().filter(newText);
                } else {
                    String query1 = newText.toLowerCase();
                    simpleAdapter.getFilter().filter(query1);
                }
                return true;
            }
        });
    }

    int plus_sign_pos = 0;
    public void GetContactsIntoArrayList(){

        String order = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, order);
        String temp_name = "";
        while (cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY));
            if (name.equals(temp_name))
                continue;
            temp_name = name;
            phonenumber = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
            if (hasCountryCode(phonenumber)) {
                String country_digits = phonenumber.replace("+91","");
                phonenumber = country_digits;
            }
            HashMap hm = new HashMap();
            hm.put(keys[0],name);
            hm.put(keys[1],phonenumber);
            storeContacts.add(hm);
        }
        cursor.close();
    }
    private boolean hasCountryCode(String number) {
        return number.charAt(plus_sign_pos) == '+'; // Didn't String had contains() method?...
    }

    public void enableRuntimePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                ContactsListActivity.this,
                Manifest.permission.READ_CONTACTS)) {

        } else {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_CONTACTS}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {
        switch (RC) {
            case RequestPermissionCode:
                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                }
                break;
        }
    }
}
