package com.yash.calm.streamsim.model;

import java.util.Date;

/**
 * The settings of the processing plant at a given time. 
 * These settings will be tweaked, and the 
 * output of the plant will be analyzed to determine
 * the most efficient configurations.
 * 
 * 
 * @author karl.roth
 *
 */
public class Attributes {

	// The id of the processing plant associated with the attributes
	private int plantId;
	// Time associated with the given setting
	private Date time; 
	// Speed setting of the processing plant
	private int speed;
	// How fine the material is crushed
	private int fineness;
	
	public int getPlantId() {
		return plantId;
	}
	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getFineness() {
		return fineness;
	}
	public void setFineness(int fineness) {
		this.fineness = fineness;
	}
	
	

}
