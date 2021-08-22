package com.bugcode.s3bucket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class S3bucketServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(S3bucketServiceApplication.class, args);
	}

}
