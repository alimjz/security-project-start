package com.sematec.sematecprojectstart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
@Table(name = "TBL_ADDRESS",
        indexes = {
                @Index(columnList = "POSTAL_CODE")
        })
public class CustomerAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESS_ID")
    private int addressId;
    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customerId;
    @Column(name = "PROVINCE")
    private String province;
    @Column(name = "CITY")
    private String city;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "POSTAL_CODE")
    private String postalCode;
}
