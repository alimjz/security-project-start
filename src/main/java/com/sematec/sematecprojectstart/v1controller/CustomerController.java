package com.sematec.sematecprojectstart.v1controller;

import com.sematec.sematecprojectstart.conf.Constants;
import com.sematec.sematecprojectstart.controller.ApiResponse;
import com.sematec.sematecprojectstart.dto.Customer;
import com.sematec.sematecprojectstart.repo.AddressRepository;
import com.sematec.sematecprojectstart.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController("RestControllerV1")
@RequestMapping("api/v1/customer")
@Validated
public class CustomerController {
    private CustomerRepository customerRepository;
    private AddressRepository addressRepository;

    @PostMapping("/operation/create")
    public ResponseEntity<ApiResponse> createCustomer(@RequestBody @Valid Customer customer){
        customerRepository.save(customer);
        HttpHeaders httpHeaders = new HttpHeaders();
        URI uri = ServletUriComponentsBuilder.fromUriString("api/v1/customer/queryCustomerInfo/").
                path("/{id}").
                buildAndExpand(customer.getId()).toUri();
        httpHeaders.set("CustomerId",String.valueOf(customer.getId()));
        httpHeaders.setLocation(uri);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setTimeStampe(new Timestamp(System.currentTimeMillis()).toString());
        apiResponse.setResultCode(Constants.SUCCESS);
        apiResponse.setResultDescription(Constants.SUCCESS_DESC);
        return new ResponseEntity<>(apiResponse,httpHeaders,HttpStatus.CREATED);
    }

    @GetMapping("/query/{id}")
    public ResponseEntity<ApiResponse> getCustomer(@PathVariable("id") int customerId){
        List<Customer> customer = customerRepository.findById(customerId);
        ApiResponse apiResponse = new ApiResponse();
        if (customer == null){
            throw new ResourceNotFoundException("Customer with Id " + customerId + " Not Found.");
        }
        apiResponse.setTimeStampe(new Timestamp(System.currentTimeMillis()).toString());
        apiResponse.setResultCode(Constants.SUCCESS);
        apiResponse.setResultDescription(Constants.SUCCESS_DESC);
        apiResponse.setCustomer(customer);
        return new ResponseEntity<>(apiResponse,null,HttpStatus.FOUND);
    }

    @GetMapping("/query")
    public ResponseEntity<ApiResponse> getAllCustomer(){
        List<Customer> customer = customerRepository.findAll();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setTimeStampe(new Timestamp(System.currentTimeMillis()).toString());
        apiResponse.setResultCode(Constants.SUCCESS);
        apiResponse.setResultDescription(Constants.SUCCESS_DESC);
        apiResponse.setCustomer(customer);
        return new ResponseEntity<>(apiResponse,null,HttpStatus.FOUND);
    }

    @DeleteMapping("/operation/delete/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable int customerId){
        List<Customer> customer = customerRepository.findById(customerId);
        if (customer == null){
            throw new ResourceNotFoundException("Customer with Id " + customerId + " Not Found.");
        }
        customerRepository.deleteById(customerId);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setTimeStampe(new Timestamp(System.currentTimeMillis()).toString());
        apiResponse.setResultCode(Constants.SUCCESS);
        apiResponse.setResultDescription(Constants.SUCCESS_DELETE);
        return new ResponseEntity<>(apiResponse,null,HttpStatus.OK);
    }
    @DeleteMapping("/operation/delete/")
    public ResponseEntity<ApiResponse> deleteAll(@PathVariable int customerId){
        customerRepository.deleteAll();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setTimeStampe(new Timestamp(System.currentTimeMillis()).toString());
        apiResponse.setResultCode(Constants.SUCCESS);
        apiResponse.setResultDescription(Constants.SUCCESS_DELETE);
        return new ResponseEntity<>(apiResponse,null,HttpStatus.OK);
    }

    @PatchMapping("/operation/update/{id}")
    public ResponseEntity<ApiResponse> updateCustomerInfo(@PathVariable Customer customer){
        if (customer == null){
            throw new ResourceNotFoundException("Customer with Id " + customer.getId() + " Not Found.");
        }
        List<Customer> oldCustomer = customerRepository.findById(customer.getId());
        oldCustomer.get(customer.getId()).setCertificateType(customer.getCertificateType());
        oldCustomer.get(customer.getId()).setCertificateNumber(customer.getCertificateNumber());
        oldCustomer.get(customer.getId()).setName(customer.getName());
        oldCustomer.get(customer.getId()).setLastName(customer.getLastName());
        oldCustomer.get(customer.getId()).setBirthDate(customer.getBirthDate());
        oldCustomer.get(customer.getId()).setBirthPlace(customer.getBirthPlace());
        customerRepository.save(oldCustomer.get(customer.getId()));
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setTimeStampe(new Timestamp(System.currentTimeMillis()).toString());
        apiResponse.setResultCode(Constants.SUCCESS);
        apiResponse.setResultDescription(Constants.SUCCESS_DELETE);
        return new ResponseEntity<>(apiResponse,null,HttpStatus.OK);
    }







    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @Autowired
    public void setAddressRepository(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
}
