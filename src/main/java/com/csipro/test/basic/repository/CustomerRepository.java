package com.csipro.test.basic.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.csipro.test.basic.entity.CustomerEntity;

public interface CustomerRepository extends PagingAndSortingRepository<CustomerEntity, Integer>{
	public CustomerEntity getById(Integer id);
	public List<CustomerEntity> findAll();
}
