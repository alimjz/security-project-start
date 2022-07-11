package com.sematec.sematecprojectstart.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
@Table(name = "TBL_INDIVIDUAL_CUSTOMER",
indexes = {
        @Index(columnList = "CERTIFICATE_NO")
})
public class Customer {
    enum certificateType {NationalId,Passport,RefugeeCard}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID")
    private int id;
    @Column(name = "CERTIFICATE_TYPE")
//    @NotNull(message = "Certificate Type Cannot Be null or empty.")
//    @NotEmpty(message = "Certificate Type Cannot Be null or empty.")
    private certificateType certificateType;
    @Column(name = "CERTIFICATE_NO")
    @NotNull(message = "Certificate Number Cannot Be null or empty.")
    @NotEmpty(message = "Certificate Number Cannot Be null or empty.")
    private String certificateNumber;
    @Column(name = "NAME")
    private String name;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "BIRTH_DATE",columnDefinition = "DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
//    @NotNull(message = "BirthDate Cannot Be null or empty.")
//    @NotEmpty(message = "BirthDate Cannot Be null or empty.")
    @Past
    private LocalDate birthDate;
    @Column(name = "BIRTH_PLACE")
    @NotNull(message = "Birth Place Cannot Be null or empty.")
    @NotEmpty(message = "Birth Place Cannot Be null or empty.")
    private String birthPlace;

}
