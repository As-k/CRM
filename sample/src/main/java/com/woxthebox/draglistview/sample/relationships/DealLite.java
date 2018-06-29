package com.woxthebox.draglistview.sample.relationships;


import android.util.Log;

import com.woxthebox.draglistview.sample.contacts.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 22/06/18..
 */

public class DealLite {
    public String pk;
    public String user;
    public String name;
    public String companyPk;
    public String value;
    public String currency;
    public ArrayList<Integer> internalUsers = new ArrayList<Integer>();
    public List<Contact> contactsList;
//    public ArrayList<Integer> contactsList;
    public String contactPk,contactName,contactEmail,contactMobile,contactDesignation,contactDp;
    boolean contactMale;
    public JSONObject jsonObject;


    public DealLite(){

    }


    public DealLite(String pk, String user, String name, String companyPk, String contactPk,
                    String contactName, String contactEmail, String contactMobile, String contactDesignation,
                    String contactDp, boolean contactMale, ArrayList internalUsers) {
        this.pk = pk;
        this.user = user;
        this.name = name;
        this.companyPk = companyPk;
        this.value = value;
        this.currency = currency;
        this.internalUsers = internalUsers;
        this.contactPk = contactPk;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactMobile = contactMobile;
        this.contactDesignation = contactDesignation;
        this.contactDp = contactDp;
        this.contactMale = contactMale;

    }

    // make a get request
    public DealLite(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        this.contactsList = new ArrayList<Contact>();
        try {
            this.pk = jsonObject.getString("pk");
            this.user = jsonObject.getString("user");
            this.name = jsonObject.getString("name");
            this.value = jsonObject.getString("value");
            this.currency = jsonObject.getString("currency");

            this.companyPk = jsonObject.getString("company");

            JSONArray contactsArry = jsonObject.getJSONArray("contacts");
            for (int i = 0; i < contactsArry.length(); i++) {
                JSONObject contacts = contactsArry.getJSONObject(i);
                Contact contact = new Contact(contacts);
                Log.e("DealLite", "getDesignation " +contact.getDesignation());;
                contactsList.add(contact);
                this.contactPk = contacts.getString("pk");
                String contactName = contacts.getString("name");
                if (contactName.equals("null")) {
                    this.contactName = "";
                } else {
                    this.contactName = contactName;
                }
                String contactEmail = contacts.getString("email");
                if (contactEmail.equals("null")) {
                    this.contactEmail = "";
                } else {
                    this.contactEmail = contactEmail;
                }
                String contactMobile = contacts.getString("mobile");
                if (contactMobile.equals("null")) {
                    this.contactMobile = "";
                } else {
                    this.contactMobile = contactMobile;
                }
                String contactDesignation = contacts.getString("designation");
                if (contactDesignation.equals("null")) {
                    this.contactDesignation = "";
                } else {
                    this.contactDesignation = contactDesignation;
                }
                String contactDp = contacts.getString("dp");
                if (contactDp.equals("null")) {
                    this.contactDp = "";
                } else {
                    this.contactDp = contactDp;
                }
                this.contactMale = contacts.getBoolean("male");

            }
            JSONArray jsonArray1 = jsonObject.getJSONArray("internalUsers");
            for (int j = 0; j < jsonArray1.length(); j++) {
                this.internalUsers.add(jsonArray1.getInt(j));
            }

            } catch(JSONException e){
                e.printStackTrace();
            }

        }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCompanyPk() {
        return companyPk;
    }

    public void setCompanyPk(String companyPk) {
        this.companyPk = companyPk;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }



    public String getContactPk() {
        return contactPk;
    }

    public void setContactPk(String contactPk) {
        this.contactPk = contactPk;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactDesignation() {
        return contactDesignation;
    }

    public void setContactDesignation(String contactDesignation) {
        this.contactDesignation = contactDesignation;
    }

    public String getContactDp() {
        return contactDp;
    }

    public void setContactDp(String contactDp) {
        this.contactDp = contactDp;
    }

    public boolean isContactMale() {
        return contactMale;
    }

    public List<Contact> getContactsList() {
        return contactsList;
    }

    public void setContactsList(ArrayList<Contact> contactsList) {
        this.contactsList = contactsList;
    }

    public void setContactMale(boolean contactMale) {
        this.contactMale = contactMale;
    }

    public ArrayList<Integer> getInternalUsers() {
        return internalUsers;
    }

    public void setInternalUsers(ArrayList<Integer> internalUsers) {
        this.internalUsers = internalUsers;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}

