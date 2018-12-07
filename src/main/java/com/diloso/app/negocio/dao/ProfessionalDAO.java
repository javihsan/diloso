package com.diloso.app.negocio.dao;

import java.util.List;

import com.diloso.app.negocio.dto.ProfessionalDTO;

public interface ProfessionalDAO {

	ProfessionalDTO create(ProfessionalDTO professional) throws Exception;

	ProfessionalDTO remove(long id) throws Exception;

	ProfessionalDTO update(ProfessionalDTO professional) throws Exception;

	ProfessionalDTO getById(long id);
	
	ProfessionalDTO getByEmail(long resFirId, String email);

	List<ProfessionalDTO> getProfessional(long resFirId);
	
}