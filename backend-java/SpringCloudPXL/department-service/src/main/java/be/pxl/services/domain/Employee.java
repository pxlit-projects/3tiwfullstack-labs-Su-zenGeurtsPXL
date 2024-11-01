package be.pxl.services.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private Long id;
    private Long organizationId;
    private String name;
    private int age;
    private String position;

}
