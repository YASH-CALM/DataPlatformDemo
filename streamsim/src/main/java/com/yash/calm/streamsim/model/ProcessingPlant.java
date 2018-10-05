package com.yash.calm.streamsim.model;

/**
 * The processing plant where the metal
 * is extracted from the raw material
 * @author karl.roth
 */
public class ProcessingPlant {
	
	// The id of the processing plant
	private int id;
	// Functional status of the plant
	private boolean isActive;
	// Geographic location of the plant
	private String location;
	// Associated settings/attributes 
	private Attributes attributes;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Attributes getAttributes() {
		return attributes;
	}
	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}
 	
	
}