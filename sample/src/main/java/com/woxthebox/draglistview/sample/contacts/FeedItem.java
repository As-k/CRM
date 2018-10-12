package com.woxthebox.draglistview.sample.contacts;

import com.woxthebox.draglistview.sample.ServerUrl;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class FeedItem {

	private  String location;
	private String duration;
	private  String created;
	private String pk;
	private  String user;
	private int id;
	int userId;
	String internalUser;
	private JSONArray jsonArray1;
	private  JSONArray jsonArray;

	private String designation,contactName, profilePic, timeStamp, url, notes,deal,dp,doc,contacts,data;
	private Integer internalUsers;
	private String contactsName,designationContacts;
	public JSONObject jsonObject;

	public String  male;



	public FeedItem(JSONObject jsonObject) {
		this.jsonObject=jsonObject;
		try{
//			this.pk = jsonObject.getString("pk");
//			this.user = jsonObject.getString("user");
//			String type = jsonObject.getString("typ");
//			this.created = jsonObject.getString("created");
//			if (type.equals("note")) {
//				this.data = jsonObject.getString("data");
//			}
//			if (type.equals("call")) {
//				JSONObject data = jsonObject.getJSONObject("data");
//				this.duration = data.getString("duration");
//
//			if (type.equals("meeting")) {
//				JSONObject data = jsonObject.getJSONObject("data");
//				this.duration = data.getString("duration");
//				this.location = data.getString("location");
//			}
//
//			String notes = jsonObject.getString("notes");
//			if (notes.equals("null")) {
//				this.notes = "";
//			} else {
//				this.notes = notes;
//			}

			    this.data=jsonObject.getString("data");
				this.notes=jsonObject.getString("notes");
				this.doc=jsonObject.getString("doc");

				jsonArray = new JSONArray(jsonObject.getString("contacts"));

				for(int i=0;i<jsonArray.length();i++){
					 JSONObject  jobj =jsonArray.getJSONObject(i);
				    this.contactsName=jobj.getString("name");
				    this.designationContacts=jobj.getString("designation");
				    this.male=jobj.getString("male");


				}

				this.jsonArray1 = jsonObject.getJSONArray("internalUsers");
				for(int i=0;i<jsonArray1.length();i++){
				   this.internalUsers=jsonArray1.getInt(i);
                }



				JSONObject contact=jsonObject.getJSONObject("contact");
				if(contact!=null) {
				    this.contactName = contact.getString("name");
					this.designation = contact.getString("designation");



				}


		}
		catch (JSONException e){
			e.printStackTrace();
		}
	}


	public void setDeal(String deal) {
		this.deal = deal;
	}

    public String getMale() {
        return male;
    }

    public void setMale(String male) {
        this.male = male;
    }

    public void setDp(String dp) {

		this.dp = dp;
	}




	public void setDoc(String doc) {
		this.doc = doc;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public void setInternalUsers(Integer internalUsers) {
		this.internalUsers = internalUsers;
	}
	public Integer getInternalUsers(){
		return this.internalUsers;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDeal() {
		return deal;
	}



//	public void setInternalUser(int internalUser) {
//		this.internalUser = internalUser;
//	}

	public String getDp() {
		return dp;
	}


	public String getDoc() {
		return doc;
	}

	public String getContacts() {
		return contacts;
	}


	public String getData() {
		return data;
	}

	public int getId() {
		return id;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotes() {

		return notes;
	}

	public void setId(int id) {
		this.id = id;

	}

	public String getcontactName() {
		return contactName;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public String getUrl() {
		return url;
	}

	public void setcontactName(String name) {
		this.contactName = name;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
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

	public void setUrl(String url) {
		this.url = url;
	}

	public int getUserId() {
		return userId;
	}

	public String getContactName() {
		return contactName;
	}

	public String getDesignationContacts() {
		return designationContacts;
	}

	public void setDesignationContacts(String designationContacts) {
		this.designationContacts = designationContacts;
	}


	public String getDesignation() {
		return designation;
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	public JSONArray getJsonArray1() {
		return this.jsonArray1;
	}

	public void setJsonArray1(JSONArray jsonArray1) {
		this.jsonArray1 = jsonArray1;
	}
}
