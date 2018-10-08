package com.yash.calm.streamsim.generator;

import com.yash.calm.streamsim.model.Attributes;

public class AttributeGenerator {
	
	private static Attributes attributes = new Attributes();
	
	public Attributes generate() {
		
		// Randomly generate attributes
		int speed = 0;
		int particleSize = 0;
		double frequency = 1.0;
		int force = 1000;
		
		attributes.setSpeed(speed);
		attributes.setParticleSize(particleSize);
		attributes.setFrequency(frequency);
		attributes.setForce(force);
		return attributes;
	}
	
	
}
