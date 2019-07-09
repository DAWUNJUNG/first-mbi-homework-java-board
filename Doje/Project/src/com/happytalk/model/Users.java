package com.happytalk.model;

public class Users {
	String No;
	String NAME;
	String PW;
	String TITLE;
	String CONTENTS;
	String MAKE_TIME;

	public String getNo() {
		return No;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String NAME) {
		this.NAME = NAME;
	}

	public String getPW() {
		return PW;
	}

	public void setPW(String PW) {
		this.PW = PW;
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

	public String getMAKE_TIME() {
		return MAKE_TIME;
	}

	public void setMAKE_TIME(String MAKE_TIME) {
		this.MAKE_TIME = MAKE_TIME;
	}

	public void setNo(String no) {
		No = no;
	}
}
