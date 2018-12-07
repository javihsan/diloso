package com.diloso.app.negocio.dao;

import java.util.List;

import com.diloso.app.negocio.dto.ProductClassDTO;

public interface ProductClassDAO {

	ProductClassDTO create(ProductClassDTO productClass) throws Exception;

	ProductClassDTO remove(long id) throws Exception;

	ProductClassDTO update(ProductClassDTO productClass) throws Exception;

	ProductClassDTO getById(long id);
	
	List<ProductClassDTO> getProductClassByLang(String lang);
	
	List<ProductClassDTO> getProductClass();
	
}