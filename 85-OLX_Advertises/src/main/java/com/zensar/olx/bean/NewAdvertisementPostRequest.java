package com.zensar.olx.bean;

import java.util.Objects;

public class NewAdvertisementPostRequest {

	int id;
	String title;
	int categoryId;
	double price;
	String description;
	int statusId;


	public NewAdvertisementPostRequest() {
		super();
	}


	public NewAdvertisementPostRequest(int id) {
		super();
		this.id = id;
	}

	public NewAdvertisementPostRequest(String title, int categoryId, double price, String description) {
		super();
		this.title = title;
		this.categoryId = categoryId;
		this.price = price;
		this.description = description;
	}

	public NewAdvertisementPostRequest(int id, String title, int categoryId, double price, String description) {
		super();
		this.id = id;
		this.title = title;
		this.categoryId = categoryId;
		this.price = price;
		this.description = description;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public int getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatusId() {
		return statusId;
	}


	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}


	@Override
	public String toString() {
		return "\nNewAdvertisementPostRequest [id=" + id + ", title=" + title + ", categoryId=" + categoryId + ", price="
				+ price + ", description=" + description + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewAdvertisementPostRequest other = (NewAdvertisementPostRequest) obj;
		return id == other.id;
	}
}
