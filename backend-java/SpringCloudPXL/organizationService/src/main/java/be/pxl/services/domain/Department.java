package be.pxl.services.domain;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    private Long id;
    private String name;
    private List<Employee> employees;
    private String position;

}