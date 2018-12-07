package com.diloso.app.persist.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * The persistent class for the MultiText entity
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="getMultiText", query = "SELECT t FROM MultiText t WHERE t.enabled =1 order by t.id asc"),
	@NamedQuery(name="getMultiLangCode", query = "SELECT t FROM MultiText t WHERE t.mulLanCode=:mulLanCode and t.enabled =1 order by t.id asc"),
	@NamedQuery(name="getMultiKey", query = "SELECT t FROM MultiText t WHERE t.mulKey=:mulKey and t.enabled =1 order by t.id asc"),
	@NamedQuery(name="getByLanCodeAndKey", query = "SELECT t FROM MultiText t WHERE t.mulLanCode=:mulLanCode and t.mulKey=:mulKey and t.enabled =1 order by t.id asc")
})
public class MultiText implements Serializable {

	protected static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long id;
	
	protected Integer enabled;
	
	protected String mulKey;
	
	protected String mulLanCode;
	
	protected String mulText;
	
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
	
	public String getMulKey() {
		return mulKey;
	}

	public void setMulKey(String mulKey) {
		this.mulKey = mulKey;
	}

	public String getMulLanCode() {
		return mulLanCode;
	}

	public void setMulLanCode(String mulLanCode) {
		this.mulLanCode = mulLanCode;
	}
	
	public String getMulText() {
		return mulText;
	}

	public void setMulText(String mulText) {
		this.mulText = mulText;
	}

	
}
