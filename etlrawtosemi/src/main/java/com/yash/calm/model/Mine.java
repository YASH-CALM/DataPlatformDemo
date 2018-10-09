package com.yash.calm.model;

/**
 * Data Model for each mine
 * @author karl.roth
 */
public class Mine {
	
	// Id for the mine
	private int id;
	// Functional status of the mine
	private int isActive;
	// Geographic location of the mine
	private String location;
	// Raw material being extracted from the mine.
	private String material;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getActive() {
		return isActive;
	}
	public void setActive(int isActive) {
		this.isActive = isActive;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	@Override
	public String toString() {
		return "Mine [id=" + id + ", isActive=" + isActive + ", location=" + location + ", material=" + material + "]";
	}
	
	
	
}
