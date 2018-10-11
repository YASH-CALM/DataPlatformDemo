package com.yash.calm.model;

import java.util.Date;

/**
 * Data model for the output of each processing plant facility.
 * 
 * @author neelpatel
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
	// speed of the input material
	private double speed;
	// size of the crushed particles
	private int particleSize;
	// Frequency of the crusher
	private double frequency;
	// Newtons of force crusher applies
	private int force;


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

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

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

	@Override
	public String toString() {
		return "Output [plantId=" + plantId + ", mineId=" + mineId + ", expectedOutput=" + expectedOutput
				+ ", actualOutput=" + actualOutput + ", time=" + time + ", speed=" + speed + ", particleSize="
				+ particleSize + ", frequency=" + frequency + ", force=" + force + "]";
	}


}
