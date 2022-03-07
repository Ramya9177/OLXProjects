package com.zensar.olx.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.zensar.olx.bean.OlxUser;
import com.zensar.olx.db.OlxUserDAO;

@Service
public class OlxUserService {
	@Autowired
	OlxUserDAO dao;
	
	public OlxUser addOlxUser(OlxUser olxUser) {
		return this.dao.save(olxUser);
	}
	public OlxUser updateOlxUser(OlxUser olxUser) {
		return this.dao.save(olxUser);
	}
	
	public OlxUser findOlxUser(int id) {
		Optional<OlxUser> optional=this.dao.findById(id);
		if(optional.isPresent())
			return optional.get();
		else
		return null;
	}
	
	public OlxUser findOlxUserByName(String name) {
		OlxUser olxUser=this.dao.findByUserName(name);
		return olxUser;
	}
}
