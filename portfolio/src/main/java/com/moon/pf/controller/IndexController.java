package com.moon.pf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping("/index.do")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/home.do")
	public String home() {
		return "home";
	}
	
	@RequestMapping("/devNotes.do")
	public String test() {
		return "devNotes";
	}
}
