package com.diloso.app.negocio.config.impl;

import com.diloso.app.negocio.config.IConfigPay;

public class ConfigPay extends Config implements IConfigPay {

	Integer configPayAdmin;

	public Integer getConfigPayAdmin() {
		return configPayAdmin;
	}

	public void setConfigPayAdmin(Integer configPayAdmin) {
		this.configPayAdmin = configPayAdmin;
	}
	
	
	
}
