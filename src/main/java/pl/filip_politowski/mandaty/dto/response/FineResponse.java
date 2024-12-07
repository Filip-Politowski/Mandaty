package pl.filip_politowski.mandaty.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.filip_politowski.mandaty.model.Currency;
import pl.filip_politowski.mandaty.model.EmployeeType;
import pl.filip_politowski.mandaty.model.FineStatus;
import pl.filip_politowski.mandaty.model.ViolationReason;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FineResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String companyName;
    private String phoneNumber;
    private String fineSignature;
    private LocalDate violationDate;
    private String violationReason;
    private String customViolationReason;
    private Double amount;
    private Double administrationFee;
    private Currency currency;
    private EmployeeType employeeType;
    private LocalDate paymentDeadline;
    private FineStatus fineStatus;
    private String pdf;



}
