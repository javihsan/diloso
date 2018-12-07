package com.diloso.app.negocio.dao;

import com.diloso.app.negocio.dto.WhereDTO;

public interface WhereDAO {

	WhereDTO create(WhereDTO where) throws Exception;

	WhereDTO remove(long id) throws Exception;

	WhereDTO update(WhereDTO where) throws Exception;

	WhereDTO getById(long id);
}