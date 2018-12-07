package com.diloso.app.persist.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The persistent class for the SemanalDiary entity
 * 
 */
@Entity
public class SemanalDiary implements Serializable {
	protected static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long id;
	
	protected Integer enabled;

	protected Long semMonDiaryId;

	protected Long semTueDiaryId;

	protected Long semWedDiaryId;

	protected Long semThuDiaryId;

	protected Long semFriDiaryId;

	protected Long semSatDiaryId;

	protected Long semSunDiaryId;

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

	public Long getSemMonDiaryId() {
		return semMonDiaryId;
	}

	public void setSemMonDiaryId(Long semMonDiaryId) {
		this.semMonDiaryId = semMonDiaryId;
	}

	public Long getSemTueDiaryId() {
		return semTueDiaryId;
	}

	public void setSemTueDiaryId(Long semTueDiaryId) {
		this.semTueDiaryId = semTueDiaryId;
	}

	public Long getSemWedDiaryId() {
		return semWedDiaryId;
	}

	public void setSemWedDiaryId(Long semWedDiaryId) {
		this.semWedDiaryId = semWedDiaryId;
	}

	public Long getSemThuDiaryId() {
		return semThuDiaryId;
	}

	public void setSemThuDiaryId(Long semThuDiaryId) {
		this.semThuDiaryId = semThuDiaryId;
	}

	public Long getSemFriDiaryId() {
		return semFriDiaryId;
	}

	public void setSemFriDiaryId(Long semFriDiaryId) {
		this.semFriDiaryId = semFriDiaryId;
	}

	public Long getSemSatDiaryId() {
		return semSatDiaryId;
	}

	public void setSemSatDiaryId(Long semSatDiaryId) {
		this.semSatDiaryId = semSatDiaryId;
	}

	public Long getSemSunDiaryId() {
		return semSunDiaryId;
	}

	public void setSemSunDiaryId(Long semSunDiaryId) {
		this.semSunDiaryId = semSunDiaryId;
	}

		
}
