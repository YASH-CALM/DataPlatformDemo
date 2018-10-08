 package com.yash.calm.streamsim.model;

import java.util.Date;

/**
 * Data model for the output of each processing plant facility. 
 * @author karl.roth
 */
public class Output {

	// The id of the plant associated with the output
	private int plantId;
	// The id of the mine associated with the output
	private int mineId;
	// Expected theoretical output
	private double expectedOutput;
	// Actual measured output
	private double actualOutput;
	// Time that the measurement
	private Date time;
	
	
	public int getPlantId() {
		return plantId;
	}
	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}
	public int getMineId() {
		return mineId;
	}
	public void setMineId(int mineId) {
		this.mineId = mineId;
	}
	public double getExpectedOutput() {
		return expectedOutput;
	}
	public void setExpectedOutput(double expectedOutput) {
		this.expectedOutput = expectedOutput;
	}
	public double getActualOutput() {
		return actualOutput;
	}
	public void setActualOutput(double actualOutput) {
		this.actualOutput = actualOutput;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	
}