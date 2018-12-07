package com.diloso.app.persist.transformer;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.diloso.app.negocio.dto.ProductDTO;
import com.diloso.app.persist.entities.Product;
import com.google.appengine.api.datastore.Entity;


@Component
@Scope(value = "singleton")
public class ProductTransformer {
	
	public ProductTransformer() {

	}
	public Product transformDTOToEntity(ProductDTO product){
		
		Product entityProduct = new Product();
		
		try {
			PropertyUtils.copyProperties(entityProduct, product);
		} catch (Exception e) {
		}
		return entityProduct;
	}
	
	public ProductDTO transformEntityToDTO(Product entityProduct) {

		ProductDTO product = new ProductDTO();

		try {
			PropertyUtils.copyProperties(product, entityProduct);
			
		} catch (Exception e) {
		}
		return product;
	}
	
	public ProductDTO transformEntityToDTO(Entity entityProduct) {

		ProductDTO product = new ProductDTO();

		try {
			product.setId(entityProduct.getKey().getId());
			PropertyDescriptor[] pd = PropertyUtils.getPropertyDescriptors(ProductDTO.class);
			Object value;
			for (PropertyDescriptor descriptor : pd) {
				Method writeMethod = PropertyUtils.getWriteMethod(descriptor);
				if (writeMethod!=null){
					value = entityProduct.getProperty(descriptor.getName());
					if (value instanceof Long){
						if (writeMethod.getParameterTypes()[0].getName().equals("java.lang.Integer")){
							value = new Integer(value.toString());
						}
					} else if (value instanceof Double){
						if (writeMethod.getParameterTypes()[0].getName().equals("java.lang.Float")){
							value = new Float(value.toString());
						}
					} 
					if (value!=null) writeMethod.invoke(product,value);
				}
			}
			
		} catch (Exception e) {
		}
		return product;
	}
	
}
