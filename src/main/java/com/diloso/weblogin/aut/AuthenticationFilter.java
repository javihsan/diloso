package com.diloso.weblogin.aut;

import java.io.IOException;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import com.diloso.app.controllers.InitController;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

public class AuthenticationFilter extends GenericFilterBean {

	protected AuthenticationApp authenticationApp;
	protected AuthenticationManager authenticationManager;
	protected AppAccessDeniedHandler deniedHandler;

	private final static String OPERATOR = "operator";
	private final static String	MANAGER = "manager";
	private final static String ADMIN = "admin";
	private final static String REPORT = "report";
	private final static String BOOKING = "booking";
	private final static String CONTINUE = "continue";
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		((HttpServletResponse)response).setHeader("Access-Control-Allow-Origin", "*");
		((HttpServletResponse)response).setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		((HttpServletResponse)response).setHeader("Access-Control-Max-Age", "3600");
		((HttpServletResponse)response).setHeader("Access-Control-Allow-Headers", "x-requested-with");
		
		String path = ((HttpServletRequest)request).getServletPath();
				
		if (path.indexOf(CONTINUE)==-1 && (
				path.indexOf(OPERATOR)!=-1||
					path.indexOf(MANAGER)!=-1||
						path.indexOf(ADMIN)!=-1||
							path.indexOf(REPORT)!=-1||
								path.indexOf(BOOKING)!=-1
				)				
	    ){
			String serverName = ((HttpServletRequest)request).getServerName();
			String domain = "";
			if (InitController.isAppUrl(serverName)){
				String[] a = path.split("/");
				domain = InitController.DEMO_APP;
				if  (a.length>0){
					domain = a[1];
				}
			}
			String pathKey = serverName+domain;
			if (path.indexOf(OPERATOR)!=-1){
				pathKey += OPERATOR;
			} else if (path.indexOf(MANAGER)!=-1){
				pathKey += MANAGER;
			} else if (path.indexOf(ADMIN)!=-1){
				pathKey += ADMIN;
			} else if (path.indexOf(REPORT)!=-1){
				pathKey += REPORT;
			}  else if (path.indexOf(BOOKING)!=-1){
				pathKey += BOOKING;
			} 
					
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication !=null && !(authentication.getDetails() instanceof String)){
				authentication = null;
			}
			if (authentication != null && ((String)authentication.getDetails()).indexOf(pathKey)==-1) { // Si tenemos autorizaci�n pero no para esta url, reseteamos el SecurityContextHolder
				authentication = null;
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			
			if (authentication == null) {
				/* Si el nivel de User para /booking es libre, forzamos la autentificaci�n */
				if (((HttpServletRequest)request).getServletPath().indexOf(BOOKING)!=-1 
			       && !authenticationApp.isRestrictedNivelUser((HttpServletRequest)request, (HttpServletResponse)response)
				){	    
					Collection<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
					Set<AppRole> rolesUser = EnumSet.noneOf(AppRole.class);
					rolesUser.add(AppRole.USER);
					for (AppRole r : rolesUser) {
						roles.add(r);
					}
					authentication = new UserAuthentication(pathKey,roles, true);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				} else {
				
					// User isn't authenticated. Check if there is a Google Accounts user
					User googleUser = UserServiceFactory.getUserService().getCurrentUser();
		
					if (googleUser != null) {
						// User has returned after authenticating through Gwt. Need to
						// authenticate to Spring Security.
						List<String> users = authenticationApp.findUsers((HttpServletRequest)request, (HttpServletResponse)response);
						PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(googleUser, users);
						token.setDetails(pathKey);
						try {
							authentication = authenticationManager.authenticate(token);
							// Setup the security context
							SecurityContextHolder.getContext().setAuthentication(authentication);
		
						} catch (AuthenticationException e) {
							deniedHandler.handle(((HttpServletRequest)request), ((HttpServletResponse)response), new AccessDeniedException(""));
						}
					}	
				}	
			} 
		}	
		chain.doFilter(request, response);
	}

	public AuthenticationApp getAuthenticationApp() {
		return authenticationApp;
	}

	public void setAuthenticationApp(AuthenticationApp authenticationApp) {
		this.authenticationApp = authenticationApp;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public AppAccessDeniedHandler getDeniedHandler() {
		return deniedHandler;
	}

	public void setDeniedHandler(AppAccessDeniedHandler deniedHandler) {
		this.deniedHandler = deniedHandler;
	}

	
}
