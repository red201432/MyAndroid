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
	private static final String JSON_ID="id";
	private static final String JSON_TITLE="title";
	private static final String JSON_SOLVED="solved";
	private static final String JSON_DATE="date";
	
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
	
	public JSONObject toJSON() throws JSONException {
		JSONObject json=new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_TITLE, mTitle);
		json.put(JSON_SOLVED, mSolved);
		json.put(JSON_DATE, mDate.getTime());
		return json;
	}

}
