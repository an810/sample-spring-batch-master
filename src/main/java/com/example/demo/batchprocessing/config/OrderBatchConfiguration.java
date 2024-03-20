//package com.example.demo.batchprocessing.config;
//
//import com.example.demo.batchprocessing.repository.OrderRepository;
//import com.example.demo.batchprocessing.entity.Order;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.*;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.data.RepositoryItemWriter;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.retry.annotation.Backoff;
//import org.springframework.retry.annotation.EnableRetry;
//import org.springframework.retry.annotation.Retryable;
//
//@EnableRetry
//@Configuration
//@EnableBatchProcessing
//public class OrderBatchConfiguration {
//
//	@Autowired
//	public JobBuilderFactory jobBuilderFactory;
//
//	@Autowired
//	public StepBuilderFactory stepBuilderFactory;
//
//	@Autowired
//	private OrderRepository orderRepository;
//
//	@Bean
//	@StepScope
//	public ItemReader<Order> reader(@Value("#{jobParameters['fileName']}") String fileName) {
//		return new FlatFileItemReaderBuilder<Order>()
//			.name("orderItemReader")
//			.resource(new ClassPathResource(fileName))
//			.delimited()
//			.names(new String[] {"CustomerId", "ItemId", "ItemPrice", "ItemName", "PurchaseDate"})
//			.fieldSetMapper(new BeanWrapperFieldSetMapper<Order>() {{
//				setTargetType(Order.class);
//			}})
//			.build();
//	}
//
//	@Bean
//	@StepScope
//	public ItemProcessor<Order, Order> processor() {
//		return new ItemProcessor<Order, Order>() {
//
//			@Override
//			public Order process(final Order order) throws Exception {
//				return order;
//			}
//		};
//	}
//
//
//	@Bean
//	@StepScope
//	public ItemWriter<Order> writer() {
//		RepositoryItemWriter<Order> writer = new RepositoryItemWriter<>();
//		writer.setRepository(orderRepository);
//		writer.setMethodName("save");
//		return writer;
//	}
//
//
//	@Bean
//	public Job importOrderJob()
//	{
//		return jobBuilderFactory.get("importOrderJob")
//			.incrementer(new RunIdIncrementer())
//			.listener(new JobCompletionNotificationListener())
//			.flow(step1())
//			.end()
//			.build();
//	}
//
//	@Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
//	@Bean
//	public Step step1() {
//	  return stepBuilderFactory.get("step1")
//		  .<Order, Order> chunk(10)
//		  .reader(reader("order_1.csv"))
//		  .processor(processor())
//		  .writer(writer())
//		  .faultTolerant()
//		  .retryLimit(3)
//		  .retry(Exception.class)
//		  .build();
//	}
//}
