package com.example.demo.batchprocessing.step;

import com.example.demo.batchprocessing.entity.Order;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import org.springframework.batch.core.step.tasklet.Tasklet;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataProcessingTasklet implements Tasklet {

    public static List<Order> processedOrders = new ArrayList<>();

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        for (String line : FileReadingTasklet.lines) {
            // Process the line here
            Order processedOrder = processLine(line);
            processedOrders.add(processedOrder);
        }
        return RepeatStatus.FINISHED;
    }

    private Order processLine(String line) {
        // Implement your line processing logic here
        // For example, split the line by comma and map the fields to an Order object
        String[] fields = line.split(",");
        Order order = new Order();
        order.setItemId(Integer.parseInt(fields[0]));
        order.setCustomerId(Integer.parseInt(fields[1]));
        order.setItemPrice(Integer.parseInt(fields[2]));
        order.setItemName(fields[3]);
        order.setPurchaseDate(fields[4]);
        return order;
    }
}