package com.woxthebox.draglistview.sample.relationships;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by amit on 20/4/18.
 */

public class Relationships implements Serializable{
    public String pk,companyName,mobile,logo,web;
    public String addressPk,street,city,state,pincode,country;
    public JSONObject jsonObject;


    public Relationships(String pk, String companyName, String mobile, String logo, String web, String addressPk, String street, String city, String state, String pincode, String country) {
        this.pk = pk;
        this.companyName = companyName;
        this.mobile = mobile;
        this.logo = logo;
        this.web = web;
        this.addressPk = addressPk;

        this.street = street;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.country = country;
    }

    public Relationships(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        try {
            this.pk = jsonObject.getString("pk");
            String companyName = jsonObject.getString("name");
            if (companyName.equals("null")) {
                this.companyName = "";
            } else {
                this.companyName = companyName;
            }
            String mobile = jsonObject.getString("mobile");
            if (mobile.equals("null")) {
                this.mobile = "";
            } else {
                this.mobile = mobile;
            }
            String logo = jsonObject.getString("logo");
            if (logo.equals("null")) {
                this.logo = "";
            } else {
                this.logo = logo;
            }
            String web = jsonObject.getString("web");
            if (web.equals("null")) {
                this.web = "";
            } else {
                this.web = web;
            }

            JSONObject address = jsonObject.getJSONObject("address");
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




        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getCompanyName() {

        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }




}

