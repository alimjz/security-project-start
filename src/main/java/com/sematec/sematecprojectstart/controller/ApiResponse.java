package com.sematec.sematecprojectstart.controller;

import com.sematec.sematecprojectstart.dto.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
public class ApiResponse {
    private String resultCode;
    private String resultDescription;
    private String timeStampe;
    private List<Customer> customer;
}
