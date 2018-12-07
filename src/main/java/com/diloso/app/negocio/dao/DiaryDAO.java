package com.diloso.app.negocio.dao;

import com.diloso.app.negocio.dto.DiaryDTO;

public interface DiaryDAO {

	DiaryDTO create(DiaryDTO diary) throws Exception;

	DiaryDTO remove(long id) throws Exception;

	DiaryDTO update(DiaryDTO diary) throws Exception;

	DiaryDTO getById(long id);

}