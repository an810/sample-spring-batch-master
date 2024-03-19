package com.example.demo.batchprocessing.repository;

import com.example.demo.batchprocessing.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
	
}
