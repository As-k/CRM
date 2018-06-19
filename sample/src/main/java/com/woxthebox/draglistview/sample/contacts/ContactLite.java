package com.woxthebox.draglistview.sample.contacts;

import com.woxthebox.draglistview.sample.ServerUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by amit on 3/5/18.
 */

public class ContactLite implements Serializable {
        public String pk;
        public String user;
        public String name;
        public String companyPk;
        public String email;
        public String mobile;
        public String designation;
        public String dp;
        public boolean male;
        public JSONObject jsonObject;



    public ContactLite(String pk, String user, String name, String companyPk, String email, String mobile, String designation, String dp, boolean male) {
        this.pk = pk;
        this.user = user;
        this.name = name;
        this.companyPk = companyPk;
        this.email = email;
        this.mobile = mobile;
        this.designation = designation;
        this.dp = dp;
        this.male = male;
    }


    public ContactLite(JSONObject jsonObject) {
        this.jsonObject = jsonObject;

        try {
            this.pk = jsonObject.getString("pk");
            this.user = jsonObject.getString("user");
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
            String mobile = jsonObject.getString("mobile");
            if (mobile.equals("null")) {
                this.mobile = "";
            } else {
                this.mobile = mobile;
            }
            this.companyPk = jsonObject.getString("company");
            String designation = jsonObject.getString("designation");
            if (designation.equals("null")) {
                this.designation = "";
            } else {
                this.designation = designation;
            }
            this.male = jsonObject.getBoolean("male");
            String img  = jsonObject.getString("dp");
            if (img.equals("null")){
                if (this.male) {
                    this.dp = ServerUrl.url+"/static/images/img_avatar_card.png";
                }
                else {
                    this.dp = ServerUrl.url+"/static/images/img_avatar_card2.png";
                }
            } else {
                this.dp = img;
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

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }
}
