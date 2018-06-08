package com.woxthebox.draglistview.sample.contacts;

import com.woxthebox.draglistview.sample.ServerUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


/**
 * Created by amit on 16/4/18.
 */

public class Contact implements Serializable {
    public String pk;
    public String user;
    public String name;
    public String created;
    public String updated;
    public String email;
    public String emailSecondary;
    public String mobile;
    public String mobileSecondary;
    public String designation;
    public String notes;
    public String linkedin;
    public String facebook;
    public String dp;
    public boolean male;
    public String companyPk,companyName,cin,tin,telephone,companyMobile,about,web;
    public String addressPk,street,city,state,pincode,lat,lon,country;
    public JSONObject jsonObject;

    public Contact() {

    }

    public Contact(String pk, String user, String name, String created, String updated, String email,
                   String emailSecondary, String mobile, String mobileSecondary, String designation, String notes,
                   String linkedin, String facebook, String dp, boolean male,String companyPk,String comapnyName,
                   String cin, String tin, String telephone,String companyMobile, String about,String web,
                   String addressPk,String street,String city, String pincode,String country) {
        this.pk = pk;
        this.user = user;
        this.name = name;
        this.created = created;
        this.updated = updated;
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
        this.companyPk = companyPk;
        this.companyName = comapnyName;
        this.cin = cin;
        this.tin = tin;
        this.telephone = telephone;
        this.companyMobile = companyMobile;
        this.about  = about;
        this.web = web;
        this.addressPk = addressPk;
        this.street = street;
        this.city = city;
        this.pincode = pincode;
        this.country = country;
    }

    public Contact(JSONObject jsonObject) {
        this.jsonObject = jsonObject;

        try{
            this.pk = jsonObject.getString("pk");
            String name = jsonObject.getString("name");
            if (name.equals("null")) {
                this.name = "";
            } else {
                this.name = name;
            }
            String email = jsonObject.getString("email");
            if (email.equals("null")) {
                this.email = "";
            } else {
                this.email = email;
            }
            String emailSecondary = jsonObject.getString("emailSecondary");
            if (emailSecondary.equals("null")) {
                this.emailSecondary = "";
            } else {
                this.emailSecondary = emailSecondary;
            }
            String mobile = jsonObject.getString("mobile");
            if (mobile.equals("null")) {
                this.mobile = "";
            } else {
                this.mobile = mobile;
            }
            String mobileSecondary = jsonObject.getString("mobileSecondary");
            if (mobileSecondary.equals("null")) {
                this.mobileSecondary = "";
            } else {
                this.mobileSecondary = mobileSecondary;
            }

            String designation = jsonObject.getString("designation");
            if (designation.equals("null")) {
                this.designation = "";
            } else {
                this.designation = designation;
            }
            String  notes = jsonObject.getString("notes");
            if (notes.equals("null")) {
                this.notes = "";
            } else {
                this.notes = notes;
            }
            String  linkedin = jsonObject.getString("linkedin");
            if (linkedin.equals("null")) {
                this.linkedin = "";
            } else {
                this.linkedin = linkedin;
            }
            String facebook = jsonObject.getString("facebook");
            if (facebook.equals("null")) {
                this.facebook = "";
            } else {
                this.facebook = facebook;
            }
            this.male = jsonObject.getBoolean("male");
            String img  = jsonObject.getString("dp");
            if (img.equals("null")){
                if (this.male)
                    this.dp = ServerUrl.url+"/static/images/img_avatar_card.png";
                else
                    this.dp = ServerUrl.url+"/static/images/img_avatar_card2.png";
            } else {
                this.dp = img;
            }

            JSONObject company = jsonObject.getJSONObject("company");
            this.companyPk = company.getString("pk");
            this.companyName = company.getString("name");
            String cin = company.getString("cin");
            if (cin.equals("null")) {
                this.cin = "";
            } else {
                this.cin = cin;
            }
            String tin = company.getString("tin");
            if (tin.equals("null")) {
                this.tin = "";
            } else {
                this.tin = tin;
            }
            String telephone = company.getString("telephone");
            if (telephone.equals("null")) {
                this.telephone = "";
            } else {
                this.telephone = telephone;
            }

            String companyMobile = company.getString("mobile");
            if (companyMobile.equals("null")) {
                this.companyMobile = "";
            } else {
                this.companyMobile = companyMobile;
            }
            String about = company.getString("about");
            if (about.equals("null")) {
                this.about = "";
            } else {
                this.about = about;
            }
            String web = company.getString("web");
            if (web.equals("null")) {
                this.web = "";
            } else {
                this.web = web;
            }

            JSONObject address = company.getJSONObject("address");
            this.addressPk = address.getString("pk");
            String street = address.getString("street");
            if (street.equals("null")||street==null) {
                this.street = "";
            } else {
                this.street = street;
            }
            String city = address.getString("city");
            if (street.equals("null")||city==null) {
                this.city = "";
            } else {
                this.city = city;
            }
            String state = address.getString("state");
            if (state.equals("null")||state==null) {
                this.state = "";
            } else {
                this.state = state;
            }
            String pincode = address.getString("pincode");
            if (pincode.equals("null")||pincode==null) {
                this.pincode = "";
            } else {
                this.pincode = pincode;
            }
            String country = address.getString("country");
            if (country.equals("null")||country==null) {
                this.country = "";
            } else {
                this.country = country;
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

    public String getAddressPk() {
        return addressPk;
    }

    public void setAddressPk(String addressPk) {
        this.addressPk = addressPk;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public boolean isMale() {
        return male;
    }


    public String getCin() {
        return cin;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCompanyMobile() {
        return companyMobile;
    }

    public void setCompanyMobile(String companyMobile) {
        this.companyMobile = companyMobile;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
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

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
