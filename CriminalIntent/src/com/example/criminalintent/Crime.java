package com.example.criminalintent;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.format.Time;

public class Crime {
	private UUID mId;
	private String mTitle;
	private Date mDate;
	private boolean mSolved;
	private Photo mPhoto;
	
	private static final String JSON_ID="id";
	private static final String JSON_TITLE="title";
	private static final String JSON_SOLVED="solved";
	private static final String JSON_DATE="date";
	private static final String JSON_PHOTO="photo";
	
	public Crime(JSONObject json) throws JSONException{
		mId=UUID.randomUUID();
		mDate=new Date(json.getLong(JSON_DATE));
		if(json.has(JSON_PHOTO))
			mPhoto=new Photo(json.getJSONObject(JSON_PHOTO));
	}
	public Crime(){
		mId=UUID.randomUUID();
		mDate=new Date();
	}
	public UUID getmId() {
		return mId;
	}
	public void setmId(UUID mId) {
		this.mId = mId;
	}
	public String getmTitle() {
		return mTitle;
	}
	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	public Date getmDate() {
		return mDate;
	}
	public void setmDate(Date mDate) {
		this.mDate = mDate;
	}
	public boolean ismSolved() {
		return mSolved;
	}
	public void setmSolved(boolean mSolved) {
		this.mSolved = mSolved;
	}
	@Override
	public String toString(){
		return mTitle;
	}
	
	public Photo getPhoto() {
		return mPhoto;
	}
	public void setPhoto(Photo photo) {
		mPhoto=photo;
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject json=new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_TITLE, mTitle);
		json.put(JSON_SOLVED, mSolved);
		json.put(JSON_DATE, mDate.getTime());
		if(mPhoto!=null) json.put(JSON_PHOTO, mPhoto.toJSON());
		return json;
	}

}
