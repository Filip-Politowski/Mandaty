package pl.filip_politowski.mandaty.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Enumerated(EnumType.STRING)
    @Column(name = "employee_type")
    private EmployeeType employeeType;
    @Enumerated(EnumType.STRING)
    @Column(name = "job_status")
    private JobStatus jobStatus;
    @Column(name="company_name")
    private String companyName;
}
