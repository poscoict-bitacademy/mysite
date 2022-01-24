package com.poscoict.mysite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.poscoict.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {

	public List<GuestbookVo> getMessageList() {
		return null;
	}
	
	public Boolean deleteMessage(Long no, String password) {
		return false;
	}
	
	public Boolean addMessage(GuestbookVo vo) {
		return false;
	}
}
