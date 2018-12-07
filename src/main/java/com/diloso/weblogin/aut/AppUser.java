package com.diloso.weblogin.aut;

import java.io.Serializable;
import java.util.Set;

public class AppUser implements Serializable {

	protected String email;
	protected Set<AppRole> authorities;
	protected boolean enabled;
		
	
	public AppUser(String email, Set<AppRole> authorities, boolean enabled) {
		super();
		this.email = email;
		this.authorities = authorities;
		this.enabled = enabled;
	}



	public String getEmail() {
		return email;
	}

	public Set<AppRole> getAuthorities() {
		return authorities;
	}

	public boolean isEnabled() {
		return enabled;
	}

}
