package com.woxthebox.draglistview.sample.contacts;

import com.woxthebox.draglistview.sample.ServerUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


/**
 * Created by amit on 16/4/18.
 */

public class FeedActivity implements Serializable {
    public String pk, contactPk, data, deal, contact, doc;
    public String user;
    public String name;
    public String created;
    public String typ;
    public String email;
    public String mobile;
    public String designation;
    public String notes;
    public String dp;
    public boolean male;
    public String companyPk;
    public JSONObject jsonObject;

    public FeedActivity() {

    }

    public FeedActivity(String pk, String contactPk, String data, String deal, String contact, String doc, String user, String name, String created,
                        String typ, String email, String mobile, String designation, String notes, String dp, boolean male, String companyPk) {
        this.pk = pk;
        this.contactPk = contactPk;
        this.data = data;
        this.deal = deal;
        this.contact = contact;
        this.doc = doc;
        this.user = user;
        this.name = name;
        this.created = created;
        this.typ = typ;
        this.email = email;
        this.mobile = mobile;
        this.designation = designation;
        this.notes = notes;
        this.dp = dp;
        this.male = male;
        this.companyPk = companyPk;

    }

    public FeedActivity(JSONObject jsonObject) {
        this.jsonObject = jsonObject;

        try{
            this.pk = jsonObject.getString("pk");
            this.typ = jsonObject.getString("typ");
            this.created = jsonObject.getString("created");
            this.data = jsonObject.getString("data");

            String notes = jsonObject.getString("notes");
            if (notes.equals("null")) {
                this.notes = "";
            } else {
                this.notes = notes;
            }

            String doc = jsonObject.getString("doc");
            if (doc.equals("null")) {
                this.doc = "";
            } else {
                this.doc = doc;
            }

            JSONArray contactsArr = jsonObject.getJSONArray("contacts");
            for (int i=0; i<contactsArr.length();i++) {
                JSONObject contacts = contactsArr.getJSONObject(i);

                this.contactPk = contacts.getString("pk");
                String name = contacts.getString("name");
                if (name.equals("null")) {
                    this.name = "";
                } else {
                    this.name = name;
                }
                String email = contacts.getString("email");
                if (email.equals("null")) {
                    this.email = "";
                } else {
                    this.email = email;
                }

                String mobile = contacts.getString("mobile");
                if (mobile.equals("null")) {
                    this.mobile = "";
                } else {
                    this.mobile = mobile;
                }

                String designation = contacts.getString("designation");
                if (designation.equals("null")) {
                    this.designation = "";
                } else {
                    this.designation = designation;
                }

                this.male = contacts.getBoolean("male");
                String img = contacts.getString("dp");
                if (img.equals("null")) {
                    if (this.male)
                        this.dp = ServerUrl.url + "/static/images/img_avatar_card.png";
                    else
                        this.dp = ServerUrl.url + "/static/images/img_avatar_card2.png";
                } else {
                    this.dp = img;
                }

                this.companyPk = contacts.getString("pk");
            }


        }catch (JSONException e){

        }
    }

    public void save(){
        // make a patch request
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

    public String getCompanyPk() {
        return companyPk;
    }

    public void setCompanyPk(String companyPk) {
        this.companyPk = companyPk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public boolean getMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public String getContactPk() {
        return contactPk;
    }

    public void setContactPk(String contactPk) {
        this.contactPk = contactPk;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDeal() {
        return deal;
    }

    public void setDeal(String deal) {
        this.deal = deal;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }



}
