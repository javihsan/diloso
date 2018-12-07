package com.diloso.app.persist.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
/**
 * The persistent class for the Who entity
 * 
 */
@Entity
@MappedSuperclass
public class Who extends Resource implements Serializable {
	protected static final long serialVersionUID = 1L;

	protected String whoName;
	
	protected String whoSurname;

	protected String whoEmail;
	
	protected String whoTelf1;
	
	protected String whoTelf2;
	
	protected String whoDesc;
	
	protected Integer whoGender;
	
	protected Date whoBirthday;
	
	protected Long whoWhereId;
	
	public Who() {
    }

	public String getWhoName() {
		return whoName;
	}

	public void setWhoName(String whoName) {
		this.whoName = whoName;
	}

	public String getWhoSurname() {
		return whoSurname;
	}

	public void setWhoSurname(String whoSurname) {
		this.whoSurname = whoSurname;
	}

	public String getWhoEmail() {
		return whoEmail;
	}

	public void setWhoEmail(String whoEmail) {
		this.whoEmail = whoEmail;
	}

	public String getWhoTelf1() {
		return whoTelf1;
	}

	public void setWhoTelf1(String whoTelf1) {
		this.whoTelf1 = whoTelf1;
	}

	public String getWhoTelf2() {
		return whoTelf2;
	}

	public void setWhoTelf2(String whoTelf2) {
		this.whoTelf2 = whoTelf2;
	}

	public String getWhoDesc() {
		return whoDesc;
	}

	public void setWhoDesc(String whoDesc) {
		this.whoDesc = whoDesc;
	}
	
	public Integer getWhoGender() {
		return whoGender;
	}

	public void setWhoGender(Integer whoGender) {
		this.whoGender = whoGender;
	}

	public Date getWhoBirthday() {
		return whoBirthday;
	}

	public void setWhoBirthday(Date whoBirthday) {
		this.whoBirthday = whoBirthday;
	}

	public Long getWhoWhereId() {
		return whoWhereId;
	}

	public void setWhoWhereId(Long whoWhereId) {
		this.whoWhereId = whoWhereId;
	}
	
	
}