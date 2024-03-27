package com.example.demo.batchprocessing.step;

import com.example.demo.batchprocessing.repository.ImportFileRepository;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.StepContribution;
import com.example.demo.batchprocessing.entity.ImportFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class AfterWritingTasklet implements Tasklet {

    private String fileName;
    @Autowired
    private ImportFileRepository importFileRepository;
    public AfterWritingTasklet(@Value("#{jobParameters['fileName']}") String fileName) {
        this.fileName = fileName;
    }
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ImportFile importedFile = new ImportFile();
        importedFile.setFileName(fileName);
        importFileRepository.delete(importedFile);
        return RepeatStatus.FINISHED;
    }
}
