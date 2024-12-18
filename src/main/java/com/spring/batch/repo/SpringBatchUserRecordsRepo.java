package com.spring.batch.repo;


import com.spring.batch.model.SpringBatchUserRecords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringBatchUserRecordsRepo extends JpaRepository<SpringBatchUserRecords, Integer> {
}
