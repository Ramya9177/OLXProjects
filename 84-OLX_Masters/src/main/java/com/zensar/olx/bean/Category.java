package com.zensar.olx.bean;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="olx_category")
public class Category {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int Id;
	
	private String name;
	private String description;
	
	public Category() {
		super();
	}
	public Category(int id, String name, String description) {
		super();
		Id = id;
		this.name = name;
		this.description = description;
	}
	public Category(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	public Category(int id) {
		super();
		Id = id;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Category [Id=" + Id + ", name=" + name + ", description=" + description + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(Id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return Id == other.Id;
	}
	

}
