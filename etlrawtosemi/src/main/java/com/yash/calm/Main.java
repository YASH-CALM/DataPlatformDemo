package com.yash.calm;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yash.calm.encryption.Credentials;
import com.yash.calm.model.EnrichedOutput;
import com.yash.calm.model.Mine;
import com.yash.calm.model.Output;
import com.yash.calm.model.ProcessingPlant;

public class Main implements RequestHandler<S3Event, String> {

	private static AmazonS3 s3Client;
	private static final String MASTER_BUCKET = "master-dpd2018";
	private static final String PLANTS_KEY = "processing_plant.json";
	private static final String MINES_KEY = "mine.json";
	private static final String SEMI_BUCKET = "semi-transformed-dpd2018";
	private static final String REGION = "us-east-1";
	
	public String handleRequest(S3Event s3event, Context context) {
		// Set up access credentials
		AWSCredentials credentials = new BasicAWSCredentials(Credentials.ACCESSKEY, Credentials.SECRETKEY);
		// Set up s3client
		s3Client = AmazonS3ClientBuilder.standard()
				.withRegion(REGION)
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.build();

		// get the s3 even record
		S3EventNotificationRecord record = s3event.getRecords().get(0);

		// get S3 bucket and object key from record
		String s3Bucket = record.getS3().getBucket().getName();
		String s3Key = record.getS3().getObject().getKey();

		// Get S3 Objects
		S3Object output = s3Client.getObject(new GetObjectRequest(s3Bucket, s3Key));
		S3Object plants = s3Client.getObject(MASTER_BUCKET, PLANTS_KEY);
		S3Object mines = s3Client.getObject(MASTER_BUCKET, MINES_KEY);

		// Extract S3 Objects Data into StringBuilders
		StringBuilder outputContent = getDataFromObject(output.getObjectContent());
		outputContent.setLength(outputContent.length()-1);
		StringBuilder plantsContent = getDataFromObject(plants.getObjectContent());
		StringBuilder minesContent = getDataFromObject(mines.getObjectContent());
		
		String outputContentString = "["+outputContent+"]";
		String plantsContentString = "["+plantsContent+"]";
		String minesContentString = "["+minesContent+"]";

		// Parse JSON into Object Arrays
		ObjectMapper mapper = new ObjectMapper();
		try {
			Output[] outputData = mapper.readValue(outputContentString, Output[].class);
			Mine[] mineData = mapper.readValue(minesContentString, Mine[].class);
			ProcessingPlant[] plantData = mapper.readValue(plantsContentString, ProcessingPlant[].class);
			
			// Enrich Data
			EnrichedOutput[] enrichedOutput = enrichData(outputData, mineData, plantData);
			
			// Convert Enriched Data to JSON String
			String json = convertToJson(enrichedOutput);
			
			// Upload Object
			uploadObject(json, s3Key);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static StringBuilder getDataFromObject(InputStream objectData) {
		BufferedReader streamReader = null;
		StringBuilder responseStrBuilder = new StringBuilder();
		try {
			streamReader = new BufferedReader(new InputStreamReader(objectData, "UTF-8"));
			String inputStr;
			while ((inputStr = streamReader.readLine()) != null)
				responseStrBuilder.append(inputStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseStrBuilder;

	}

	private EnrichedOutput[] enrichData(Output[] outputData, Mine[] mineData, ProcessingPlant[] plantData) {
		EnrichedOutput[] enrichedOutput = new EnrichedOutput[outputData.length];
		for (int i = 0; i < enrichedOutput.length; i++) {
			EnrichedOutput initiate = new EnrichedOutput();
			enrichedOutput[i] = initiate;
			enrichedOutput[i].setPlantId(outputData[i].getPlantId());
			enrichedOutput[i].setMineId(outputData[i].getMineId());
			enrichedOutput[i].setExpectedOutput(outputData[i].getExpectedOutput());
			enrichedOutput[i].setActualOutput(outputData[i].getActualOutput());
			enrichedOutput[i].setTime(outputData[i].getTime());
			enrichedOutput[i].setSpeed(outputData[i].getSpeed());
			enrichedOutput[i].setParticleSize(outputData[i].getParticleSize());
			enrichedOutput[i].setFrequency(outputData[i].getFrequency());
			enrichedOutput[i].setForce(outputData[i].getForce());
			for (Mine mine : mineData) {
				if (mine.getId() == outputData[i].getMineId()) {
					enrichedOutput[i].setMineLocation(mine.getLocation());
					enrichedOutput[i].setIsMineActive(mine.getActive());
					enrichedOutput[i].setMaterial(mine.getMaterial());
				}
			}
			for (ProcessingPlant plant : plantData) {
				if (plant.getId() == outputData[i].getPlantId()) {
					enrichedOutput[i].setPlantLocation(plant.getLocation());
					enrichedOutput[i].setIsPlantActive(plant.getIsActive());
				}
			}
		}
		return enrichedOutput;
	}
	
	private static String convertToJson(EnrichedOutput[] enrichedOutput) {
		String json = "[";
		for(EnrichedOutput iterator : enrichedOutput) {
			json = json + "{" +
					"\"plantId\":"+ iterator.getPlantId()+ ","+
                    "\"mineId\":"+ iterator.getMineId()+ ","+
                    "\"expectedOutput\":"+ iterator.getExpectedOutput()+ ","+
                    "\"actualOutput\":"+ iterator.getActualOutput()+ ","+
                    "\"time\":"+ iterator.getTime().getTime() + ","+
                    "\"speed\":"+ iterator.getSpeed() +"," +
                    "\"particleSize\":"+ iterator.getParticleSize() +","+
                    "\"frequency\":"+ iterator.getFrequency() +","+
                    "\"force\":"+ iterator.getForce() +","+
                    "\"isMineActive\":"+ iterator.getIsMineActive() +","+
                    "\"mineLocation\":"+ "\"" +iterator.getMineLocation()+ "\"" +","+
                    "\"material\":"+ "\""+ iterator.getMaterial() + "\"" +","+
                    "\"isPlantActive\":"+ iterator.getIsPlantActive() +","+
                    "\"plantLocation\":"+ "\"" + iterator.getPlantLocation() + "\""+
            "},";		
		}
		json = json.substring(0, json.length()-1);
		json = json + "]";
		
		return json;
	}
	
	private static void uploadObject(String json, String s3Key) {
		// Set up access credentials
		AWSCredentials credentials = new BasicAWSCredentials(Credentials.ACCESSKEY, Credentials.SECRETKEY);
		// Set up s3client
		s3Client = AmazonS3ClientBuilder.standard()
				.withRegion(REGION)
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.build();
		// Put object in bucket
		s3Client.putObject(SEMI_BUCKET, s3Key, json);
	}

}
