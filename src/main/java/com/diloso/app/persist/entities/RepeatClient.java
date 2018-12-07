package com.diloso.app.persist.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;

/**
 * The persistent class for the RepeatClient entity
 * 
 */
@Entity
/*@NamedQueries({
	@NamedQuery(name="getRepeatClientRep", query = "SELECT t FROM RepeatClient t WHERE t.recRepeatId = :recRepeatId and t.enabled =1 order by recBookingTime desc"),
	@NamedQuery(name="getRepeatClientCli", query = "SELECT t FROM RepeatClient t WHERE t.recClientId = :recClientId and t.enabled =1 order by recBookingTime desc")
})*/
public class RepeatClient extends Resource implements Serializable {
	protected static final long serialVersionUID = 1L;

	protected Long recRepeatId;

	protected Long recClientId;

	protected Date recBookingTime;
	
	protected Integer recBooking;
	
	public RepeatClient() {
    }

	public Long getRecRepeatId() {
		return recRepeatId;
	}

	public void setRecRepeatId(Long recRepeatId) {
		this.recRepeatId = recRepeatId;
	}

	public Long getRecClientId() {
		return recClientId;
	}

	public void setRecClientId(Long recClientId) {
		this.recClientId = recClientId;
	}

	public Date getRecBookingTime() {
		return recBookingTime;
	}

	public void setRecBookingTime(Date recBookingTime) {
		this.recBookingTime = recBookingTime;
	}

	public Integer getRecBooking() {
		return recBooking;
	}

	public void setRecBooking(Integer recBooking) {
		this.recBooking = recBooking;
	}
	
				  
}