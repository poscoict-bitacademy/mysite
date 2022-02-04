package com.poscoict.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscoict.mysite.service.SiteService;
import com.poscoict.mysite.vo.SiteVo;

//@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private SiteService siteService;
	
	@RequestMapping("")
	public String main(Model model) {
		SiteVo site = siteService.getSite();

		model.addAttribute("site", site);
		return "admin/main";
	}

	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}

	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}

	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}	
}
