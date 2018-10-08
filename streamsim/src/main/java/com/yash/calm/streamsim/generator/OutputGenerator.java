package com.yash.calm.streamsim.generator;

import java.util.Random;

import com.yash.calm.streamsim.model.Attributes;
import com.yash.calm.streamsim.model.Output;
import com.yash.calm.streamsim.model.ProcessingPlant;

public class OutputGenerator {

	private static Output output = new Output();
	private Random rand = new Random();
	
	public Output generate(ProcessingPlant plant) {
		int plantId = plant.getId();
		output.setPlantId(plantId);
		
		Attributes attributes = plant.getAttributes();
		
		int scalingFactor = 1;
		double randomFluctuation = 0.0;
		
		int speed = attributes.getSpeed();
		int particleSize = attributes.getParticleSize();
		double frequency = attributes.getFrequency();
	
		double actualOutput = speed*frequency - particleSize;
		actualOutput *= scalingFactor;
		output.setExpectedOutput(actualOutput);
		
		randomFluctuation = calculateFluctuation(actualOutput);
		actualOutput += randomFluctuation;
		output.setActualOutput(actualOutput);
		
		return output;
	}
	
	/*
	 * Calculates the random fluctuations for a given input 
	 * by generating a random number between
	 * the negative square root and positive square root of the input
	 */
	private double calculateFluctuation(double input) {
		double rangeMax = Math.sqrt(input);
		double rangeMin = -1.0*rangeMax;
		double randomFluctuation = rangeMin + (rangeMax - rangeMin)*rand.nextDouble();
		return randomFluctuation;
	}
	
	
}

