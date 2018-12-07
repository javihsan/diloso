package com.diloso.app.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//import com.diloso.GMailManager;

@Controller
@RequestMapping(value={"/*/booking", "/booking"})
public class BookingController {
	
	//private static final Logger LOG = Logger.getLogger(BookingController.class.getSimpleName());
	
	@RequestMapping("")
	public ModelAndView init(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		ModelAndView mav = new ModelAndView("/app/booking");
		return mav;
//		String[] args = {};
//		GMailManager.main(args);

	}

}
