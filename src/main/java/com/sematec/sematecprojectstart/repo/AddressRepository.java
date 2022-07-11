package com.sematec.sematecprojectstart.repo;

import com.sematec.sematecprojectstart.dto.CustomerAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<CustomerAddress,Integer> {
}
