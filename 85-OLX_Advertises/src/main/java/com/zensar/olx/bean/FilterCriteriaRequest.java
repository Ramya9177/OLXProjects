package com.zensar.olx.bean;

import java.time.LocalDate;

public class FilterCriteriaRequest {
	LocalDate fromDate;
	LocalDate toDate;
	LocalDate onDate;
	String categoryName;
	String postedby;
	String sortBy;
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	public LocalDate getOnDate() {
		return onDate;
	}
	public void setOnDate(LocalDate onDate) {
		this.onDate = onDate;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getPostedby() {
		return postedby;
	}
	public void setPostedby(String postedby) {
		this.postedby = postedby;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	
	
	
	

}
