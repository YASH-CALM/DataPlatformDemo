package com.yash.calm.streamsim.util;


import com.yash.calm.streamsim.model.Attributes;
import com.yash.calm.streamsim.model.Output;
import com.yash.calm.streamsim.model.ProcessingPlant;

public class StreamUtil {

	public static String jsonGenerator(Output output, ProcessingPlant plant) {
		Attributes attributes = plant.getAttributes();
		String json = "{" +
							"\"plantId\":"+ output.getPlantId()+ ","+
							"\"mineId\":"+ output.getMineId()+ ","+
							"\"expectedOutput\":"+ output.getExpectedOutput()+ ","+
							"\"actualOutput\":"+ output.getActualOutput()+ ","+
							"\"time\":"+ output.getTime().getTime() + ","+
							"\"speed\":"+ attributes.getSpeed() +"," +
							"\"particleSize\":"+ attributes.getParticleSize() +","+
							"\"frequency\":"+ attributes.getFrequency() +","+
							"\"force\":"+ attributes.getForce() +
						"},";
		return json;
	}
}
