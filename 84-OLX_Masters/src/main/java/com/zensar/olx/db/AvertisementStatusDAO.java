package com.zensar.olx.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zensar.olx.bean.AdvertisementStatus;

@Repository
public interface AvertisementStatusDAO extends JpaRepository<AdvertisementStatus, Integer>{

}
