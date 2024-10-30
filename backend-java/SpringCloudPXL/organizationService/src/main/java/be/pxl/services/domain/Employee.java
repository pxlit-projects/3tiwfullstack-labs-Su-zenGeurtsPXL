package be.pxl.services.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    private Organization organization;

}