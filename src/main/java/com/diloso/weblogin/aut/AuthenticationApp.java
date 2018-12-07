package com.diloso.weblogin.aut;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface AuthenticationApp {

	List<String> findUsers(HttpServletRequest request, HttpServletResponse response);
	
	boolean isRestrictedNivelUser(HttpServletRequest request, HttpServletResponse response);
}
