package com.example.demo.batchprocessing.controller;

import com.example.demo.batchprocessing.config.OrderBatchConfiguration;
import com.example.demo.batchprocessing.entity.ImportFile;
import com.example.demo.batchprocessing.repository.ImportFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@EnableScheduling
@Slf4j
public class OrderBatchScheduler {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private OrderBatchConfiguration orderBatchConfiguration;

    @Autowired
    private ImportFileRepository importFileRepository;

    @Scheduled(fixedRate = 1000L)
    public void runJob() {
        List<ImportFile> importFiles = importFileRepository.findAll();
        log.info("Import files found: {}", importFiles.size());

        importFiles.forEach(importFile -> {
            String fileName = importFile.getFileName();
            if (fileName != null) {
                JobParameters jobParameters = new JobParametersBuilder()
                        .addString("fileName", importFile.getFileName())
                        .toJobParameters();
                log.info("Processing import file {}", fileName);
                try {
                    jobLauncher.run(orderBatchConfiguration.importOrderJob(), jobParameters);
                    log.info("Import file {} has been processed", fileName);
                } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                         | JobParametersInvalidException |
                         org.springframework.batch.core.repository.JobRestartException e) {
                    log.error(e.getMessage());
                }
            } else {
                log.error("Filename is null for import file");
            }
//            importFileRepository.delete(importFile);
        });

    }
}
