package com.csipro.test.basic.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csipro.test.basic.entity.AccountEntity;
import com.csipro.test.basic.entity.CustomerEntity;
import com.csipro.test.basic.repository.CustomerRepository;
import com.csipro.test.basic.request.CustomerRequest;
import com.csipro.test.basic.response.AccountResponse;
import com.csipro.test.basic.response.CommonResponse;
import com.csipro.test.basic.response.CustomerResponse;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping()
	public CommonResponse getCustomerList() {
		List<CustomerResponse> customerList = new ArrayList<>();
		List<CustomerEntity> cutomerEntityList = customerRepository.findAll();
		if (cutomerEntityList != null) {
			for (CustomerEntity entity : cutomerEntityList) {
				customerList.add(convertCustomerEntityToCustomerRespose(entity));
			}
		}

		return new CommonResponse("SUCCESS", customerList);
	}

	@GetMapping("/{id}")
	public CommonResponse getCustomerById(@PathVariable("id") Integer id) {
		CustomerResponse customerResponse = new CustomerResponse();
		CustomerEntity customerEntity = customerRepository.getById(id);
		if (customerEntity != null) {
			customerResponse = convertCustomerEntityToCustomerRespose(customerEntity);
		}
		return new CommonResponse("SUCCESS", customerResponse);
	}

	@PostMapping()
    public CommonResponse createCustomer(@RequestBody CustomerRequest customerRequest) {
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setName(customerRequest.getName());
		customerEntity.setAddress(customerRequest.getAddress());
		customerEntity.setPhone(customerRequest.getPhone());
		customerEntity.setBirthday(customerRequest.getEmail());
		
		customerRepository.save(customerEntity);
		return new CommonResponse("SUCCESS", "account created.");
	}

	private CustomerResponse convertCustomerEntityToCustomerRespose(CustomerEntity entity) {
		CustomerResponse customer = new CustomerResponse();
		customer.setId(entity.getId());
		customer.setName(entity.getName());
		customer.setAddress(entity.getAddress());
		customer.setPhone(entity.getPhone());
		customer.setBirthday(entity.getBirthday());
		customer.setEmail(entity.getEmail());
		if (null != entity.getAccountEntity()) {
			customer.setAccount(convertAccountEntitytoAccountResponse(entity.getAccountEntity()));
		}
		
		return customer;
	}

	private AccountResponse convertAccountEntitytoAccountResponse(AccountEntity entity) {
		AccountResponse account = new AccountResponse();
		account.setId(entity.getId());
		account.setName(entity.getName());
		account.setAmount(entity.getAmount());
		account.setNumber(entity.getNumber());
		return account;
	}

}
