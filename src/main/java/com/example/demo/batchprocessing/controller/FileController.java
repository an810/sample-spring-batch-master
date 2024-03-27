//package com.example.demo.batchprocessing.controller;
//
//import com.example.demo.batchprocessing.config.FileBatchConfiguration;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api")
//public class FileController {
//    @Autowired
//    private FileBatchConfiguration fileBatchConfiguration;
//    @Autowired
//    private JobLauncher jobLauncher;
//    @PostMapping("/file")
//    public void uploadFile() {
//        try {
//            jobLauncher.run(fileBatchConfiguration.importFileJob(), new JobParameters());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
