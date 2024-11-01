package be.pxl.services.domain;

import jakarta.persistence.*;

import lombok.*;

import java.util.List;

@Entity
@Table(name ="department")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long organizationId;
    private String name;

    @Transient
    private List<Employee> employees;
    private String position;

}