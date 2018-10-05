package com.woxthebox.draglistview.sample;

import com.woxthebox.draglistview.sample.opportunities.Item;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 19/06/18.
 */

public class UserSearch {
    public String pk;
    public String username;
    public String firstName;
    public String lastName;
    public String displayPicture;
    public String prefix;
    public String picPk;
    public String social;
    public String designation;
    public JSONObject jsonObject;

    public UserSearch() {
    }

    public UserSearch(String pk, String username, String firstName, String lastName, String displayPicture, String prefix, String picPk, String social, String designation) {
        this.pk = pk;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayPicture = displayPicture;
        this.prefix = prefix;
        this.picPk = picPk;
        this.social = social;
        this.designation = designation;
    }

    public UserSearch(JSONObject jsonObject) {
        this.jsonObject = jsonObject;

        try {
            this.pk = jsonObject.getString("pk");
            this.username = jsonObject.getString("username");
            String fname = jsonObject.getString("first_name");
            if (fname.equals("null")) {
                this.firstName = "";
            } else {
                this.firstName = fname;
            }
            String lname = jsonObject.getString("first_name");
            if (lname.equals("null")) {
                this.lastName = "";
            } else {
                this.lastName = lname;
            }

            JSONObject object = jsonObject.getJSONObject("profile");

            String img  = object.getString("displayPicture");
            if (img.equals("null")){
                this.displayPicture = ServerUrl.url+"/static/images/img_avatar_card.png";
            } else {
                this.displayPicture = img;
            }
            this.prefix = object.getString("prefix");
            this.picPk = object.getString("pk");

            this.social = jsonObject.getString("social");
            this.designation = jsonObject.getString("designation");

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayPicture() {
        return displayPicture;
    }

    public void setDisplayPicture(String displayPicture) {
        this.displayPicture = displayPicture;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPicPk() {
        return picPk;
    }

    public void setPicPk(String picPk) {
        this.picPk = picPk;
    }

    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
