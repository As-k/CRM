package com.woxthebox.draglistview.sample.contacts;

import org.json.JSONObject;

/**
 * Created by admin on 06/06/18.
 */

public class Company {
    public String companyPk,companyName, created, cin,tin,telephone,companyMobile,about,web;
    public String addressPk,street,city,state,pincode,lat,lon,country;
    public JSONObject jsonObject;

    public Company() {

    }

    public Company(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        try {
            this.companyPk = jsonObject.getString("pk");
            this.created = jsonObject.getString("created");
            this.companyName = jsonObject.getString("name");
            String cin = jsonObject.getString("cin");
            if (cin.equals("null")) {
                this.cin = "";
            } else {
                this.cin = cin;
            }
            String tin = jsonObject.getString("tin");
            if (tin.equals("null")) {
                this.tin = "";
            } else {
                this.tin = tin;
            }
            String telephone = jsonObject.getString("telephone");
            if (telephone.equals("null")) {
                this.telephone = "";
            } else {
                this.telephone = telephone;
            }

            String companyMobile = jsonObject.getString("mobile");
            if (companyMobile.equals("null")) {
                this.companyMobile = "";
            } else {
                this.companyMobile = companyMobile;
            }
            String about = jsonObject.getString("about");
            if (about.equals("null")) {
                this.about = "";
            } else {
                this.about = about;
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

        } catch (Exception e){
            e.printStackTrace();

        }
    }

    public String getCompanyPk() {
        return companyPk;
    }

    public void setCompanyPk(String companyPk) {
        this.companyPk = companyPk;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCin() {
        return cin;
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

    public void save(String pk, String name, String created){
        this.companyPk = pk;
        this.companyName = name;
        this.created = created;
    }

    public void company_Address(String addressPk, String street, String city, String state, String pincode, String lat, String lon, String country) {
        this.addressPk = addressPk;
        this.street = street;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.lat = lat;
        this.lon = lon;
        this.country = country;
    }
}
