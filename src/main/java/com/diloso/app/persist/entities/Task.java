package com.diloso.app.persist.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * The persistent class for the Task entity
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="getTask", query = "SELECT t FROM Task t WHERE t.enabled =1 order by t.id asc"),
	@NamedQuery(name="getTaskMultiKey", query = "SELECT t FROM Task t WHERE t.tasNameMulti=:tasNameMulti and t.enabled =1 order by t.id asc")
})
public class Task implements Serializable {

	protected static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long id;
	
	protected Integer enabled;
	
	protected Long tasClassId;
	
	protected String tasNameMulti;
	
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
	
	public Long getTasClassId() {
		return tasClassId;
	}

	public void setTasClassId(Long tasClassId) {
		this.tasClassId = tasClassId;
	}

	public String getTasNameMulti() {
		return tasNameMulti;
	}

	public void setTasNameMulti(String tasNameMulti) {
		this.tasNameMulti = tasNameMulti;
	}
}
