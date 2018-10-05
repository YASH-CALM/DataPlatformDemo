package com.yash.calm.streamsim.main;


import java.nio.ByteBuffer;
import java.util.ArrayList;
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
        
        // Populate Array of PUT Record Request Entries
        List <PutRecordsRequestEntry> putRecordsRequestEntryList  = new ArrayList<PutRecordsRequestEntry>(); 
        for (int i = 0; i < 100; i++) {
            PutRecordsRequestEntry putRecordsRequestEntry  = new PutRecordsRequestEntry();
            putRecordsRequestEntry.setData(ByteBuffer.wrap(String.valueOf(i).getBytes()));
            putRecordsRequestEntry.setPartitionKey(String.format("partitionKey-%d", i));
            putRecordsRequestEntryList.add(putRecordsRequestEntry); 
        }
        
        // Send records to AWS Kinesis
        putRecordsRequest.setRecords(putRecordsRequestEntryList);
        PutRecordsResult putRecordsResult  = kinesisClient.putRecords(putRecordsRequest);
        System.out.println("Put Result" + putRecordsResult);
	}
}
