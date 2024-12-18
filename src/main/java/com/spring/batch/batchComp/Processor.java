package com.spring.batch.batchComp;

import com.spring.batch.model.SpringBatchUserRecords;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component

public class Processor implements ItemProcessor<SpringBatchUserRecords, SpringBatchUserRecords> {
    private static final Map<String, String> DEPT_NAMES =
            new HashMap<>();

    public Processor() {
        DEPT_NAMES.put("001", "Technology");
        DEPT_NAMES.put("002", "Operations");
        DEPT_NAMES.put("003", "Accounts");
    }

    @Override
    public SpringBatchUserRecords process(SpringBatchUserRecords springBatchUserRecords) throws Exception {
        String deptCode = springBatchUserRecords.getDept();
        String dept = DEPT_NAMES.get(deptCode);
        springBatchUserRecords.setDept(dept);
        springBatchUserRecords.setTime(new Date());
        System.out.println(String.format("Converted from [%s] to [%s]", deptCode, dept));
        return springBatchUserRecords;
    }
}
