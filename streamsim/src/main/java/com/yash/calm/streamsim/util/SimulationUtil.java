package com.yash.calm.streamsim.util;

import java.util.ArrayList;
import java.util.List;

import com.yash.calm.streamsim.generator.AttributeGenerator;
import com.yash.calm.streamsim.generator.OutputGenerator;
import com.yash.calm.streamsim.model.Attributes;
import com.yash.calm.streamsim.model.Output;
import com.yash.calm.streamsim.model.ProcessingPlant;

public class SimulationUtil {

	public static List<String> generateJsonOutputs(List<Output> outputs, List<ProcessingPlant> plants) {
		List<String> jsonOutputs = new ArrayList<String>();
		for(int j=0; j < outputs.size(); j++) {
			Output output = outputs.get(j);
			ProcessingPlant plant = plants.get(j);
			String json = StreamUtil.jsonGenerator(output, plant);
			
			jsonOutputs.add(json);
		}
		return jsonOutputs;
	}


	public static List<Output> generateOutputs(List<ProcessingPlant> plants) {
		List<Output> outputs = new ArrayList<Output>();
		
		for(ProcessingPlant plant: plants) {
			Output output = OutputGenerator.generate(plant);
			outputs.add(output);
		}
		
		return outputs;
	}
	
	
	/**
	 * Generate attributes for each plant
	 */
	public static void generateAttributes(List<ProcessingPlant> plants) {
		for(ProcessingPlant plant: plants) {
			Attributes attributes = AttributeGenerator.generate();
			plant.setAttributes(attributes);
		}
	}
	
	/**
	 * Initialize plants.
	 */
	public static List<ProcessingPlant> generateProcessingPlants() {
		List<ProcessingPlant> plants = new ArrayList<ProcessingPlant>();
		
		ProcessingPlant plantA = new ProcessingPlant();
		ProcessingPlant plantB = new ProcessingPlant();
		ProcessingPlant plantC = new ProcessingPlant();
		ProcessingPlant plantD = new ProcessingPlant();
		ProcessingPlant plantE = new ProcessingPlant();
		ProcessingPlant plantF = new ProcessingPlant();
		plantA.setId(100);
		plantB.setId(101);
		plantC.setId(102);
		plantD.setId(103);
		plantE.setId(104);
		plantF.setId(105);
		
		plants.add(plantA);
		plants.add(plantB);
		plants.add(plantC);
		plants.add(plantD);
		plants.add(plantE);
		plants.add(plantF);
		
		return plants;
	}
	
}
