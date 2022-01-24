package com.poscoict.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscoict.mysite.service.UserService;
import com.poscoict.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(UserVo userVo) {
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping(value="/joinsuccess")
	public String joinsuccess() {
		return "user/joinsuccess";
	}	
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(
			HttpSession session,
			@RequestParam(value="email", required=true, defaultValue="") String email,
			@RequestParam(value="password", required=true, defaultValue="") String password,
			Model model) {
		UserVo authUser = userService.getUser(email, password);
		
		if(authUser == null) {
			model.addAttribute("result", "fail");
			model.addAttribute("email", email);
			 return "user/login";
		}
		
		/* 인증처리 */
		session.setAttribute("authUser", authUser);
		
		return "redirect:/";
		
	}	

	@RequestMapping(value="/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("authUser");
		session.invalidate();
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(HttpSession session, Model model) {
		/* access controller */
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		
		Long userNo = authUser.getNo();
		UserVo userVo = userService.getUser(userNo);
		model.addAttribute("userVo", userVo);
		
		return "user/update";
	}
}
