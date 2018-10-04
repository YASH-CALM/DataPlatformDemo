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
	private float expectedOutput;
	// Actual measured output
	private float actualOutput;
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
	public float getExpectedOutput() {
		return expectedOutput;
	}
	public void setExpectedOutput(float expectedOutput) {
		this.expectedOutput = expectedOutput;
	}
	public float getActualOutput() {
		return actualOutput;
	}
	public void setActualOutput(float actualOutput) {
		this.actualOutput = actualOutput;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	
}
