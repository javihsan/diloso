package com.diloso.app.persist.entities;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * The persistent class for the Diary entity
 * 
 */
@Entity 
public class Sincro extends Resource implements Serializable {
	protected static final long serialVersionUID = 1L;
	
	protected Integer sinType;

	protected String sinApiKey;

	protected String sinApiSecret;

	protected String sinConKey;

	protected String sinConSecret;

	protected String sinConEmail;

	protected String sinConTelf;
	
	public Sincro() {
    }
	
	public Integer getSinType() {
		return sinType;
	}


	public void setSinType(Integer sinType) {
		this.sinType = sinType;
	}


	public String getSinApiKey() {
		return sinApiKey;
	}


	public void setSinApiKey(String sinApiKey) {
		this.sinApiKey = sinApiKey;
	}


	public String getSinApiSecret() {
		return sinApiSecret;
	}


	public void setSinApiSecret(String sinApiSecret) {
		this.sinApiSecret = sinApiSecret;
	}


	public String getSinConKey() {
		return sinConKey;
	}


	public void setSinConKey(String sinConKey) {
		this.sinConKey = sinConKey;
	}


	public String getSinConSecret() {
		return sinConSecret;
	}


	public void setSinConSecret(String sinConSecret) {
		this.sinConSecret = sinConSecret;
	}


	public String getSinConEmail() {
		return sinConEmail;
	}


	public void setSinConEmail(String sinConEmail) {
		this.sinConEmail = sinConEmail;
	}


	public String getSinConTelf() {
		return sinConTelf;
	}


	public void setSinConTelf(String sinConTelf) {
		this.sinConTelf = sinConTelf;
	}
}
