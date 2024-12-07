package pl.filip_politowski.mandaty.mapper;

import org.springframework.stereotype.Component;
import pl.filip_politowski.mandaty.dto.request.FineRequest;
import pl.filip_politowski.mandaty.dto.response.FineResponse;
import pl.filip_politowski.mandaty.model.Fine;

@Component
public class FineMapper {
    public Fine toFine(FineRequest fineRequest) {
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

    public FineResponse toFineResponse(Fine fine) {
        if (fine == null) {
            throw new NullPointerException("Fine is null");
        }
        return FineResponse.builder()
                .id(fine.getId())
                .firstName(fine.getEmployee().getFirstName())
                .lastName(fine.getEmployee().getLastName())
                .companyName(fine.getEmployee().getCompanyName())
                .phoneNumber(fine.getEmployee().getPhoneNumber())
                .fineSignature(fine.getSignature())
                .violationDate(fine.getViolationDate())
                .violationReason(String.valueOf(fine.getViolationReason()))
                .customViolationReason(fine.getCustomViolationReason())
                .amount(fine.getAmount())
                .administrationFee(fine.getAdministrativeFee())
                .currency(fine.getCurrency())
                .employeeType(fine.getEmployee().getEmployeeType())
                .paymentDeadline(fine.getPaymentDeadline())
                .fineStatus(fine.getFineStatus())
                .pdf(fine.getPdf())
                .build();
    }
}
