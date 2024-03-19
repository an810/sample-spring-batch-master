package com.example.demo.batchprocessing.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orders")
@Getter
@Setter
public class Order {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private Integer customerId;

	private Integer itemId;
	
	private String itemName;

	private Integer itemPrice;
	
	private String purchaseDate;
}

