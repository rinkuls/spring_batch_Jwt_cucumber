package com.spring.batch.batchComp;

import com.spring.batch.model.SpringBatchUserRecords;
import com.spring.batch.repo.SpringBatchUserRecordsRepo;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DBWriter implements ItemWriter<SpringBatchUserRecords> {
    private SpringBatchUserRecordsRepo springBatchUserRecordsRepo;

    @Override
    public void write(List<? extends SpringBatchUserRecords> users) throws Exception {
        System.out.println("Data Saved for Users: " + users);
        springBatchUserRecordsRepo.saveAll(users);

    }
}
