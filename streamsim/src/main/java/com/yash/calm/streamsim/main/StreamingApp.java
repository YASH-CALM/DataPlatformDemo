package com.yash.calm.streamsim.main;


import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsRequestEntry;
import com.amazonaws.services.kinesis.model.PutRecordsResult;
import com.yash.calm.streamsim.encryption.Credentials;
import com.yash.calm.streamsim.generator.AttributeGenerator;
import com.yash.calm.streamsim.generator.OutputGenerator;
import com.yash.calm.streamsim.model.Attributes;
import com.yash.calm.streamsim.model.Output;
import com.yash.calm.streamsim.model.ProcessingPlant;
import com.yash.calm.streamsim.util.StreamUtil;

public class StreamingApp {
	public static void main(String[] args) {
		
		// Set credentials
		AWSCredentials credentials = new BasicAWSCredentials(
				Credentials.ACCESS_KEY, 
				Credentials.SECRET_KEY);

		// Generate Kinesis Client
		AmazonKinesis kinesisClient = AmazonKinesisClientBuilder.standard()
				.withRegion(Regions.US_EAST_1)
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.build();
				
		// Create PUT request object
		PutRecordsRequest putRecordsRequest  = new PutRecordsRequest();
		putRecordsRequest.setStreamName("DataPlatformDemo"); 
		
		// Generate the processing plants for simulation and add attributes
		List<ProcessingPlant> plants = generateProcessingPlants();
		
		// Simulate, and stream data into kinesis record request.
		// Generate new attributes every minute
		for(int j = 0; j < 60; j++) {
			System.out.println("Recalculating Attributes...");
			generateAttributes(plants);
			
			//Generate new outputs every five seconds.
			for(int i = 0; i < 11; i++) {
				
				List<Output> outputs = generateOutputs(plants);
				List<String> jsonOutputs = generateJsonOutputs(outputs, plants);
			
				// Populate Array of PUT Record Request Entries
				List <PutRecordsRequestEntry> putRecordsRequestEntryList  = new ArrayList<PutRecordsRequestEntry>();
				
				for(String data: jsonOutputs) {
					int elem = 0;
					PutRecordsRequestEntry putRecordsRequestEntry  = new PutRecordsRequestEntry();
	            	putRecordsRequestEntry.setData(ByteBuffer.wrap(data.getBytes()));
	            	putRecordsRequestEntry.setPartitionKey(String.format("partitionKey-%d", elem));
	            	putRecordsRequestEntryList.add(putRecordsRequestEntry); 
	            	elem++;
				}
			
				// Send records to AWS Kinesis
	        	putRecordsRequest.setRecords(putRecordsRequestEntryList);
	        	PutRecordsResult putRecordsResult  = kinesisClient.putRecords(putRecordsRequest);
	        	System.out.println("Put Result" + putRecordsResult);
			
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}


	private static List<String> generateJsonOutputs(List<Output> outputs, List<ProcessingPlant> plants) {
		List<String> jsonOutputs = new ArrayList<String>();
		for(int j=0; j < outputs.size(); j++) {
			Output output = outputs.get(j);
			ProcessingPlant plant = plants.get(j);
			String json = StreamUtil.jsonGenerator(output, plant);
			
			jsonOutputs.add(json);
		}
		return jsonOutputs;
	}


	private static List<Output> generateOutputs(List<ProcessingPlant> plants) {
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
	private static void generateAttributes(List<ProcessingPlant> plants) {
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
