package com.diloso.app.controllers;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.diloso.app.negocio.config.impl.ConfigFirm;
import com.diloso.app.negocio.dao.FirmDAO;
import com.diloso.app.negocio.dao.ProfessionalDAO;
import com.diloso.app.negocio.dao.WhereDAO;
import com.diloso.app.negocio.dto.FirmDTO;
import com.diloso.app.negocio.dto.ProfessionalDTO;
import com.diloso.app.negocio.dto.WhereDTO;
import com.diloso.app.negocio.utils.ApplicationContextProvider;
import com.diloso.app.persist.manager.FirmManager;
import com.diloso.app.persist.manager.IEMF;
import com.diloso.weblogin.aut.AppRole;
import com.diloso.weblogin.aut.AppUser;
import com.diloso.weblogin.aut.AuthenticationApp;
import com.diloso.weblogin.aut.DatastoreUserRegistry;
import com.diloso.weblogin.aut.UserRegistry;

@Controller
@RequestMapping("/firm")
public class FirmController implements AuthenticationApp{
	
	protected static final Logger log = Logger.getLogger(FirmController.class.getName());
	
	@Autowired
	protected FirmDAO firmDAO;
	
	@Autowired
	protected ProfessionalDAO professionalDAO;
	
	@Autowired
	protected WhereDAO whereDAO;
	
	protected UserRegistry userRegistry = new DatastoreUserRegistry();
	
