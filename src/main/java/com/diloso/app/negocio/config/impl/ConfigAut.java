package com.diloso.app.negocio.config.impl;

import com.diloso.app.negocio.config.IConfigAut;

public class ConfigAut extends Config implements IConfigAut {

	Integer configAutNivelUser;

	public Integer getConfigAutNivelUser() {
		return configAutNivelUser;
	}

	public void setConfigAutNivelUser(Integer configAutNivelUser) {
		this.configAutNivelUser = configAutNivelUser;
	}

	
}
