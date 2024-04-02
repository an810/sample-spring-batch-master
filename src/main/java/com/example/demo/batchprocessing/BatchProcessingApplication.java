package com.example.demo.batchprocessing;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.demo.batchprocessing.config", "com.example.demo.batchprocessing.controller",
"com.example.demo.batchprocessing.step"})
public class BatchProcessingApplication {
	public static void main(String[] args) {
		SpringApplication.run(BatchProcessingApplication.class, args);
	}
}
