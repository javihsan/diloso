package com.diloso.app.persist.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * The persistent class for the LocalTask entity
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="getLocalTask", query = "SELECT t FROM LocalTask t WHERE t.lotLocalId=:lotLocalId and t.enabled =1 order by t.id asc"),
	@NamedQuery(name="getLocalTaskVisible", query = "SELECT t FROM LocalTask t WHERE t.lotLocalId=:lotLocalId and t.lotVisible=1 and t.enabled =1 order by t.id asc"),
	@NamedQuery(name="getLocalTaskMultiKey", query = "SELECT t FROM LocalTask t WHERE t.lotNameMulti=:lotNameMulti and t.enabled =1 order by t.id asc"),
	@NamedQuery(name="getLocalTaskAdmin", query = "SELECT t FROM LocalTask t WHERE t.lotLocalId=:lotLocalId order by t.id asc")
})
public class LocalTask implements Serializable {

	protected static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long id;
	
	protected Integer enabled;
		
	protected Long lotLocalId;
	
	protected Long lotTaskId;
	
	protected String lotNameMulti;
	
	protected Integer lotTaskDuration;
	
	protected Float lotTaskRate;
		
	protected List<Long> lotTaskCombiId;

	protected List<Integer> lotTaskCombiRes;
	
	protected Integer lotVisible;
	
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
	
	public Long getLotLocalId() {
		return lotLocalId;
	}

	public void setLotLocalId(Long lotLocalId) {
		this.lotLocalId = lotLocalId;
	}

	public Long getLotTaskId() {
		return lotTaskId;
	}

	public void setLotTaskId(Long lotTaskId) {
		this.lotTaskId = lotTaskId;
	}

	public String getLotNameMulti() {
		return lotNameMulti;
	}

	public void setLotNameMulti(String lotNameMulti) {
		this.lotNameMulti = lotNameMulti;
	}

	public Integer getLotTaskDuration() {
		return lotTaskDuration;
	}

	public void setLotTaskDuration(Integer lotTaskDuration) {
		this.lotTaskDuration = lotTaskDuration;
	}
	
	public Float getLotTaskRate() {
		return lotTaskRate;
	}

	public void setLotTaskRate(Float lotTaskRate) {
		this.lotTaskRate = lotTaskRate;
	}

	public List<Long> getLotTaskCombiId() {
		return lotTaskCombiId;
	}

	public void setLotTaskCombiId(List<Long> lotTaskCombiId) {
		this.lotTaskCombiId = lotTaskCombiId;
	}

	public List<Integer> getLotTaskCombiRes() {
		return lotTaskCombiRes;
	}

	public void setLotTaskCombiRes(List<Integer> lotTaskCombiRes) {
		this.lotTaskCombiRes = lotTaskCombiRes;
	}
	
	public Integer getLotVisible() {
		return lotVisible;
	}

	public void setLotVisible(Integer lotVisible) {
		this.lotVisible = lotVisible;
	}

}
