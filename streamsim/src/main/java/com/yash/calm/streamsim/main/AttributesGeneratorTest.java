package com.yash.calm.streamsim.main;

import com.yash.calm.streamsim.generator.AttributeGenerator;
import com.yash.calm.streamsim.generator.OutputGenerator;
import com.yash.calm.streamsim.model.Attributes;
import com.yash.calm.streamsim.model.Output;
import com.yash.calm.streamsim.model.ProcessingPlant;
import com.yash.calm.streamsim.util.StreamUtil;


public class AttributesGeneratorTest {

	public static void main(String[] args) {
		
		Attributes attributes = AttributeGenerator.generate();
		
//		System.out.println(attributes.getForce());
//		System.out.println(attributes.getSpeed());
//		System.out.println(attributes.getFrequency());
//		System.out.println(attributes.getParticleSize());
		ProcessingPlant plant = new ProcessingPlant();
		
		plant.setActive(true);
	    plant.setId(101);
	    plant.setAttributes(attributes);
		
	    Output output = OutputGenerator.generate(plant);
	    
	    double actualOutput = output.getActualOutput();
	
		System.out.println(StreamUtil.jsonGenerator(output,plant));
		
	}
}
