package com.yash.calm.model;

/**
 * The processing plant where the metal
 * is extracted from the raw material
 * @author karl.roth
 */
public class ProcessingPlant {
	
	// The id of the processing plant
	private int id;
	// Functional status of the plant
	private int isActive;
	// Geographic location of the plant
	private String location;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int isActive() {
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
	@Override
	public String toString() {
		return "ProcessingPlant [id=" + id + ", isActive=" + isActive + ", location=" + location + "]";
	}
	
	
}
