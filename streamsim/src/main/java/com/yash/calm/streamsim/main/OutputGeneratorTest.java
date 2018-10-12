package com.yash.calm.streamsim.main;

import com.yash.calm.streamsim.generator.OutputGenerator;
import com.yash.calm.streamsim.model.Attributes;
import com.yash.calm.streamsim.model.Output;
import com.yash.calm.streamsim.model.ProcessingPlant;

/**
 * Hello world!
 *
 */
public class OutputGeneratorTest 
{
    public static void main( String[] args )
    {
       ProcessingPlant plant = new ProcessingPlant();
       Attributes attributes = new Attributes();
       
       attributes.setPlantId(101);
       attributes.setSpeed(3);
       attributes.setFrequency(1.0);
       attributes.setParticleSize(1);

       plant.setActive(true);
       plant.setId(101);
       plant.setAttributes(attributes);
       
       Output output = OutputGenerator.generate(plant);
       double actualOutput = output.getActualOutput();
       double max = 2+Math.sqrt(2);
       double min = 2-Math.sqrt(2);
       
       if(actualOutput < max && actualOutput > min) {
    	   System.out.println("The actual output, "+actualOutput+", was simulated properly.");
       } else {
    	   System.out.println("Error: "+actualOutput);
       }
       
    }
}
