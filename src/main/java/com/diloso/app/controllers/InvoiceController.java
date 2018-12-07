package com.diloso.app.controllers;


import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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

import com.diloso.app.negocio.dao.BilledDAO;
import com.diloso.app.negocio.dao.CalendarDAO;
import com.diloso.app.negocio.dao.ClientDAO;
import com.diloso.app.negocio.dao.EventDAO;
import com.diloso.app.negocio.dao.InvoiceDAO;
import com.diloso.app.negocio.dao.LocalDAO;
import com.diloso.app.negocio.dao.LocalTaskDAO;
import com.diloso.app.negocio.dao.ProductDAO;
import com.diloso.app.negocio.dto.BilledDTO;
import com.diloso.app.negocio.dto.ClientDTO;
import com.diloso.app.negocio.dto.EventDTO;
import com.diloso.app.negocio.dto.InvoiceDTO;
import com.diloso.app.negocio.dto.LocalDTO;
import com.diloso.app.negocio.dto.LocalTaskDTO;
import com.diloso.app.negocio.dto.ProductDTO;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {
	
	@Autowired
	protected MessageSource messageSourceApp;
		
	@Autowired
	protected LocalDAO localDAO;
	
	@Autowired
	protected ClientDAO clientDAO;
	
	@Autowired
	protected InvoiceDAO invoiceDAO;
	
	@Autowired
	protected LocalTaskDAO localTaskDAO;
	
	@Autowired
	protected ProductDAO productDAO;
	
	@Autowired
	protected EventDAO eventDAO;
	
	@Autowired
	protected BilledDAO billedDAO;
	
	@Autowired
	protected CalendarDAO calendarDAO;
	
	@ExceptionHandler(UncategorizedDataAccessException.class)
	@ResponseStatus(value=HttpStatus.CONFLICT,reason="")
	protected void error(Exception t, HttpServletResponse response) throws IOException{
		response.sendError(HttpStatus.CONFLICT.value(), t.getMessage());
	}
	
	/* El cliente, fecha a facturar, servicios (o productos) y tarifas no est� vac�o.
	*/
	protected boolean validateNew(HttpServletRequest arg0, boolean isNew, String strInvClientId, String cliName, Date invTime, String strInvTask, String strInvRate) throws UncategorizedDataAccessException {
		boolean res = true;
		String message = "";
		Locale locale = RequestContextUtils.getLocale(arg0);
		if(isNew && (cliName == null || cliName.length()==0)){
			message = messageSourceApp.getMessage("form.error.client.nameReq", null, locale);
			res = false;
		} else if (!isNew && (strInvClientId==null || strInvClientId.length()==0)){
			message = messageSourceApp.getMessage("form.error.invoice.clientReq", null, locale);
			res = false;
		} else if (invTime==null){
			message = messageSourceApp.getMessage("form.error.invoice.timeReq", null, locale);
			res = false;
		}  else if (strInvTask==null || strInvTask.length()==0){
			message = messageSourceApp.getMessage("form.error.invoice.taskReq", null, locale);
			res = false;
		} else if (strInvRate==null || strInvRate.length()==0){
			message = messageSourceApp.getMessage("form.error.invoice.rateReq", null, locale);
			res = false;
		} else {
			try{
				
				String[] rates = strInvRate.split(",");
				for (String str : rates) {
					new Float(str);
				}
		
			} catch( NumberFormatException e){
				message = messageSourceApp.getMessage("form.error.invoice.rateNum", null, locale);
				res = false;
			}
		}
		
		if (!res){
			throw new ErrorService(message, null);
		}
		return res;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/operator/new")
	@ResponseStatus(HttpStatus.OK)
	protected @ResponseBody
	InvoiceDTO newObject (HttpServletRequest arg0, HttpServletResponse arg1)
			throws Exception {
		
		Long lngInvTime = new Long(arg0.getParameter("invTime"));
		Date invTime = new Date(lngInvTime);
		
		Calendar calendarGreg = new GregorianCalendar();
		calendarGreg.setTime(invTime);
		calendarGreg.set(Calendar.MILLISECOND, 0);
		invTime = calendarGreg.getTime();
		
		String strInvClientId = arg0.getParameter("invClientId");
		String cliName = null;
		String cliSurname = null;
		String cliEmail = null;
				
		boolean isNew = false;
		
		if (strInvClientId==null){
			cliName = arg0.getParameter("cliName");
			cliSurname = arg0.getParameter("cliSurname");
			cliEmail = arg0.getParameter("cliEmail").toLowerCase();
			// Es nuevo seguro. Puede que no venga email (luego es nuevo); pero si viene hemos comprobado seguro que no est� repetido. 
			isNew = true;
		}
		
		String strInvTask = arg0.getParameter("bilTaskId");
		String strInvType = arg0.getParameter("bilTypeId");
		String strInvCalendar = arg0.getParameter("bilCalendarId");
		String strInvRate = arg0.getParameter("bilRateId");
		
		InvoiceDTO invoice = null;
		
		if (validateNew(arg0, isNew, strInvClientId, cliName, invTime, strInvTask, strInvRate)){
			
			Long localId = new Long(arg0.getParameter("localId"));
			LocalDTO local = localDAO.getById(localId);
						
			TimeZone calendarTimeZone = TimeZone.getTimeZone(local.getLocWhere().getWheTimeZone());
			calendarGreg = new GregorianCalendar();
			calendarGreg.set(Calendar.MILLISECOND, 0);
			Date invIssueTime = calendarGreg.getTime();
			// Calculamos el desplazamiento de la zona horaria desde UTC
			invIssueTime =  new Date(invIssueTime.getTime() + calendarTimeZone.getOffset(invIssueTime.getTime()));
			
			String invDesc = arg0.getParameter("invDesc");
			
			ClientDTO invClient = null;
			if (!isNew){ // Si no es nuevo, buscamos por el id
				invClient = clientDAO.getById(new Long(strInvClientId));	
			} else {
				invClient = new ClientDTO();
				invClient.setEnabled(1);
				invClient.setResFirId(local.getResFirId());
				if (cliEmail != null && cliEmail.length()>0){
					invClient.setWhoEmail(cliEmail);
				}
				invClient.setWhoName(cliName);
				invClient.setWhoSurname(cliSurname);
				invClient.setCliCreationTime(invIssueTime);
				invClient = clientDAO.create(invClient);
			}
			
			// Rates
			Float invRate = new Float(0);
			String[] rates = strInvRate.split(",");
			for (String str : rates) {
				invRate += new Float(str);
			}
							
			invoice = new InvoiceDTO();
				
			invoice.setEnabled(1);
			invoice.setInvLocalId(localId);
			invoice.setInvIssueTime(invIssueTime);
			invoice.setInvDesc(invDesc);
			invoice.setInvClient(invClient);
			invoice.setInvRate(invRate);
			invoice.setInvTime(invTime);
			
			invoice = invoiceDAO.create(invoice);
			
			String ICS = arg0.getParameter("ICS");
			if (ICS !=null){
				List<EventDTO> listEventLocal = eventDAO.getEventByICS(ICS);
				for (EventDTO event : listEventLocal) {
					event.setEveConsumed(1);
					eventDAO.update(event);
				}
			}	
			
			String[] tasks = strInvTask.split(",");
			String[] types = strInvType.split(",");
			String[] calendars = strInvCalendar.split(",");
			int i = 0;
			for (String strTask : tasks) {
				
				Long bilTaskId = new Long(strTask);
				Integer bilType = new Integer(types[i]);
				Long bilCalendarId = new Long(calendars[i]);
				Float bilRate = new Float(rates[i]);
				
				newBilled (local, invoice, bilRate, bilCalendarId, bilTaskId, bilType);
				
				i++;
			}

	
		}
		return invoice;
	}
	
	
	protected void newBilled (LocalDTO local, InvoiceDTO bilInvoice, Float bilRate, Long bilCalendarId, Long bilTaskId, Integer bilType)	throws Exception {
		
		LocalTaskDTO bilTask = null;
		ProductDTO bilProduct = null;

		if (bilType==0){
			bilTask = localTaskDAO.getById(bilTaskId);
		} else {
			bilProduct = productDAO.getById(bilTaskId);
		}
		
		BilledDTO billed = new BilledDTO();
		
		billed.setEnabled(1);
		billed.setBilCalendarId(bilCalendarId);
		billed.setBilClient(bilInvoice.getInvClient());
		billed.setBilInvoiceId(bilInvoice.getId());
		billed.setBilLocalTask(bilTask);
		billed.setBilProduct(bilProduct);
		billed.setBilRate(bilRate);
		billed.setBilTime(bilInvoice.getInvTime());
				
		billed = billedDAO.create(billed);
	
	}

	@RequestMapping("/operator/get")
	protected @ResponseBody
	InvoiceDTO get(@RequestParam("id") Long id) throws Exception {

		InvoiceDTO invoice = invoiceDAO.getById(id);	
					
		return invoice;
	}
	
	@RequestMapping("/operator/listByDiary")
	protected @ResponseBody
	List<InvoiceDTO> listByDiary(HttpServletRequest arg0, @RequestParam("localId") Long localId, @RequestParam("selectedDate") String selectedDate) throws Exception {

		Locale locale = RequestContextUtils.getLocale(arg0);
		
		List<InvoiceDTO> listInvoice = invoiceDAO.getInvoiceByWeek(localId, selectedDate);
		
		for (InvoiceDTO invoice : listInvoice) {
			List<BilledDTO> listBilled = billedDAO.getBilledByInvoice(invoice.getId(), locale.getLanguage());
			String strBilleds = "";
			for (BilledDTO billed : listBilled) {
				if (strBilleds!=""){
					strBilleds += " , ";
				}			
				if (billed.getBilLocalTask()!=null){
					strBilleds += billed.getBilLocalTask().getLotName();
				} else {	 
					strBilleds += billed.getBilProduct().getProName();
				}
			}
			invoice.setInvBilleds(strBilleds);
		}

		return listInvoice;
	}
	
	@RequestMapping("/operator/listByClientAgo")
	protected @ResponseBody
	List<InvoiceDTO> listByClientAgo( HttpServletRequest arg0, @RequestParam("localId") Long localId, @RequestParam("id") Long id) throws Exception {
		
		Locale locale = RequestContextUtils.getLocale(arg0);
		
		List<InvoiceDTO> listInvoice = invoiceDAO.getInvoiceByClientAgo(localId,id);

		for (InvoiceDTO invoice : listInvoice) {
			List<BilledDTO> listBilled = billedDAO.getBilledByInvoice(invoice.getId(), locale.getLanguage());
			String strBilleds = "";
			for (BilledDTO billed : listBilled) {
				if (strBilleds!=""){
					strBilleds += " , ";
				}			
				if (billed.getBilLocalTask()!=null){
					strBilleds += billed.getBilLocalTask().getLotName();
				} else {	 
					strBilleds += billed.getBilProduct().getProName();
				}
			}
			invoice.setInvBilleds(strBilleds);
		}

		return listInvoice;
	}
	
	
	
}
