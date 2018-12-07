package com.diloso.app.negocio.dao;

import com.diloso.app.negocio.dto.SincroDTO;

public interface SincroDAO {

	SincroDTO create(SincroDTO sincro) throws Exception;

	SincroDTO remove(long id) throws Exception;

	SincroDTO update(SincroDTO sincro) throws Exception;

	SincroDTO getById(long id);
	
}