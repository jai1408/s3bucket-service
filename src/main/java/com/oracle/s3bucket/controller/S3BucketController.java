package com.oracle.s3bucket.controller;

import com.oracle.s3bucket.model.S3Obj;
import com.oracle.s3bucket.service.S3BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/s3")
public class S3BucketController {

  @Autowired S3BucketService service;

  @GetMapping("/getMessage")
  public String getMessage() {
    return "started";
  }

    @GetMapping("/getFiles")
    public List<S3Obj> getFiles(){
      return service.listFiles();
    }

}
