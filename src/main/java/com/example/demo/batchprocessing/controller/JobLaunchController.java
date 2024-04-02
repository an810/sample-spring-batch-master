package com.example.demo.batchprocessing.controller;

import com.example.demo.batchprocessing.entity.ImportFile;
import com.example.demo.batchprocessing.repository.ImportFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class JobLaunchController {

    @Qualifier("importOrderJob")
    @Autowired
    private Job importOrderJob;

    @Qualifier("importFileJob")
    @Autowired
    private Job importFileJob;

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private ImportFileRepository importFileRepository;

    @GetMapping("/launchFileJob/{id}")
    public void launchFileJob(@PathVariable("id") String id) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("param", id)
                .toJobParameters();
        jobLauncher.run(importFileJob, jobParameters);
    }

    @GetMapping("/launchOrderJob/{id}")
    public void launchOrderJob(@PathVariable("id") String id) throws Exception {
        List<ImportFile> importFiles = importFileRepository.findAll();

        importFiles.forEach(importFile -> {
            String fileName = importFile.getFileName();
            if (fileName != null) {
                JobParameters jobParameters = new JobParametersBuilder()
                        .addString("fileName", importFile.getFileName())
                        .addString("param", id)
                        .toJobParameters();
                log.info("Processing import file {}", fileName);
                try {
                    jobLauncher.run(importOrderJob, jobParameters);
                    log.info("Import file {} has been processed", fileName);
                } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                         | JobParametersInvalidException |
                         org.springframework.batch.core.repository.JobRestartException e) {
                    log.error(e.getMessage());
                }
            } else {
                log.error("Filename is null for import file");
            }
            importFileRepository.delete(importFile);
        });
    }


}
