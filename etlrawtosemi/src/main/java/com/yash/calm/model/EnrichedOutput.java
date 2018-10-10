package com.yash.calm.model;

import java.util.Date;

public class EnrichedOutput {

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

	private int isMineActive;
	// Geographic location of the mine
	private String mineLocation;
	// Raw material being extracted from the mine.
	private String material;

	// Functional status of the plant
	private int isPlantActive;
	// Geographic location of the plant
	private String plantLocation;

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

	public int getIsMineActive() {
		return isMineActive;
	}

	public void setIsMineActive(int isMineActive) {
		this.isMineActive = isMineActive;
	}

	public String getMineLocation() {
		return mineLocation;
	}

	public void setMineLocation(String mineLocation) {
		this.mineLocation = mineLocation;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public int getIsPlantActive() {
		return isPlantActive;
	}

	public void setIsPlantActive(int isPlantActive) {
		this.isPlantActive = isPlantActive;
	}

	public String getPlantLocation() {
		return plantLocation;
	}

	public void setPlantLocation(String plantLocation) {
		this.plantLocation = plantLocation;
	}

	@Override
	public String toString() {
		return "EnrichedOutput [plantId=" + plantId + ", mineId=" + mineId + ", expectedOutput=" + expectedOutput
				+ ", actualOutput=" + actualOutput + ", time=" + time + ", speed=" + speed + ", particleSize="
				+ particleSize + ", frequency=" + frequency + ", force=" + force + ", isMineActive=" + isMineActive
				+ ", mineLocation=" + mineLocation + ", material=" + material + ", isPlantActive=" + isPlantActive
				+ ", plantLocation=" + plantLocation + "]";
	}
}
