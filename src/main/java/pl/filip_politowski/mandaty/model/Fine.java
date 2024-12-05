package pl.filip_politowski.mandaty.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;



@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fine")
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "employee_type")
    private EmployeeType employeeType;
    @Column(name ="signature")
    private String signature;
    @Column(name = "violation_date")
    private LocalDate violationDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "violation_reason")
    private ViolationReason violationReason;
    @Column(name = "amount")
    private Double amount;
    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;
    @Column(name = "administrative_fee")
    private Double administrativeFee = 100.00;
    @Column(name = "pdf")
    private String pdf;
    @Column(name = "payment_deadline")
    private LocalDate paymentDeadline;
    @Column(name = "fine_status")
    @Enumerated(EnumType.STRING)
    private FineStatus fineStatus = FineStatus.UNPAID;
}
