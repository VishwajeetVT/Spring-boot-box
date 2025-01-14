package com.spring.mysql.service;

import com.spring.mysql.dto.CustomerRequest;
import com.spring.mysql.dto.CustomerResponse;
import com.spring.mysql.exception.CustomerNotFoundException;
import com.spring.mysql.model.Customer;
import com.spring.mysql.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse createCustomerAccount(CustomerRequest customerRequest) {
        Customer customer = customerRepository.save(CustomerRequest.toCustomer(customerRequest));
        return CustomerResponse.toCustomerResponse(customer);
    }

    @Override
    public List<CustomerResponse> findAllCustomer() {
        return customerRepository.findAll().stream()
                .map(CustomerResponse::toCustomerResponse)
                .toList();
    }

    @Override
    public CustomerResponse getCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException("Customer with id: "+id+" not found")
        );
        return CustomerResponse.toCustomerResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomerById(Integer id, CustomerRequest customerRequest) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException("Customer with id: "+id+" not found")
        );
        customer.setCustomerName(customer.getCustomerName());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhone(customerRequest.getPhone());
        customer.setCustomerAddress(customerRequest.getCustomerAddress());
        Customer updatedCustomerDetails = customerRepository.save(customer);
        return CustomerResponse.toCustomerResponse(updatedCustomerDetails);
    }

    @Override
    public void deleteCustomerById(Integer id) {
        if (!customerRepository.existsById(id)) {
            throw new IllegalArgumentException("Customer not found");
        }
        customerRepository.deleteById(id);
    }
}
