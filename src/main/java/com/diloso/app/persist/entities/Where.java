package com.diloso.app.persist.entities;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * The persistent class for the Where entity
 * 
 */
@Entity
/*@NamedQueries({
	@NamedQuery(name="getWhere", query = "SELECT t FROM Where t WHERE t.resFirId=:resFirId and t.enabled =1 order by t.id desc")
})*/
public class Where extends Resource implements Serializable {
	protected static final long serialVersionUID = 1L;

	protected String wheAddress;
	
	protected String wheCity;

	protected String wheState;
	
	protected String wheCP;
	
	protected String wheCountry;
	
	protected String wheTimeZone;
	
	protected String wheGoogleHoliday;
	
	protected String wheCurrency;
	
	public Where() {
    }

	public String getWheAddress() {
		return wheAddress;
	}

	public void setWheAddress(String wheAddress) {
		this.wheAddress = wheAddress;
	}
	
	public String getWheCity() {
		return wheCity;
	}

	public void setWheCity(String wheCity) {
		this.wheCity = wheCity;
	}

	public String getWheState() {
		return wheState;
	}

	public void setWheState(String wheState) {
		this.wheState = wheState;
	}

	public String getWheCP() {
		return wheCP;
	}

	public void setWheCP(String wheCP) {
		this.wheCP = wheCP;
	}

	public String getWheCountry() {
		return wheCountry;
	}

	public void setWheCountry(String wheCountry) {
		this.wheCountry = wheCountry;
	}

	public String getWheTimeZone() {
		return wheTimeZone;
	}

	public void setWheTimeZone(String wheTimeZone) {
		this.wheTimeZone = wheTimeZone;
	}
	
	public String getWheGoogleHoliday() {
		return wheGoogleHoliday;
	}

	public void setWheGoogleHoliday(String wheGoogleHoliday) {
		this.wheGoogleHoliday = wheGoogleHoliday;
	}

	public String getWheCurrency() {
		return wheCurrency;
	}

	public void setWheCurrency(String wheCurrency) {
		this.wheCurrency = wheCurrency;
	}
	
}