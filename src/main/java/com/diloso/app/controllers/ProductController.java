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
import com.diloso.app.negocio.dao.ProductDAO;
import com.diloso.app.negocio.dto.LangDTO;
import com.diloso.app.negocio.dto.MultiTextDTO;
import com.diloso.app.negocio.dto.ProductDTO;
import com.diloso.app.persist.manager.ProductManager;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	protected static final String PRO_NAME_PARAM = "proName";
	
	@Autowired
	protected MessageSource messageSourceApp;
	
	@Autowired
	protected ProductDAO productDAO;
	
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
	protected boolean validateNew(HttpServletRequest arg0, String defaultNameValue, String strProRate) throws UncategorizedDataAccessException {
		boolean res = true;
		String message = "";
		Locale locale = RequestContextUtils.getLocale(arg0);
		if (defaultNameValue==null || defaultNameValue.length()==0){
			message =  messageSourceApp.getMessage("form.error.product.nameReq", null, locale);
			res = false;
		} else if (strProRate==null || strProRate.length()==0){
			message = messageSourceApp.getMessage("form.error.product.rateReq", null, locale);
			res = false;
		} else {
			try{
				strProRate = strProRate.replace(",", ".");
				new Float(strProRate);
				
			} catch( NumberFormatException e){
				message = messageSourceApp.getMessage("form.error.product.rateNum", null, locale);
				res = false;
			}
		}
		
		if (!res){
			throw new ErrorService(message, null);
		}
		return res;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/manager/new")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	protected ProductDTO newObject(HttpServletRequest arg0, HttpServletResponse arg1)
			throws Exception {
		
		Locale locale = RequestContextUtils.getLocale(arg0);
		String langDefault = locale.getLanguage();
		
		String proId = arg0.getParameter("id");
		
		String defaultNameValue = arg0.getParameter(PRO_NAME_PARAM+"_"+langDefault);			
				
		String strProRate = arg0.getParameter("proRate");
				
		if (validateNew(arg0, defaultNameValue, strProRate)){ 	
						
			Long localId = new Long(arg0.getParameter("localId"));
			
			ProductDTO product = new ProductDTO();
			
			if (proId!=null){ // Existe
				product = productDAO.getById(new Long(proId));
			}
			
			String nameValue = "";
			String keyNameMulti = ProductManager.KEY_MULTI_RATE_NAME+localId+"_"+defaultNameValue.toLowerCase();
			Map<String,String> hashNamesParam = new HashMap<String,String>();

			List<LangDTO> listLang = langDAO.getLang();	
			for (LangDTO lang : listLang) {
				nameValue = arg0.getParameter(PRO_NAME_PARAM+"_"+lang.getLanCode());
				if (nameValue == null || nameValue.length()==0){
					nameValue = defaultNameValue;
				}
				hashNamesParam.put(lang.getLanCode(), nameValue);
			}

			if (proId!=null){ // Existe
				List<MultiTextDTO> listMulti = multiTextDAO.getMultiTextByKey(product.getProNameMulti());
				for (MultiTextDTO nameMulti: listMulti){
					nameValue = hashNamesParam.get(nameMulti.getMulLanCode());
					nameMulti.setMulText(nameValue);
					multiTextDAO.update(nameMulti);
				}
			} else {
				int indx = 0;
				while (multiTextDAO.getByLanCodeAndKey(locale.getLanguage(), keyNameMulti)!=null){
					keyNameMulti = ProductManager.KEY_MULTI_RATE_NAME+localId+"_"+defaultNameValue.toLowerCase()+"_"+indx;
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
			strProRate = strProRate.replace(",", ".");
			Float proRate = new Float(strProRate);
			
			if (proId!=null){ // Existe
				product.setProRate(proRate);
				product = productDAO.update(product);
			} else { // Es nuevo
				product.setEnabled(1);
				product.setProLocalId(localId);
				product.setProNameMulti(keyNameMulti);
				product.setProRate(proRate);
				product = productDAO.create(product);
			}
			return product;
		}
		return null;
	}

	
	@RequestMapping(method = RequestMethod.PUT, value = "/manager/remove")
	@ResponseStatus(HttpStatus.OK)
	protected void remove(@RequestParam("id") Long id)
			throws Exception {

		if (id!=null){ // Existe
			ProductDTO product = productDAO.getById(id);
			product.setEnabled(0);
			productDAO.update(product);
		}
	}
	
	@RequestMapping("/operator/list")
	protected @ResponseBody
	List<ProductDTO> list(HttpServletRequest arg0, @RequestParam("localId") String localId) throws Exception {

		Locale locale = RequestContextUtils.getLocale(arg0);
		
		List<ProductDTO> listProduct = productDAO.getProductByLang(new Long(localId), locale.getLanguage());	
					
		return listProduct;
	}
		
	@RequestMapping("/manager/get")
	protected @ResponseBody
	ProductDTO get(@RequestParam("id") Long id) throws Exception {

		ProductDTO product = productDAO.getById(id);	
					
		return product;
	}
	

}
