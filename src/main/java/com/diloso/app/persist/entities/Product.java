package com.diloso.app.persist.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * The persistent class for the Product entity
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="getProduct", query = "SELECT t FROM Product t WHERE t.proLocalId=:proLocalId and t.enabled =1 order by t.id asc"),
	@NamedQuery(name="getProductAdmin", query = "SELECT t FROM Product t WHERE t.proLocalId=:proLocalId order by t.id asc")
})
public class Product implements Serializable {

	protected static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long id;
	
	protected Integer enabled;
	
	protected Long proLocalId;
	
	protected Long proClassId;
		
	protected String proNameMulti;
	
	protected Float proRate;
	
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

	public Long getProLocalId() {
		return proLocalId;
	}

	public void setProLocalId(Long proLocalId) {
		this.proLocalId = proLocalId;
	}
	
	public Long getProClassId() {
		return proClassId;
	}

	public void setProClassId(Long proClassId) {
		this.proClassId = proClassId;
	}

	public String getProNameMulti() {
		return proNameMulti;
	}

	public void setProNameMulti(String proNameMulti) {
		this.proNameMulti = proNameMulti;
	}

	public Float getProRate() {
		return proRate;
	}

	public void setProRate(Float proRate) {
		this.proRate = proRate;
	}

		
}
