package pl.filip_politowski.mandaty.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.filip_politowski.mandaty.model.Currency;
import pl.filip_politowski.mandaty.model.EmployeeType;
import pl.filip_politowski.mandaty.model.ViolationReason;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FineRequest {
    private EmployeeType employeeType;
    private LocalDate violationDate;
    private ViolationReason violationReason;
    private String customViolationReason;
    private Double amount;
    private Currency currency;
    private String pdf;
    private LocalDate paymentDeadline;
    private String firstName;
    private String lastName;
    private Long employeeId;
}
