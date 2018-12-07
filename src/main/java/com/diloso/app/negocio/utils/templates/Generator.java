package com.diloso.app.negocio.utils.templates;


public interface Generator<T> {
			
	StringBuffer generate(T model, String templatePath) throws Exception;
	
	
	StringBuffer generateContent(T model, String templateContent) throws Exception;
		
}
