package com.diloso.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value={"/*/rss", "/rss"})
public class RSSController {

	@RequestMapping
	(method = RequestMethod.GET, value = "/demo.xml")
	@ResponseBody
	public String demo(@RequestParam("time") String time) throws Exception {
		return "holaaa";
	}

	

}
