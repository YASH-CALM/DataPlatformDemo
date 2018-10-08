package com.yash.calm.streamsim.generator;

import java.util.Random;

import com.yash.calm.streamsim.model.Attributes;

public class AttributeGenerator {
	
	private static Attributes attributes = new Attributes();
	private static Random rand = new Random();
	
	public static Attributes generate() {
		
		// Randomly generate attributes
		// speed between 0 and 1 m/s
		double speed = rand.nextDouble();
		// particle size between 50 and 450 um 
		int particleSize = 50 + rand.nextInt(450);
		// frequencey between 0.5 and 2 Hz
		double frequency = 0.5 + 1.5*rand.nextDouble();
		// force between 1000 and 5000 N
		int force = 1000 + rand.nextInt(4000);
		
		attributes.setSpeed(speed);
		attributes.setParticleSize(particleSize);
		attributes.setFrequency(frequency);
		attributes.setForce(force);
		return attributes;
	}
	
	
}
