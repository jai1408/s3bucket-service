package com.bugcode.s3bucket.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class S3BucketConfig {

  @Autowired PropertiesConfig propertiesConfig;

  @Bean
  public AmazonS3 getS3Client() {
    return buildS3Config();
  }

  private AmazonS3 buildS3Config() {
    System.setProperty("com.amazonaws.sdk.disableCertChecking", "true");
    StringBuilder accessKey = new StringBuilder(propertiesConfig.getAccessKey());
    accessKey.append('Q');
    StringBuilder secretKey = new StringBuilder(propertiesConfig.getSecretKey());
    secretKey.append('X');
    log.info("IAM User creds :: accessKey :: {}, secretKey :: {}", accessKey, secretKey);
    AWSCredentials credentials =
        new BasicAWSCredentials(accessKey.toString(), secretKey.toString());
    return AmazonS3ClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .withRegion(Regions.AP_SOUTH_1)
        .build();
  }
}
