package com.woxthebox.draglistview.sample.relationships;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 29/06/18.
 */

public class ProductMeta {
    public String pk, description, typ, code, taxRate;
    public JSONObject jsonObject;

    public ProductMeta(String pk, String description, String typ, String code, String taxRate, JSONObject jsonObject) {
        this.pk = pk;
        this.description = description;
        this.typ = typ;
        this.code = code;
        this.taxRate = taxRate;
        this.jsonObject = jsonObject;
    }

    public ProductMeta(JSONObject jsonObject) {
        this.jsonObject = jsonObject;

        try {
            this.pk = jsonObject.getString("pk");
            this.description = jsonObject.getString("description");
            this.typ = jsonObject.getString("typ");
            this.code = jsonObject.getString("code");
            this.taxRate = jsonObject.getString("taxRate");

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
