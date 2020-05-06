package com.awssample.costdatalake;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.support.AWSSupport;
import com.amazonaws.services.support.AWSSupportClientBuilder;
import com.amazonaws.services.support.model.DescribeTrustedAdvisorCheckResultRequest;
import com.amazonaws.services.support.model.DescribeTrustedAdvisorCheckResultResult;
import com.amazonaws.services.support.model.RefreshTrustedAdvisorCheckRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;


/**
 * Handler for requests to Lambda function.
 */
public class TrustedAdvisorFunction implements RequestHandler<Object, Object> {
    String bucketName = System.getenv("S3BUCKET");
    String accountnumber = System.getenv("ACCOUNTNO");
    ObjectMapper objectMapper = new ObjectMapper();
    AWSSupport awsSupport = AWSSupportClientBuilder.defaultClient();

    public Object handleRequest(final Object input, final Context context) {
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DAY_OF_MONTH) ;
        long unixTimestamp = Instant.now().getEpochSecond();

        StringBuffer output_string = new StringBuffer("accountnumber,scandate,region,checkname,resourceid,type,metadata,currentcost\n");
        var s3key = "year=" + year + "/month=" + month + "/date=" + date + "/ta.csv";

        TAChecks taChecks = new TAChecks();
        List<TAChecks> avalable = taChecks.getAvailableChecks();
        avalable.forEach((tc) -> {
            String desc = tc.getDescription();
            DescribeTrustedAdvisorCheckResultResult describeTrustedAdvisorCheckResultResult = awsSupport.describeTrustedAdvisorCheckResult(new DescribeTrustedAdvisorCheckResultRequest().withCheckId(tc.getId()));
            describeTrustedAdvisorCheckResultResult.getResult().getFlaggedResources().forEach(resource -> {
                System.out.println("Cost-->"+resource.getMetadata().get(tc.getCostMetaDataPosition()));
                System.out.println("Converting to-->"+resource.getMetadata().get(tc.getCostMetaDataPosition()).substring(1, resource.getMetadata().get(tc.getCostMetaDataPosition()).length()));

                output_string.append(accountnumber).append(",")
                        .append(unixTimestamp).append(",")
                        .append(resource.getMetadata().get(0)).append(",")
                        .append(desc).append(",")
                        .append(resource.getMetadata().get(1)).append(",")
                        .append(resource.getMetadata().get(tc.getTypeMetaDataPosition())).append(",")
                        .append(String.join(" ~ ", resource.getMetadata())).append(",")
                        .append(resource.getMetadata().get(tc.getCostMetaDataPosition()).substring(1, resource.getMetadata().get(tc.getCostMetaDataPosition()).length())).append("\n");

            });

        });

        //TODO : Figure out a way to stream in better way so as to avoid out of memory
        InputStream inputStream = new ByteArrayInputStream(output_string.toString().getBytes());
        AmazonS3 s3client = AmazonS3Client.builder().build();
        PutObjectRequest request = new PutObjectRequest(bucketName, s3key, inputStream, new ObjectMetadata());
        s3client.putObject(request);

        //Trigger a TA refresh. This will ensure that checks are ready for next run
        avalable.forEach((tc) -> {
            awsSupport.refreshTrustedAdvisorCheck(new RefreshTrustedAdvisorCheckRequest().withCheckId(tc.getId()));
        });

        return null;
    }

}
