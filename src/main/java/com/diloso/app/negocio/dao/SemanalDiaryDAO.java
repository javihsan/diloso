package com.diloso.app.negocio.dao;

import com.diloso.app.negocio.dto.SemanalDiaryDTO;

public interface SemanalDiaryDAO {

	SemanalDiaryDTO create(SemanalDiaryDTO semanalDiary) throws Exception;

	SemanalDiaryDTO remove(long id) throws Exception;

	SemanalDiaryDTO update(SemanalDiaryDTO semanalDiary) throws Exception;

	SemanalDiaryDTO getById(long id);

}