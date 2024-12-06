package pl.filip_politowski.mandaty.mapper;

import org.springframework.stereotype.Component;
import pl.filip_politowski.mandaty.dto.request.FineRequest;
import pl.filip_politowski.mandaty.model.Fine;

@Component
public class FineMapper {
    public Fine toFine (FineRequest fineRequest) {
        if (fineRequest == null) {
            throw new NullPointerException("Fine request is null");
        }


        return Fine.builder()
                .employeeType(fineRequest.getEmployeeType())
                .violationDate(fineRequest.getViolationDate())
                .violationReason(fineRequest.getViolationReason())
                .customViolationReason(fineRequest.getCustomViolationReason())
                .amount(fineRequest.getAmount())
                .currency(fineRequest.getCurrency())
                .pdf(fineRequest.getPdf())
                .paymentDeadline(fineRequest.getPaymentDeadline())
                .build();
    }
}
