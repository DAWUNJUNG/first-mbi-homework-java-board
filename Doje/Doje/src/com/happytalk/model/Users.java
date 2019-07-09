package com.happytalk.model;

public class Users {
	String PN;
	String ID;
	String NAME;
	String PW;
	String TITLE;
	String CONTENTS;
	String MAKE_TIME;
	
	public String getPN() {
		return PN;
	}
	public void setPN(String PN) {
		this.PN = PN;
	}
	public String getId() {
		return ID;
	}
	public void setId(String id) {
		this.ID = id;
	}
	public String getName() {
		return NAME;
	}
	public String getTITLE() {
		return TITLE;
	}
	public void setTITLE(String TITLE) {
		this.TITLE = TITLE;
	}

	public String getCONTENTS() {
		return CONTENTS;
	}
	public void setCONTENTS(String CONTENTS) {
		this.CONTENTS = CONTENTS;
	}

	public void setName(String name) {
		this.NAME = name;
	}
	public String getPassword() {
		return PW;
	}
	public void setPassword(String password) {
		this.PW = password;
	}
	public String setReg_date() {
		return MAKE_TIME;
	}
	public void setReg_date(String reg_date) {
		this.MAKE_TIME = reg_date;
	}
	public Object setCONTENTS() { return CONTENTS; }
}
