package com.labasterasign.upsign.repository;

import com.labasterasign.upsign.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepo extends MongoRepository<User, String> {
    User findByUsername(String username);

    boolean existsByUsername(String username);

}