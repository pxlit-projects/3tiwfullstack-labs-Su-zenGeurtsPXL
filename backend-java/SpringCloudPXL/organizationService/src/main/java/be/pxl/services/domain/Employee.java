package be.pxl.services.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private Long id;
    private Long departmentId;
    private String name;
    private int age;
    private String position;

}