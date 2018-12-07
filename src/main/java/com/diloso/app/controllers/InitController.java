package com.diloso.app.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InitController {

	public static final String URL_WEB = "www.diloso.com";
	public static final String URL_APP = "app.diloso.com";
	public static final String URL_APPSPOT = "dilosoweb.appspot.com";
	public static final String URL_LOCAL = "localhost";
	public static final String DEMO_APP = "demo";
	public static final String PATH_BOOKING = "/booking";
	
	public static final String[] PATHS_APP = {URL_WEB,URL_APP,URL_APPSPOT,URL_LOCAL};
	public static final String[] PATHS_HOME = {URL_WEB,URL_APPSPOT,URL_LOCAL};
	
	@RequestMapping(value="*", headers="host=app.bookingprof.com")
	protected ModelAndView initAppDemo(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
				
		String path = arg0.getRequestURI().toLowerCase();
		String[] a = path.split("/");
		
		String domain = DEMO_APP;
		if  (a.length>0){
			domain = a[1];
		}
		
		arg1.sendRedirect("/"+domain+PATH_BOOKING);
		return null;

	}
	
	@RequestMapping("")
	protected ModelAndView initDefault(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		String serverName = arg0.getServerName();
		if (isWebUrl(serverName)){
			arg1.sendRedirect("/home");
			return null;
		} else if (serverName.equalsIgnoreCase(URL_APP)){
			arg1.sendRedirect("/"+DEMO_APP+PATH_BOOKING);
			return null;
		} else{
			arg1.sendRedirect(PATH_BOOKING);
			return null;
		}
	}
	
	public static boolean isAppUrl(String serverName){
		for (String url : PATHS_APP) {
			if (serverName.toLowerCase().contains(url)){
				return true;
			}
		}
		return false;
	}
	
	
	public static boolean isWebUrl(String serverName){
		for (String url : PATHS_HOME) {
			if (serverName.toLowerCase().contains(url)){
				return true;
			}
		}
		return false;
	}
}
