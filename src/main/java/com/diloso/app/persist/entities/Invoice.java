package com.diloso.app.persist.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * The persistent class for the Invoice entity
 * 
 */
@Entity
public class Invoice implements Serializable {
	protected static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long id;
	
	protected Integer enabled;

	protected Long invLocalId;
	
	protected String invDesc;

	protected Long invClientId;

	protected Date invIssueTime;

	protected Date invTime;

	protected Float invRate;
	
    public Invoice() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	
	public Long getInvLocalId() {
		return invLocalId;
	}

	public void setInvLocalId(Long invLocalId) {
		this.invLocalId = invLocalId;
	}
	
	public String getInvDesc() {
		return invDesc;
	}

	public void setInvDesc(String invDesc) {
		this.invDesc = invDesc;
	}

	public Long getInvClientId() {
		return invClientId;
	}

	public void setInvClientId(Long invClientId) {
		this.invClientId = invClientId;
	}

	public Date getInvIssueTime() {
		return invIssueTime;
	}

	public void setInvIssueTime(Date invIssueTime) {
		this.invIssueTime = invIssueTime;
	}

	public Date getInvTime() {
		return invTime;
	}

	public void setInvTime(Date invTime) {
		this.invTime = invTime;
	}

	public Float getInvRate() {
		return invRate;
	}

	public void setInvRate(Float invRate) {
		this.invRate = invRate;
	}
    
    			
}