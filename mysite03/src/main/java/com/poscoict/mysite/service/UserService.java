package com.poscoict.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.mysite.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
}
