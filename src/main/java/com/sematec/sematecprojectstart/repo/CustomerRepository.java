package com.sematec.sematecprojectstart.repo;

import com.sematec.sematecprojectstart.dto.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Integer> {
    List<Customer> findById(int customerId);
    List<Customer> findAll();
}
