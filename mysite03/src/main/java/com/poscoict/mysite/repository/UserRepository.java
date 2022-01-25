package com.poscoict.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.exception.UserRepositoryException;
import com.poscoict.mysite.vo.UserVo;

@Repository
public class UserRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public boolean update(UserVo vo) {
		int count = sqlSession.update("user.update", vo);
		return count == 1;
	}
	
	public boolean insert(UserVo vo) {
		int count = sqlSession.insert("user.insert", vo);
		return count == 1;
	}	

	public UserVo findByNo(Long userNo) {
		return sqlSession.selectOne("user.findByNo", userNo);
	}

	public UserVo findByEmailAndPassword(String email, String password) throws UserRepositoryException {
		Map<String, String> map = new HashMap<>();
		map.put("e", email);
		map.put("p", password);
		
		return sqlSession.selectOne("user.findByEmailAndPassword", map);
	}	
}