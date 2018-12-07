package com.diloso.app.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

import com.diloso.app.negocio.dao.LangDAO;
import com.diloso.app.negocio.dao.MultiTextDAO;
import com.diloso.app.negocio.dao.TaskClassDAO;
import com.diloso.app.negocio.dto.LangDTO;
import com.diloso.app.negocio.dto.MultiTextDTO;
import com.diloso.app.negocio.dto.TaskClassDTO;
import com.diloso.app.persist.manager.TaskClassManager;

@Controller
@RequestMapping("/taskClass")
public class TaskClassController {
	
	protected static final String TCL_NAME_PARAM = "tclName";
	
	@Autowired
	protected MessageSource messageSourceApp;
	
	@Autowired
	protected TaskClassDAO taskClassDAO;
	
	@Autowired
	protected MultiTextDAO multiTextDAO;
	
	@Autowired
	protected LangDAO langDAO;
	
	@ExceptionHandler(UncategorizedDataAccessException.class)
	@ResponseStatus(value=HttpStatus.CONFLICT,reason="")
	protected void error(Exception t, HttpServletResponse response) throws IOException{
		response.sendError(HttpStatus.CONFLICT.value(), t.getMessage());
	}
	
	/* El nombre y el valor no est� vac�o.
	*/
	protected boolean validateNew(HttpServletRequest arg0, String defaultNameValue) throws UncategorizedDataAccessException {
		boolean res = true;
		String message = "";
		Locale locale = RequestContextUtils.getLocale(arg0);
		if (defaultNameValue==null || defaultNameValue.length()==0){
			message =  messageSourceApp.getMessage("form.error.taskClass.nameReq", null, locale);
			res = false;
		}
		
		if (!res){
			throw new ErrorService(message, null);
		}
		return res;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/manager/new")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	protected TaskClassDTO newObject(HttpServletRequest arg0, HttpServletResponse arg1)
			throws Exception {
		
		Locale locale = RequestContextUtils.getLocale(arg0);
		String langDefault = locale.getLanguage();
		
		String tclId = arg0.getParameter("id");
		
		String defaultNameValue = arg0.getParameter(TCL_NAME_PARAM+"_"+langDefault);			
				
		if (validateNew(arg0, defaultNameValue)){ 	
						
			Long localId = new Long(arg0.getParameter("localId"));
			
			TaskClassDTO taskClass = new TaskClassDTO();
			
			if (tclId!=null){ // Existe
				taskClass = taskClassDAO.getById(new Long(tclId));
			}
			
			String nameValue = "";
			String keyNameMulti = TaskClassManager.KEY_MULTI_TASKCLASS_NAME+localId+"_"+defaultNameValue.toLowerCase();
			Map<String,String> hashNamesParam = new HashMap<String,String>();

			List<LangDTO> listLang = langDAO.getLang();	
			for (LangDTO lang : listLang) {
				nameValue = arg0.getParameter(TCL_NAME_PARAM+"_"+lang.getLanCode());
				if (nameValue == null || nameValue.length()==0){
					nameValue = defaultNameValue;
				}
				hashNamesParam.put(lang.getLanCode(), nameValue);
			}

			if (tclId!=null){ // Existe
				List<MultiTextDTO> listMulti = multiTextDAO.getMultiTextByKey(taskClass.getTclNameMulti());
				for (MultiTextDTO nameMulti: listMulti){
					nameValue = hashNamesParam.get(nameMulti.getMulLanCode());
					nameMulti.setMulText(nameValue);
					multiTextDAO.update(nameMulti);
				}
			} else {
				int indx = 0;
				while (multiTextDAO.getByLanCodeAndKey(locale.getLanguage(), keyNameMulti)!=null){
					keyNameMulti = TaskClassManager.KEY_MULTI_TASKCLASS_NAME+localId+"_"+defaultNameValue.toLowerCase()+"_"+indx;
					indx ++;
				}
				
				MultiTextDTO nameMulti = null;
				for (String codeLangMap : hashNamesParam.keySet()) {
					nameValue = hashNamesParam.get(codeLangMap);
					nameMulti = new MultiTextDTO();
					nameMulti.setEnabled(1);
					nameMulti.setMulKey(keyNameMulti);
					nameMulti.setMulLanCode(codeLangMap);
					nameMulti.setMulText(nameValue);
					multiTextDAO.create(nameMulti);
				}
			}
					
			if (tclId!=null){ // Existe
				taskClass = taskClassDAO.update(taskClass);
			} else { // Es nuevo
				taskClass.setEnabled(1);
				taskClass.setTclNameMulti(keyNameMulti);
				taskClass = taskClassDAO.create(taskClass);
			}
			return taskClass;
		}
		return null;
	}

	
	@RequestMapping(method = RequestMethod.PUT, value = "/manager/remove")
	@ResponseStatus(HttpStatus.OK)
	protected void remove(@RequestParam("id") Long id)
			throws Exception {
			
		if (id!=null){ // Existe
			TaskClassDTO taskClass = taskClassDAO.getById(id);
			
			List<MultiTextDTO> listMulti = multiTextDAO.getMultiTextByKey(taskClass.getTclNameMulti());
			for (MultiTextDTO nameMulti: listMulti){
				multiTextDAO.remove(nameMulti.getId());
			}
			taskClassDAO.remove(id);
		}
	}
	
	@RequestMapping("/operator/list")
	protected @ResponseBody
	List<TaskClassDTO> list(HttpServletRequest arg0) throws Exception {

		Locale locale = RequestContextUtils.getLocale(arg0);
		
		List<TaskClassDTO> listTaskClass = taskClassDAO.getTaskClassByLang(locale.getLanguage());	
					
		return listTaskClass;
	}
		
	@RequestMapping("/manager/get")
	protected @ResponseBody
	TaskClassDTO get(@RequestParam("id") Long id) throws Exception {

		TaskClassDTO taskClass = taskClassDAO.getById(id);	
					
		return taskClass;
	}
	

}
