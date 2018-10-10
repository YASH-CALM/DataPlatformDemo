package com.yash.calm.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.yash.calm.encryption.Credentials;
import com.yash.calm.model.EnrichedOutput;
import com.yash.calm.model.Mine;
import com.yash.calm.model.Output;
import com.yash.calm.model.ProcessingPlant;

public class Main implements RequestHandler<S3Event, String> {

	private static AmazonS3 s3Client;
	private static final String OUTPUT_BUCKET = "raw-output-dpd2018";
	// private static final String attributesBucket = "raw-attributes-dpd2018";
	private static final String MASTER_BUCKET = "master-dpd2018";
	private static final String PLANTS_KEY = "processing_plant.json";
	private static final String MINES_KEY = "mine.json";
	private static final String OUTPUT_KEY = "output.json";
	// private static final String attributeKey = "atrributes.json";
	private static final String SEMI_BUCKET = "semi-transformed-dpd2018";
	private static final String REGION = "us-east-1";

	public static void main(String[] args) throws IOException {
		AWSCredentials credentials = new BasicAWSCredentials(Credentials.ACCESSKEY, Credentials.SECRETKEY);

		s3Client = AmazonS3ClientBuilder.standard().withRegion(REGION)
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

		S3Object output = s3Client.getObject(new GetObjectRequest(OUTPUT_BUCKET, OUTPUT_KEY));
		S3Object plants = s3Client.getObject(MASTER_BUCKET, PLANTS_KEY);
		S3Object mines = s3Client.getObject(MASTER_BUCKET, MINES_KEY);

		StringBuilder outputContent = getDataFromObject(output.getObjectContent());
		outputContent.setLength(outputContent.length()-1);
		StringBuilder plantsContent = getDataFromObject(plants.getObjectContent());
		StringBuilder minesContent = getDataFromObject(mines.getObjectContent());
		
		String outputContentString = "["+outputContent+"]";
		String plantsContentString = "["+plantsContent+"]";
		String minesContentString = "["+minesContent+"]";

		// S3Object attributes = s3Client.getObject(new
		// GetObjectRequest(attributesBucket, attributeKey));

		// StringBuilder outputContent = getDataFromObject(output.getObjectContent());
		// StringBuilder attributesContent =
		// getDataFromObject(attributes.getObjectContent());

		ObjectMapper mapper = new ObjectMapper();
		try {
			Output[] outputData = mapper.readValue(outputContentString, Output[].class);
			Mine[] mineData = mapper.readValue(minesContentString, Mine[].class);
			ProcessingPlant[] plantData = mapper.readValue(plantsContentString, ProcessingPlant[].class);
			EnrichedOutput[] enrichedOutput = enrichData(outputData, mineData, plantData);

			for (Output out : outputData) {
				System.out.println(out);
			}
			for (ProcessingPlant plant : plantData) {
				System.out.println(plant);
			}
			for (Mine mine : mineData) {
				System.out.println(mine);
			}
			for (EnrichedOutput eout : enrichedOutput) {
				System.out.println(eout);
			}
			// System.out.println(outputData);
			// System.out.println(mineData);
			// System.out.println(plantData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// File file = new File("semi.json");
		// mapper.writeValue(file, data);
		// s3Client.putObject(semiBucket, "semi.json", file);
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

	// public Object handleRequest(Object o, Context context) {
	// S3Event input = (S3Event) o;
	// for (S3EventNotification.S3EventNotificationRecord record :
	// input.getRecords()) {
	// String s3Key = record.getS3().getObject().getKey();
	// String s3Bucket = record.getS3().getBucket().getName();
	// // retrieve s3 object
	// S3Object object = s3Client.getObject(new GetObjectRequest(s3Bucket, s3Key));
	// //Read in stream data to string
	// InputStream objectData = object.getObjectContent();
	// BufferedReader streamReader = null;
	// try {
	// streamReader = new BufferedReader(new InputStreamReader(objectData,
	// "UTF-8"));
	// } catch (UnsupportedEncodingException e) {
	// e.printStackTrace();
	// }
	// StringBuilder responseStrBuilder = new StringBuilder();
	// String inputStr;
	// try{
	// while ((inputStr = streamReader.readLine()) != null)
	// responseStrBuilder.append(inputStr);
	// }
	// catch (Exception e){
	// e.printStackTrace();
	// }
	// //Map string to object
	// ObjectMapper mapper = new ObjectMapper();
	// MapType type = mapper.getTypeFactory().constructMapType(
	// Map.class, String.class, Object.class);
	// Map<String, Object> data = null;
	// try {
	// data = mapper.readValue(responseStrBuilder.toString(), type);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// System.out.println(data);
	//
	// }
	// return null;
	// }

	public String handleRequest(S3Event s3event, Context context) {
		// Set up access credentials
		AWSCredentials credentials = new BasicAWSCredentials(Credentials.ACCESSKEY, Credentials.SECRETKEY);
		// Set up s3client
		s3Client = AmazonS3ClientBuilder.standard().withRegion(REGION)
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

		// get the s3 even record
		S3EventNotificationRecord record = s3event.getRecords().get(0);

		// get S3 bucket and object key from record
		String s3Bucket = record.getS3().getBucket().getName();
		String s3Key = record.getS3().getObject().getKey();

		System.out.println("source bucket: " + s3Bucket);
		System.out.println("source key: " + s3Key);

		// Get S3 Objects
		S3Object output = s3Client.getObject(new GetObjectRequest(s3Bucket, s3Key));
		S3Object plants = s3Client.getObject(MASTER_BUCKET, PLANTS_KEY);
		S3Object mines = s3Client.getObject(MASTER_BUCKET, MINES_KEY);

		// Extract S3 Objects Data into StringBuilders
		StringBuilder outputContent = getDataFromObject(output.getObjectContent());
		StringBuilder plantsContent = getDataFromObject(plants.getObjectContent());
		StringBuilder minesContent = getDataFromObject(mines.getObjectContent());

		// Translate JSON into Object Arrays
		ObjectMapper mapper = new ObjectMapper();
		try {
			Output[] outputData = mapper.readValue(outputContent.toString(), Output[].class);
			Mine[] mineData = mapper.readValue(minesContent.toString(), Mine[].class);
			ProcessingPlant[] plantData = mapper.readValue(plantsContent.toString(), ProcessingPlant[].class);
			EnrichedOutput[] enrichedOutput = enrichData(outputData, mineData, plantData);

			for (Output out : outputData) {
				System.out.println(out);
			}
			for (ProcessingPlant plant : plantData) {
				System.out.println(plant);
			}
			for (Mine mine : mineData) {
				System.out.println(mine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static EnrichedOutput[] enrichData(Output[] outputData, Mine[] mineData, ProcessingPlant[] plantData) {
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

}
