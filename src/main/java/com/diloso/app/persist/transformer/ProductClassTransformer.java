package com.diloso.app.persist.transformer;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.diloso.app.negocio.dto.ProductClassDTO;
import com.diloso.app.persist.entities.ProductClass;


@Component
@Scope(value = "singleton")
public class ProductClassTransformer {
	
	public ProductClassTransformer() {

	}
	
	public ProductClass transformDTOToEntity(ProductClassDTO productClass){
		
		ProductClass entityProductClass = new ProductClass();
		
		try {
			PropertyUtils.copyProperties(entityProductClass, productClass);
		} catch (Exception e) {
		}
		return entityProductClass;
	}
	
	public ProductClassDTO transformEntityToDTO(ProductClass entityProductClass) {

		ProductClassDTO productClass = new ProductClassDTO();

		try {
			PropertyUtils.copyProperties(productClass, entityProductClass);
			
		} catch (Exception e) {
		}
		return productClass;
	}
	

	
}
