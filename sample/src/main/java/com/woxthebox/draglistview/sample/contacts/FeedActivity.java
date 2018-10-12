package com.woxthebox.draglistview.sample.contacts;

import com.woxthebox.draglistview.sample.ServerUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


/**
 * Created by admin on 20/06/18.
 */

public class FeedActivity implements Serializable {
    public String pk, contactPkContacts, contactPk, data, duration, location, deal, contact, doc;
    public String user, name, email, mobile, designation, dp, companyPk;
    public String nameContacts, emailContacts, mobileContacts, designationContacts, dpContacts, companyPkContacts;
    public String created;
    public String typ;
    public String notes;
    public boolean male, maleContacts;
    public JSONArray contactsArr;
    public JSONObject jsonObject;

    //make get request
    public FeedActivity(JSONObject jsonObject) {
        this.jsonObject = jsonObject;

        try{
            this.pk = jsonObject.getString("pk");
            this.user = jsonObject.getString("user");
            String type = jsonObject.getString("typ");
            this.created = jsonObject.getString("created");
            if (type.equals("note")) {
                this.data = jsonObject.getString("data");
            }
            if (type.equals("call")) {
                JSONObject data = jsonObject.getJSONObject("data");
                this.duration = data.getString("duration");
            }
            if (type.equals("meeting")) {
                JSONObject data = jsonObject.getJSONObject("data");
                this.duration = data.getString("duration");
                this.location = data.getString("location");
            }

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
            JSONObject contactsObj = jsonObject.getJSONObject("contact");
            if (contactsObj!=null) {
                this.contactPk = contactsObj.getString("pk");
                String name = contactsObj.getString("name");
                if (name.equals("null")) {
                    this.name = "";
                } else {
                    this.name = name;
                }
                this.companyPk = contactsObj.getString("company");
                String email = contactsObj.getString("email");
                if (email.equals("null")) {
                    this.email = "";
                } else {
                    this.email = email;
                }

                String mobile = contactsObj.getString("mobile");
                if (mobile.equals("null")) {
                    this.mobile = "";
                } else {
                    this.mobile = mobile;
                }

                String designation = contactsObj.getString("designation");
                if (designation.equals("null")) {
                    this.designation = "";
                } else {
                    this.designation = designation;
                }

                this.male = contactsObj.getBoolean("male");
                String img = contactsObj.getString("dp");
                if (img.equals("null")) {
                    if (this.male)
                        this.dp = ServerUrl.url + "/static/images/img_avatar_card.png";
                    else
                        this.dp = ServerUrl.url + "/static/images/img_avatar_card2.png";
                } else {
                    this.dp = img;
                }
            }

            this.contactsArr = new JSONArray(jsonObject.getString("contacts"));
            for (int i=0; i<contactsArr.length();i++) {
                JSONObject contacts = contactsArr.getJSONObject(i);

                this.contactPkContacts = contacts.getString("pk");
                String name1 = contacts.getString("name");
                if (name1.equals("null")) {
                    this.nameContacts = "";
                } else {
                    this.nameContacts = name1;
                }
                this.companyPkContacts = contacts.getString("company");
                String email1 = contacts.getString("email");
                if (email1.equals("null")) {
                    this.emailContacts = "";
                } else {
                    this.emailContacts = email1;
                }

                String mobileContacts = contacts.getString("mobile");
                if (mobileContacts.equals("null")) {
                    this.mobileContacts = "";
                } else {
                    this.mobileContacts = mobileContacts;
                }

                String designation1 = contacts.getString("designation");
                if (designation1.equals("null")) {
                    this.designationContacts = "";
                } else {
                    this.designationContacts = designation1;
                }

                this.maleContacts = contacts.getBoolean("male");
                String img1 = contacts.getString("dp");
                if (img1.equals("null")) {
                    if (this.maleContacts)
                        this.dpContacts = ServerUrl.url + "/static/images/img_avatar_card.png";
                    else
                        this.dpContacts = ServerUrl.url + "/static/images/img_avatar_card2.png";
                } else {
                    this.dpContacts = img1;
                }
            }
            JSONArray internalUsers = jsonObject.getJSONArray("internalUsers");
            for (int j=0; j<internalUsers.length(); j++){
                String arrStr = internalUsers.getString(j);
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

    public String getContactPkContacts() {
        return contactPkContacts;
    }

    public void setContactPkContacts(String contactPkContacts) {
        this.contactPkContacts = contactPkContacts;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNameContacts() {
        return nameContacts;
    }

    public void setNameContacts(String nameContacts) {
        this.nameContacts = nameContacts;
    }

    public String getEmailContacts() {
        return emailContacts;
    }

    public void setEmailContacts(String emailContacts) {
        this.emailContacts = emailContacts;
    }

    public String getMobileContacts() {
        return mobileContacts;
    }

    public void setMobileContacts(String mobileContacts) {
        this.mobileContacts = mobileContacts;
    }

    public String getDesignationContacts() {
        return designationContacts;
    }

    public void setDesignationContacts(String designationContacts) {
        this.designationContacts = designationContacts;
    }

    public String getDpContacts() {
        return dpContacts;
    }

    public void setDpContacts(String dpContacts) {
        this.dpContacts = dpContacts;
    }

    public boolean isMale() {
        return male;
    }

    public boolean isMaleContacts() {
        return maleContacts;
    }

    public void setMaleContacts(boolean maleContacts) {
        this.maleContacts = maleContacts;
    }

    public String getCompanyPkContacts() {
        return companyPkContacts;
    }

    public void setCompanyPkContacts(String companyPkContacts) {
        this.companyPkContacts = companyPkContacts;
    }

    public JSONArray getContactsArr() {
        return contactsArr;
    }

    public void setContactsArr(JSONArray contactsArr) {
        this.contactsArr = contactsArr;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }



}
