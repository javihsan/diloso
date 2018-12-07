package com.diloso.app.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.UncategorizedDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.diloso.app.negocio.dao.ClientDAO;
import com.diloso.app.negocio.dao.FirmDAO;
import com.diloso.app.negocio.dao.LocalDAO;
import com.diloso.app.negocio.dto.ClientDTO;
import com.diloso.app.negocio.dto.FirmDTO;
import com.diloso.app.negocio.utils.Utils;

@Controller
@RequestMapping("/client")
public class ClientController {
	
	protected static final Logger log = Logger.getLogger(ClientController.class.getName());
	
	@Autowired
	protected MessageSource messageSourceApp;
	
	@Autowired
	protected FirmDAO firmDAO;
	
	@Autowired
	protected ClientDAO clientDAO;
	
	@Autowired
	protected LocalDAO localDAO;
	
	@ExceptionHandler(UncategorizedDataAccessException.class)
	@ResponseStatus(value=HttpStatus.CONFLICT,reason="")
	protected void error(Exception t, HttpServletResponse response) throws IOException{
		response.sendError(HttpStatus.CONFLICT.value(), t.getMessage());
	}
	
	/* El nombre no está vacío
	*/
	protected boolean validateNew(HttpServletRequest arg0, String cliName) throws UncategorizedDataAccessException {
		boolean res = true;
		String message = "";
		Locale locale = RequestContextUtils.getLocale(arg0);
		
		if (cliName==null || cliName.length()==0){
			message = messageSourceApp.getMessage("form.error.client.nameReq", null, locale);
			res = false;
		}
		
		if (!res){
			throw new ErrorService(message, null);
		}
		return res;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/operator/update")
	@ResponseStatus(HttpStatus.OK)
	protected void update(HttpServletRequest arg0, HttpServletResponse arg1)
			throws Exception {
		
		Locale locale = RequestContextUtils.getLocale(arg0);
		
		Long id = Long.parseLong(arg0.getParameter("id"));
		String cliEmail = arg0.getParameter("cliEmail").toLowerCase();
		String cliName = arg0.getParameter("cliName");
		String cliSurname = arg0.getParameter("cliSurname");
		String cliTelf1 = arg0.getParameter("cliTelf1");
		String cliTelf2 = arg0.getParameter("cliTelf2");
		String cliDesc = arg0.getParameter("cliDesc");
		Integer cliGender = Integer.parseInt(arg0.getParameter("cliGender"));
		String strCliBirthday = arg0.getParameter("cliBirthday");
				
		if (validateNew(arg0, cliName)){ 
			
			ClientDTO client = new ClientDTO();
			client.setId(id);
			client.setWhoEmail(cliEmail);
			client.setWhoName(cliName);
			client.setWhoSurname(cliSurname);
			client.setWhoTelf1(cliTelf1);
			client.setWhoTelf2(cliTelf2);
			client.setWhoDesc(cliDesc);
			if (cliGender>=0){
				client.setWhoGender(cliGender);
			}	
			if (strCliBirthday!=null && !strCliBirthday.equals("")){
				client.setWhoBirthday(Utils.getDate(strCliBirthday, locale));
			}
			
			clientDAO.update(client);

		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/operator/remove")
	@ResponseStatus(HttpStatus.OK)
	public void remove(HttpServletRequest arg0, HttpServletResponse arg1, @RequestParam("id") Long id)
			throws Exception {
		
		ClientDTO client = clientDAO.getById(id);
		if (client!=null){
			client.setEnabled(0);
			clientDAO.update(client);
			log.info("Cliente borrado : "+client.getId());
		}	
	}
	
	@RequestMapping("/operator/list")
	protected @ResponseBody
	List<ClientDTO> list(@RequestParam("domain") String domain) throws Exception {

		FirmDTO firm = firmDAO.getFirmDomain(domain);

		List<ClientDTO> listClient = clientDAO.getClient(firm.getId());	
					
		return listClient;
	}
	
	@RequestMapping("/operator/listByEmail")
	protected @ResponseBody
	ClientDTO listByEmail(@RequestParam("domain") String domain, @RequestParam("email") String email) throws Exception {
		
		FirmDTO firm = firmDAO.getFirmDomain(domain);
		
		ClientDTO client = clientDAO.getByEmail(firm.getId(), email);	
					
		return client;
	}
		
}
