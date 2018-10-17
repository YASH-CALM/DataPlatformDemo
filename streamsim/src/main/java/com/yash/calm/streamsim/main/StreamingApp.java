package com.yash.calm.streamsim.main;


import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.yash.calm.streamsim.model.Output;
import com.yash.calm.streamsim.model.ProcessingPlant;
import com.yash.calm.streamsim.util.SimulationUtil;

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
		List<ProcessingPlant> plants = SimulationUtil.generateProcessingPlants();
		
		// Simulate, and stream data into kinesis record request.
		// Generate new attributes every minute
		for(int j = 0; j < 60; j++) { 
			System.out.println(new Date());
			System.out.println("Recalculating Attributes...");
			SimulationUtil.generateAttributes(plants);
			
			//Generate new outputs every five seconds.
			for(int i = 0; i < 11; i++) {

				List<Output> outputs = SimulationUtil.generateOutputs(plants);
				List<String> jsonOutputs = SimulationUtil.generateJsonOutputs(outputs, plants);
			
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
}
