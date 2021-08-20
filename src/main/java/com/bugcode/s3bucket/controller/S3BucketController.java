package com.bugcode.s3bucket.controller;

import com.amazonaws.services.s3.model.Bucket;
import com.bugcode.s3bucket.model.S3Obj;
import com.bugcode.s3bucket.service.S3BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/s3")
public class S3BucketController {

  @Autowired
  S3BucketService service;

  @GetMapping("/getMessage")
  public String getMessage() {
    return "started";
  }

  @GetMapping("/getFiles")
  public List<S3Obj> getFiles() {
    return service.listFiles();
  }

  @PostMapping("/upload")
  public String upload(@RequestHeader String localPath) {
    return service.upload(localPath);
  }

  @GetMapping("/download")
  public List<String> getSongs(@RequestParam String fileName, @RequestParam String type) {
    return service.download(fileName, type);
  }

  @GetMapping("/create")
  public String createBucket(@RequestParam String bucketName) {
    return service.createBucket(bucketName);
  }

  @GetMapping("/getBuckets")
  public List<Bucket> getBuckets() {
    return service.getBuckets();
  }
}
