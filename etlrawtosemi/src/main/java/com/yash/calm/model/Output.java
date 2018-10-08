package com.yash.calm.model;

import java.util.Date;

/**
 * Data model for the output of each processing plant facility. 
 * @author karl.roth
 */
public class Output {

	protected int id;
	// The id of the plant associated with the output
	protected int plantId;
	// The id of the mine associated with the output
	protected int mineId;
	// Expected theoretical output
	protected float expectedOutput;
	// Actual measured output
	protected float actualOutput;
	// Time that the measurement
	protected Date time;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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

	@Override
	public String toString() {
		return "Output{" +
				"id=" + id +
				", plantId=" + plantId +
				", mineId=" + mineId +
				", expectedOutput=" + expectedOutput +
				", actualOutput=" + actualOutput +
				", time=" + time +
				'}';
	}
}
