package com.woxthebox.draglistview.sample.relationships;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by amit on 16/4/18.
 */

public class Contract {
    public String pk, user, created, updated, value, deal, status, dueDate, details, data, billedDate,
            recivedDate, archivedDate,grandTotal, currency, type, tax, desc, rate, quantity, taxCode, total, totalTax, subtotal, $$hashKey ;
    public JSONObject jsonObject;
    ArrayList<Integer> size= new ArrayList<Integer>();

    public Contract(){

    }

    public Contract(String pk, String user, String created, String updated, String value, String deal,
                    String status, String dueDate, String details, String data, String billedDate, String recivedDate,
                    String archivedDate, String grandTotal) {
        this.pk = pk;
        this.user = user;
        this.created = created;
        this.updated = updated;
        this.value = value;
        this.deal = deal;
        this.status = status;
        this.dueDate = dueDate;
        this.details = details;
        this.data = data;
        this.billedDate = billedDate;
        this.recivedDate = recivedDate;
        this.archivedDate = archivedDate;
        this.grandTotal = grandTotal;

    }

    public Contract(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        try {
            this.pk = jsonObject.getString("pk");
            this.user = jsonObject.getString("user");
            this.created = jsonObject.getString("created");
            this.updated = jsonObject.getString("updated");
            this.value = jsonObject.getString("value");
            this.status = jsonObject.getString("status");
            this.details = jsonObject.getString("details");
            this.dueDate = jsonObject.getString("dueDate");
            String str = jsonObject.getString("data");
            this.data = str;
            JSONArray data = new JSONArray(str);
            for (int i=0; i<data.length(); i++){
                JSONObject object = data.getJSONObject(i);
                this.currency = object.getString("currency");
                this.type = object.getString("type");
                this.tax = object.getString("tax");
                this.desc = object.getString("desc");
                this.rate = object.getString("rate");
                this.quantity = object.getString("quantity");
                this.taxCode = object.getString("taxCode");
                this.total = object.getString("total");
                this.totalTax = object.getString("totalTax");
                this.subtotal = object.getString("subtotal");
                size.add(i);
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getBilledDate() {
        return billedDate;
    }

    public void setBilledDate(String billedDate) {
        this.billedDate = billedDate;
    }

    public String getRecivedDate() {
        return recivedDate;
    }

    public void setRecivedDate(String recivedDate) {
        this.recivedDate = recivedDate;
    }

    public String getArchivedDate() {
        return archivedDate;
    }

    public void setArchivedDate(String archivedDate) {
        this.archivedDate = archivedDate;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String get$$hashKey() {
        return $$hashKey;
    }

    public void set$$hashKey(String $$hashKey) {
        this.$$hashKey = $$hashKey;
    }

    public ArrayList<Integer> getSize() {
        return size;
    }

    public void setSize(ArrayList<Integer> size) {
        this.size = size;
    }

    public JSONObject getJsonObject(){
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
