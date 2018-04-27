package com.testSSM.test.model;

public class Contacts {
	private String phonename;
	private String phonenum;
	private int phoneid;
	public String getPhonename() {
		return phonename;
	}
	public void setPhonename(String phonename) {
		this.phonename = phonename;
	}
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public int getPhoneid() {
		return phoneid;
	}
	public void setPhoneid(int phoneid) {
		this.phoneid = phoneid;
	}
	@Override
	public String toString() {
		return "Contacts [phonename=" + phonename + ", phonenum=" + phonenum + ", phoneid=" + phoneid + "]";
	}
}
