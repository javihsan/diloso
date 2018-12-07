package com.diloso.app.negocio.config;

import java.util.Map;

import com.diloso.app.negocio.config.impl.ConfigClientField;

public interface IConfigClient extends IConfig {
	
	Map<String,ConfigClientField> getExtraTable();
	
	Map<String,ConfigClientField> getExtraBook();
		
}
