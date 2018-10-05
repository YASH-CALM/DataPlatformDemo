package com.yash.calm.streamsim.main;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.yash.calm.streamsim.encryption.Credentials;

public class StreamingApp {
	public static void main(String[] args) {
		// Set credentials
		AWSCredentials credentials = new BasicAWSCredentials(
				Credentials.ACCESS_KEY, 
				Credentials.SECRET_KEY);

		AmazonKinesis kinesisClient = AmazonKinesisClientBuilder.standard()
		 		.withCredentials(new AWSStaticCredentialsProvider(credentials))
		 		.build();
		
	}
}
