package com.privania.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privania.business.dao.UserDAO;
import com.privania.business.entity.User;

@Service
public class UserService {

	@Autowired private UserDAO userDao;
	
	public User getUser(Long privadaId, String username){
		return userDao.getUser(privadaId, username);
	}
	
	public List<String> getUserRoles(Long privadaId, String username){
		return userDao.getUserRoles(privadaId, username);
	}
	
	public Boolean validateUser(Long privadaId, String username, String password){
		return userDao.validateUser(privadaId, username, password);
	}
	
}
