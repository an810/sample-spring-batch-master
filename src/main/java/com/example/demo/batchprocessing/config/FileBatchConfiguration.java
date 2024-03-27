//package com.example.demo.batchprocessing.config;
//
//
//import com.example.demo.batchprocessing.entity.ImportFile;
//import com.example.demo.batchprocessing.repository.ImportFileRepository;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.data.RepositoryItemWriter;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//
//@Configuration
//@EnableBatchProcessing
//public class FileBatchConfiguration {
//    @Autowired
//    private JobBuilderFactory jobBuilderFactory;
//    @Autowired
//    private StepBuilderFactory stepBuilderFactory;
//    @Autowired
//    private ImportFileRepository importFileRepository;
//
//    @Bean
//    public ItemReader<ImportFile> reader() {
//        return new FlatFileItemReaderBuilder<ImportFile>()
//                .name("orderItemReader")
//                .resource(new ClassPathResource("order_list.csv"))
//                .delimited()
//                .names(new String[]{"FileName"})
//                .fieldSetMapper(new BeanWrapperFieldSetMapper<ImportFile>() {{
//                    setTargetType(ImportFile.class);
//                }})
//                .build();
//    }
//
//    @Bean
//    public ItemProcessor<ImportFile, ImportFile> processor() {
//        return importFile -> importFile;
//    }
//
//
//    @Bean
//    public ItemWriter<ImportFile> writer() {
//        RepositoryItemWriter<ImportFile> writer = new RepositoryItemWriter<>();
//        writer.setRepository(importFileRepository);
//        writer.setMethodName("save");
//        return writer;
//    }
//
//
//    @Bean
//    public Job importFileJob()
//    {
//        return jobBuilderFactory.get("importFileJob")
//                .incrementer(new RunIdIncrementer())
//                .listener(new JobCompletionNotificationListener())
//                .flow(step())
//                .end()
//                .build();
//    }
//
//    @Bean
//    public Step step() {
//        return stepBuilderFactory.get("step1")
//                .<ImportFile, ImportFile> chunk(10)
//                .reader(reader())
//                .processor(processor())
//                .writer(writer())
//                .build();
//    }
//
//
//}
