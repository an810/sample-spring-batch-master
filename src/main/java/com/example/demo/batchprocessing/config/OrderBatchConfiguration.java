package com.example.demo.batchprocessing.config;

import com.example.demo.batchprocessing.step.AfterWritingTasklet;
import com.example.demo.batchprocessing.step.DataProcessingTasklet;
import com.example.demo.batchprocessing.step.DatabaseWritingTasklet;
import com.example.demo.batchprocessing.step.FileReadingTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class OrderBatchConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private FileReadingTasklet fileReadingTasklet;
    @Autowired
    private DataProcessingTasklet dataProcessingTasklet;
    @Autowired
    private DatabaseWritingTasklet databaseWritingTasklet;
    @Autowired
    private AfterWritingTasklet afterWritingTasklet;

    @Bean
    public Job importOrderJob() {
        return jobBuilderFactory.get("importOrderJob")
                .incrementer(new RunIdIncrementer())
                .start(fileReadingStep())
                .next(dataProcessingStep())
                .next(databaseWritingStep())
                .next(afterWritingStep())
                .build();
    }

    @Bean
    public Step fileReadingStep() {
        return stepBuilderFactory.get("fileReadingStep")
            .tasklet(fileReadingTasklet)
            .build();
    }

    @Bean
    public Step dataProcessingStep() {
        return stepBuilderFactory.get("dataProcessingStep")
            .tasklet(dataProcessingTasklet)
            .build();
    }

    @Bean
    public Step databaseWritingStep() {
        return stepBuilderFactory.get("databaseWritingStep")
            .tasklet(databaseWritingTasklet)
            .build();
    }

    @Bean
    public Step afterWritingStep() {
        return stepBuilderFactory.get("afterWritingStep")
            .tasklet(afterWritingTasklet)
            .build();
    }
}
