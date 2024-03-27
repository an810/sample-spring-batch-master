package com.example.demo.batchprocessing.step;

import com.example.demo.batchprocessing.entity.Order;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.batchprocessing.repository.OrderRepository;

@Component
public class DatabaseWritingTasklet implements Tasklet {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        for (Order order : DataProcessingTasklet.processedOrders) {
            writeToDatabase(order);
        }
        return RepeatStatus.FINISHED;
    }

    private void writeToDatabase(Order order) {
        orderRepository.save(order);
    }
}