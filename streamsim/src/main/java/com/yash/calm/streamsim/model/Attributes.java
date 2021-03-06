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
	private double speed;
	// How fine the material is crushed
	private int particleSize;
	// The frequency of the crusher
	private double frequency;
	// The force applied by the crusher
	private int force;
	
	public int getParticleSize() {
		return particleSize;
	}
	public void setParticleSize(int particleSize) {
		this.particleSize = particleSize;
	}
	public double getFrequency() {
		return frequency;
	}
	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
	public int getForce() {
		return force;
	}
	public void setForce(int force) {
		this.force = force;
	}
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
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public int getFineness() {
		return particleSize;
	}
	public void setFineness(int fineness) {
		this.particleSize = fineness;
	}
	
	public String toJSON() {
		String json = "[{" +
				"plantId:"+ this.plantId+ ","+
				"time:"+ this.time+ ","+
				"speed:"+ this.speed +"," +
				"particleSize:"+ this.particleSize +","+
				"frequency:"+ this.frequency +","+
				"force:"+ this.force +
				"}]";
		return json;
	}
	
	
                                                                                                                    
}