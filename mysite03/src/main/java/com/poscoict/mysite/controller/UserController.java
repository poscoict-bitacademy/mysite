package com.poscoict.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.poscoict.mysite.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
}
