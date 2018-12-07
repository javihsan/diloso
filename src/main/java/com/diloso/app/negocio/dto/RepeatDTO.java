package com.diloso.app.negocio.dto;

import java.io.Serializable;



public class RepeatDTO extends EventDTO implements Serializable {
	protected static final long serialVersionUID = 1L;

	// Nombre del servicio del local en multiIdioma
	protected String repNameMulti;
	
	// Matriz de la repetici�n
	protected String repMatrix;
	
	// Tipo de la repetici�n (0: curso; 1: incripci�n)
	protected Integer repType;
	
	// Colecci�n a la que pertenece la repetici�n
	//protected CollectionEvent repCollection;
	// Profesional que trabaja la repetici�n
	protected ProfessionalDTO repProf;
	
	// M�ximo num de clientes de la repetici�n
	protected Integer repMaxClients;
	
	/* Propiedades aplicables a los dias de la semana 
	  (Horarios de apertura semanal en horas) 
	  de la repetici�n */
	protected SemanalDiaryDTO repSemanalDiary;

	// Nombre de la repetici�n, solo para mostrar, no se guarda
	protected String repName;

	
    public RepeatDTO() {
    }

    
	public String getRepNameMulti() {
		return repNameMulti;
	}

	public void setRepNameMulti(String repNameMulti) {
		this.repNameMulti = repNameMulti;
	}
	
	public String getRepMatrix() {
		return repMatrix;
	}

	public void setRepMatrix(String repMatrix) {
		this.repMatrix = repMatrix;
	}

	public Integer getRepType() {
		return repType;
	}

	public void setRepType(Integer repType) {
		this.repType = repType;
	}
	
	public ProfessionalDTO getRepProf() {
		return repProf;
	}

	public void setRepProf(ProfessionalDTO repProf) {
		this.repProf = repProf;
	}

	public Integer getRepMaxClients() {
		return repMaxClients;
	}

	public void setRepMaxClients(Integer repMaxClients) {
		this.repMaxClients = repMaxClients;
	}

	public SemanalDiaryDTO getRepSemanalDiary() {
		return repSemanalDiary;
	}

	public void setRepSemanalDiary(SemanalDiaryDTO repSemanalDiary) {
		this.repSemanalDiary = repSemanalDiary;
	}
	

	public String getRepName() {
		return repName;
	}

	public void setRepName(String repName) {
		this.repName = repName;
	}
  	
}