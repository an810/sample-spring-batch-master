package com.example.demo.batchprocessing.job;

import com.example.demo.batchprocessing.entity.Order;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class OrderReader extends FlatFileItemReader<Order> {

    public OrderReader() {
        this.setName("orderItemReader");
//        this.setLinesToSkip(1);
        this.setLineMapper(new DefaultLineMapper<Order>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"CustomerId", "ItemId", "ItemPrice", "ItemName", "PurchaseDate"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Order>() {{
                setTargetType(Order.class);
            }});
        }});
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        String fileName = executionContext.getString("fileName");
        this.setResource(new ClassPathResource(fileName));
        super.open(executionContext);
    }
}