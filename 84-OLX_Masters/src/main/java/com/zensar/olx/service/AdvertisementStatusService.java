package com.zensar.olx.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zensar.olx.bean.AdvertisementStatus;
import com.zensar.olx.db.AvertisementStatusDAO;

@Service
public class AdvertisementStatusService {
	
	
	@Autowired
	AvertisementStatusDAO dao;
	
	public AdvertisementStatus addAdvertisementStatus(AdvertisementStatus advertisementStatus) {
		return this.dao.save(advertisementStatus);
	}
	
public List<AdvertisementStatus> getAllStatus() {
	return this.dao.findAll();
}
public AdvertisementStatus findAdvertisementStatus(int id) {
	Optional<AdvertisementStatus> optional;
	optional=this.dao.findById(id);
	if(optional.isPresent())
		return optional.get();
	else
		return null;
}
	
}
