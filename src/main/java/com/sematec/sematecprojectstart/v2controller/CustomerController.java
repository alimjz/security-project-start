package com.sematec.sematecprojectstart.v2controller;

import com.sematec.sematecprojectstart.conf.Constants;
import com.sematec.sematecprojectstart.controller.ApiResponse;
import com.sematec.sematecprojectstart.dto.Customer;
import com.sematec.sematecprojectstart.repo.AddressRepository;
import com.sematec.sematecprojectstart.repo.CustomerRepository;
import com.sematec.sematecprojectstart.security.User;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.builders.HttpAuthenticationBuilder;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController("RestControllerV2")
@RequestMapping("api/v2/customer")
@Validated
@Slf4j
public class CustomerController {
    private CustomerRepository customerRepository;
    private AddressRepository addressRepository;

    @Autowired
    private User user;

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
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<ApiResponse> getCustomer(@PathVariable("id") int customerId,
                                                   HttpServletRequest httpServletRequest){
        List<Customer> customer = customerRepository.findById(customerId);
        ApiResponse apiResponse = new ApiResponse();
        log.info("Initiate Response.");

        if (customer == null){
            throw new ResourceNotFoundException("Customer with Id " + customerId + " Not Found.");
        }
        log.info(httpServletRequest.getRemoteUser());
        if (!Objects.equals(httpServletRequest.getRemoteUser(), "admin")){
            customer.forEach(customer1 -> customer1.setCertificateNumber("Permission Not Enough."));
        }
        apiResponse.setTimeStampe(new Timestamp(System.currentTimeMillis()).toString());
        apiResponse.setResultCode(Constants.SUCCESS);
        apiResponse.setResultDescription(Constants.SUCCESS_DESC);
        apiResponse.setCustomer(customer);
        return new ResponseEntity<>(apiResponse,null,HttpStatus.FOUND);
    }

    @GetMapping("/query")
    public ResponseEntity<ApiResponse> getAllCustomer(HttpServletRequest httpServletRequest){
        List<Customer> customer = customerRepository.findAll();
        log.info(httpServletRequest.getRemoteUser());
        if (!Objects.equals(httpServletRequest.getRemoteUser(), "admin")){
            customer.forEach(customer1 -> customer1.setCertificateNumber("Permission Not Enough."));
        }
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
