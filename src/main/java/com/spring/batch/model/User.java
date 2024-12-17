package com.spring.batch.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class User {
    @Id
    private Integer id;
    private String name;
    private String dept;
    private Integer salary;
    private Date time;
}