	@RequestMapping(method = RequestMethod.POST, value = "/admin/new")
	@ResponseStatus(HttpStatus.OK)
	protected void newObject(HttpServletRequest arg0, HttpServletResponse arg1)
			throws Exception {
		
		String firId = arg0.getParameter("id");
		
		String firName = arg0.getParameter("firName");
		String firDomain = arg0.getParameter("firDomain");
		String firServer = arg0.getParameter("firServer");
		
		String firResponName = arg0.getParameter("firResponName");
		String firResponSurname = arg0.getParameter("firResponSurname");
		String firResponEmail = arg0.getParameter("firResponEmail");
		String firResponTelf1 = arg0.getParameter("firResponTelf1");
		
		String firAddress = arg0.getParameter("firAddress");
		String firCity = arg0.getParameter("firCity");
		String firState = arg0.getParameter("firState");
		String firCP = arg0.getParameter("firCP");
		String firCountry = arg0.getParameter("firCountry");
		
		int firBilledModule = Integer.parseInt(arg0.getParameter("firBilledModule"));
		String firConfig = arg0.getParameter("firConfig");
		
		String strFirClassTasks = arg0.getParameter("firClassTasks");
		
		String strFirGwtUsers = arg0.getParameter("firGwtUsers");
		
		FirmDTO firm = new FirmDTO();
		if (firId!=null){ // Existe
			firm = firmDAO.getById(new Long(firId));
		}
		
		ProfessionalDTO respon = new ProfessionalDTO();
		if (firId!=null){ // Existe
			respon = firm.getFirRespon();
			respon.setWhoName(firResponName);
			respon.setWhoSurname(firResponSurname);
			respon.setWhoEmail(firResponEmail);
			respon.setWhoTelf1(firResponTelf1);
			
			respon = professionalDAO.update(respon);

		} else {
		
			respon.setEnabled(1);
			respon.setWhoName(firResponName);
			respon.setWhoSurname(firResponSurname);
			respon.setWhoEmail(firResponEmail);
			respon.setWhoTelf1(firResponTelf1);
		
			respon = professionalDAO.create(respon);
		}
		firm.setFirRespon(respon);
		
		WhereDTO where = new WhereDTO();
		if (firId!=null){ // Existe
			where = firm.getFirWhere();
			
			where.setWheAddress(firAddress);
			where.setWheCity(firCity);
			where.setWheState(firState);
			where.setWheCP(firCP);
			where.setWheCountry(firCountry);
			
			where = whereDAO.update(where);
		} else {
			where.setEnabled(1);
			where.setWheAddress(firAddress);
			where.setWheCity(firCity);
			where.setWheState(firState);
			where.setWheCP(firCP);
			where.setWheCountry(firCountry);
			
			where = whereDAO.create(where);
		}
		firm.setFirWhere(where);
		firm.setFirName(firName);
		firm.setFirDomain(firDomain);
		firm.setFirServer(firServer);
		firm.setFirBilledModule(firBilledModule);
		firm.setFirConfig(new ConfigFirm(firConfig));
		
		List<String> firGwtUsers = new ArrayList<String>();
		String[] a = strFirGwtUsers.split(",");
		AppUser user = null;
		Set<AppRole> roles = EnumSet.noneOf(AppRole.class);
		roles.add(AppRole.MANAGER);
		roles.add(AppRole.OPERATOR);
		for (String strUser : a) {
			if (strUser.length()>0){
				firGwtUsers.add(strUser);
				if(userRegistry.findUser(strUser)==null){
					user = new AppUser(strUser, roles, true);
					userRegistry.registerUser(user);
				}	
			}	
		}
		firm.setFirGwtUsers(firGwtUsers);
		
		//List<TaskClassDTO> firClassTasks = taskClassDAO.getTaskClass();
		
		List<Long> firClassTasksId = new ArrayList<Long>();
		a = strFirClassTasks.split(",");
		for (String strTask : a) {
			if (strTask.length()>0){
				firClassTasksId.add(new Long(strTask));
			}	
		}
		firm.setFirClassTasks(firClassTasksId);
		
		if (firId!=null){ // Existe

			firm = firmDAO.update(firm);
		} else {
			
			firm.setEnabled(0);
			firm = firmDAO.create(firm);
			
			where.setResFirId(firm.getId());
			whereDAO.update(where);
			
			respon.setResFirId(firm.getId());
			professionalDAO.update(respon);
		}
		
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/admin/enabled")
	@ResponseStatus(HttpStatus.OK)
	public void enabled(@RequestParam("id") Long id)
			throws Exception {
		FirmDTO firm = firmDAO.getById(id);

		if (firm!=null){
			if (firm.getEnabled()==1){
				firm.setEnabled(0);
				log.info("Firma deshabilitada : "+firm.getFirDomain());
			} else {
				firm.setEnabled(1);
				log.info("Firma habilitada : "+firm.getFirDomain());
			}
			firmDAO.update(firm);
		}	
	}
	
	@RequestMapping("/admin/list")
	protected @ResponseBody
	List<FirmDTO> list() throws Exception {

		List<FirmDTO> firmLocal = firmDAO.getFirmAdmin();
		return firmLocal;
	}
	
	@RequestMapping("/get")
	protected @ResponseBody
	FirmDTO getDomain(@RequestParam("domain") String domain) throws Exception {
	
		FirmDTO firm = firmDAO.getFirmDomain(domain);
		
		return firm;
	}	
	
	
	@RequestMapping("/admin/get")
	protected @ResponseBody
	FirmDTO getDomainAdmin(@RequestParam("domain") String domain) throws Exception {
	
		FirmDTO firm = firmDAO.getFirmDomainAdmin(domain);
		
		return firm;
	}
	
		
	@RequestMapping("/getDomainServer")
	protected @ResponseBody
	String getServerAdmin(@RequestParam("server") String server) throws Exception {
	
		return firmDAO.getDomainServer(server);
	}
	
	public List<String> findUsers(HttpServletRequest arg0, HttpServletResponse arg1){
		
		if (firmDAO==null){
			firmDAO = new FirmManager();
			IEMF beanEMF = (IEMF) ApplicationContextProvider
					.getApplicationContext().getBean("beanEMF");
			((FirmManager)firmDAO).setBeanEMF(beanEMF);
		}
		
		String serverName = arg0.getServerName();
		String domain = "";
		if (InitController.isAppUrl(serverName)){
			String path = arg0.getRequestURI().toLowerCase();
			String[] a = path.split("/");
			domain = InitController.DEMO_APP;
			if  (a.length>0){
				domain = a[1];
			}
		} else {
			domain = firmDAO.getDomainServer(serverName);
		}

		return firmDAO.findUsers(domain);
	}
	
	public boolean isRestrictedNivelUser(HttpServletRequest arg0, HttpServletResponse arg1){
		
		if (firmDAO==null){
			firmDAO = new FirmManager();
			IEMF beanEMF = (IEMF) ApplicationContextProvider
					.getApplicationContext().getBean("beanEMF");
			((FirmManager)firmDAO).setBeanEMF(beanEMF);
		}
		
		String serverName = arg0.getServerName();
		String domain = "";
		if (InitController.isAppUrl(serverName)){
			String path = arg0.getRequestURI().toLowerCase();
			String[] a = path.split("/");
			domain = InitController.DEMO_APP;
			if  (a.length>0){
				domain = a[1];
			}
		} else {
			domain = firmDAO.getDomainServer(serverName);
		}
		
		return firmDAO.isRestrictedNivelUser(domain);
	}
}
