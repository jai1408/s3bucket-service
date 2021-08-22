package com.bugcode.s3bucket.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Configuration
@RefreshScope
public class PropertiesConfig {

  @Value("${s3Bucket.name}")
  private String bucketName;

  @Value("${s3Bucket.accessKey}")
  private String accessKey;

  @Value("${s3Bucket.secretKey}")
  private String secretKey;
}
