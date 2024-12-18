package com.spring.batch.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DAOUserRepo extends CrudRepository<com.spring.batch.model.DAOUser, Integer> {
    com.spring.batch.model.DAOUser findByUsername(String username);
}
