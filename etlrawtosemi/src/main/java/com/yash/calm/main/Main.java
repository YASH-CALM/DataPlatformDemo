package com.yash.calm.main;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;

import java.io.*;
import java.util.Map;

public class Main implements RequestHandler<Object, Object> {

    private static AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
    private static String bucketName = "raw-dpd2018";
    private static String key = "dummy.json";

    public static void main(String[] args) throws IOException {
        S3Object object = s3Client.getObject(new GetObjectRequest(bucketName, key));
        InputStream objectData = object.getObjectContent();

        BufferedReader streamReader = null;
        try {
            streamReader = new BufferedReader(new InputStreamReader(objectData, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);
        ObjectMapper mapper = new ObjectMapper();
        MapType type = mapper.getTypeFactory().constructMapType(
                Map.class, String.class, Object.class);
        Map<String, Object> data = mapper.readValue(responseStrBuilder.toString(), type);
        System.out.println(data);

//        ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName).withMaxKeys(2);
//        ListObjectsV2Result result;
//
//        result = s3Client.listObjectsV2(req);
//
//        for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
//            System.out.println(objectSummary.getKey());
//        }
    }

    public Object handleRequest(Object o, Context context) {
        S3Event input = (S3Event) o;
        for (S3EventNotification.S3EventNotificationRecord record : input.getRecords()) {
            String s3Key = record.getS3().getObject().getKey();
            String s3Bucket = record.getS3().getBucket().getName();
            // retrieve s3 object
            S3Object object = s3Client.getObject(new GetObjectRequest(s3Bucket, s3Key));
            //Read in stream data to string
            InputStream objectData = object.getObjectContent();
            BufferedReader streamReader = null;
            try {
                streamReader = new BufferedReader(new InputStreamReader(objectData, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            try{
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            //Map string to object
            ObjectMapper mapper = new ObjectMapper();
            MapType type = mapper.getTypeFactory().constructMapType(
                    Map.class, String.class, Object.class);
            Map<String, Object> data = null;
            try {
                data = mapper.readValue(responseStrBuilder.toString(), type);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(data);

        }
        return null;
    }

}
