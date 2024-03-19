package com.example.demo.batchprocessing.schedule;

import com.example.demo.batchprocessing.config.BatchConfiguration;
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

import java.time.LocalDateTime;
import java.util.List;


@Component
@EnableScheduling
@Slf4j
public class BatchScheduler {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private BatchConfiguration batchConfiguration;

    @Autowired
    private ImportFileRepository importFileRepository;

    @Scheduled(fixedRate = 1000L)
    public void runJob() {
        List<ImportFile> importFiles = importFileRepository.findAll();

        importFiles.forEach(importFile -> {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("date", LocalDateTime.now().toString())
                    .toJobParameters();

            try {
                jobLauncher.run(batchConfiguration.importOrderJob(), jobParameters);
            } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                     | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {
                log.error(e.getMessage());
            }
        });



    }
}
