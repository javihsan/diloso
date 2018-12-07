package com.diloso.app.negocio.dto;

import java.io.Serializable;
import java.util.Date;



public class EventDTO implements Serializable {
	protected static final long serialVersionUID = 1L;

	protected Long id;
	
	protected Integer enabled;

	// Descripcion del evento
	protected String eveDesc;
	// Puesto donde se reserva el evento
	protected Long eveCalendarId;
	// Cliente que consume el evento
	protected ClientDTO eveClient;
	// Quien reserva el evento 1 profesional / 0 cliente 
	protected Integer eveBooking;
	// Fecha en la que se reservó el evento
	protected Date eveBookingTime;
	// Fecha comienzo el evento
	protected Date eveStartTime;
	// Fecha fin el evento
	protected Date eveEndTime;
	// Consumido
	protected Integer eveConsumed;
	// Notificado
	protected Integer eveNotified;
	// Tarea local a realizar en el evento
	protected LocalTaskDTO eveLocalTask;
	// UID del evento en los iCalendars (*.ICS), o cita-visita a la que pertenece
	protected String eveICS;

	// Nombre del puesto, solo para mostrar, no se guarda
	protected String eveCalendarName;

	// ID del evento en Google Calendar
	protected String eveIDGCalendar;

	
    public EventDTO() {
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
	
	public String getEveDesc() {
		return eveDesc;
	}

	public void setEveDesc(String eveDesc) {
		this.eveDesc = eveDesc;
	}

	public Long getEveCalendarId() {
		return eveCalendarId;
	}

	public void setEveCalendarId(Long eveCalendarId) {
		this.eveCalendarId = eveCalendarId;
	}

	public ClientDTO getEveClient() {
		return eveClient;
	}

	public void setEveClient(ClientDTO eveClient) {
		this.eveClient = eveClient;
	}
	
	public Integer getEveBooking() {
		return eveBooking;
	}

	public void setEveBooking(Integer eveBooking) {
		this.eveBooking = eveBooking;
	}
	
	public Date getEveBookingTime() {
		return eveBookingTime;
	}

	public void setEveBookingTime(Date eveBookingTime) {
		this.eveBookingTime = eveBookingTime;
	}

	public Date getEveStartTime() {
		return eveStartTime;
	}

	public void setEveStartTime(Date eveStartTime) {
		this.eveStartTime = eveStartTime;
	}
	
	public Date getEveEndTime() {
		return eveEndTime;
	}

	public void setEveEndTime(Date eveEndTime) {
		this.eveEndTime = eveEndTime;
	}
	
	public Integer getEveConsumed() {
		return eveConsumed;
	}

	public void setEveConsumed(Integer eveConsumed) {
		this.eveConsumed = eveConsumed;
	}

	public Integer getEveNotified() {
		return eveNotified;
	}

	public void setEveNotified(Integer eveNotified) {
		this.eveNotified = eveNotified;
	}

	public LocalTaskDTO getEveLocalTask() {
		return eveLocalTask;
	}

	public void setEveLocalTask(LocalTaskDTO eveLocalTask) {
		this.eveLocalTask = eveLocalTask;
	}

	public String getEveICS() {
		return eveICS;
	}

	public void setEveICS(String eveICS) {
		this.eveICS = eveICS;
	}


	public String getEveCalendarName() {
		return eveCalendarName;
	}

	public void setEveCalendarName(String eveCalendarName) {
		this.eveCalendarName = eveCalendarName;
	}

	public String getEveIDGCalendar() {
		return eveIDGCalendar;
	}

	public void setEveIDGCalendar(String eveIDGCalendar) {
		this.eveIDGCalendar = eveIDGCalendar;
	}
	
}