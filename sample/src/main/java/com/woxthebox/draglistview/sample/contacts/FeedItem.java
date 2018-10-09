package com.woxthebox.draglistview.sample.contacts;

import com.woxthebox.draglistview.sample.ServerUrl;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FeedItem {

	private int id;
	int userId;
	String Pk;
	private String designation,contactName, profilePic, timeStamp, url, notes,deal,dp,doc,contacts,internalUsers,data;
	private String contactsName,designationContacts;
	public JSONObject jsonObject;
	public Boolean male;



	public FeedItem(JSONObject jsonObject) {
		this.jsonObject=jsonObject;
		try{
				this.data=jsonObject.getString("data");
				this.notes=jsonObject.getString("notes");
				this.doc=jsonObject.getString("doc");

				JSONArray jsonArray = new JSONArray(jsonObject.getString("contacts"));

				for(int i=0;i<jsonArray.length();i++){
				    JSONObject jobj=jsonArray.getJSONObject(i);
				    this.contactsName=jobj.getString("name");
				    this.designationContacts=jobj.getString("designation");
				}

                JSONArray jsonArray1=new JSONArray(jsonObject.getString("internalUsers"));
				for(int i=0;i<jsonArray1.length();i++){
					JSONObject jobj1=jsonArray1.getJSONObject(i);
				    this.Pk=jobj1.getString("");
                }



				JSONObject contact=jsonObject.getJSONObject("contact");
				this.contactName=contact.getString("name");
				this.designation=contact.getString("designation");

				String img = jsonObject.getString("dp");
				if (img.equals("null")) {
					if (this.male)
						this.dp = ServerUrl.url + "/static/images/img_avatar_card.png";
					else
						this.dp = ServerUrl.url + "/static/images/img_avatar_card2.png";
				} else {
					this.dp = img;
				}
                   /* JSONArray contactsJsonArray=jsonObject.getJSONArray("contacts");
				        for(int i=0;i<contactsJsonArray.length();i++) {
				    JSONObject contactsObject=(JSONObject) contactsJsonArray.get(i);
                    this.contactsName = contactsObject.getString("name");
                    this.designation = contactsObject.getString("designation");
                }*/


				//	this.userId=internalUsersArray.getInt(i);


		}
		catch (JSONException e){
			e.printStackTrace();
		}
	}


	public void setDeal(String deal) {
		this.deal = deal;
	}

	public void setDp(String dp) {
		this.dp = dp;
	}

	public void setMale(Boolean male) {
		this.male = male;
	}


	public void setDoc(String doc) {
		this.doc = doc;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public void setInternalUsers(String internalUsers) {
		this.internalUsers = internalUsers;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDeal() {
		return deal;
	}

	public String getPk() {
		return Pk;
	}

	public void setPk(String pk) {
		this.Pk = pk;
	}

	public String getDp() {
		return dp;
	}

	public Boolean getMale() {
		return male;
	}

	public String getDoc() {
		return doc;
	}

	public String getContacts() {
		return contacts;
	}

	public String getInternalUsers() {
		return internalUsers;
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

	public String getcontactsName() {
		return contactsName;
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

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
}
