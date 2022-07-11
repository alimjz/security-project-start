package com.sematec.sematecprojectstart.repo;

import com.sematec.sematecprojectstart.security.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String username);
}
