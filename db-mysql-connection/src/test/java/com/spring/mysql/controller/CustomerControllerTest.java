package com.spring.mysql.controller;

import com.spring.mysql.dto.CustomerRequest;
import com.spring.mysql.dto.CustomerResponse;
import com.spring.mysql.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private CustomerRequest customerRequest;
    private CustomerResponse customerResponse;

    @BeforeEach
    public void setup() {
        customerRequest = new CustomerRequest("John Doe","john.doe@springbox.com","9876543210", "Somewhere");
        customerResponse = new CustomerResponse(1, "John Doe", "john.doe@springbox.com","9876543210","Somewhere");
    }
}