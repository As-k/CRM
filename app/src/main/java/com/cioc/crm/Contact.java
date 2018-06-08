package com.cioc.crm;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


/**
 * Created by amit on 16/4/18.
 */

public class Contact implements Serializable {
    public String pk, user, name, created, updated, company, email, emailSecondary,
            mobile, mobileSecondary, designation, notes, linkedin, facebook, dp, telephone, about,web, cmpmob, tin, cin;
    public boolean male;
    public JSONObject jsonObject;

    public Contact() {

    }

    public Contact(String pk, String user, String name, String created, String updated, String company, String email,
                   String emailSecondary, String mobile, String mobileSecondary, String designation, String notes,
                   String linkedin, String facebook, String dp, boolean male) {
        this.pk = pk;
        this.user = user;
        this.name = name;
        this.created = created;
        this.updated = updated;
        this.company = company;
        this.email = email;
        this.emailSecondary = emailSecondary;
        this.mobile = mobile;
        this.mobileSecondary = mobileSecondary;
        this.designation = designation;
        this.notes = notes;
        this.linkedin = linkedin;
        this.facebook = facebook;
        this.dp = dp;
        this.male = male;
    }

    public Contact(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        try{
            this.pk = jsonObject.getString("pk");
            this.name = jsonObject.getString("name");
            this.created = jsonObject.getString("created");
            this.updated = jsonObject.getString("updated");
            this.email = jsonObject.getString("email");
            this.emailSecondary = jsonObject.getString("emailSecondary");
            this.mobile = jsonObject.getString("mobile");
            this.mobileSecondary = jsonObject.getString("mobileSecondary");
            this.designation = jsonObject.getString("designation");
            this.notes = jsonObject.getString("notes");
            this.linkedin = jsonObject.getString("linkedin");
            this.facebook = jsonObject.getString("facebook");
            this.dp = jsonObject.getString("dp");
            this.male = jsonObject.getBoolean("male");
            JSONObject companyObj = jsonObject.getJSONObject("company");
            this.company = companyObj.getString("name");
            this.telephone = companyObj.getString("telephone");
            this.about = companyObj.getString("about");
            this.web = companyObj.getString("web");

        }catch (JSONException e){

        }
        try{
        JSONObject company = jsonObject.getJSONObject("company");
        String companyName = company.getString("name");
        String cin = company.getString("cin");
        String tin = company.getString("tin");
        String telephone = company.getString("telephone");
        String cMobile = company.getString("mobile");
        String about = company.getString("about");
        String web = company.getString("web");
//                            String doc = company.getString("doc");


        JSONObject a = company.getJSONObject("address");
//
        String street = a.getString("street");
        String city = a.getString("city");
        String state = a.getString("state");
        String pincode = a.getString("pincode");
        String country = a.getString("country");
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

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailSecondary() {
        return emailSecondary;
    }

    public void setEmailSecondary(String emailSecondary) {
        this.emailSecondary = emailSecondary;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobileSecondary() {
        return mobileSecondary;
    }

    public void setMobileSecondary(String mobileSecondary) {
        this.mobileSecondary = mobileSecondary;
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

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
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

}
