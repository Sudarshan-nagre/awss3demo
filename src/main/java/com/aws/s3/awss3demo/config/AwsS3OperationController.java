package com.aws.s3.awss3demo.config;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;

@RestController
@RequestMapping("/s3")
public class AwsS3OperationController {
	
	private Logger log = LoggerFactory.getLogger(AwsS3OperationController.class);
	
	@Autowired
	private AmazonS3 amazonS3Client;

	@PostMapping("/create")
	public String createS3Bucket(@RequestParam(name = "bucketName") String bucketName) {
	    if(amazonS3Client.doesBucketExistV2(bucketName)) {
	        log.info("Bucket name already in use. Try another name.");
	        return "BUCKET CREATION FAILED";
	    }
	    Bucket b = amazonS3Client.createBucket(bucketName);
	    return b.getName();
	}
	
	@GetMapping("/put")
	public void putObject(){
	    try {
	    	amazonS3Client.putObject("myownbuckettest2345", "abc.txt", new File("M:\\abc.txt"));
	    } catch (AmazonServiceException e) {
	        System.err.println(e.getErrorMessage());
	        System.exit(1);
	    }
	}
}
